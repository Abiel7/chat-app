package Client;

import Client.Database.FetchData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.MainServer;
import server.ServerView;

import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;

import static server.ServerView.socketList;

public class RoomView {

    protected static  ArrayList<String> room = new ArrayList<>();
    protected static ListView listView = new ListView();
    protected static ObservableList<String>  romlis  = FXCollections.observableArrayList(room);
    protected   FetchData dat = new FetchData();
    private ToggleGroup group = new ToggleGroup();
    private String roomNR ;
    protected  static Button btn = new Button(" join chat");


    RoomView(Stage stage){
        listView.setItems(romlis);
        listView.setPrefSize(150, 250);
        listView.setEditable(true);
        Platform.runLater(() -> {

                //ServerView.
            ObservableList<String> str = null;
            try {
                str = dat.searchRoom();
                socketList.forEach((to) ->{
                    //romlis.add(to);
                });
                romlis.addAll(str);


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }



        });


       listView.setCellFactory(param -> new RadioListCell());

        StackPane root = new StackPane();
        root.getChildren().addAll(listView ,btn);
        stage.setScene(new Scene(root, 1000,500));
        stage.show();

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new ChatView(stage);
            }
        });

    }
    private class RadioListCell extends ListCell<String> {
        @Override
        public void updateItem(String obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
                System.out.println("empt");
            }

            else {
                RadioButton radioButton = new RadioButton(obj);

                radioButton.setToggleGroup(group);
                setGraphic(radioButton);
                System.out.println("selected ");
            }
        }
    }


}
