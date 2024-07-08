package gameObjects.BunkerObjects.BunkerShapes;

public class ArrowShape extends BunkerShape {
    public ArrowShape() {
        super(new int[][] {
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1}
        });
    }
}

