package server.database;

public class Config {
    protected static String dbHost="LocalHost";
    protected static String dbPort="3306";
    protected static String dbUser="root";
    protected static String dbPassword="root";
    protected static String dbName="todo";
    protected  static  String jdbcURl =  "jdbc:mysql://localhost:3306/todo";
    protected  static String servertable=  "serverdata";
    protected static  String messageTable = "messages" ;



    /*
+----------------+
| Tables_in_todo |
+----------------+
| messages       |
| serverdata     |
+----------------+

+------------+-------------+------+-----+---------------------+----------------+
| Field      | Type        | Null | Key | Default             | Extra          |
+------------+-------------+------+-----+---------------------+----------------+
| message_id | bigint(20)  | NO   | PRI | NULL                | auto_increment |
| user_name  | varchar(20) | NO   |     | NULL                |                |
| message    | text        | NO   |     | NULL                |                |
| server_id  | int(11)     | NO   |     | NULL                |                |
| date       | datetime    | NO   |     | current_timestamp() |                |
+------------+-------------+------+-----+---------------------+----------------+

+-------------+-------------+------+-----+---------+----------------+
| Field       | Type        | Null | Key | Default | Extra          |
+-------------+-------------+------+-----+---------+----------------+
| server_id   | int(11)     | NO   | PRI | NULL    | auto_increment |
| server_url  | varchar(50) | NO   |     | NULL    |                |
| port_number | int(11)     | NO   |     | NULL    |                |
+-------------+-------------+------+-----+---------+----------------+

     */

}
