package gui;

import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleBandBlue.setToggleGroup(COLORBANDGROUP);
        toggleBandGreen.setToggleGroup(COLORBANDGROUP);
        toggleBandRed.setToggleGroup(COLORBANDGROUP);
    }

    


    public void uploadImage(ActionEvent actionEvent) {
        Image uploadImage = selectImage();
        if (uploadImage != null){
            btbUploadImage.setVisible(false);
            imgInput.setImage(uploadImage);
        }
    }

    public void uploadMask(ActionEvent actionEvent) {
        Image uploadImage = selectImage();
        if (uploadImage != null){
            btbUploadMask.setVisible(false);
            imgMask.setImage(uploadImage);
        }
    }

    public Image selectImage(){
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

    public void applyFilters(MouseEvent mouseEvent) {
        BufferedImage image = SwingFXUtils.fromFXImage(imgInput.getImage(), null);



    }


}
