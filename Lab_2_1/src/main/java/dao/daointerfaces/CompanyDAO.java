package dao.daointerfaces;

import offices.Company;

import java.util.List;

public interface CompanyDAO {

    List<Company> getAllCompanies();

    Company getCompany(String id);

    void updateCompany(String id);

    void deleteCompany(String id);

    void addCompany(Company company);
}
