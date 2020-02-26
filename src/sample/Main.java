package sample;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Controllers.MainSceneController;
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
        //s1 = getParameters().getNamed().toString();

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = Main.class.getResource("Views/MainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.setCursor(new ImageCursor(game.getFoodSprite()));
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
