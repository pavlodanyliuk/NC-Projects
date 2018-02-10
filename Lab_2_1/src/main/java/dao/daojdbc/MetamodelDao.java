package dao.daojdbc;


import executor.Executor;

import jdbcutil.DBUtil;
import offices.generator.GeneratorId;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MetamodelDao {
    protected final Connection connection;
    protected final Executor executor;

    protected MetamodelDao(Connection connection) {
        this.connection = connection;
        this.executor = new Executor(connection);
    }

    /**
     * Mains methods
     */

    protected void addObject(String objectId, Object obj){
        try {
            connection.setAutoCommit(false);

            if(isObjectExistInTable(objectId)){
                System.out.println("Object already exists in Data Base");
                return;
            }

            addLogic(obj);

            connection.commit();

        } catch (SQLException e) {
            DBUtil.showErrorMessage(e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                DBUtil.showErrorMessage(e1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                DBUtil.showErrorMessage(e);
            }
        }
    }

    protected abstract void addLogic(Object obj) throws SQLException;



    /**
     * Utils methods
     * @param canonicalName
     * @return
     * @throws SQLException
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

    protected String insertInTypesAndAttributes(Class clazz) throws SQLException {
        //insert in Types
        String typesId;
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
            fields.add(field.getName());
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

        return typesId;

    }

    protected void insertIntoObjects(String objId, String typesId) throws SQLException{
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



}
