package executor;

import java.sql.*;

public class Executor {
    private final Connection connection;

    public Executor (Connection connection){
        this.connection = connection;
    }

    public int execUpdate(String update, PreparedStatementHandler handler) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(update);
        handler.descript(stmt);

        int value = stmt.executeUpdate();

        stmt.close();
        return value;
    }

    public <T> T execQuery(String query, ResultHandler<T> handler, PreparedStatementHandler handStm) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        handStm.descript(stmt);

        stmt.executeQuery();

        ResultSet resultSet = stmt.getResultSet();

        T value = handler.handle(resultSet);

        resultSet.close();
        stmt.close();

        return value;
    }
}
