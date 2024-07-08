// Enum representing the states in the state machine for movement
public enum MoveState {
    STOP,
    FOLLOW_WALL,
    GO_TO_TARGET,
    ALLIGN_TO_WALL,
    GO_TO_CLOSEST_POINT,
    ALLIGN_TO_WALL_ON_DISTANCE_TO_POINT
};