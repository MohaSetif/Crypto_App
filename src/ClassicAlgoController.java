import java.io.IOException;

import crypto_algos.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


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

    public void tripleHillEncrypt() {
        String inputText = message.getText();
        String hill_key = key.getText();
        result.setText(TripleHill.hillCipherDecrypt(inputText, hill_key));
    }

    public void switchToHomeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
