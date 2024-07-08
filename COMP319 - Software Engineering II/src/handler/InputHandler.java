package handler;

import handler.GameEvents.GameEvent;
import interfaces.EventHandler;

public class InputHandler implements EventHandler {
    private EventHandler nextHandler;

    // Private constructor to prevent instantiation from outside
    private InputHandler(EventHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleEvent(GameEvent event) {
        switch (event.getType()) {
            case MOVE_RIGHT:
                movePlayerRight();
                break;
            case MOVE_LEFT:
                movePlayerLeft();
                break;
            case SHOOT:
                playerShoot();
                break;
            default:
                if (nextHandler != null) {
                    nextHandler.handleEvent(event);
                }
                break;
        }
    }

    private void movePlayerRight() {
        // Logic to move the player right
    }

    private void movePlayerLeft() {
        // Logic to move the player left
    }

    private void playerShoot() {
        // Logic for player shooting
    }
}
