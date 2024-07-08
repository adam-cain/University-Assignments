package gameObjects.AlienObjects;

import util.Image;

public class UFO extends Alien {
    private static Image sprite = new Image("src/assets/UFO.png", 40, 40);

    public UFO(int xPosition, int yPosition) {
        super(xPosition, yPosition, sprite, (int) (Math.random() * 100));
    }
}
