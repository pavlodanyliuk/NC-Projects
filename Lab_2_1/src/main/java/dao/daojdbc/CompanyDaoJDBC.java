package dao.daojdbc;

import dao.daointerfaces.CompanyDAO;
import jdbcutil.DBUtil;
import offices.Company;
import offices.Identificateble;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CompanyDaoJDBC extends MetamodelDao implements CompanyDAO {

    public CompanyDaoJDBC(Connection connection) {
        super(connection);

        try {
            typesId = isTypesExistInTable(Company.class.getCanonicalName());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
    }

    public CompanyDaoJDBC(Connection connection, boolean withCommit) {
        super(connection, withCommit);

        try {
            typesId = isTypesExistInTable(Company.class.getCanonicalName());
        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
        }
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
        addObject(company, Company.class);
    }

    @Override
    protected void insertAllIntoParams(Identificateble obj) throws SQLException {
        Company company = (Company)obj;

        Map<String, String> map = getAttrIds(typesId);

        insertIntoParams(company.getDirector(), map.get("director"), company.getId(),false );
        insertIntoParams(company.getName(), map.get("name"), company.getId(), false);
    }

}
