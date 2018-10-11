package dao.daointerfaces;

import offices.Location;
import offices.Office;

import java.util.List;

public interface OfficeDAO {
    List<Office> getAllOffices();

    Office getOffice(String id);

    void updateOffice(Office office);

    void deleteOffice(String id);

    void addOffice(Office office);
}
