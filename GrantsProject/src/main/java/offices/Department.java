package offices;

import offices.generator.GeneratorId;

public class Department implements Identificateble{

    private final String id;
    private String name;
    private Office office;

    public Department(String name, Office office){
        this.name = name;
        this.office = office;
        this.id = GeneratorId.generateUniqId();
    }

    public Department(String name, Office office, String id){
        this.name = name;
        this.office = office;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getId() {
        return id;
    }
}
