package interfaces;
public interface Moving extends Positionable {
    default void translate(int deltaX, int deltaY){
        setX(getX() + deltaX);
        setY(getY() + deltaY);
    }
}
