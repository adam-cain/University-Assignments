package gameObjects.AlienObjects;

import gameObjects.GameObject;
import interfaces.Shooting;
import util.Image;
import util.ProjectileDirection;

public abstract class ShootingAlien extends Alien implements Shooting{
    public ShootingAlien(int xPosition, int yPosition, Image sprite, int points) {
        super(xPosition, yPosition, sprite, points);
    }

    @Override
    public ProjectileDirection getProjectileDirection(){
        return ProjectileDirection.DOWN;
    }

    @Override
    public GameObject getThis(){
        return this;
    }
}
