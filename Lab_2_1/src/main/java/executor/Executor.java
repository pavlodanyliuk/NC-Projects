package executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private final Connection connection;

    public Executor (Connection connection){
        this.connection = connection;
    }

    public int execUpdate(String update) throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        int value = stmt.getUpdateCount();

        stmt.close();
        return value;
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);

        ResultSet resultSet = stmt.getResultSet();

        T value = handler.handle(resultSet);

        resultSet.close();
        stmt.close();

        return value;
    }
}
