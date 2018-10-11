package dao.daointerfaces;

import offices.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> getAllEmployes();

    Employee getEmployee(String id);

    void updateEmployee(Employee employee);

    void deleteEmployee(String id);

    void addEmployee(Employee employee);
}
