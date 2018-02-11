package executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementHandler {
    void descript(PreparedStatement stmt) throws SQLException;
}
