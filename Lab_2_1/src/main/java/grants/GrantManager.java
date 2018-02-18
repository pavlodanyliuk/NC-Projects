package grants;

import executor.Executor;
import jdbcutil.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GrantManager {
    private final Executor executor;
    private final Connection connection;

    public GrantManager(Connection connection){
        this.connection = connection;
        this.executor = new Executor(connection);
    }


    /**
     * Main public methods
     */
    public Map<String, Boolean> getGrants(Role role, String objId, String attrName){
        Map<String, Boolean> grants = null;

        try {
            Map<String, Boolean> typeGrants = getGrantsForType(role, getTypeId(objId));

            Map<String, Boolean> attrGrants = getGrantsForAttribute(role, objId, attrName);

            if(typeGrants == null || attrGrants == null) return null;


            grants = new HashMap<>(2);
            grants.put("read", typeGrants.get("read") && attrGrants.get("read"));
            grants.put("write", typeGrants.get("write") && attrGrants.get("write"));

        } catch (SQLException e) {
            e.printStackTrace();
            DBUtil.showErrorMessage(e);
        }

        return grants;
    }

    public void setGrantsForType(Role role, String canonicalName, boolean read, boolean write){
        //TODO
    }

    public void setGrantsForAttribute(Role role, String canonicalName, String attrName, boolean read, boolean write){
        //TODO
    }

    public void setGrantsForObject(Role role, String objId, boolean read, boolean write){
        //TODO
    }


    /**
     *Additional private methods
     */

    private Map<String, Boolean> getGrantsForType(Role role, String typeId) throws SQLException{
        String query = "SELECT G.TYPE_ID, T.PARENT_ID, G.ROLE, G.READ, G.WRITE FROM TYPES T " +
                "LEFT OUTER JOIN GRANTTYPES G ON T.ID=G.TYPE_ID " +
                "WHERE T.ID=?";

        return executor.execQuery(
                query,
                resultSet -> findTypeGrantsForRole(role, resultSet),
                stmt -> stmt.setString(1, typeId)
        );

    }

    private Map<String, Boolean> findTypeGrantsForRole(Role role, ResultSet resultSet) throws SQLException {
        if(!resultSet.next()){
            System.out.println("ID of types is not correct!!!");
            return null;
        }


        Map<String, Boolean> map;

        map = findInResultSet(role, resultSet);
        if(map.size() == 2) return map;

        resultSet.first();

        String parentId = resultSet.getString("PARENT_ID");
        if(resultSet.wasNull()){
            map = new HashMap<>(2);
            map.put("read", false);
            map.put("write", false);

            return map;
        }

        return getGrantsForType(role, parentId);
    }


    private Map<String, Boolean> findInResultSet(Role role, ResultSet resultSet) throws SQLException{
        String roleName;
        Map<String, Boolean> map = new HashMap<>(2);

        do{

            roleName = resultSet.getString("ROLE");
            if(resultSet.wasNull()) break;

            if(roleName.equals(role.getName())){

                boolean canRead = false;
                boolean canWrite = false;

                int read = resultSet.getInt("READ");
                int write = resultSet.getInt("WRITE");

                if (read == 1) canRead = true;
                if (write == 1) canWrite = true;

                map.put("read", canRead);
                map.put("write", canWrite);

                return map;
            }

        }while (resultSet.next());

        return map;
    }

    private Map<String,Boolean> getGrantsForAttribute(Role role, String objId, String attrName) throws SQLException {
        String query = "SELECT O.ID O_ID, O.PARENT_ID O_PAR_ID, G.ROLE, G.READ, G.WRITE FROM OBJECTS O " +
                "INNER JOIN TYPES T ON T.ID = O.TYPE_ID " +
                "INNER JOIN ATTRIBUTES A ON A.TYPE_ID = T.ID " +
                "LEFT OUTER JOIN GRANTATTR G ON G.ATTR_ID = A.ID " +
                "WHERE A.NAME=? AND O.ID=?";

        Map<String, Boolean> map;

        map =  executor.execQuery(
                query,
                resultSet -> findAttrGrantsForRole(role, resultSet),
                stmt -> {
                    stmt.setString(1, attrName);
                    stmt.setString(2, objId);
                }
        );

        if ( map==null ) return null;
        if ( map.size()==2 ) return map;

        return getGrantsForObject(role, objId);
    }

    private Map<String, Boolean> findAttrGrantsForRole(Role role, ResultSet resultSet) throws SQLException {
        if(!resultSet.next()){
            System.out.println("ID of objects or attribute name is not correct!!!");
            return null;
        }

        return findInResultSet(role, resultSet);
    }

    private Map<String, Boolean> getGrantsForObject(Role role, String objId) throws SQLException{
        String query = "SELECT O.ID, O.PARENT_ID, G.ROLE, G.READ, G.WRITE FROM OBJECTS O " +
                "LEFT OUTER JOIN GRANTOBJECTS G ON G.OBJ_ID=O.ID " +
                "WHERE O.ID=?";

        return executor.execQuery(
                query,
                resultSet -> findObjGrantsForRole(role, resultSet),
                stmt -> stmt.setString(1, objId)
                );
    }

    private Map <String,Boolean> findObjGrantsForRole(Role role, ResultSet resultSet) throws SQLException {
        if(!resultSet.next()){
            System.out.println("ID of objects is not correct!!!");
            return null;
        }

        Map<String, Boolean> map;

        map = findInResultSet(role, resultSet);
        if(map.size() == 2) return map;

        resultSet.first();

        String parentId = resultSet.getString("PARENT_ID");
        if(resultSet.wasNull()){
            map = new HashMap<>(2);
            map.put("read", false);
            map.put("write", false);

            return map;
        }

        return getGrantsForObject(role, parentId);
    }

    private String getTypeId(String objId) throws SQLException{
        String query = "SELECT O.TYPE_ID FROM OBJECTS O WHERE O.ID=?";
        return executor.execQuery(
                query,
                resultSet -> {
                    if (!resultSet.next()) return "";
                    return resultSet.getString("TYPE_ID");
                },
                stmt -> stmt.setString(1, objId)
        );
    }

}