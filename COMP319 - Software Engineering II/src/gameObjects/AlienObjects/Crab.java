package gameObjects.AlienObjects;

import util.Image;

public class Crab  extends ShootingAlien{
    private static Image sprite = new Image("src/assets/crab.png", 40, 40);
    public Crab(int xPosition, int yPosition) {
        super(xPosition, yPosition, sprite, 20);
    }

    @Override
    public int getProjectileSpeed() {
        return 2;
    }
}
