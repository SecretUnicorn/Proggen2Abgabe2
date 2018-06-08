package gui;

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
import java.awt.Color;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleBandBlue.setToggleGroup(COLORBANDGROUP);
        toggleBandGreen.setToggleGroup(COLORBANDGROUP);
        toggleBandRed.setToggleGroup(COLORBANDGROUP);
        toggleBandBlue.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        toggleBandRed.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        toggleBandGreen.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        colorReplacement1.addEventHandler(ActionEvent.ACTION, new MyButtonHandler());
        colorReplacement2.addEventHandler(ActionEvent.ACTION, new MyButtonHandler());


    }


    private class MyButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent evt) {
            if (evt.getSource().equals(colorReplacement1)) {
                String hex3 = "#" + Integer.toHexString(colorReplacement1.getValue().hashCode()).substring(0, 6).toUpperCase();
                //System.out.println(colorReplacement1.getValue().hashCode());
                colorReplacement1.setStyle("-fx-background-color:" + hex3 + "; -fx-text-fill: white;");
            } else if (evt.getSource().equals(colorReplacement2)) {
                String hex3 = "#" + Integer.toHexString(colorReplacement2.getValue().hashCode()).substring(0, 6).toUpperCase();
                colorReplacement2.setStyle("-fx-background-color:" + hex3 + "; -fx-text-fill: white;");
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
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(textUrlImage.getText()));
        } catch (IOException e){

        }


        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println(width+"<- ->"+height);

        int [] pixel = new int[width*height];
        int counter = 0;

        /*for (int i = 0; i < width; i++) {
           for (int l = 0; l < height; l++){
               counter++;
                pixel[counter-1] = image.getRGB(i,l);
           }
        }*/

            BufferedWriter bw = null;
            try{
                bw = new BufferedWriter(new FileWriter("ausgabe.txt"));
                System.out.println(pixel.length);
                for (int i = 0; i < width; i++) {
                    for (int l = 0; l < height; l++) {
                        counter++;
                        pixel[counter - 1] = image.getRGB(i, l);
                        bw.write(pixel[counter - 1] + "\n");
                    }
                }
                bw.close();
            } catch(IOException e){}
            finally {
                bw.close();
            }



    }


    public void savePicture(ActionEvent actionEvent) {
    }

    public void changeColor(ActionEvent actionEvent) {

    }
}
