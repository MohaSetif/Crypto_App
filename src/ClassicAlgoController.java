import java.io.IOException;

import crypto_algos.*;
import crypto_algos.Affine;
import crypto_algos.DoubleHill;
import crypto_algos.Euclidean;
import crypto_algos.Mult_Inv;
import crypto_algos.TripleHill;
import crypto_algos.Vigenere;
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


public class ClassicAlgoController{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextArea message;
    @FXML
    private TextArea key;
    @FXML
    private TextField rails;
    @FXML
    private TextArea result;
    @FXML
    private Label error;
    @FXML
    private RadioButton encryptionRadioButton;
    @FXML
    private RadioButton decryptionRadioButton;
    @FXML
    private TextField affine_key1, affine_key2, shift, vig_key, hill_key;


    public void tripleHillCipher() {
        String inputText = message.getText();
        String h_key = hill_key.getText();
        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
        }
        if (h_key.isEmpty()) {
            setError("Please set your 3x3 Hill key.");
        }
        if (encryptionRadioButton.isSelected()) {
            result.setText(TripleHill.hillCipherEncrypt(inputText, h_key));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(TripleHill.hillCipherDecrypt(inputText, h_key));
        } else {
            setError("Please select encryption or decryption.");
        }
    }

    public void DoubleHillCipher() {
        String inputText = message.getText();
        String h_key = hill_key.getText();
        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
        }
        if (h_key.isEmpty()) {
            setError("Please set your 2x2 Hill key.");
        }
        if (encryptionRadioButton.isSelected()) {
            result.setText(DoubleHill.Hill_Enc(inputText, h_key));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(DoubleHill.Hill_Dec(inputText, h_key));
        } else {
            setError("Please select encryption or decryption.");
        }
    }

    public void VigenereCipher() {
        String inputText = message.getText();
        String vigenere_key = vig_key.getText();
        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
        }
        if (vigenere_key.isEmpty()) {
            setError("Please set your Vigenere key.");
        }
        if (encryptionRadioButton.isSelected()) {
            result.setText(Vigenere.vigenereEncrypt(inputText, vigenere_key));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(Vigenere.vigenereDecrypt(inputText, vigenere_key));
        } else {
            setError("Please select encryption or decryption.");
        }
    }

    public void AffineCipher() {
        String inputText = message.getText();
        String k1_text = affine_key1.getText();
        String k2_text = affine_key2.getText();

        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
        }

        if (k1_text.isEmpty() || k2_text.isEmpty()) {
            setError("Please set values for k1 and k2.");
        }

        int k1 = Integer.parseInt(k1_text);
        int k2 = Integer.parseInt(k2_text);

        if (k1 == 0 || k2 == 0) {
            setError("Values of K1 and K2 cannot be zero.");
        }
        if (encryptionRadioButton.isSelected()) {
            if (Euclidean.is_euclidean(k1, 26) != 1) {
                setError("K1 must be coprime with 26.");
            }
            else{
                result.setText(Affine.Affine_Enc(inputText, k1, k2));
            }
        } else if (decryptionRadioButton.isSelected()) {
            int inv_k1 = Mult_Inv.mult_inv(k1, 26);
            if (inv_k1 == -1) {
                setError("K1 must be coprime with 26.");
            }
            else{
                result.setText(Affine.Affine_Dec(inputText, inv_k1, k2));
            }
        } else {
            setError("Please select encryption or decryption.");
        }
    }

    public void CaesarCipher() {
        String inputText = message.getText();
        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
        }
        if (encryptionRadioButton.isSelected()) {
            result.setText(Caesar.Caesar_Enc(inputText));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(Caesar.Caesar_Dec(inputText));
        } else {
            setError("Please select encryption or decryption.");
        }
    }

    public void ShiftCipher() {
        String inputText = message.getText();
        String shift_key = shift.getText();
        
        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
        }
        if (shift_key.isEmpty()) {
            setError("Please set values for k1 and k2.");
        }

        int sh = Integer.parseInt(shift_key);

        if (encryptionRadioButton.isSelected()) {
            result.setText(Shift.Shift_Enc(inputText, sh));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(Shift.Shift_Dec(inputText, sh));
        } else {
            setError("Please select encryption or decryption.");
        }
    }

    public void RailFenceCipher() {
        String inputText = message.getText();
        String rails_text = rails.getText();
        
        if (inputText.isEmpty()) {
            setError("Please set your plain text.");
        }
        if (rails_text.isEmpty()) {
            setError("Please set the number of the rails.");
        }

        int rails_nbr = Integer.parseInt(rails_text);

        if (encryptionRadioButton.isSelected()) {
            result.setText(Rail_Fence.railFenceEncrypt(inputText, rails_nbr));
        } else if (decryptionRadioButton.isSelected()) {
            result.setText(Rail_Fence.railFenceDecrypt(inputText, rails_nbr));
        } else {
            setError("Please select encryption or decryption.");
        }
    }

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
}
