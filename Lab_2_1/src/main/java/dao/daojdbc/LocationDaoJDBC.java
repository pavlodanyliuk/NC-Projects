package dao.daojdbc;

import dao.daointerfaces.LocationDAO;
import jdbcutil.DBUtil;
import offices.Identificateble;
import offices.Location;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationDaoJDBC extends MetamodelDao implements LocationDAO {


    public LocationDaoJDBC(Connection connection) {
        super(connection);

        try {
            typesId = isTypesExistInTable(Location.class.getCanonicalName());
            parentId = isTypesExistInTable("ALL");

            if(parentId == null){
                parentId = addTypeALL();
            }

            //if the type doesnt exist, than add into TYPES and ATTRIBUTES tables

            if(typesId == null){
                insertInTypesAndAttributes(Location.class);
            }
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }

    public LocationDaoJDBC(Connection connection, boolean withCommit) {
        super(connection, withCommit);

        try {
            typesId = isTypesExistInTable(Location.class.getCanonicalName());
            parentId = isTypesExistInTable("ALL");

            if(parentId == null){
                parentId = addTypeALL();
            }

            //if the type doesnt exist, than add into TYPES and ATTRIBUTES tables

            if(typesId == null){
                insertInTypesAndAttributes(Location.class);
            }
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }



    @Override
    public List<Location> getAllLocations() {

        if(typesId == null) return null;

        List<String> objects = null;
        try {
            objects = getAllObjectsForType();
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtil.showErrorMessage(e);
        }

        ArrayList<Location> locations = new ArrayList<>();

        for(String id : objects) locations.add(getLocation(id));

        return locations;
    }

    @Override
    public Location getLocation(String id) {
        return (Location)getObject(id);
    }

    @Override
    protected Identificateble getConstructedObject(Map<String, String> map, String id) {
        return new Location(map.get("country"), map.get("region"), map.get("index"), map.get("address"), id);
    }

    @Override
    public void updateLocation(Location location) {
        updateObject(location, null);
    }

    @Override
    public void deleteLocation(String id) {
        deleteObject(id);
    }

    @Override
    public void addLocation(Location location) {
        addObject(location, null);
    }

    @Override
    protected void insertAllIntoParams(Identificateble obj) throws SQLException {
        Location location = (Location)obj;

        Map<String, String> map = getAttrIds(typesId);

        insertIntoParams(location.getCountry(), map.get("country"), location.getId(), false);
        insertIntoParams(location.getRegion(), map.get("region"), location.getId(), false);
        insertIntoParams(location.getAddress(), map.get("address"), location.getId(), false);
        insertIntoParams(location.getIndex(), map.get("index"), location.getId(), false);
    }

    @Override
    protected void updateRealization(Identificateble obj) throws SQLException {
        Location location = (Location) obj;

        Map<String, String> map = getAttrIds(typesId);

        updateTextValue(location.getAddress(), map.get("address"), location.getId());
        updateTextValue(location.getCountry(), map.get("country"), location.getId());
        updateTextValue(location.getRegion(), map.get("region"), location.getId());
        updateTextValue(location.getIndex(), map.get("index"), location.getId());

    }
}
