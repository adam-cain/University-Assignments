package gameObjects.ProjectileObjects;
import gameObjects.GameObject;
import interfaces.Moving;
import interfaces.ProjectileBehavior;
import util.Image;

public class Projectile extends GameObject implements Moving {

    private ProjectileBehavior behavior;
    private GameObject shooter;

    public Projectile(int xPosition, int yPosition, ProjectileBehavior behavior, Image sprite, GameObject shooter) {
        super(xPosition, yPosition, sprite);
        this.behavior = behavior;
        this.shooter = shooter;
    }

    public void update() {
        behavior.execute(this);
    }

    public GameObject getShooter() {
        return shooter;
    }
}