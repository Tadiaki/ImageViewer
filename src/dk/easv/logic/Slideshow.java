package dk.easv.logic;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.util.List;

public class Slideshow extends Task<Image> {

    private List<Image> images;
    private int imageNumber;


    public Slideshow() {
    }

    @Override
    protected Image call() throws Exception {
        Image imageList = images.get(0);

        for (Image image : images) {
            imageNumber++;
            images.get(imageNumber);
            updateValue(image);
            System.out.println("Image Number: " + imageNumber);
            return image;
        }


        return imageList;
    }
}
