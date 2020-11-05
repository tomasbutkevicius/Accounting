package accounting.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtilities {

    public static Connection connect() {
        String DB_URL = "jdbc:mysql://localhost/accounting_demo";
        String USER = "root";
        String PASS = "";
        try{
            return DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e){
            return null;
        }
    }

    public static void disconect(Connection connection, Statement statement) throws SQLException {
        connection.close();
    }

}
