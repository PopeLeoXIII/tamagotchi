package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import sample.Controllers.Register;
import sample.Models.Game;

import java.net.URL;
import java.util.TimeZone;

public class Main extends Application {

    public static Game game;
    public static Register register;

    @Override
    public void start(Stage primaryStage) throws Exception{
        register = new Register();
        game = new Game();

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getResource("Views/MainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        //scene.setCursor(new ImageCursor(game.getFoodSprite()));
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
