package gui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class Alert {

    public static void display(String title, String message) {
        Stage window = new Stage();

        //Block events von allen anderen
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setStyle("-fx-padding: 3 10 3 10; -fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: white; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.6), 5, 0.0, 0, 1);");
        label.setText(message);
        Button closeButton = new Button("OK");
        closeButton.setStyle("-fx-background-color: #090a0c,\n" +
                "    linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "    linear-gradient(#20262b, #191d22),\n" +
                "    radial-gradient(center 50% 0%, radius 100%, rgba(114, 131, 148, 0.9), rgba(255, 255, 255, 0));\n" +
                "    -fx-background-radius: 5, 4, 3, 5;\n" +
                "    -fx-background-insets: 10, 10, 10, 10;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.6), 5, 0.0, 0, 1);\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-padding: 10 20 10 20;");
        closeButton.setOnAction(e -> window.close());


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        //alles zentriert
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(#FF0000 0%, #6b1818 100%);");
        Scene scene = new Scene(layout);
        window.setScene(scene);

        //Block events von allen anderen
        window.showAndWait();
    }

}
