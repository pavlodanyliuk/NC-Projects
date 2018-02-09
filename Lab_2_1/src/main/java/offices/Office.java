package offices;

import offices.generator.GeneratorId;

public class Office {

    private final String id;
    private Company company;
    private Location location;

    public Office (Company company, Location location){
        this.company = company;
        this.location = location;
        this.id = GeneratorId.generateUniqId();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }
}
