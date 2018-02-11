package dao.daojdbc;

import dao.daointerfaces.DepartmentDAO;


import jdbcutil.DBUtil;
import offices.Department;
import offices.Identificateble;
import offices.Office;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;

import java.util.Map;

public class DepartmentDaoJDBC extends MetamodelDao implements DepartmentDAO {

    public DepartmentDaoJDBC( Connection connection){
        super (connection);

        try {
            typesId = isTypesExistInTable(Department.class.getCanonicalName());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }

    public DepartmentDaoJDBC( Connection connection, boolean withCommit){
        super (connection, withCommit);

        try {
            typesId = isTypesExistInTable(Department.class.getCanonicalName());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }



    public List<Department> getAllDepartments() {
        return null;
    }

    public Department getDepartment(String id) {
        return (Department)getObject(id);
    }

    @Override
    protected Identificateble getConstructedObject(Map<String, String> params, String id) {
        String name = params.get("name");
        return new Department(name, new OfficeDaoJDBC(connection, false).getOffice(params.get("office")), id);
    }

    public void updateDepartment(Department department) {

    }

    public void deleteDepartment(Department department) {

    }

    public void addDepartment(Department department) {
        addObject(department, Department.class);
    }

    protected void insertAllIntoParams(Identificateble obj) throws SQLException{
        Department department = (Department)obj;

        Map<String, String> map = getAttrIds(typesId);

        insertIntoParams(department.getName(), map.get("name"), department.getId(), false);

        //if Office object doesnt exist in table OBJECTS, then add the object
        if(!isObjectExistInTable(department.getOffice().getId())){
            new OfficeDaoJDBC(connection, false).addOffice(department.getOffice());
        }
        insertIntoParams(department.getOffice().getId(), map.get("office"), department.getId(), true);
    }


}
