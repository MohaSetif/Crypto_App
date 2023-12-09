import java.io.IOException;

import crypto_algos.*;
import crypto_algos.AES;
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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ModernAlgoController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextArea message;
    @FXML
    private TextField des_key;
    @FXML
    private TextField aes_key;
    @FXML
    private TextField triple_des_key;
    @FXML
    private TextArea result;
    @FXML
    private Label error;
    @FXML
    private RadioButton encryptionRadioButton;
    @FXML
    private RadioButton decryptionRadioButton;

    @FXML
    private RadioButton ECB, CBC, CFB, OFB, CTR;

    @FXML
    private RadioButton aes_128;
    @FXML
    private RadioButton aes_192;
    @FXML
    private RadioButton aes_256;

    public void setError(String err){
        error.setText(err);
        error.setTextFill(Color.rgb(210, 39, 30));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            error.setText("");
        }));
        timeline.play();
    }

    public void switchToHomeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void DES_Cipher() throws Exception{
        String inputText = message.getText();
        String desKey = des_key.getText();
        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
        }
        if (desKey.isEmpty()) {
            setError("Please set your DES key.");
        }
        if (encryptionRadioButton.isSelected()) {
            result.setText(DES.DES_Enc(inputText, desKey));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(DES.DES_Dec(inputText, desKey));
        } else {
            setError("Please select encryption or decryption.");
        }
    }

    public void Triple_DES_Cipher() throws Exception{
        String inputText = message.getText();
        String tripleDesKey = triple_des_key.getText();
        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
        }
        if (tripleDesKey.isEmpty()) {
            setError("Please set your AES key.");
        }
        if (encryptionRadioButton.isSelected()) {
            result.setText(TripleDES.DES_Enc(inputText, tripleDesKey));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(TripleDES.DES_Dec(inputText, tripleDesKey));
        } else {
            setError("Please select encryption or decryption.");
        }
    }

    public void AES_Cipher() throws Exception {
        String inputText = message.getText();
        String aesKey = aes_key.getText();
        
        if (inputText.isEmpty() || aesKey.isEmpty()) {
            setError("Please set your plain text and AES key.");
            return;
        }

        String mode = "";
        if (ECB.isSelected()) {
            mode = "ECB";
        } else if (CBC.isSelected()) {
            mode = "CBC";
        } else if (CFB.isSelected()) {
            mode = "CFB";
        } else if (OFB.isSelected()) {
            mode = "OFB";
        } else if (CTR.isSelected()) {
            mode = "CTR";
        } else {
            setError("Please select a mode of operation.");
            return;
        }

        int size = aes_128.isSelected() ? 16 : (aes_192.isSelected() ? 24 : (aes_256.isSelected() ? 32 : 0));

        if (mode.isEmpty() || size == 0) {
            setError("Set the type and size of your AES!");
            return;
        }

        if(aesKey.length() != size){
            setError("Your key size must be: "+size);
        }

        String resultText;
        if (encryptionRadioButton.isSelected()) {
            resultText = AES.AES_Enc(inputText, aesKey, mode);
        } else if (decryptionRadioButton.isSelected()) {
            resultText = AES.AES_Dec(inputText, aesKey, mode);
        } else {
            setError("Please select encryption or decryption.");
            return;
        }

        result.setText(resultText);
    }
}
