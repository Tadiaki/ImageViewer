package dk.easv.ui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.easv.logic.Slideshow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImageViewerWindowController implements Initializable
{
    private final List<Image> images = new ArrayList<>();
    public Slider sliderSpeed;
    public Label lblImageTitle;
    public Label lblRed;
    public Label lblGreen;
    public Label lblBlue;
    private int currentImageIndex = 0;
    private int speed;

    private Slideshow ss;

    @FXML
    Parent root;

    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleBtnLoadAction()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty())
        {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
    }

    @FXML
    private void handleBtnPreviousAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex =
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }

    @FXML
    private void handleBtnNextAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }

    private void displayImage()
    {
        if (!images.isEmpty())
        {
            imageView.setImage(images.get(currentImageIndex));
            lblImageTitle.setText(images.get(currentImageIndex).getUrl());
        }
    }

    public void handleBtnSlideshowAction(ActionEvent actionEvent) {
        ss = new Slideshow(images, (int) sliderSpeed.getValue());
        ss.valueProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setImage(newValue);
        });
        ss.messageProperty().addListener((observable, oldValue, newValue) -> {
            lblImageTitle.setText(newValue);
        });
        ss.titleProperty().addListener((observable, oldValue, newValue) -> {
            lblRed.setText(newValue);
            lblGreen.setText(newValue);
            lblBlue.setText(newValue);
        });

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(ss);
    }

    public void handleBtnSlideshowStopAction(ActionEvent actionEvent) {
        ss.makeIsSlideShowRunningFalse();
    }

}