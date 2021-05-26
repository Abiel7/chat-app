package server;

//kjør main på server for å se hvodan det ser ut
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;


public class ServerView {
    protected static Label lbllog = new Label("Logg");;
    protected static Label lblActiveUsers = new Label(" Online  Brukere");;
    protected  static  Label lblSockets =  new Label("Åpent room ");
    protected  static Label label = new Label(" Ingen forbindelse");

    protected static ArrayList<String> logList = new ArrayList<>();
    protected static ArrayList<String> userList = new ArrayList<>();
    public static ArrayList<ServerSocket> socketList = new ArrayList<>();


    protected static ListView<String> loglistView = new ListView<String>();
    protected static ListView<String> userlistview = new ListView<String>();
    protected static ListView<ServerSocket> socketView = new ListView<ServerSocket>();


    protected static ObservableList <String> logitems = FXCollections.observableArrayList(logList);
    protected static ObservableList <String>  useritems= FXCollections.observableArrayList(userList);
    protected static ObservableList <ServerSocket>  sokcetItems = FXCollections.observableArrayList(socketList);



    protected static TextField room = new TextField();

    protected static Button openRoom = new Button("Åpne Room");
    protected  static Button closeRoom = new Button("Lukk room ");



    ServerView(){

    }
    ServerView(Stage stage) {
        GridPane gridPane = new GridPane();
        VBox vBox = new VBox(10);
        userlistview.setItems(useritems);
        loglistView.setItems(logitems);
        socketView.setItems(sokcetItems);

        loglistView.setPrefWidth(500);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.add(lbllog,0,0);
        gridPane.add(loglistView,0,1);
        gridPane.add(lblActiveUsers,0,2);
        gridPane.add(userlistview,0,3);
        gridPane.add(lblSockets,1,2);
        gridPane.add(socketView,1,3);


        room.setPromptText(" Gi room nr ");vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(room,openRoom,closeRoom,label);
        gridPane.add(vBox,2,1);

        Scene scene = new Scene(gridPane,1000,500);
        stage.setScene(scene);
        stage.show();

        openRoom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new MainController().openRoom(room,label);
            }
        });
        closeRoom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new MainController().closeRoom();
            }
        });

             new Thread(MainServer::new).start();
    }

}

