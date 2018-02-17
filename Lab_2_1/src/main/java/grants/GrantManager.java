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
            grants = getGrantsForType(role, getTypeId(objId));

            Map<String, Boolean> attrGrants = getGrantsForAttribute(role, objId, attrName);

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
                resultSet ->{

                    return findGrantsForRole(role, resultSet);
                },
                stmt -> stmt.setString(1, typeId)
        );

    }

    private Map<String, Boolean> findGrantsForRole(Role role, ResultSet resultSet) throws SQLException {
        if(!resultSet.next()){
            System.out.println("ID of types is not correct!!!");
            return null;
        }

        String roleName;
        do{

            roleName = resultSet.getString("ROLE");
            if(resultSet.wasNull()) break;

            if(roleName.equals(role.getName())){
                Map<String, Boolean> map = new HashMap<>(2);
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

        resultSet.first();

        String parentId = resultSet.getString("PARENT_ID");
        if(resultSet.wasNull()){
            Map<String, Boolean> map = new HashMap<>(2);

            map.put("read", false);
            map.put("write", false);

            return map;
        }

        return getGrantsForType(role, parentId);
    }

    private Map<String,Boolean> getGrantsForAttribute(Role role, String objId, String attrName) throws SQLException {
        //TODO: Realize this method!!!
        return null;
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