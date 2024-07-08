
/**
 * The Point class represents a point in a two-dimensional space.
 */
class Point {
    private Vector2D position;
    private String text;
    private boolean hidden = false;

    /**
     * Constructs a Point object with the specified position and text.
     * 
     * @param position The position parameter is a Vector2D object that represents the position of the point.
     * @param text The text parameter is a String that represents the text associated with the point.
     */
    public Point(Vector2D position, String text) {
        this.position = position;
        this.text = text;
    }

    /**
     * Sets the position of the point.
     * 
     * @param position The position parameter is a Vector2D object that represents the new position of the point.
     */
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    /**
     * Returns the position of the point.
     * 
     * @return A Vector2D object representing the position of the point.
     */
    public Vector2D getPosition() {
        return this.position;
    }

    /**
     * Returns the text associated with the point.
     * 
     * @return A String representing the text associated with the point.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the hidden variable to true, hiding the point.
     */
    public void hide() {
        this.hidden = true;
    }

    /**
     * Sets the hidden variable to false, showing the point.
     */
    public void show() {
        this.hidden = false;
    }

    /**
     * Returns a boolean value indicating whether the point is hidden or not.
     * 
     * @return true if the point is hidden, false otherwise.
     */
    public boolean isHidden() {
        return this.hidden;
    }

    /**
     * Calculates the distance between the current point and another point.
     * 
     * @param other The other parameter is a Point object representing the other point.
     * @return The distance between the current point and the other point.
     */
    public double distanceTo(Point other) {
        return this.position.distanceTo(other.getPosition());
    }

    /**
     * Calculates the distance between the current point and a target position.
     * 
     * @param target The target parameter is a Vector2D object representing the target position.
     * @return The distance between the current point and the target position.
     */
    public double distanceTo(Vector2D target) {
        return this.position.distanceTo(target);
    }
}