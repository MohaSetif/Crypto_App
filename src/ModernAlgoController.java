import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import crypto_algos.*;
import crypto_algos.AES;
import crypto_algos.RSA;
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
    private TextArea message, result;
    @FXML
    private TextField des_key, aes_key, triple_des_key, p_val, q_val, e_val;
    @FXML
    private Label error;
    @FXML
    private RadioButton encryptionRadioButton, decryptionRadioButton, aes_128, aes_192, aes_256, ECB, CBC, CFB, OFB, CTR, sha1, sha256, ds_1024, ds_2048, ds_4096, ds_rsa, ds_dh, ds_dsa;

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


        if (encryptionRadioButton.isSelected()) {
            result.setText(DES.DES_Enc(inputText, desKey, mode));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(DES.DES_Dec(inputText, desKey, mode));
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

    public void RSA_Cipher() {
        String inputText = message.getText();

        int p = Integer.parseInt(p_val.getText());
        int q = Integer.parseInt(q_val.getText());
        int e = Integer.parseInt(e_val.getText());

        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
            return;
        }

        if (p == 0 || q == 0 || e == 0) {
            setError("Please set your RSA values correctly.");
            return;
        }

        if (encryptionRadioButton.isSelected()) {

            int[] resultEnc = RSA.RSA_enc(inputText, p, q, e);
            if (resultEnc == null) {
                setError("Invalid RSA parameters.");
                return;
            }

        result.appendText(Arrays.toString(resultEnc));

    } else if (decryptionRadioButton.isSelected()) {

        int[] intArray = new int[inputText.length()];
    
        for (int i = 0; i < inputText.length(); i++) {
            intArray[i] = inputText.charAt(i);
        }
    
        String resultDec = RSA.RSA_dec(intArray, p, q, e);
        if (resultDec == null) {
            setError("Invalid RSA parameters.");
            return;
        }
    
        result.appendText(resultDec);
    
        } else {
            setError("Please select encryption or decryption.");
            return;
        }
    }

    public void Hash() throws Exception{
        String inputText = message.getText();
        String sha = sha1.isSelected() ? "SHA-1" : (sha256.isSelected() ? "SHA-256" : "");
        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
            return;
        }
        if (sha.isEmpty()) {
            setError("Set the type of your hash!");
            return;
        }
        result.setText(Hash.hash_msg(inputText, sha));
    }

    public void Digital_Sign() throws Exception {
        String inputText = message.getText();
        int ds_size = ds_1024.isSelected() ? 1024 : (ds_2048.isSelected() ? 2048 : (ds_4096.isSelected() ? 4096 : 0));
        String ds_type = ds_rsa.isSelected() ? "RSA" : (ds_dh.isSelected() ? "DH" : (ds_dsa.isSelected() ? "DSA" : ""));
        
        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
            return;
        }
        if (ds_size == 0) {
            setError("Set the size of your digital signature's key!");
            return;
        }
        if (ds_type.isEmpty()) {
            setError("Set the type of your digital signature!");
            return;
        }
        
        if (ds_type.equals("DSA")) {
            ds_4096.setDisable(true);
        }
        
        try {
            String signature = Digital_Signature.generateDS(inputText, ds_size, ds_type);
            result.setText(signature);
        } catch (NoSuchAlgorithmException e) {
            setError("Selected algorithm not supported.");
        } catch (Exception ex) {
            setError("An error occurred during signature generation.");
        }
    }

}
