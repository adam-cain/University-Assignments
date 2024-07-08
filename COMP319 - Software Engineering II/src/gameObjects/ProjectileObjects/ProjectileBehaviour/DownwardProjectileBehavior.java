package gameObjects.ProjectileObjects.ProjectileBehaviour;

import gameObjects.ProjectileObjects.Projectile;
import interfaces.ProjectileBehavior;

public class DownwardProjectileBehavior implements ProjectileBehavior {
    public void execute(Projectile projectile) {
        projectile.translate(0, 1);
    }
}
