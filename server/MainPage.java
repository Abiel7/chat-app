package server;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        new ServerView(stage);

    }

    public static Stage getStage(){
        return  stage;
    }
}
