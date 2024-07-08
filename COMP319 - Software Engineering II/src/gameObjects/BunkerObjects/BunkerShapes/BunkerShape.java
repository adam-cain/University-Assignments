package gameObjects.BunkerObjects.BunkerShapes;

abstract class BunkerShape {
    private int[][] shapeMatrix;

    public BunkerShape(int[][] matrix) {
        if (!isMatrixUniform(matrix)) {
            throw new IllegalArgumentException("Matrix rows and columns must be of uniform size.");
        }
        this.shapeMatrix = matrix;
    }

    public int[][] getShapeMatrix() {
        return shapeMatrix;
    }

    public int getShapeWidth() {
        return shapeMatrix.length;
    }

    public int getShapeHeight() {
        return shapeMatrix[0].length;
    }

    private boolean isMatrixUniform(int[][] matrix) {
        if (matrix.length == 0) return true;

        int firstRowLength = matrix[0].length;
        for (int[] row : matrix) {
            if (row.length != firstRowLength) {
                return false;
            }
        }
        return true;
    }
}
