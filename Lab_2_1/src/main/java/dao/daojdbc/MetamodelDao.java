package dao.daojdbc;


import executor.Executor;

import jdbcutil.DBUtil;
import offices.Identificateble;
import offices.generator.GeneratorId;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.sql.Date;

public abstract class MetamodelDao {
    protected final Connection connection;
    protected final Executor executor;
    protected String typesId;
    protected boolean isCommit = true;

    protected MetamodelDao(Connection connection) {
        this.connection = connection;
        this.executor = new Executor(connection);
    }
    protected MetamodelDao(Connection connection, boolean isCommit) {
        this.connection = connection;
        this.executor = new Executor(connection);
        this.isCommit = isCommit;
    }

    /**
     * Mains methods
     */

    /**
     * Get methods (construct)
     */

    protected Identificateble getObject(String id){
        Identificateble obj = null;
        try {
            connection.setAutoCommit(false);

            if(!isObjectExistInTable(id)) return null;
            if(!getObjectsType(id).equals(typesId)) return null;

            Map<String, String> attrIds = getAttrIds(typesId);
            Map<String, String> params = getParams(attrIds, id);

            obj = getConstructedObject(params, id);

            if (isCommit) connection.commit();

        } catch (SQLException e) {
            catchLogic(e);
        } finally {
            setAutoCommit();
        }

        return obj;
    }




    /**
     *Add methods
     */

    protected void addObject(Identificateble obj, Class clazz){
        try {
            connection.setAutoCommit(false);

            if(isObjectExistInTable(obj.getId())){
                System.out.println("Object already exists in Data Base");
                return;
            }

            //if the type doesnt exist, than add into TYPES and ATTRIBUTES tables
            if(typesId == null){
                insertInTypesAndAttributes(clazz);
            }

            //insert into OBJECT table
            insertIntoObjects(obj.getId());

            //insert into PARAMS table
            insertAllIntoParams(obj);
            if(isCommit){
                connection.commit();
            }

        } catch (SQLException e) {
           catchLogic(e);
        } finally {
            setAutoCommit();
        }
    }

    /**
     *Update method
     */

    protected void updateObject(Identificateble obj, Class clazz){
        try {
            connection.setAutoCommit(false);
            if(!isObjectExistInTable(obj.getId())){
                addObject(obj, clazz);
                return;
            }

            updateRealization(obj);

            connection.commit();
        } catch (SQLException e) {
            catchLogic(e);
        } finally {
            setAutoCommit();
        }
    }

    /**
     * Delete method
     */

    public void deleteObject(String objId){
        try{
            connection.setAutoCommit(false);

            if(!isObjectExistInTable(objId)){
                System.out.println("There is no objects with the id");
                return;
            }

            if(!getObjectsType(objId).equals(typesId)){
                System.out.println("Object is not such type");
                return;
            }

            Map<String, String> attributes = getAttrIds(typesId);
            deleteParams(attributes, objId);

            deleteObjectId(objId);

            connection.commit();
        } catch (SQLException e) {
            catchLogic(e);
        } finally {
            setAutoCommit();
        }
    }



