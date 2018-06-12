package gui;

import Filter.*;
//Dustin the gui guy

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public ImageView imgMask;
    public ImageView imgInput;
    public ImageView imgOutput;

    public Button addBlur;
    public Button addPixel;
    public Button addThresh;
    public Button addReplace;
    public Button addBand;
    public Button addMono;
    public Button addInvert;
    public Button addMutilation;
    public Button btnChain;
    public Button btnClear;
    public Button btbUploadMask;
    public Button btbUploadImage;
    public Button btnSave;

    public ToggleButton toggleBandRed;
    public ToggleButton toggleBandBlue;
    public ToggleButton toggleBandGreen;
    public ToggleSwitch toggleMask;

    public Label textUrlImage;
    public Label textUrlMask;
    public Label lblFilterAnwenden;

    public Slider sliderBlur;
    public Slider sliderPixel;

    public ColorPicker colorReplacement1;
    public ColorPicker colorReplacement2;

    public TextField textThreshold;
    public TextField textPixel;
    public TextField textBlur;

    public ProgressIndicator loading;

    public Pane window;
    public Stage stage = Main.getPrimaryStage();

    public final ToggleGroup COLORBANDGROUP = new ToggleGroup();

    final int black = 0x000000;
    final int white = 0xFFFFFF;

    public Filter chainfilterNormal = new ChainFilter();
    public Filter chainfilterProcess = new ChainFilter();
    public Filter warhol = new ChainFilter();
    public BufferedImage outputSave = null;
    public Slider sliderMinimum;
    public Button addMinimum;
    public TextField textMinimum;

    Service service = new ProcessService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Groups
        toggleBandBlue.setToggleGroup(COLORBANDGROUP);
        toggleBandGreen.setToggleGroup(COLORBANDGROUP);
        toggleBandRed.setToggleGroup(COLORBANDGROUP);

        //EventHandler
        colorReplacement1.addEventHandler(ActionEvent.ACTION, new colorHandler());
        colorReplacement2.addEventHandler(ActionEvent.ACTION, new colorHandler());
        addBand.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addBlur.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addPixel.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addThresh.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addReplace.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addMono.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addInvert.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addMutilation.addEventHandler(ActionEvent.ACTION, new AddHandler());
        addMinimum.addEventHandler(ActionEvent.ACTION, new AddHandler());
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
        sliderBlur.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                textBlur.textProperty().setValue(
                        String.valueOf((int) sliderBlur.getValue()) + "px");

            }
        });
        sliderPixel.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                textPixel.textProperty().setValue(
                        String.valueOf((int) sliderPixel.getValue()) + "px");

            }
        });
        sliderMinimum.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                textMinimum.textProperty().setValue(
                        String.valueOf((int) sliderMinimum.getValue()) + "px");

            }
        });

        service.setOnSucceeded(e -> {
            service.reset();
            loading.setVisible(false);
        });


        //Styles
        toggleBandBlue.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        toggleBandRed.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        toggleBandGreen.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        addBand.getStyleClass().add("add");
        addMono.getStyleClass().add("add");
        addReplace.getStyleClass().add("add");
        addThresh.getStyleClass().add("add");
        addPixel.getStyleClass().add("add");
        addBlur.getStyleClass().add("add");
        addInvert.getStyleClass().add("add");
        addMutilation.getStyleClass().add("add");
        addMinimum.getStyleClass().add("add");
    }

    public void applyChainFilter(MouseEvent mouseEvent) {
        apply(chainfilterNormal);
    }

    public void reset(MouseEvent mouseEvent) {
        imgOutput.setImage(null);
        ((ChainFilter) chainfilterNormal).getArrayList().clear();
        lblFilterAnwenden.setText("");
    }

    public void applywarhol(MouseEvent mouseEvent) {
        ((ChainFilter) warhol).getArrayList().clear();
        ((ChainFilter) warhol).addFilter(new ThresholdFilter(64, 128, 192));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(ImageHelper.setGreyPixel(0)));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(ImageHelper.setGreyPixel(96)));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(ImageHelper.setGreyPixel(160)));
        ((ChainFilter) warhol).addFilter(new ColorReplacementFilter(ImageHelper.setGreyPixel(255)));
        apply(warhol);
    }

    public void apply(Filter c) {
        chainfilterProcess = (ChainFilter) c;
        loading.setVisible(true);
        imgOutput.setVisible(false);
        if (!service.isRunning())
            service.start();
    }

    class ProcessService extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override

                protected Void call() throws Exception {
                    boolean maskToggled = toggleMask.switchOnProperty();
                    ChainFilter chain = (ChainFilter) chainfilterNormal;
                    BufferedImage image = null;
                    BufferedImage mask = null;
                    try {
                        image = ImageIO.read(new File(textUrlImage.getText()));
                        if (maskToggled) {
                            mask = ImageIO.read(new File(textUrlMask.getText()));
                        }
                    } catch (IOException e) {
                        loading.setVisible(false);
                        Alert.display("FEHLER", "Es wurde vergessen ein Bild zu setzten!");

                    }
                    try {
                        if (maskToggled) {
                            outputSave = chain.process(image, mask);

                        } else {
                            outputSave = chain.process(image);
                        }
                        Image outputImage = SwingFXUtils.toFXImage(outputSave, null);
                        btnSave.setVisible(true);
                        imgOutput.setVisible(true);
                        imgOutput.setImage(outputImage);

                    } catch (NullPointerException e) {
                        Alert.display("Kein Filter gewählt", "Bitte wählen Sie mindestens ein Filter aus");
                    }
                    return null;
                }

            };
        }
    }

    public void sound(MouseEvent dragEvent) {
        Random random = new Random();
        String musicFile;
        if (random.nextBoolean()) {
            musicFile = "src/sounds/airhorn.mp3";
        } else {
            musicFile = "src/sounds/boing.mp3";
        }

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public void changePixel(ActionEvent actionEvent) {
        String contentOfTextfield = textPixel.getText();
        while (!checkIfCointainsNumbers(contentOfTextfield) && contentOfTextfield.length() != 0) {
            contentOfTextfield = contentOfTextfield.substring(0, contentOfTextfield.length() - 1);
        }
        sliderPixel.setValue(Integer.parseInt(contentOfTextfield));
    }

    public void changeBlur(ActionEvent actionEvent) {
        String contentOfTextfield = textBlur.getText();
        while (!checkIfCointainsNumbers(contentOfTextfield) && contentOfTextfield.length() != 0) {
            contentOfTextfield = contentOfTextfield.substring(0, contentOfTextfield.length() - 1);
        }
        sliderBlur.setValue(Integer.parseInt(contentOfTextfield));
    }

    public void changeMinimum(ActionEvent actionEvent) {
        String contentOfTextfield = textMinimum.getText();
        while (!checkIfCointainsNumbers(contentOfTextfield) && contentOfTextfield.length() != 0) {
            contentOfTextfield = contentOfTextfield.substring(0, contentOfTextfield.length() - 1);
        }
        sliderMinimum.setValue(Integer.parseInt(contentOfTextfield));
    }

    public boolean checkIfCointainsNumbers(String check) {
        try {
            int i = Integer.parseInt(check);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private class colorHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent evt) {
            if (evt.getSource().equals(colorReplacement1)) {
                colorReplacement1.setStyle("-fx-background-color:" + getHex(colorReplacement1) + ";");
            } else if (evt.getSource().equals(colorReplacement2)) {
                colorReplacement2.setStyle("-fx-background-color:" + getHex(colorReplacement2) + ";");
            }
        }

        private String getHex(ColorPicker c) {
            if (c.getValue().hashCode() != -1 && c.getValue().hashCode() != 255) {
                return "#" + Integer.toHexString(c.getValue().hashCode()).substring(0, 6).toUpperCase();
            } else if (c.getValue().hashCode() == -1) {
                return "white";
            } else {
                return "black";
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
                    ((ChainFilter) chainfilterNormal).addFilter(new ColorBandFilter(ColorBand.RED));
                    added = true;
                    additional = ColorBand.RED.toString();
                } else if (toggleBandBlue.isSelected()) {
                    ((ChainFilter) chainfilterNormal).addFilter(new ColorBandFilter(ColorBand.BLUE));
                    added = true;
                    additional = ColorBand.BLUE.toString();
                } else if (toggleBandGreen.isSelected()) {
                    ((ChainFilter) chainfilterNormal).addFilter(new ColorBandFilter(ColorBand.GREEN));
                    added = true;
                    additional = ColorBand.GREEN.toString();
                } else {
                    Alert.display("FEHLER!", "Bitte wählen Sie RED,GREEN oder BLUE beim Colorpassfilter aus!");
                }
            } else if (e.getSource().equals(addBlur)) {
                int blurRadius = (int) sliderBlur.getValue();
                ((ChainFilter) chainfilterNormal).addFilter(new BlurFilter(blurRadius));
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
                ((ChainFilter) chainfilterNormal).addFilter(new ColorReplacementFilter(replace, replaceWith));
                int r1, g1, b1, r2, g2, b2;
                r1 = (replace >> 16) & 0xFF;
                g1 = (replace >> 8) & 0xFF;
                b1 = (replace) & 0xFF;
                r2 = (replaceWith >> 16) & 0xFF;
                g2 = (replaceWith >> 8) & 0xFF;
                b2 = (replaceWith) & 0xFF;

                additional = r1 + "," + g1 + "," + b1 + " ~> " + r2 + "," + g2 + "," + b2;
                added = true;
            } else if (e.getSource().equals(addPixel)) {
                int pixelWidth = (int) sliderPixel.getValue();
                ((ChainFilter) chainfilterNormal).addFilter(new PixelGraphicFilter(pixelWidth));
                additional += pixelWidth;
                added = true;
            } else if (e.getSource().equals(addThresh)) {
                String thresholdValues[] = textThreshold.getText().split(",");
                int tValues[] = new int[thresholdValues.length];
                for (int i = 0; i < tValues.length; i++) {
                    tValues[i] = Integer.parseInt(thresholdValues[i]);
                }
                ((ChainFilter) chainfilterNormal).addFilter(new ThresholdFilter(tValues));
                additional = textThreshold.getText();
                added = true;
            } else if (e.getSource().equals(addMono)) {
                ((ChainFilter) chainfilterNormal).addFilter(new MonochromFilter());
                added = true;
            } else if (e.getSource().equals(addInvert)) {
                ((ChainFilter) chainfilterNormal).addFilter(new InvertFilter());
                added = true;
            } else if (e.getSource().equals(addMutilation)) {
                ((ChainFilter) chainfilterNormal).addFilter(new PixelMutilation());
                added = true;
            } else if (e.getSource().equals(addMinimum)) {
                int pixelRadius = (int) sliderMinimum.getValue();
                ((ChainFilter) chainfilterNormal).addFilter(new MinimumFilter(pixelRadius));
                additional += pixelRadius;
                added = true;
            }
            if (added) {
                StringBuilder sb = new StringBuilder();
                sb.append(lblFilterAnwenden.getText());
                if (((ChainFilter) chainfilterNormal).getArrayList().size() != 1) {
                    sb.append(" -> ");
                }
                sb.append(((ChainFilter) chainfilterNormal).getArrayList().get(((ChainFilter) chainfilterNormal).getArrayList().size() - 1).getName());
                if (!additional.isEmpty()) {
                    sb.append("(").append(additional).append(")");
                }
                lblFilterAnwenden.setText(sb.toString());
            }
        }
    }

    public void uploadImage(ActionEvent actionEvent) {
        Image uploadImage = selectImage(1);
        if (uploadImage != null) {
            btbUploadImage.setVisible(false);
            imgInput.setImage(uploadImage);
        }
    }

    public void uploadMask(ActionEvent actionEvent) {
        Image uploadImage = selectImage(2);
        ;
        if (uploadImage != null) {
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

            try {
                image = ImageIO.read(new File(file.getAbsolutePath()));
                ImageIO.write(image, "bmp", new File(file.getAbsolutePath()));
                if (choose == 1) {
                    textUrlImage.setText(file.getAbsolutePath());
                } else {
                    textUrlMask.setText(file.getAbsolutePath());
                }

                imageFound = true;
            } catch (IOException e) {
                Alert.display("File was not found!", "File was not found!");
            }
        }
        Image uploadImage = null;
        if (imageFound) {
            uploadImage = SwingFXUtils.toFXImage(image, null);
        }
        return uploadImage;

    }


    public void savePicture(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BMP Image", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
                ImageIO.write(outputSave, "bmp", file);
        } else {
            Alert.display("Fehler beim Speichern!", "Es wurde keine Datei gespeichert, da kein korrekter Pfad angegeben wurde");
        }
    }

}
