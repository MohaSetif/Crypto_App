import javafx.application.Application;  
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;  
public class App extends Application{  
  
    @Override  
    public void start(Stage primaryStage) throws Exception {  
        try {
            Parent root= FXMLLoader.load(getClass().getResource("EntryScene.fxml"));  
            Scene scene=new Scene(root);
            //String css = this.getClass().getResource("style.css").toExternalForm();
            //scene.getStylesheets().add(css);
            primaryStage.setTitle("Encryptify");  
            primaryStage.setScene(scene);  
            primaryStage.show(); 
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }  
    public static void main (String[] args)  
    {  
        launch(args);  
    }  
  
}  