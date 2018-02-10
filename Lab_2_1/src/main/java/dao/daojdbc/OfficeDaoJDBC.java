package dao.daojdbc;

import dao.daointerfaces.OfficeDAO;
import executor.Executor;
import jdbcutil.DBUtil;
import offices.Office;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OfficeDaoJDBC extends MetamodelDao implements OfficeDAO {
    private String officeId;

    public OfficeDaoJDBC(Connection connection){
        super(connection);

        try {
            officeId = isTypesExistInTable(Office.class.getCanonicalName());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }


    @Override
    public List<Office> getAllOffices() {
        return null;
    }

    @Override
    public Office getOffice(String id) {
        return null;
    }

    @Override
    public void updateOffice(String id) {

    }

    @Override
    public void deleteOffice(String id) {

    }

    @Override
    public void addOffice(Office office) {

            try {
                connection.setAutoCommit(false);

                if(isObjectExistInTable(office.getId())){
                    System.out.println("Office object already exists in Data Base");
                    return;
                }

                addLogic(office);

                connection.commit();

            } catch (SQLException e) {
                DBUtil.showErrorMessage(e);
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    DBUtil.showErrorMessage(e);
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    DBUtil.showErrorMessage(e);
                }
            }

    }

    protected void addLogic(Object obj) throws SQLException {
        Office office = (Office)obj;

        //if Class is in Types, this means that class attributes are in Attributes
        if (officeId == null){
            officeId = insertInTypesAndAttributes(Office.class);
        }

        insertIntoObjects(office.getId(), officeId);

        insertAllIntoParams(office);

    }

    private void insertAllIntoParams(Office office) throws SQLException{
        Map<String, String> map = getAttrIds(officeId);


        //if Office object doesnt exist in table OBJECTS, then add the object
        if(!isObjectExistInTable(office.getCompany().getId())){
            new CompanyDaoJDBC(connection).addCompany(office.getCompany());
        }
        insertIntoParams(office.getCompany().getId(), map.get("office"), office.getId(), true);
    }
}
