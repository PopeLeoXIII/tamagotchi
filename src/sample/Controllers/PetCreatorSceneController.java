package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;
import sample.Models.Game;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class PetCreatorSceneController {
    @FXML
    private TextField textName1;
    @FXML
    private TextField textName2;
    @FXML
    private TextField textName3;

    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private Text text3;
    
    @FXML
    private Button createButton1;
    @FXML
    private Button createButton2;
    @FXML
    private Button createButton3;

    @FXML
    private Pane eggPane1;
    @FXML
    private Pane foodPane1;
    @FXML
    private Pane eggPane2;
    @FXML
    private Pane foodPane2;
    @FXML
    private Pane eggPane3;
    @FXML
    private Pane foodPane3;

    private String name;
    private ArrayList<ArrayList<String>> list;

    @FXML
    public void takeName1(){
        name = textName1.getText();
        createButton1.setDisable(false);
    }

    @FXML
    public void takeName2(){
        name = textName2.getText();
        createButton2.setDisable(false);
    }

    @FXML
    public void takeName3(){
        name = textName3.getText();
        createButton3.setDisable(false);
    }

    @FXML
    public void create1(){
        name = textName1.getText();
        create(0);
    }
    @FXML
    public void create2(){
        name = textName2.getText();
        create(1);
    }
    @FXML
    public void create3(){
        name = textName3.getText();
        create(2);
    }
    private void create(int i){
        ArrayList<String> arrayList = new ArrayList<>(list.get(i));
        arrayList.set(4, name);
        Stage stage = (Stage) createButton1.getScene().getWindow();
        stage.close();
        try {
            Main.game = new Game(arrayList);
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(Main.class.getResource("Views/MainScene.fxml"));//fxmlStream);
            stage = new Stage();
            stage.setTitle("Create");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImageView conversion(ImageView imageView, int translate, double scale){
        imageView.setTranslateX(translate);
        imageView.setTranslateY(translate);
        imageView.setScaleX(scale);
        imageView.setScaleY(scale);
        return imageView;
    }


    public void initialize() throws FileNotFoundException {
        list = Main.register.readFolder("pets");
        ImageView imageView;

        ArrayList<Pane> eggPaneList = new ArrayList<Pane>(Arrays.asList(eggPane1, eggPane2, eggPane3));
        ArrayList<Pane> foodPaneList = new ArrayList<Pane>(Arrays.asList(foodPane1, foodPane2, foodPane3));
        ArrayList<Text> textList = new ArrayList<Text>(Arrays.asList(text1, text2, text3));

        for(int i = 0; i < Math.min(list.size(), eggPaneList.size()); i++){
            textList.get(i).setText("Difficulty: " + list.get(i).get(8));
            imageView = new ImageView(new Image(new FileInputStream(list.get(i).get(7))));
            eggPaneList.get(i).getChildren().add(conversion(imageView, 30, 1.2));
            imageView = new ImageView(new Image(new FileInputStream(list.get(i).get(6))));
            foodPaneList.get(i).getChildren().add(conversion(imageView, 40, 1.5));

        }
    }

}
