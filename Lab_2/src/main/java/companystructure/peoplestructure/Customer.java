package companystructure.peoplestructure;

import companystructure.projstucture.Project;

import java.util.ArrayList;

public class Customer {
    private String name;
    private String address;
    private String bankID;
    private ArrayList<Project> projects;

    public Customer(String name, String address, String bankID){
        this.name = name;
        this.address = address;
        this.bankID = bankID;
        projects = new ArrayList<>(0);
    }

    public void orderProject(Project project){
        projects.add(project);
    }


}
