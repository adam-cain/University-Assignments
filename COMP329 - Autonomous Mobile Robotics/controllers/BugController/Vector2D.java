/**
 * The Vector2D class represents a 2D vector and provides methods to get the x and y coordinates, as
 * well as calculate the distance between two vectors.
 */
/**
 * The Vector2D class represents a 2D vector with x and y coordinates.
 */
class Vector2D {
  private double x;
  private double y;

  /**
   * Constructs a Vector2D object with the specified x and y coordinates.
   *
   * @param x The x coordinate of the vector.
   * @param y The y coordinate of the vector.
   */
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the x coordinate of the vector.
   *
   * @return The x coordinate of the vector.
   */
  public double getX() {
    return this.x;
  }

  /**
   * Returns the y coordinate of the vector.
   *
   * @return The y coordinate of the vector.
   */
  public double getY() {
    return this.y;
  }

  /**
   * Calculates the distance between the current vector and another vector using the Euclidean distance formula.
   *
   * @param other The other vector to calculate the distance to.
   * @return The distance between the current vector and the other vector.
   */
  public double distanceTo(Vector2D other){
    return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
  }
}