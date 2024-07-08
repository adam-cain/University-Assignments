package interfaces;

import handler.GameEvents.GameEvent;

public interface EventHandler {
    void handleEvent(GameEvent event);
}

