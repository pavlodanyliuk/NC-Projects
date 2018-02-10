package dao.daojdbc;

import dao.daointerfaces.CompanyDAO;
import jdbcutil.DBUtil;
import offices.Company;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CompanyDaoJDBC extends MetamodelDao implements CompanyDAO {

    private String companyID;

    public CompanyDaoJDBC(Connection connection) {
        super(connection);

        try {
            companyID = isTypesExistInTable(Company.class.getCanonicalName());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }

    @Override
    protected void addLogic(Object obj) throws SQLException {

    }

    @Override
    public List<Company> getAllCompanies() {
        return null;
    }

    @Override
    public Company getCompany(String id) {
        return null;
    }

    @Override
    public void updateCompany(String id) {

    }

    @Override
    public void deleteCompany(String id) {

    }

    @Override
    public void addCompany(Company company) {

    }
}
