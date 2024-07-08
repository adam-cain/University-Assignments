package handler;

import interfaces.EventHandler;
import handler.GameEvents.GameEvent;

public class EventDispatcher {
    private EventHandler firstHandler;

    public EventDispatcher(EventHandler firstHandler) {
        this.firstHandler = firstHandler;
    }

    public void dispatch(GameEvent event) {
        if (firstHandler != null) {
            firstHandler.handleEvent(event);
        }
    }
}

