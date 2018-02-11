package dao.daojdbc;

import dao.daointerfaces.LocationDAO;
import jdbcutil.DBUtil;
import offices.Identificateble;
import offices.Location;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class LocationDaoJDBC extends MetamodelDao implements LocationDAO {


    public LocationDaoJDBC(Connection connection) {
        super(connection);

        try {
            typesId = isTypesExistInTable(Location.class.getCanonicalName());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }

    public LocationDaoJDBC(Connection connection, boolean withCommit) {
        super(connection, withCommit);

        try {
            typesId = isTypesExistInTable(Location.class.getCanonicalName());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }



    @Override
    public List<Location> getAllLocations() {
        return null;
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
    public void updateLocation(String id) {

    }

    @Override
    public void deleteLocation(String id) {

    }

    @Override
    public void addLocation(Location location) {
        addObject(location, Location.class);
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
}
