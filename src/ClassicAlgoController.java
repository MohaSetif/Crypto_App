import java.io.IOException;

import crypto_algos.*;
import crypto_algos.TripleHill;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ClassicAlgoController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private TextArea message;
    @FXML
    private TextArea key;
    @FXML
    private TextArea result;
    @FXML
    private Label error;
    @FXML
    private RadioButton encryptionRadioButton;
    @FXML
    private RadioButton decryptionRadioButton;

    public void tripleHillCipher() {
        String inputText = message.getText();
        String hill_key = key.getText();
        if (encryptionRadioButton.isSelected()) {
            result.setText(TripleHill.hillCipherEncrypt(inputText, hill_key));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(TripleHill.hillCipherDecrypt(inputText, hill_key));
        } else {
            error.setText("Please select encryption or decryption.");
            error.setTextFill(Color.rgb(210, 39, 30));
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
                error.setText("");
            }));
            timeline.play();
        }
    }

     public void DoubleHillCipher() {
        String inputText = message.getText();
        String hill_key = key.getText();
        if (encryptionRadioButton.isSelected()) {
            result.setText(DoubleHill.Hill_Enc(inputText, hill_key));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(DoubleHill.Hill_Dec(inputText, hill_key));
        } else {
            error.setText("Please select encryption or decryption.");
            error.setTextFill(Color.rgb(210, 39, 30));
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
                error.setText("");
            }));
            timeline.play();
        }
    }

    public void switchToHomeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
