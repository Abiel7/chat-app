package Client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ChatView   extends  Pane{

    protected static Label lblMessage =  new Label("Meldinger");
    protected  static Label lblWelcome = new Label();
    protected static Label errorLabel =  new Label( "");
    protected static Label lblActiveUsers  =  new Label( "online brukere ");
    protected  static  Label userNameLabel = new Label("Bruker navn:");

    protected  static  TextField usernameTextField = new TextField();
    public static TextArea taMessageView =  new TextArea();
    protected static TextField textField = new TextField();

    protected static Button btnSendMsg = new Button("send");
    protected static Button btnExitChatt = new Button("Lukk");
    protected static Button btnotherRoom = new Button("Rooms");
    protected  static  Button joinRoom = new Button("Join");

    public   static ArrayList<String> userList = new ArrayList<>();
    public   static  ArrayList<String> chatMsg=  new ArrayList<>();

    protected static  ListView<String> userListView=  new ListView<>();
    protected  static  ListView<String> msgListView = new ListView<>();

    protected  static ObservableList<String> userItems  = FXCollections.observableArrayList(userList);
    protected static ObservableList <String> messageItems  = FXCollections.observableArrayList(chatMsg);

    public ChatView(){

    }


    public ChatView(Stage stage){
     GridPane gridPane = new GridPane();
       GridPane gridPane2 = new GridPane();
       BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        borderPane.setPadding(new Insets(10));




      //  lblWelcome.setText("Velkommen til " +"\n" + "anonym chat-applikasjon ");
       // lblWelcome.setText( "anonym chat-applikasjon ");

        Color titleColor = new Color(0.1, 0, 0.5,1);
        lblWelcome.setTextFill(titleColor);
        taMessageView.setPromptText("skriv inn melding");
        taMessageView.setPrefHeight(2*(usernameTextField.getHeight()));
        taMessageView.setPrefWidth(250);


        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        gridPane.add(userNameLabel,0,0);
        gridPane.add(usernameTextField,1,0);
        gridPane.add(joinRoom,2,0);
        gridPane.add(lblMessage,0,2);
        gridPane.add(errorLabel,1,1,2,1);
        gridPane.add(msgListView,1,2,2,1);


        userListView.setItems(userItems);
        msgListView.setItems(messageItems);

        userListView.setEditable(false);
        msgListView.setEditable(false);

       userListView.setMinWidth(180);
        userListView.setMaxHeight(250);









        vBox.setPadding(new Insets(20,0,10,0));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(lblActiveUsers,userListView);
        borderPane.setRight(vBox);


        gridPane2.add(lblMessage,0,0);
        gridPane.add(lblWelcome,0,0);
        gridPane2.add(taMessageView,1,0);
        gridPane2.add(btnSendMsg,4,0);
        gridPane2.add(btnExitChatt,5,0);
        gridPane2.add(btnotherRoom,6,0);
        gridPane2.setHgap(20);
        gridPane2.setPadding(new Insets(10,0,10,10));
        btnSendMsg.setAlignment(Pos.BASELINE_RIGHT);



        borderPane.setCenter(gridPane);


        borderPane.setBottom(gridPane2);
        Scene scene = new Scene(borderPane,600,500);
        stage.setScene(scene);
        stage.show();


        btnotherRoom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new RoomView(stage);
            }
        });
        btnSendMsg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new ClinetController().insertMsg();
            }
        });
        btnExitChatt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new ClinetController().exit();
            }
        });

        joinRoom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                    new ClinetController().joinChatt();


            }
        });

    }



}
