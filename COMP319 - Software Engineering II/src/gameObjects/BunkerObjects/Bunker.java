package gameObjects.BunkerObjects;

import java.util.ArrayList;
import java.util.List;

import gameObjects.GameObject;
import gameObjects.BunkerObjects.BunkerShapes.BunkerShape;
import interfaces.Collidable;
import util.Image;

public class Bunker {

    private List<BunkerCube> cubes;

    public Bunker(int x, int y, BunkerShape shape) {
        cubes = new ArrayList<>();
        createBunkerCubes(x, y, shape);
    }

    private void createBunkerCubes(int x, int y, BunkerShape shape) {
        // Create individual bunker cubes and add them to the list
        int[][] shapeMatrix = shape.getShapeMatrix();
        for (int i = 0; i < shape.getShapeWidth(); i++) {
            for (int j = 0; j < shape.getShapeWidth(); j++) {
                if (shapeMatrix[i][j] == 1) {
                    BunkerCube cube = new BunkerCube(x + i, y + j);
                    cubes.add(cube);
                }
            }
        }
    }

    private class BunkerCube extends GameObject implements Collidable{
        private static Image spritePath = new Image("src/assets/bunkerCube.png", 40, 40);
        public BunkerCube(int x, int y) {
            super(x, y, spritePath);
        }

        @Override
        public void handleCollision() {
            
        }
    }
}
