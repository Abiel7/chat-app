package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterServer extends Config {
    private String ipadress;
    private String port;
    private String jdbc ;
    private String sql;
    private DatabaseConn dbHandl;


    public RegisterServer(String ipadress, String port) {
        this.ipadress = ipadress;
        this.port = port;
        jdbc = Config.jdbcURl;
    }


    /**
     * registrer rom nr og ip adresse
     */
    public  void registerToDB(){

        sql = "SELECT * FROM `serverdata` WHERE `server_ip` = ? AND `port_number` = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbHandl = new DatabaseConn();


            PreparedStatement statement = dbHandl.getDbConection().prepareStatement(sql);
            statement.setString(1,ipadress);
            statement.setString(2,port);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                System.out.println(" servern finnes allerede på databasen");
            } else {
                System.out.println(" ny server er lagt til på database ");

                sql = "INSERT INTO `serverdata` (`server_ip`, `port_number`) VALUES (?, ?);";

                PreparedStatement statement1 = dbHandl.getDbConection().prepareStatement(sql);

                statement1.setString(1,ipadress);
                statement1.setString(2,port);

                statement1.executeUpdate();

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
