package server.database;

import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class InsertData extends Config{

    private String userName,message ;
    private byte[] ip;
    private String room ;

    private DatabaseConn dbConnection;
    private  String sql;
    private String jdbc;

    public InsertData(byte[] ip, String userName, String message, String room ) {
        this.ip = ip;
        this.room = room ;
        this.userName = userName;
        this.message = message;
       // this.date  =date;
        jdbc = Config.jdbcURl;

    }


    /**
     * insert melding
     */
    public void insert(){
    sql = "SELECT * FROM `serverdata` WHERE `server_ip` = ? AND `port_number` = ?";

    try {
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = new DatabaseConn();

        PreparedStatement statement = dbConnection.getDbConection().prepareStatement(sql);
        statement.setString(1, Arrays.toString(ip));
        statement.setString(2,room);

        ResultSet resultSet = statement.executeQuery() ;

        String id =null;
        if(resultSet.next()){
            id= resultSet.getString("server_id");


            id= resultSet.getString("message id");
        }


        sql = "INSERT INTO `messages` (`user_name`, `message`, `serverdata_server_id`) VALUES (?, ?, ?);";
        PreparedStatement statement1 = dbConnection.getDbConection().prepareStatement(sql);
        statement1.setString(1, userName);
        statement1.setString(2, message);
        statement1.setString(3, id);
        statement1.executeUpdate();

    }catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }


    }
}
