package gameObjects.BunkerObjects.BunkerShapes;

public class ClassicShape extends BunkerShape {
    public ClassicShape() {
        super(new int[][] {
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1},
            {1, 1, 0, 1, 1}
        });
    }
}

