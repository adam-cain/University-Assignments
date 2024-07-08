package core;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import factory.AlienFactory;
import factory.types.AlienType;
import gameObjects.AlienObjects.ShootingAlien;
import gameObjects.ProjectileObjects.Projectile;
import ui.ViewController;

public class AlienSwarm {
    private List<ShootingAlien> aliens;
    private int currentLevel;

    //Swarm Edges
    private int leftEdgePos = Integer.MAX_VALUE;
    private int rightEdgePos = Integer.MIN_VALUE;
    private int bottomEdgePos = Integer.MIN_VALUE;

    private int xDirection = 1;

    public AlienSwarm(int currentLevel) {
        this.currentLevel = currentLevel;
        this.aliens = new ArrayList<>();
        buildSwarm();
    }

    public List<ShootingAlien> getAliens() {
        return aliens;
    }

    private void buildSwarm() {
        int horizontalSpacing = 20;
        int verticalSpacing = 40;
        int columns = 11;
        int rows = 5;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                ShootingAlien alien;
                int xPosition = col * horizontalSpacing;
                int yPosition = row * verticalSpacing;

                if (row == 0) {
                    alien = (ShootingAlien) AlienFactory.createAlien(AlienType.SQUID, xPosition, yPosition);
                } else if (row == 1 || row == 2) {
                    alien = (ShootingAlien) AlienFactory.createAlien(AlienType.CRAB, xPosition, yPosition);
                } else {
                    alien = (ShootingAlien) AlienFactory.createAlien(AlienType.OCTOPUS, xPosition, yPosition);
                }
                aliens.add(alien);
            }
        }
        updateSwarmEdges();
    }

    public void moveSwarm() {
        ViewController gc = ViewController.getInstance();
        Dimension windowSize = gc.getWindowSize();

        int yDirection = 0;

        if (rightEdgePos >= windowSize.getWidth() - 40) {
            xDirection = 1;
            yDirection = 1;
        } else if (leftEdgePos <= 40 ) {
            xDirection = -1;
            yDirection = 1;
        }
        for (ShootingAlien alien : aliens) {
            alien.translate(xDirection * currentLevel, yDirection * currentLevel);
        }
    }

    public void updateSwarmEdges() {
        for (ShootingAlien alien : aliens) {
            if (alien.getX() < leftEdgePos) {
                leftEdgePos = alien.getX();
            }
            if (alien.getX() > rightEdgePos) {
                rightEdgePos = alien.getX();
            }
            if (alien.getY() < bottomEdgePos) {
                bottomEdgePos = alien.getY();
            }
        }
    }
    
    public Projectile randomshoot() {
        Random random = new Random();
        int shooterIndex = random.nextInt(aliens.size());
        ShootingAlien alien = aliens.get(shooterIndex);
        return alien.shoot();
    }

    public void removeDeadAliens() {
        aliens.removeIf(alien -> !alien.isAlive());
        updateSwarmEdges();
    }
}
