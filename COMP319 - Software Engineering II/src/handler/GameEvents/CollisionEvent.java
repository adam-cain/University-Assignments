package handler.GameEvents;

import gameObjects.GameObject;

public class CollisionEvent extends GameEvent {
    private GameObject source;
    private GameObject target;

    public CollisionEvent(GameObject source, GameObject target) {
        super(EventType.COLLISION);
        this.source = source;
        this.target = target;
    }

    public GameObject getSource() {
        return source;
    }

    public GameObject getTarget() {
        return target;
    }
}
