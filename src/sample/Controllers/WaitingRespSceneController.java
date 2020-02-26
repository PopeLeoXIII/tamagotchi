package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;

public class WaitingRespSceneController {
    @FXML
    private Text nameText;
    @FXML
    private Button button;
    @FXML
    public void confirm(){
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    public void initialize(){
        String s = Main.game.getPetName();
        nameText.setText(s);
    }
}
