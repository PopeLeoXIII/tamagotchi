package sample.Controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.GameStatus;
import sample.Main;
import sample.Models.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainSceneController {
    private Game game;
    private AnimationTimer timer;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button exitButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button playButton;
    @FXML
    private Button feedButton;
    @FXML
    private Button cleanButton;
    @FXML
    private Button createButton;


    private ImageView backgroundIV;
    @FXML
    private Pane petPane;
    @FXML
    private Pane mainPane;
    @FXML
    private Text nameTextBox;
    @FXML
    private Text dayTextBox;
    @FXML
    private Text timeTextBox;
    @FXML
    private Text textForDeadMessage;

    @FXML
    public void feed(){
        game.feedPet();
        game.updateTooltip("fed up");
    }
    @FXML
    public void play(){
        game.updateTooltip(game.getPetName() + " played and happy");
    }
    @FXML
    public void clean(){
        game.updateTooltip(game.getPetName() + " looks satisfied\n in a clean room");
    }


    public void createNewPet(){
        if(game.getLastFeed() + game.getStateDead() < new Date().getTime()) {
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
            try {
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(Main.class.getResource("Views/PetCreatorScene.fxml"));
                stage = new Stage();
                stage.setTitle("Create");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (game.getLastFeed() + game.getStateBad() < new Date().getTime()) {
                Stage stage1 = (Stage) createButton.getScene().getWindow();
                try {
                    FXMLLoader loader = new FXMLLoader();
                    Parent root1 = loader.load(Main.class.getResource("Views/WaitingRespScene.fxml"));
                    stage1 = new Stage();
                    stage1.setTitle("Create");
                    stage1.setScene(new Scene(root1));
                    stage1.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean alive(long d){
        if(d > game.getLastFeed() + game.getStateBad()){

            funeral();

            return false;
        }
        return true;
    }

    private Integer stepsCount = 100;
    private int stepDirection = 1;
    private void update(){
        long d = Calendar.getInstance().getTime().getTime();
        if(alive(d)) {
            dayTextBox.setText("Day: " + (d - game.getBirthdayDate()) / 86400000 );
            timeTextBox.setText(String.format("%tT", d - game.getBirthdayDate()));
            if (Math.random() < 0.005) {
                stepsCount = 25;
                stepDirection = (int) (Math.random() * 4);
                if (game.getPet().getTranslateX() < 30)
                    stepDirection = 1;
                if (game.getPet().getTranslateX() > 360)
                    stepDirection = 0;
                if (game.getPet().getTranslateY() < 30)
                    stepDirection = 3;
                if (game.getPet().getTranslateY() > 120)
                    stepDirection = 2;
                //System.out.println(game.getPet().getTranslateY());
            }
            if (stepsCount >= 0) {
                --stepsCount;
                switch (stepDirection) {
                    case (0): {
                        game.getPetAnimation().play();
                        game.getPetAnimation().setOffsetY(32);
                        game.getPet().moveX(-2);
                        break;
                    }
                    case (1): {
                        game.getPetAnimation().play();
                        game.getPetAnimation().setOffsetY(64);
                        game.getPet().moveX(2);
                        break;
                    }
                    case (2): {
                        game.getPetAnimation().play();
                        game.getPetAnimation().setOffsetY(96);
                        game.getPet().moveY(-2);
                        break;
                    }
                    case (3): {
                        game.getPetAnimation().play();
                        game.getPetAnimation().setOffsetY(0);
                        game.getPet().moveY(2);
                        break;
                    }
                }

            } else {
                game.getPetAnimation().stop();

            }
            game.updateTooltip();
        }
    }
    private void funeral(){
        if(game.getGameStatus() == GameStatus.STILL_ALIVE) {
            ImageView imageView = new ImageView(game.getGraveSprite());
            imageView.setTranslateX(game.getPet().getTranslateX());
            imageView.setTranslateY(game.getPet().getTranslateY());
            petPane.getChildren().add(imageView);
            mainPane.getChildren().remove(game.getPet());
            game.setGameStatus(GameStatus.ALREADY_DEAD);
            timer.stop();
        }
        dayTextBox.setText("Day: " + String.format("%tj", game.getLastFeed() + game.getStateBad() - game.getBirthdayDate()));
        timeTextBox.setText(String.format("%tT" ,game.getLastFeed() + game.getStateBad() - game.getBirthdayDate()) );
        //textForDeadMessage.setText(game.getPet().getPetName() + " died suddenly");
        playButton.setDisable(true);
        cleanButton.setDisable(true);
        feedButton.setDisable(true);
        createButton.setVisible(true);
        playButton.setDisable(true);
    }

    public void initialize() {
        game = Main.game;
        backgroundIV = new ImageView(game.getBackground());
        mainPane.getChildren().add(backgroundIV);
        if(game.getGameStatus() == GameStatus.STILL_ALIVE) {
            createButton.setVisible(false);
            createButton.setDisable(true);
            nameTextBox.setText("Name: " + game.getPet().getName());
            mainPane.getChildren().addAll(game.getPet());
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    update();

                }
            };
            timer.start();
            anchorPane.setCursor(new ImageCursor(game.getFoodSprite()));
        }
        if(game.getGameStatus() == GameStatus.ALREADY_DEAD){
            nameTextBox.setText("Name: " + game.getPetName());
            ImageView imageView = new ImageView(game.getGraveSprite());
            imageView.setTranslateX(180);
            imageView.setTranslateY(250);
            mainPane.getChildren().addAll(imageView);
            funeral();
        }
        if(game.getGameStatus() == GameStatus.MAKE_NEW){
            createButton.setVisible(true);
            playButton.setDisable(true);
            cleanButton.setDisable(true);
            feedButton.setDisable(true);
            createButton.setDisable(false);

        }
    }

}
