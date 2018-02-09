package offices;

import offices.generator.GeneratorId;

public class Location {
    private final String id;
    private String country;
    private String region;
    private String index;
    private String address;

    public Location (String country, String region, String index, String address){
        this.country = country;
        this.region = region;
        this.index = index;
        this.address = address;
        this.id = GeneratorId.generateUniqId();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }
}
