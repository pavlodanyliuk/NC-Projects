package dao.daointerfaces;

import offices.Location;

import java.util.List;

public interface LocationDAO {

    List<Location> getAllLocations();

    Location getLocation(String id);

    void updateLocation(String id);

    void deleteLocation(String id);

    void addLocation(Location location);
}
