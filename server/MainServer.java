package server;







import Client.Database.FetchData;
import javafx.application.Platform;

import server.database.RegisterServer;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class MainServer  extends  Thread{


    protected static int room;
    private ServerSocket serverSocket ;
    private InetAddress ipadrees;
    private FetchData data ;



    private Hashtable outputStream =  new Hashtable();

    public  MainServer(){

    }
    public  MainServer(int room){
        this.room = room;

    }

    /**
     *  åpner et room kaster untakk hvis rommet er åpent  og prøver åpener det igjen
     *  og ny roomm blir registert
     *  run thred
     */
    public void run(){


        try {
            serverSocket = new ServerSocket(room);
            System.out.println(" Room er nå åpent  ");
            ipadrees = InetAddress.getLocalHost();
            String str = ipadrees.getHostAddress();
            Platform.runLater(() -> new ServerView().logitems.add("Ny Tråd " + new Date()));

            Platform.runLater(() -> new ServerView().sokcetItems.add(serverSocket));

        while (true){
            Socket clientSocket;

                clientSocket = serverSocket.accept();
                MainController.socketlist.add(clientSocket);

                Platform.runLater(() ->
                        new ServerView().logitems.add("Koblinger  fra : " +
                                clientSocket.getRemoteSocketAddress() + " " +
                                str +  " " + new Date())
                        );
            DataOutputStream dataOutputStream  = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.put(clientSocket,dataOutputStream);

             new ClientHandle( this, clientSocket,room);

        }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     *Denne metoden sender brukerliste  over hvem som er på til alle brukere på samme server serveren.
     */
    private void showActiveUsers() {
        this.sendMsgToActriveUsers(ServerView.userList.toString());
    }

    /**
     *  henter output stream bruker enum interface
     * @return
     */
    Enumeration getOutputStream() {
        return outputStream.elements();
    }

    /**
     * brukt for å sende melding til alle brukere
     * gå gjennom hashtabel og send melding til hver output stream
     * og skrive melding
     * @param message
     */
    void sendMsgToActriveUsers(String message){
        for (Enumeration e = getOutputStream();e.hasMoreElements();){
            DataOutputStream dataOutputStream= (DataOutputStream) e.nextElement();
            try {
                dataOutputStream.writeUTF(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * Clint handle klasse opprette en server med flere tråder  hver socket i sitt eget tråd
     * konstruktør tar imot det som blir register og og lagrer det på databasen
     */
    class ClientHandle extends Thread{
        private  MainServer mainServer;
        private Socket socket;
        private String username;
        boolean userIsLoggedIn  = false ;


       private String room;
        private String hostAddress;

        public ClientHandle(MainServer mainServer, Socket socket, int roomNr ){
            this.mainServer = mainServer;
            this.socket = socket;
            room = String.valueOf(roomNr);
            hostAddress = serverSocket.getInetAddress().getHostAddress();

            new RegisterServer(hostAddress,room) .registerToDB();
                start();
        }

        /**
         * run thread
         */
        public void run(){
            try{
                DataInputStream dataInputStream=  new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());


                while (true) {

                    if (!userIsLoggedIn) {
                        username = dataInputStream.readUTF();
                        if (ServerView.userList.contains(username)) {
                            // for å sjekke om brukkernavent er i bruk av andre brukkere dat
                            // sånn at kunn en bruker er lagret på listen
                            dataOutputStream.writeUTF(username);
                            System.out.println(username + " i bruk ");
                        } else {
                            ServerView.userList.add(username);
                            //
                            dataOutputStream.writeUTF("Godkjent");
                            mainServer.showActiveUsers();
                            userIsLoggedIn = true;
                            Platform.runLater(() ->
                                    ServerView.logitems.add(username + " ble med på chatterommet "));

                            Platform.runLater(() ->
                            ServerView.useritems.addAll(ServerView.userList));
                            ServerView.useritems.clear();



                        }
                    } else if (userIsLoggedIn) {
                        String string = dataInputStream.readUTF();
                        mainServer.sendMsgToActriveUsers(string);
                        mainServer.showActiveUsers();
                        Platform.runLater(() ->ServerView.logitems.add(string));

                    }
                }

            } catch (IOException ex){
                ex.printStackTrace();
                Platform.runLater(() ->

                        // exeption er håndrert etter at bruker har gått av roome
                        ServerView.logitems.add( username+  " " +"Har gått ut av  roomNR " + " " + room));
                if(username != null){
                    ServerView.userList.remove(username);
                }

                mainServer.showActiveUsers();
                if (username != null){
                    // det ble vist til brukere som er på  samme room
                    mainServer.sendMsgToActriveUsers(username + " har  gått ut av roomet ");
                }
                Platform.runLater(() ->{
                    //new ServerView().useritems.clear();
                    new ServerView().useritems.addAll(ServerView.userList);
                });

            }
        }






    }
}
