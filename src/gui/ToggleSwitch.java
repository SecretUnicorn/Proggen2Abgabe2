package gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Code taken from
 * <a href="https://gist.github.com/TheItachiUchiha/12e40a6f3af6e1eb6f75">https://gist.github.com/TheItachiUchiha/12e40a6f3af6e1eb6f75</a>
 */

public class ToggleSwitch extends HBox {

    private final Label label = new Label();
    private final Button button = new Button();

    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);

    public boolean switchOnProperty() {
        return switchedOn.get();
    }

    private void init() {

        label.setText("OFF");
        button.getStyleClass().add("mask");
        getChildren().addAll(label, button);
        button.setOnAction((e) -> {
            switchedOn.set(!switchedOn.get());
        });
        label.setOnMouseClicked((e) -> {
            switchedOn.set(!switchedOn.get());
        });
        setStyle();
        bindProperties();
    }

    private void setStyle() {
        setWidth(60);
        label.setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 0;");
        setAlignment(Pos.CENTER_LEFT);
    }

    private void bindProperties() {
        label.prefWidthProperty().bind(widthProperty().divide(2));
        label.prefHeightProperty().bind(heightProperty());
        button.prefWidthProperty().bind(widthProperty().divide(2));
        button.prefHeightProperty().bind(heightProperty());
    }

    public ToggleSwitch() {
        init();
        switchedOn.addListener((a, b, c) -> {
            if (c) {
                label.setText("ON");
                setStyle("-fx-background-color: green;");
                label.toFront();
            } else {
                label.setText("OFF");
                setStyle("-fx-background-color: grey;");
                button.toFront();
            }
        });
    }
}