package Client.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.database.DatabaseConn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FetchData {
    private String respUser;
    private String respMSG;
    private String openRooms ;
    private String respDate;
    private  String sql ;
    private String jdbc;
    private DatabaseConn dbhandle;

    // / select server_id,port_number from serverdata;

    public FetchData(){

    }

    public   ObservableList<String> searchRoom () throws SQLException,ClassNotFoundException {
      sql = "SELECT server_id,port_number FROM serverdata";


        try {
            dbhandle = new DatabaseConn();
            ArrayList<String> rooms =  new ArrayList<>();
            PreparedStatement preparedStatement = dbhandle.getDbConection().prepareStatement(sql);
            ResultSet rsEmps = preparedStatement.executeQuery();

            while(rsEmps.next()){
                rooms.add(rsEmps.getString("port_number"));
            }

            ObservableList<String> empList = FXCollections.observableArrayList(rooms);


            return empList;

        } catch (SQLException e) {
           throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }


    /**
     *  tar to string  bruker og melding, den henter data fra brukeren sin info
     * @param user
     * @param msg
     * @return
     */

    public  ArrayList<String> getMessage (String user, String msg){
        ArrayList<String> arrayList = new ArrayList<>();
    sql = "SELECT * FROM `serverdata` WHERE `server_ip` = ? AND `port_number` = ?";
        try {

           Class.forName("com.mysql.jdbc.Driver");
           dbhandle = new DatabaseConn();
           PreparedStatement preparedStatement = dbhandle.getDbConection().prepareStatement(sql);
           preparedStatement.setString(1,user);
           preparedStatement.setString(2,msg);

           ResultSet resultSet =  preparedStatement.executeQuery();

           String id= null;

            if(resultSet.next()){
                id = resultSet.getString("server_id");
            }

            sql = "SELECT * FROM `messages` WHERE `server_ip` = ? ORDER BY `messages`.`message_id` ASC";
            PreparedStatement preparedStatement1 = dbhandle.getDbConection().prepareStatement(sql);
            preparedStatement1.setString(1, id);

//			Query SQL data
            ResultSet resultSet3 = preparedStatement1.executeQuery();

            while(resultSet.next()) {

                respUser = resultSet.getString("user_name");
               // ChatView.users.add(respUser);

                respMSG = resultSet.getString("message");
                //ChatView.msgList.add(respMSG);


                respDate = resultSet.getString("date");
                //ChatView.msgList.add(respDate);

                //arrayList.add(String.valueOf(ChatView.msgList));
                //arrayList.add(String.valueOf(ChatView.users));

            }



       } catch (SQLException | ClassNotFoundException e ){
            e.printStackTrace();
       }

       return arrayList;
    }




}
