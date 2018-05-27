package gui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class Alert {

    public static void display(String title, String message){
        Stage window = new Stage();

        //Block events von allen anderen
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());



        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        //alles zentriert
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        //Block events von allen anderen
        window.showAndWait();
    }

}
