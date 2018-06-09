package gui;

import Filter.*;
//Dustin the gui guy

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    public ImageView imgMask;
    public ImageView imgInput;
    public Button btbUploadMask;
    public Button btnApplyFilters;
    public Slider sliderBlur;
    public Slider sliderPixel;
    public ColorPicker colorReplacement1;
    public Pane window;
    public Stage stage = Main.getPrimaryStage();
    public Button btbUploadImage;
    public ToggleButton toggleBlur;
    public ToggleButton togglePixel;
    public TextField textThreshold;
    public ToggleButton toggleThreshhold;
    public ColorPicker colorReplacement2;
    public ToggleButton toggleReplacement;
    public ToggleButton toggleColorband;
    public ToggleButton toggleBandRed;
    public ToggleButton toggleBandBlue;
    public ToggleButton toggleBandGreen;
    public ToggleButton toggleMonochrom;
    public ToggleButton toggleMask;

    public final ToggleGroup COLORBANDGROUP = new ToggleGroup();
    public Label textUrlImage;
    public Label textUrlMask;
    public ImageView imgOutput;
    public Button btnApplyFilters1;
    public ProgressBar pbarSave;
    public Button btnSave;

    final int black = 0xFF000000;
    final int white = 0xFFFFFFFF;

    public ArrayList<Filter> chainfilter = new ArrayList<Filter>();

    public BufferedImage outputSave = null;
    public Button addBlur;
    public Button addPixel;
    public Button addThresh;
    public Button addReplace;
    public Button addBand;
    public Button addMono;
    public Label lblFilterAnwenden;
    public Button btnChain;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Groups
        toggleBandBlue.setToggleGroup(COLORBANDGROUP);
        toggleBandGreen.setToggleGroup(COLORBANDGROUP);
        toggleBandRed.setToggleGroup(COLORBANDGROUP);

        //EventHandler
        colorReplacement1.addEventHandler(ActionEvent.ACTION, new MyButtonHandler());
        colorReplacement2.addEventHandler(ActionEvent.ACTION, new MyButtonHandler());
        addBand.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addBlur.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addPixel.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addThresh.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addReplace.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addMono.addEventHandler(ActionEvent.ACTION, new AddHandler());

        imgInput.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Image uploadImage = selectImage(1);
            imgInput.setImage(uploadImage);
            btbUploadImage.setVisible(false);
        });
        imgMask.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Image uploadMask = selectImage(2);
            imgMask.setImage(uploadMask);
            btbUploadMask.setVisible(false);
        });

        //Styles
        toggleBandBlue.setStyle("-fx-background-color: blue; -fx-text-fill: white;" +
                "");
        toggleBandRed.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        toggleBandGreen.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        addBand.getStyleClass().add("add");
        addMono.getStyleClass().add("add");
        addReplace.getStyleClass().add("add");
        addThresh.getStyleClass().add("add");
        addPixel.getStyleClass().add("add");
        addBlur.getStyleClass().add("add");
    }

    public void applyChainFilter(MouseEvent mouseEvent) {
        boolean maskToggled = toggleMask.isSelected();
        BufferedImage image = null;
        BufferedImage mask = null;
        try {
            image = ImageIO.read(new File(textUrlImage.getText()));
            if (maskToggled) {
                mask = ImageIO.read(new File(textUrlMask.getText()));
            }
        } catch (IOException e) {
            Alert.display("FEHLER", "Es wurde vergessen ein Bild zu setzten!");
            return;
        }
        pbarSave.setVisible(true);
        BufferedImage output = image;
        try {
            if (maskToggled) {
                for (int i = 0; i < chainfilter.size(); i++) {
                    output = chainfilter.get(i).process(output, mask);
                }

            } else {
                for (int i = 0; i < chainfilter.size(); i++) {
                    output = chainfilter.get(i).process(output);
                }
            }
            outputSave = output;
            Image outputImage = SwingFXUtils.toFXImage(output, null);
            btnSave.setVisible(true);
            imgOutput.setImage(outputImage);

        } catch (NullPointerException e) {
            Alert.display("Kein Filter gewählt", "Bitte wählen Sie mindestens ein Filter aus");
        }
    }

    public void reset(MouseEvent mouseEvent) {
        chainfilter.clear();
        lblFilterAnwenden.setText("");
    }


    private class MyButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent evt) {
            if (evt.getSource().equals(colorReplacement1)) {
                String hex3;
                if (colorReplacement1.getValue().hashCode() != -1 && colorReplacement1.getValue().hashCode() != 255) {
                    hex3 = "#" + Integer.toHexString(colorReplacement1.getValue().hashCode()).substring(0, 6).toUpperCase();
                } else if (colorReplacement1.getValue().hashCode() == -1) {
                    hex3 = "white";
                } else {
                    hex3 = "black";
                }


                colorReplacement1.setStyle("-fx-background-color:" + hex3 + "; -fx-text-fill: black;");
            } else if (evt.getSource().equals(colorReplacement2)) {
                String hex3;
                if (colorReplacement2.getValue().hashCode() != -1 && colorReplacement2.getValue().hashCode() != 255) {
                    hex3 = "#" + Integer.toHexString(colorReplacement2.getValue().hashCode()).substring(0, 6).toUpperCase();
                } else if (colorReplacement2.getValue().hashCode() == -1) {
                    hex3 = "white";
                } else {
                    hex3 = "black";
                }
                colorReplacement2.setStyle("-fx-background-color:" + hex3 + "; -fx-text-fill: black;");
            }
        }
    }

    private class AddHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            boolean added = false;
            String additional = "";
            if (e.getSource().equals(addBand)) {
                if (toggleBandRed.isSelected()) {
                    chainfilter.add(new ColorBandFilter(ColorBand.RED));
                    added = true;
                    additional = ColorBand.RED.toString();
                } else if (toggleBandBlue.isSelected()) {
                    chainfilter.add(new ColorBandFilter(ColorBand.BLUE));
                    added = true;
                    additional = ColorBand.BLUE.toString();
                } else if (toggleBandGreen.isSelected()) {
                    chainfilter.add(new ColorBandFilter(ColorBand.GREEN));
                    added = true;
                    additional = ColorBand.GREEN.toString();
                } else {
                    Alert.display("FEHLER!", "Bitte wählen Sie RED,GREEN oder BLUE beim Colorpassfilter aus!");
                }
            } else if (e.getSource().equals(addBlur)) {
                int blurRadius = (int) sliderBlur.getValue();
                chainfilter.add(new BlurFilter(blurRadius));
                additional += blurRadius;
                added = true;
            } else if (e.getSource().equals(addReplace)) {
                int replace, replaceWith;

                if (colorReplacement2.getValue().hashCode() != -1 && colorReplacement2.getValue().hashCode() != 255) {
                    replaceWith = (int) (colorReplacement2.getValue().getRed() * 255) << 16 | (int) (colorReplacement2.getValue().getGreen() * 255) << 8 | (int) (colorReplacement2.getValue().getBlue() * 255);
                } else if (colorReplacement2.getValue().hashCode() == -1) {
                    replaceWith = white;
                } else {
                    replaceWith = black;
                }

                if (colorReplacement1.getValue().hashCode() != -1 && colorReplacement1.getValue().hashCode() != 255) {
                    replace = (int) (colorReplacement1.getValue().getRed() * 255) << 16 | (int) (colorReplacement1.getValue().getGreen() * 255) << 8 | (int) (colorReplacement1.getValue().getBlue() * 255);
                } else if (colorReplacement1.getValue().hashCode() == -1) {
                    replace = white;
                } else {
                    replace = black;
                }
                chainfilter.add(new ColorReplacementFilter(replace, replaceWith));
                additional = "#" + replace + "-" + "#" + replaceWith;
                added = true;
            } else if (e.getSource().equals(addPixel)) {
                int pixelRadius = (int) sliderPixel.getValue();
                //chainfilter.add(new Pixel(pixelRadius));
                additional += pixelRadius;
                added = false;
            } else if (e.getSource().equals(addThresh)) {
                String thresholdValues[] = textThreshold.getText().split(",");
                int tValues[] = new int[thresholdValues.length];
                for (int i = 0; i < tValues.length; i++) {
                    tValues[i] = Integer.parseInt(thresholdValues[i]);
                }
                chainfilter.add(new ThresholdFilter(tValues));
                additional = textThreshold.getText();
                added = true;
            } else if (e.getSource().equals(addMono)) {
                chainfilter.add(new MonochromFilter());
                added = true;
            }
            if (added) {
                StringBuilder sb = new StringBuilder();
                sb.append(lblFilterAnwenden.getText());
                if (chainfilter.size() != 1) {
                    sb.append(" -> ");
                }
                sb.append(chainfilter.get(chainfilter.size() - 1).getName());
                sb.append("(").append(additional).append(")");
                lblFilterAnwenden.setText(sb.toString());
            }
        }
    }

    public void uploadImage(ActionEvent actionEvent) {
        Image uploadImage = selectImage(1);
        if (uploadImage != null){
            btbUploadImage.setVisible(false);
            imgInput.setImage(uploadImage);
        }
    }

    public void uploadMask(ActionEvent actionEvent) {
        Image uploadImage = selectImage(2);
        ;
        if (uploadImage != null){
            btbUploadMask.setVisible(false);
            imgMask.setImage(uploadImage);
        }
    }

    public Image selectImage(int choose) {
        File file;
        boolean imageFound = false;
        BufferedImage image = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Bmp image files", "*.bmp"));
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {

            try{
                image = ImageIO.read(new File(file.getAbsolutePath()));
                ImageIO.write(image,"bmp",new File(file.getAbsolutePath()));
                if (choose == 1) {
                    textUrlImage.setText(file.getAbsolutePath());
                } else {
                    textUrlMask.setText(file.getAbsolutePath());
                }

                imageFound = true;
            } catch (IOException e) {
                Alert.display("File was not found!","File was not found!");
            }
        }
        Image uploadImage = null;
        if(imageFound){
            uploadImage = SwingFXUtils.toFXImage(image, null);
        }
        return uploadImage;

    }


    public void savePicture(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BMP Image", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                ImageIO.write(outputSave, "bmp", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
