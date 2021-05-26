package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConn extends Config{
    Connection dbConection;

    /**
     * hent konling till databse
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Connection getDbConection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        dbConection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "messi123");
        return dbConection;
    }
}
