package gameObjects.AlienObjects;

import util.Image;

public class Octopus extends ShootingAlien{
    private static Image sprite = new Image("src/assets/octopus.png", 40, 40);
    public Octopus(int xPosition, int yPosition) {
        super(xPosition, yPosition, sprite, 10);
    }
    @Override
    public int getProjectileSpeed() {
        return 1;
    }
}
