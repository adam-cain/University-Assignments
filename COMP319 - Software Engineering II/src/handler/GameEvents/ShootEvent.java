package handler.GameEvents;

import interfaces.Shooting;

public class ShootEvent extends GameEvent {
    private Shooting source;

    public ShootEvent(Shooting source) {
        super(EventType.COLLISION);
        this.source = source;
    }

    public Shooting getSource() {
        return source;
    }
}
