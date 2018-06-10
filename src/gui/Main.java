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

    @Override
    public void start(Stage primaryStage) throws Exception{
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("style.fxml"));
        primaryStage.setTitle("Programmieren 2 Abgabe (Ahlers & Folwarzny)");
        Scene scene = new Scene(root, 1229, 526);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

}
