package interfaces;

import gameObjects.GameObject;
import gameObjects.ProjectileObjects.Projectile;
import util.ProjectileDirection;

public interface Shooting extends Collidable{
    int getProjectileSpeed();
    ProjectileDirection getProjectileDirection();
    GameObject getThis();
    default public Projectile shoot() {
        if(getProjectileSpeed() > 0){
            return new Projectile(getX(), getY(), getProjectileSpeed() ,getProjectileDirection(), getThis());
        }
        return null;
    }
}