package jdbcutil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DBUtil {
    private static final String dbUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String userName = "kamai";
    private static final String passwd = "789456123";

    public static Connection getConnection(DBType type) throws SQLException {
        Locale.setDefault(new Locale("EN"));
        switch(type) {
            case ORACLESQL:
                return DriverManager.getConnection(dbUrl, userName, passwd);
            default:
                return null;
        }
    }

    public static void showErrorMessage(SQLException e){
        System.err.println("Error: " + e.getMessage());
        System.err.println("Error code: " + e.getErrorCode());


    }


}
