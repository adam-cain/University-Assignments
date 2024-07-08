package handler.GameEvents;

public abstract class GameEvent {
    private EventType type;

    public GameEvent(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public enum EventType {
        COLLISION, MOVE_RIGHT, MOVE_LEFT, SHOOT, SHOOT_SOUND_EVENT
    }
}
