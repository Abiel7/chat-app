package Client;

import Client.Database.FetchData;
import javafx.application.Platform;

import javafx.scene.paint.Color;
import server.database.InsertData;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import java.util.Arrays;
import java.util.List;

public class ClinetController extends  Thread {
    private String userName;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private boolean connection = true;
    protected  String room;
    private InsertData data;
    private boolean join = false;
    private InetAddress ip;

    public ClinetController(String  room){
        this.room = room;
    }

    /**
     * konstruktør  til client controller
     * lager socket som skal bli kobelt til et room
     * lager inputstram for å ta imot data fra server
     *
     */
    public ClinetController(  ) {
        try {
            socket = new Socket("localhost", 3000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> recivMSG()).start();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }



    }

    /**
     * relvant info er vist til klient og sjekker om riktig navn har blit lagt til server
     */
    public void recivMSG (){

        try{
            while(connection){
                String message;
                if(!join){ addUser(); }
                else{
                    message = dataInputStream.readUTF();
                    if(message.startsWith("[")){ addMSG(message); }
                    else{
                        Platform.runLater(() -> { ChatView.messageItems.add(message); });
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Socket is closed.receive");
            Platform.runLater(() -> {
                ChatView.errorLabel.setTextFill(Color.RED);
               ChatView.errorLabel.setText("Unable to establish connection.");
            });
            connection = false;
        }
    }


    /**
     * join chat metode  sender brukernavn til tjener for å bli Godkjent
     */
    public void joinChatt() {
        userName = ChatView.usernameTextField.getText().toString();;
        try {
                dataOutputStream.writeUTF(userName);
            }
            catch (IOException e) {
                e.printStackTrace();
            }


    }

    /**
     * sikkrer at socket er stegngt etter siste bruker på roomet har gått  ut
     */

    public void exit() {
        try{
            if (socket != null){ socket.close(); }
            Platform.exit();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * denne metoden sender melding til tjener ved å legge   navnet til melding
     */
    public void insertMsg() {
        try {
            // bruker navn deretter ny linje og melding til brukker
            String stringU = ChatView.usernameTextField.getText().toString().trim()  + "\n"+ ChatView.taMessageView.getText().toString().trim();;
            //data = new InsertData(ip.getAddress(),stringU,stringmsg,room);
            //data.insert();
            dataOutputStream.writeUTF(stringU);
            ChatView.taMessageView.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





        /**
         *Hvis serveren sender svar til brukeren og det står godkjent,
         * da blir statusen  på join true.  det blir oppdatert på if condetion
         * hvis det ikke blir godkejnt da betyr det at brukkern finnes på array llist av navn til serveren
         *
         */

        private void addUser (){
            String response;
            try {
                response = dataInputStream.readUTF();
                if (response.startsWith("Godkjent")){ join = true; }
            } catch (IOException e) {

                Platform.runLater(() -> { connection = false; });
            }
        }

    /**
     * denne metoden her lager en arrayllist fra meding som ble sendt
     * @param msg
     */
    private void addMSG( String msg){

            List<String> list = Arrays.asList(msg.substring(1, msg.length() - 1).split(", "));
            new FetchData().getMessage(userName ,msg).add(String.valueOf(list));
            Platform.runLater(() -> { ChatView.messageItems.clear();
                for (String s : list) {
                    if (!(s.equals(userName))) {
                        ChatView.messageItems.add(s);
                    }
                }

            });
        }
    }


