package gameObjects;
import java.awt.Dimension;

import interfaces.Positionable;
import ui.ViewController;
import util.Image;

public abstract class GameObject implements Positionable{
    private int x, y; 
    private double width, height;
    private Image sprite;

    public GameObject(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        Dimension size = sprite.getSize();
        this.width = size.getWidth();
        this.height = size.getHeight();
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
    
    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    public void draw() {
        ViewController gc = ViewController.getInstance();
        gc.drawImage(sprite, x, y);
    }
}