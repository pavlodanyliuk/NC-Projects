package dao.daojdbc;

import dao.daointerfaces.DepartmentDAO;


import jdbcutil.DBUtil;
import offices.Department;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;

import java.util.Map;

public class DepartmentDaoJDBC extends MetamodelDao implements DepartmentDAO {
    private String deptId;

    public DepartmentDaoJDBC( Connection connection){
        super (connection);

        try {
            deptId = isTypesExistInTable(Department.class.getCanonicalName());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }

    public List<Department> getAllDepartments() {
        return null;
    }

    public Department getDepartment(int id) {
        return null;
    }

    public void updateDepartment(Department department) {

    }

    public void deleteDepartment(Department department) {

    }

    public void addDepartment(Department department) {
        addObject(department.getId(), department);
    }

    protected void addLogic(Object obj) throws SQLException {
        Department department = (Department)obj;

        //if Class is in Types, this means that class attributes are in Attributes
        if (deptId == null){
            deptId = insertInTypesAndAttributes(Department.class);
        }

        insertIntoObjects(department.getId(), deptId);

        insertAllIntoParams(department);


    }

    private void insertAllIntoParams(Department department) throws SQLException{
        Map<String, String> map = getAttrIds(deptId);

        insertIntoParams(department.getName(), map.get("name"), department.getId(), false);

        //if Office object doesnt exist in table OBJECTS, then add the object
//        if(!isObjectExistInTable(department.getOffice().getId())){
//            new OfficeDaoJDBC(connection).addOffice(department.getOffice());
//        }
//        insertIntoParams(department.getOffice().getId(), map.get("office"), department.getId(), true);
    }


}
