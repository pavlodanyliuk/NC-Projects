package dao.daojdbc;

import dao.daointerfaces.DepartmentDAO;
import executor.Executor;
import executor.ResultHandler;
import jdbcutil.DBUtil;
import offices.Department;
import offices.generator.GeneratorId;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDAO {
    private final Executor executor;

    public DepartmentDaoJDBC( Connection connection){
        executor = new Executor(connection);
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

        try {
            if (isDepartmentExistInTable() == null){
                insertInTypes(department);
            }
            else{
                System.out.println("Department exists");
            }

        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }

    }

    private void insertInTypes(Department department) throws SQLException {
        int row;
        String insert = String.format("INSERT INTO TYPES (ID, NAME) VALUES ('%s', '%s')",
                GeneratorId.generateUniqId(), department.getClass().getCanonicalName());

        row = executor.execUpdate(insert);

        System.out.println(row + " updated...");

    }

    private String isDepartmentExistInTable() throws SQLException{
        String query = String.format("SELECT TYPES.ID, TYPES.NAME FROM TYPES WHERE TYPES.NAME='%s'", Department.class.getCanonicalName());
        String id = executor.execQuery(
                query,
                resultSet -> {
                    if(!resultSet.next()) return null;
                    return resultSet.getString("ID");
                });

        return id;
    }

}
