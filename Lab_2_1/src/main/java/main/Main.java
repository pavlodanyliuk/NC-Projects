package main;

import dao.daointerfaces.LocationDAO;
import dao.daojdbc.CompanyDaoJDBC;
import dao.daojdbc.DepartmentDaoJDBC;
import dao.daojdbc.EmployeeDaoJDBC;
import dao.daojdbc.LocationDaoJDBC;
import grants.GrantManager;
import grants.Role;
import jdbcutil.DBType;
import jdbcutil.DBUtil;
import offices.*;
import offices.generator.GeneratorId;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Location locationUA = new Location("UKR", "Kyiv", "05214", "Shevchenko str., 42");
        Location locationGB = new Location("GB", "London", "42424", "Own str., 56" );

        Company company = new Company("Netcracker", "Andrew Feinberg");

        Office officeKyiv = new Office(company, locationUA);
        Office officeLondon = new Office(company, locationGB);

        Department deptDevUA = new Department("Developer", officeKyiv);
        Department deptMarkUA = new Department("Marketing", officeKyiv);

        Department deptDevGB = new Department("Developer", officeLondon);

        Employee vasya = new Employee("Vasyl", "Goloborodko", 42, "Kyiv, 544", deptDevUA);
        Employee kolya = new Employee("Mykola", "Petrenko", 23, "Boyarka, 123", deptDevUA);
        Employee evgen = new Employee("Evgen", "Kostenko", 53, "Nitishiv, 36", deptMarkUA);
        Employee herley = new Employee("Herley", "Hugo", 26, "London, 56", deptDevGB);
        Employee roland = new Employee("Roland", "Deskey", 41, "London, 11", deptDevGB);

        try {
            Connection connection = DBUtil.getConnection(DBType.ORACLESQL);
//
//            EmployeeDaoJDBC employeeDaoJDBC = new EmployeeDaoJDBC(connection);
////            LocationDaoJDBC locationDaoJDBC = new LocationDaoJDBC(connection);
////            CompanyDaoJDBC companyDaoJDBC = new CompanyDaoJDBC(connection);
////            DepartmentDaoJDBC departmentDaoJDBC = new DepartmentDaoJDBC(connection);
//
//            employeeDaoJDBC.addEmployee(vasya);
            Role admin = new Role("ADMIN");

//            Map<String, Boolean> map = new GrantManager(connection).getGrants(admin, "161AADDEB3B", "firstName");
//
//            for(Map.Entry<String, Boolean> maps : map.entrySet()){
//                System.out.println(maps.getKey()+ ":" + maps.getValue());
//            }

            GrantManager grantManager = new GrantManager(connection);

            grantManager.setGrantsForObject(admin, "161AADDEA4A",true, true);

        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }


    }

    public static void printInfo(Employee employee){
        Department department = employee.getDepartment();

        System.out.println(employee.getId() + ":" + employee.getFirstName() + ":" + employee.getLastName() + ":" + employee.getAge() + ":" + employee.getAddress());

        System.out.println(department.getId() + ":" + department.getName());
        System.out.println(department.getOffice().getId());
        Company cmp = department.getOffice().getCompany();
        System.out.println(cmp.getId() + ":" + cmp.getName() + ":" + cmp.getDirector());
        Location loc = department.getOffice().getLocation();
        System.out.println(loc.getId() + ":" + loc.getAddress() + ":" + loc.getCountry() + ":" + loc.getIndex() + ":" + loc.getRegion());

    }
}
