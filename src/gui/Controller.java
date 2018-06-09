package gui;

import Filter.*;
//Dustin the gui guy

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

    public BufferedImage outputSave = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleBandBlue.setToggleGroup(COLORBANDGROUP);
        toggleBandGreen.setToggleGroup(COLORBANDGROUP);
        toggleBandRed.setToggleGroup(COLORBANDGROUP);

        toggleBandBlue.setStyle("-fx-background-color: blue; -fx-text-fill: white;" +
                "");
        toggleBandRed.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        toggleBandGreen.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        colorReplacement1.addEventHandler(ActionEvent.ACTION, new MyButtonHandler());
        colorReplacement2.addEventHandler(ActionEvent.ACTION, new MyButtonHandler());
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

    public void applyFilters(MouseEvent mouseEvent) throws IOException{
        boolean maskToggled = toggleMask.isSelected();
        BufferedImage image = null;
        BufferedImage mask = null;
        try {
            image = ImageIO.read(new File(textUrlImage.getText()));
            if (maskToggled) {
                mask = ImageIO.read(new File(textUrlMask.getText()));
            }
        } catch (IOException e){
            Alert.display("FEHLER", "Es wurde vergessen ein Bild zu setzten!");
        }

        Filter filter = null;
        if (toggleBlur.isSelected()) {

        } else if (toggleMonochrom.isSelected()) {
            filter = new MonochromFilter();
        } else if (toggleBlur.isSelected()) {
            //filter = Blurfilter();
        } else if (toggleColorband.isSelected()) {
            if (toggleBandRed.isSelected()) {
                filter = new ColorBandFilter(ColorBand.RED);
            } else if (toggleBandBlue.isSelected()) {
                filter = new ColorBandFilter(ColorBand.BLUE);
            } else if (toggleBandGreen.isSelected()) {
                filter = new ColorBandFilter(ColorBand.GREEN);
            }
        } else if (toggleReplacement.isSelected()) {
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

            filter = new ColorReplacementFilter(replace, replaceWith);
        } else if (toggleThreshhold.isSelected()) {
            String thresholdValues[] = textThreshold.getText().split(",");
            int tValues[] = new int[thresholdValues.length];
            for (int i = 0; i < tValues.length; i++) {
                tValues[i] = Integer.parseInt(thresholdValues[i]);
            }
            filter = new ThresholdFilter(tValues);
        }

        BufferedImage output = null;
        try {
            if (maskToggled) {
                output = filter.process(image, mask);
            } else {
                output = filter.process(image);
            }
            outputSave = output;
            Image outputImage = SwingFXUtils.toFXImage(output, null);
            btnSave.setVisible(true);
            imgOutput.setImage(outputImage);

        } catch (NullPointerException e) {
            Alert.display("Kein Filter gewählt", "Bitte wählen Sie mindestens ein Filter aus");
        }








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
