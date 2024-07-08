package util;
import java.awt.Dimension; 

public class Image {
    private String src;
    private int width;
    private int height;

    public Image(String src, int width, int height) {
        this.src = src;
        this.width = width;
        this.height = height;
    }

    public String getSrc() {
        return src;
    }

    public Dimension getSize() {
        return new Dimension(width, height);
    }
}