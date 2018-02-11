package dao.daojdbc;

import dao.daointerfaces.OfficeDAO;
import executor.Executor;
import jdbcutil.DBUtil;
import offices.Identificateble;
import offices.Office;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OfficeDaoJDBC extends MetamodelDao implements OfficeDAO {

    public OfficeDaoJDBC(Connection connection){
        super(connection);

        try {
            typesId = isTypesExistInTable(Office.class.getCanonicalName());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }


    public OfficeDaoJDBC(Connection connection, boolean withCommit){
        super(connection, withCommit);

        try {
            typesId = isTypesExistInTable(Office.class.getCanonicalName());
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
        return (Office)getObject(id);
    }

    @Override
    protected Identificateble getConstructedObject(Map<String, String> map, String id) {
        return new Office(
                new CompanyDaoJDBC(connection, false).getCompany(map.get("company")),
                new LocationDaoJDBC(connection, false).getLocation(map.get("location")),
                id
                );
    }

    @Override
    public void updateOffice(String id) {

    }

    @Override
    public void deleteOffice(String id) {

    }

    @Override
    public void addOffice(Office office) {
        addObject(office, Office.class);
    }


    protected void insertAllIntoParams(Identificateble obj) throws SQLException{
        Office office = (Office)obj;

        Map<String, String> map = getAttrIds(typesId);


        //if Office object doesnt exist in table OBJECTS, then add the object
        if(!isObjectExistInTable(office.getCompany().getId())){
            new CompanyDaoJDBC(connection, false).addCompany(office.getCompany());
        }
        insertIntoParams(office.getCompany().getId(), map.get("company"), office.getId(), true);


        if(!isObjectExistInTable(office.getLocation().getId())){
            new LocationDaoJDBC(connection, false).addLocation(office.getLocation());
        }
        insertIntoParams(office.getLocation().getId(), map.get("location"), office.getId(), true);
    }
}
