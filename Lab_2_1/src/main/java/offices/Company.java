package offices;

import offices.generator.GeneratorId;

public class Company {
    private final String id;
    private String name;
    private String director;

    public Company(String name, String director){
        this.name = name;
        this.director = director;
        this.id = GeneratorId.generateUniqId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getId() {
        return id;
    }
}
