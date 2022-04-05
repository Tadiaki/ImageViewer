package dk.easv.logic;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelReader;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Slideshow extends Task<Image> {

    private List<Image> images;
    private static int imageNumber = 0;
    private int delay;
    private Map<String, Integer> colCount;

    private boolean isSlideShowRunning = true;
    private Object RGBColor;

    public Slideshow(List<Image> images, int delay) {
        this.images = images;
        this.delay = delay;
    }

    @Override
    protected synchronized Image call() throws InterruptedException {
        while (isSlideShowRunning) {
            if (imageNumber == images.size()) {
                imageNumber = 0;
            }
            Image currentImage = images.get(imageNumber);
            updateMessage(currentImage.getUrl());
            updateValue(images.get(imageNumber++));

            int red = 0;
            int green = 0;
            int blue = 0;

            double imageHeightY = currentImage.getHeight();
            double imageWidthX = currentImage.getWidth();
            for (int i = 0; i < imageWidthX ; i++) {
                for (int j = 0; j < imageHeightY ; j++) {
                    int pixel = currentImage.getPixelReader().getArgb(i,j);
                    Color c = new Color(pixel, true);
                    int R = c.getRed();
                    int G = c.getGreen();
                    int B = c.getBlue();

                    if (R != 0) {
                        red = red + R;
                    }
                    if (G != 0) {
                        green = green + G;
                    }
                    if (B != 0) {
                        blue = blue + B;
                    }
                }
            }
            updateTitle("Red pixels: " + red/10000 + ", green pixels: " + green/10000 + ", blue pixels: " + blue/10000);
            System.out.println("Red: " + red/10000);
            System.out.println("Green: " + green/10000);
            System.out.println("Blue: " + blue/10000);
            System.out.println("Image Number: " + imageNumber);
            Thread.sleep(delay* 1000L);
        }
        return images.get(imageNumber-1);
    }


    public void makeIsSlideShowRunningFalse() {
        isSlideShowRunning = false;
    }

}
