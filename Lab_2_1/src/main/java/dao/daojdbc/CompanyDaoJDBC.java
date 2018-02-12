package dao.daojdbc;

import dao.daointerfaces.CompanyDAO;
import jdbcutil.DBUtil;
import offices.Company;
import offices.Identificateble;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
        if(typesId == null) return null;

        List<String> objects = null;
        try {
            objects = getAllObjectsForType();
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtil.showErrorMessage(e);
        }

        ArrayList<Company> companies = new ArrayList<>();

        for(String id : objects) companies.add(getCompany(id));

        return companies;
    }

    @Override
    public Company getCompany(String id) {
        return (Company)getObject(id);
    }

    @Override
    protected Identificateble getConstructedObject(Map<String, String> map, String id) {
        return new Company(map.get("name"), map.get("director"), id);
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
