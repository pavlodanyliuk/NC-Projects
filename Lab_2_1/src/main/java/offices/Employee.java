package offices;

import offices.generator.GeneratorId;

public class Employee extends People implements Identificateble {
    private final String id;
    private Department department;

    public Employee(String firstName, String lastName, int age, String address, Department department){

        super(firstName, lastName, age, address);

        this.department = department;
        this.id = GeneratorId.generateUniqId();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getId() {
        return id;
    }
}
