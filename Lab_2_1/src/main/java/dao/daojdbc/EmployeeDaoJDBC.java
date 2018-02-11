package dao.daojdbc;

import dao.daointerfaces.EmployeeDAO;
import jdbcutil.DBUtil;
import offices.Employee;
import offices.Identificateble;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EmployeeDaoJDBC extends MetamodelDao implements EmployeeDAO {


    public EmployeeDaoJDBC(Connection connection) {
        super(connection);

        try{
            typesId = isTypesExistInTable(Employee.class.getCanonicalName());
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtil.showErrorMessage(e);
        }
    }

    public EmployeeDaoJDBC(Connection connection, boolean withCommit) {
        super(connection, withCommit);
        try{
            typesId = isTypesExistInTable(Employee.class.getCanonicalName());
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtil.showErrorMessage(e);
        }
    }

    public List<Employee> getAllEmployes() {
        return null;
    }

    @Override
    public Employee getEmployee(String id) {
        return (Employee)getObject(id);
    }

    @Override
    protected Identificateble getConstructedObject(Map<String, String> map, String id) {
        return new Employee(map.get("firstName"), map.get("lastName"), new Integer(map.get("age")), map.get("address"), new DepartmentDaoJDBC(connection, false).getDepartment(map.get("department")), id);
    }

    public void updateEmployee(Employee employee) {

    }

    public void deleteEmployee(Employee employee) {

    }

    public void addEmployee(Employee employee) {
        addObject(employee, Employee.class);
    }

    @Override
    protected void insertAllIntoParams(Identificateble obj) throws SQLException {
        Employee employee = (Employee)obj;

        Map<String, String> map = getAttrIds(typesId);

        if(!isObjectExistInTable(employee.getDepartment().getId())){
            new DepartmentDaoJDBC(connection).addDepartment(employee.getDepartment());
        }
        insertIntoParams(employee.getDepartment().getId(), map.get("department"), employee.getId(), true);
        insertIntoParams(employee.getFirstName(), map.get("firstName"), employee.getId(), false);
        insertIntoParams(employee.getAge(), map.get("age"), employee.getId());
        insertIntoParams(employee.getLastName(), map.get("lastName"), employee.getId(), false);
        insertIntoParams(employee.getAddress(), map.get("address"), employee.getId(), false);
    }
}
