import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;  
public class App extends Application{  
  
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("EntryScene.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Encryptify");
            primaryStage.getIcons().add(new Image("/img/icon64x64.png"));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main (String[] args)  
    {  
        launch(args);  
    }  
  
}  