package gameObjects.AlienObjects;

import gameObjects.GameObject;
import interfaces.Collidable;
import interfaces.Moving;
import util.Image;

public abstract class Alien extends GameObject implements Moving, Collidable{
    protected int points;
    protected boolean isAlive;


    public Alien(int xPosition, int yPosition, Image sprite, int points) {
        super(xPosition, yPosition, sprite);
        this.points = points;
        this.isAlive = true;
    }

    public void hit() {
        this.isAlive = false;
    }

    public int getPoints() {
        return points;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
