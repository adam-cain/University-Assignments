package gameObjects; // Update this to match your new package structure

import util.ProjectileDirection;
import util.Image; 
import interfaces.Moving;
import interfaces.Shooting;
public class Player extends GameObject implements Moving, Shooting{
    private static Image sprite = new Image("src/assets/player.png", 40, 40);
    private int moveSpeed = 5;

    public Player() {
        super(0, -50, sprite);
    }

    public void moveLeft() {
        translate(-1 * moveSpeed, 0);
    }

    public void moveRight() {
        translate(moveSpeed, 0);
    }

    @Override
    public int getProjectileSpeed() {
        return 3;
    }

    @Override
    public ProjectileDirection getProjectileDirection() {
        return ProjectileDirection.UP;
    }

    @Override
    public GameObject getThis() {
        return this;
    }
}
