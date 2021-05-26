package server;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MainController {
        protected static ArrayList<String> listDevice=  new ArrayList<>();
        protected static ArrayList<Socket> socketlist = new ArrayList<>();
        private ObservableList<String> ob;
        private int roomNrr=  3000;
        private Thread tr;

        /**
         * Åpner nytt room
         * @param txt
         * @param lbl
         */
        public void openRoom(TextField txt , Label lbl){
                roomNrr = Integer.parseInt((txt.getText().toString()));
                tr = new MainServer(roomNrr);
                tr.start();
                lbl.setText(" Rommet er åpent nå ");
        }

        /**
         * lopper først gjennom socket list og senter rom som er åpent
         * @throws InterruptedException
         *
         */
        public void closeRoom()  {
                try{
                        for ( Socket socket : socketlist) {
                                if (socket != null){ socket.close(); }
                                else if (tr.isAlive()){ tr.join(); }
                        }
                        Platform.exit();
                        System.exit(0);
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }

        }




}
