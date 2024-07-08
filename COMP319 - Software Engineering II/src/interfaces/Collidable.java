package interfaces;

import gameObjects.ProjectileObjects.Projectile;

public interface Collidable extends Positionable {
    void handleCollision();

    default boolean collides(Projectile object) {
        // Check if the right edge of 'this' is left of the left edge of 'object'
        if (getX() + getWidth() <= object.getX()) {
            return false;
        }
    
        // Check if the left edge of 'this' is right of the right edge of 'object'
        if (getX() >= object.getX() + object.getWidth()) {
            return false;
        }
    
        // Check if the bottom edge of 'this' is above the top edge of 'object'
        if (getY() + getHeight() <= object.getY()) {
            return false;
        }
    
        // Check if the top edge of 'this' is below the bottom edge of 'object'
        if (getY() >= object.getY() + object.getHeight()) {
            return false;
        }
    
        // If none of the above, the objects intersect
        handleCollision();
        return true;
    }
}
