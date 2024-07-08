package gameObjects.AlienObjects;

import util.Image;

public class Squid extends ShootingAlien{
    private static Image sprite = new Image("src/assets/squid.png", 40, 40);
    public Squid(int xPosition, int yPosition) {
        super(xPosition, yPosition, sprite, 40);
    }

    @Override
    public int getProjectileSpeed() {
        return 3;
    }
}
