package dk.easv.logic;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.util.List;

public class Slideshow extends Task<Image> {

    private List<Image> images;
    private static int imageNumber = 0;
    private int delay;

    private boolean isSlideShowRunning = true;

    public Slideshow(List<Image> images, int delay) {
        this.images = images;
        this.delay = delay;
    }

    @Override
    protected Image call() throws InterruptedException {
        while (isSlideShowRunning) {
            if (imageNumber == images.size()) {
                imageNumber = 0;
            }
            updateValue(images.get(imageNumber++));
            System.out.println("Image Number: " + imageNumber);
            Thread.sleep(delay* 1000L);
        }
        return images.get(imageNumber-1);
    }

    public void makeIsSlideShowRunningFalse() {
        isSlideShowRunning = false;
    }

}
