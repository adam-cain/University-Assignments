package factory;

import factory.types.AlienType;
import gameObjects.AlienObjects.Alien;
import gameObjects.AlienObjects.Crab;
import gameObjects.AlienObjects.Octopus;
import gameObjects.AlienObjects.Squid;
import gameObjects.AlienObjects.UFO;

public class AlienFactory {
    public static Alien createAlien(AlienType type, int xPosition, int yPosition) {
        if (type == AlienType.SQUID) {
            return new Squid(xPosition, yPosition);
        } else if (type == AlienType.CRAB) {
            return new Crab(xPosition, yPosition);
        } else if (type == AlienType.OCTOPUS) {
            return new Octopus(xPosition, yPosition);
        } else if (type == AlienType.UFO) {
            return new UFO(xPosition, yPosition);
        } else {
            throw new IllegalArgumentException("Invalid alien type: " + type);
        }
    }
}