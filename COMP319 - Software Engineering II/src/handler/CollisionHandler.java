package handler;

import handler.GameEvents.CollisionEvent;
import handler.GameEvents.GameEvent;
import interfaces.EventHandler;

public class CollisionHandler implements EventHandler {
    private EventHandler nextHandler;

    // Private constructor to prevent instantiation from outside
    private CollisionHandler(EventHandler nextHandler) {
        this.nextHandler = nextHandler;
    }


    @Override
    public void handleEvent(GameEvent event) {
        if (event instanceof CollisionEvent) {
            // Handle the collision event
            handleCollision((CollisionEvent) event);
        } else if (nextHandler != null) {
            // Delegate to the next handler if this handler cannot process the event
            nextHandler.handleEvent(event);
        }
    }

    // Method to handle collision events
    private void handleCollision(CollisionEvent event) {
        
    }
}