    /**
     * Helper methods for catch and finaly block
     */
    private void setAutoCommit() {
        if (isCommit) try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtil.showErrorMessage(e);
        }
    }

    private void catchLogic(SQLException e){
        e.printStackTrace();
        DBUtil.showErrorMessage(e);

        try {
            connection.rollback();
        } catch (SQLException e1) {
            e1.printStackTrace();
            DBUtil.showErrorMessage(e1);
        }
    }

    /**
     * Abstract methods
     */
    protected abstract void insertAllIntoParams(Identificateble obj) throws SQLException;

    protected abstract void updateRealization(Identificateble obj) throws SQLException;

    protected abstract Identificateble getConstructedObject(Map<String, String> map, String id);

    /**
     * Utils methods
     *
     */


    protected String isTypesExistInTable(String canonicalName) throws SQLException {
        String query = "SELECT TYPES.ID, TYPES.NAME FROM TYPES WHERE TYPES.NAME=?";

        return executor.execQuery(
                query,
                resultSet -> {
                    if(!resultSet.next()) return null;
                    return resultSet.getString("ID");
                },
                stmt -> stmt.setString(1, canonicalName));


    }

    protected boolean isObjectExistInTable(String objId) throws SQLException {
        String query = "SELECT * FROM OBJECTS WHERE OBJECTS.ID=?";

        return executor.execQuery(
                query,
                resultSet -> {
                    return resultSet.next();
                },
                stmt -> stmt.setString(1, objId)
        );
    }

    protected String getObjectsType(String objId) throws SQLException {
        String query = "SELECT * FROM OBJECTS WHERE OBJECTS.ID=?";

        return executor.execQuery(
                query,
                resultSet -> {
                    if(!resultSet.next()) return null;
                    return resultSet.getString("TYPE_ID");
                },
                stmt -> stmt.setString(1, objId)
        );
    }

    protected void insertInTypesAndAttributes(Class clazz) throws SQLException {
        //insert in Types
        String insert = "INSERT INTO TYPES (ID, NAME) VALUES (?, ?)";

        typesId = GeneratorId.generateUniqId();
        int row = executor.execUpdate(
                insert,
                stmt -> {
                    stmt.setString(1, typesId);
                    stmt.setString(2, clazz.getCanonicalName());
                }
        );

        System.out.println(row + " updated...(TYPES)");

        //insert in Attributes
        insert = "INSERT INTO ATTRIBUTES (ID, NAME, TYPE_ID) VALUES (?, ?, ?)";

        List<String> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if(field.getName().equals("id")) continue;
            fields.add(field.getName());
        }

        Class superClazz = clazz.getSuperclass();
        for (Field field : superClazz.getDeclaredFields()){
            fields.add((field.getName()));
        }

        row = 0;
        for (String field : fields) {
            row += executor.execUpdate(
                    insert,
                    stmt -> {
                        stmt.setString(1, GeneratorId.generateUniqId());
                        stmt.setString(2, field);
                        stmt.setString(3, typesId);
                    }
            );
        }

        System.out.println(row + " updated...(ATTRIBUTES)");

    }

    protected void insertIntoObjects(String objId) throws SQLException{
        String insert = "INSERT INTO OBJECTS (ID, TYPE_ID) VALUES (?, ?)";

        int row = executor.execUpdate(
                insert,
                stmt -> {
                    stmt.setString(1, objId);
                    stmt.setString(2, typesId);
                }
        );
        System.out.println(row + " updated (OBJECTS)...");
    }

    protected void insertIntoParams(String text, String attrId, String objId, boolean isReference) throws SQLException{
        String insert;

        if (isReference) insert = "INSERT INTO PARAMS (REFERENCE_VALUE, ATTR_ID, OBJ_ID) VALUES (?, ?, ?)";
        else insert = "INSERT INTO PARAMS (TEXT_VALUE, ATTR_ID, OBJ_ID) VALUES (?, ?, ?)";


        int row = executor.execUpdate(
                insert,
                stmt -> {
                    stmt.setString(1, text);
                    stmt.setString(2, attrId);
                    stmt.setString(3, objId);
                }
        );
        System.out.println(row + " updated (PARAMS)...");

    }

    protected void insertIntoParams(int numb, String attrId, String objId) throws SQLException{
        String insert = "INSERT INTO PARAMS (NUMBER_VALUE, ATTR_ID, OBJ_ID) VALUES (?, ?, ?)";

        int row = executor.execUpdate(
                insert,
                stmt -> {
                    stmt.setInt(1, numb);
                    stmt.setString(2, attrId);
                    stmt.setString(3, objId);
                }
        );
        System.out.println(row + " updated (PARAMS)...");
    }

    protected void insertIntoParams(long numb, String attrId, String objId) throws SQLException{
        String insert = "INSERT INTO PARAMS (NUMBER_VALUE, ATTR_ID, OBJ_ID) VALUES (?, ?, ?)";

        int row = executor.execUpdate(
                insert,
                stmt -> {
                    stmt.setLong(1, numb);
                    stmt.setString(2, attrId);
                    stmt.setString(3, objId);
                }
        );
        System.out.println(row + " updated (PARAMS)...");
    }

    protected void insertIntoParams(Date date, String attrId, String objId) throws SQLException{
        String insert = "INSERT INTO PARAMS (DATA_VALUE, ATTR_ID, OBJ_ID) VALUES (?, ?, ?)";

        int row = executor.execUpdate(
                insert,
                stmt -> {
                    stmt.setDate(1, date);
                    stmt.setString(2, attrId);
                    stmt.setString(3, objId);
                }
        );
        System.out.println(row + " updated (PARAMS)...");
    }


    /**
     * Update section
     */
    protected void updateTextValue(String text, String attrId, String objId) throws SQLException{
        String update = "UPDATE PARAMS SET TEXT_VALUE=? WHERE ATTR_ID=? AND OBJ_ID=?";

        int row = executor.execUpdate(
                update,
                stmt -> {
                    stmt.setString(1, text);
                    stmt.setString(2, attrId);
                    stmt.setString(3, objId);
                }
        );
        System.out.println(row + " updated text value (PARAMS)");
    }

    protected void updateNumValue(Long numb, String attrId, String objId) throws SQLException{
        String update = "UPDATE PARAMS SET NUMBER_VALUE=? WHERE ATTR_ID=? AND OBJ_ID=?";

        int row = executor.execUpdate(
                update,
                stmt -> {
                    stmt.setLong(1, numb);
                    stmt.setString(2, attrId);
                    stmt.setString(3, objId);
                }
        );
        System.out.println(row + " updated number value (PARAMS)");
    }

    protected void updateDateValue(Date date, String attrId, String objId) throws SQLException{
        String update = "UPDATE PARAMS SET DATA_VALUE=? WHERE ATTR_ID=? AND OBJ_ID=?";

        int row = executor.execUpdate(
                update,
                stmt -> {
                    stmt.setDate(1, date);
                    stmt.setString(2, attrId);
                    stmt.setString(3, objId);
                }
        );
        System.out.println(row + " updated date value (PARAMS)");
    }

    protected void updateReferenceValue(Identificateble refObj, Class clazz, String attrId, String objId) throws SQLException{

        String update = "UPDATE PARAMS SET REFERENCE_VALUE=? WHERE ATTR_ID=? AND OBJ_ID=?";

        int row = executor.execUpdate(
                update,
                stmt -> {
                    stmt.setString(1, refObj.getId());
                    stmt.setString(2, attrId);
                    stmt.setString(3, objId);
                }
        );
        System.out.println(row + " updated reference value (PARAMS)");
    }

    protected Map<String, String> getAttrIds (String typeId) throws SQLException {
        String find = "SELECT * FROM ATTRIBUTES WHERE TYPE_ID=?";

        return executor.execQuery(
                find,
                resultSet -> {
                    Map<String, String> map = new HashMap<>();
                    while(resultSet.next()){
                        map.put(resultSet.getString("NAME"), resultSet.getString("ID"));
                    }
                    return map;
                },
                stmt -> stmt.setString(1, typeId)
        );
    }

    protected Map<String, String> getParams (Map<String, String> attr, String objId) throws SQLException{
        String find = "SELECT * FROM PARAMS WHERE ATTR_ID=? AND OBJ_ID=?";

        Map<String, String> params = new HashMap<>();
        for(Map.Entry<String, String> param : attr.entrySet()) {
            String attrId = param.getValue();
            String value = executor.execQuery(
                    find,
                    resultSet -> {
                        resultSet.next();
                        String valueStr;

                        valueStr = resultSet.getString("TEXT_VALUE");
                        if(!resultSet.wasNull()) return valueStr;

                        valueStr = resultSet.getString("REFERENCE_VALUE");
                        if(!resultSet.wasNull()) return valueStr;

                        Long valueNum = resultSet.getLong("NUMBER_VALUE");
                        if(!resultSet.wasNull()) return valueNum.toString();

                        Date valueDate = resultSet.getDate("DATA_VALUE");
                        if(!resultSet.wasNull()) return valueDate.toString();

                        return null;
                    },
                    stmt -> {
                        stmt.setString(1, attrId);
                        stmt.setString(2, objId);
                    }
            );
            params.put(param.getKey(), value);
        }
        return params;
    }

    protected void deleteParams(Map<String, String> attributes, String objId) throws SQLException {
        String delete = "DELETE PARAMS WHERE ATTR_ID=? AND OBJ_ID=?";

        int row = 0;
        for(Map.Entry<String, String> attribute : attributes.entrySet()){
            row += executor.execUpdate(
                    delete,
                    stmt -> {
                        stmt.setString(1, attribute.getValue());
                        stmt.setString(2, objId);
                    }
                    );
        }

        System.out.println(row + " row was deleted...");
    }

    protected void deleteObjectId(String objId) throws SQLException {
        String delete = "DELETE OBJECTS WHERE ID=?";

        int row = executor.execUpdate(
                delete,
                stmt -> stmt.setString(1, objId)
        );

        if(row == 1) System.out.println("Object was deleted successfully");
        else System.out.println("Was been a problem");
    }


    protected List<String> getAllObjectsForType() throws SQLException{
        ArrayList<String> objectIds;

        String querry = "SELECT * FROM OBJECTS WHERE TYPE_ID=?";
        objectIds = executor.execQuery(
                querry,
                resultSet -> {
                    ArrayList<String> ids = new ArrayList<>();
                    while(resultSet.next()){
                        ids.add(resultSet.getString("ID"));
                    }
                    return ids;
                },
                stmt -> {
                    stmt.setString(1, typesId);
                }
        );

        return objectIds;
    }

    protected void setCommit(boolean commit) {
        isCommit = commit;
    }
}
