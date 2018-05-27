package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// implements EventHandler<ActionEvent>
public class Main extends Application  {

    private static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    /*
    STAGE -> SCENE
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("style.fxml"));
        primaryStage.setTitle("Programmieren 2 Abgabe");
        Scene scene = new Scene(root,921,457);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

}
