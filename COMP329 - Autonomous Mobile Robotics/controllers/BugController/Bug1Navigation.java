import com.cyberbotics.webots.controller.Supervisor;

/**
 * The Bug1Navigation class is a subclass of Navigation that implements the Bug
 * 1 algorithm for robot navigation.
 */
public class Bug1Navigation extends Navigation {

    private double distanceAtStartOfWallFollowing;
    private double distanceTravelledAtClosestPoint;
    private Point closestPointToTarget;

    // This is the constructor of the `Bug1Navigation` class. It initializes a new instance of the class
    // and sets up the necessary variables and objects.
    public Bug1Navigation(Supervisor robot, Telemetry telemetry, Dashboard dashboard, Point target, WallSide wallSide) {
        super(robot, telemetry, dashboard, target, wallSide);
        this.closestPointToTarget = new Point(new Vector2D(Double.MAX_VALUE, Double.MAX_VALUE),
                "Closest point to target");
        closestPointToTarget.hide();
        dashboard.addTarget(closestPointToTarget);
    }

    /**
     * The function `step()` controls the movement of the robot by switching between
     * different states and executing corresponding actions based on the current state.
     * 
     * @return The method returns a boolean value representing if the robot has
     *         reached the target.
     */
    @Override
    public boolean step() {
        if (hasReachedTarget()) {
            state = MoveState.STOP;
            stop();
            return true;
        }
        switch (state) {
            case GO_TO_TARGET:
                goToTarget();
                break;
            case ALLIGN_TO_WALL:
                allignToWall(wallSide, MoveState.FOLLOW_WALL);
                break;
            case FOLLOW_WALL:
                traverseObstacle();
                break;
            case ALLIGN_TO_WALL_ON_DISTANCE_TO_POINT:
                allignToWallTowardsPoint();
                break;
            case GO_TO_CLOSEST_POINT:
                traverseObstacleToPoint();
                break;
            case STOP:
                stop();
                return true;
        }
        return false;
    }

    /**
     * The function follows the wall of an obstacle until it has completed a full
     * circle. Recording the
     * point closest to the target so it can be retraversed and found for the Bug 1
     * algorithm.
     */
    protected void traverseObstacle() {
        initializeWallFollowing();
        if (hasCompletedFullCircle()) {
            state = MoveState.ALLIGN_TO_WALL_ON_DISTANCE_TO_POINT;
            System.out.println("Made full circle");
            System.out.println("Distance to Point forwards: " + distanceTravelledAtClosestPoint);
            System.out.println("Distance to Point backwards "
                    + (telemetry.getDistance() - distanceAtStartOfWallFollowing - distanceTravelledAtClosestPoint));
            wallFollowingInitialized = false;
            return;
        } else if (hasLeftStart()) {
            hasLeftWallFollowingStart = true;
        }

        updateClosestPointToTarget();
        followWall();
    }

    /**
     * The function follows the wall of an obstacle until it reaches the target
     * point.
     */
    private void traverseObstacleToPoint() {
        if (telemetry.getRobotPose().distanceTo(closestPointToTarget) < 0.2) {
            state = MoveState.GO_TO_TARGET;
            System.out.println("Reached target");
            resetClosestPointToTarget();
            stop();
            return;
        }
        followWall();
    }

    /**
     * The function initializes the wall following behavior by setting up variables
     * and displaying the
     * closest point to the target.
     */
    private void initializeWallFollowing() {
        if (!wallFollowingInitialized) {
            wallFollowingInitialized = true;
            closestPointToTarget.setPosition(telemetry.getRobotPose().toVector());
            closestPointToTarget.show();
            wallFollowingStart = telemetry.getRobotPose().toVector();
            hasLeftWallFollowingStart = false;
            distanceAtStartOfWallFollowing = telemetry.getDistance();
        }
    }

    /**
     * The function resets the position and visibility of the closestPointToTarget
     * object.
     */
    private void resetClosestPointToTarget() {
        closestPointToTarget.setPosition(new Vector2D(Double.MAX_VALUE, Double.MAX_VALUE));
        closestPointToTarget.hide();
    }

    /**
     * The function updates the closest point to the target based on the current
     * robot pose and calculates
     * the distance travelled at that point.
     */
    private void updateClosestPointToTarget() {
        if (telemetry.getRobotPose().distanceTo(target) < closestPointToTarget.distanceTo(target)) {
            closestPointToTarget.setPosition(telemetry.getRobotPose().toVector());
            distanceTravelledAtClosestPoint = telemetry.getDistance() - distanceAtStartOfWallFollowing;
        }
    }

    /**
     * The function determines whether to align to the left or right wall based on
     * the distance travelled
     * at the closest point.
     */
    private void allignToWallTowardsPoint() {
        if (distanceTravelledAtClosestPoint < telemetry.getDistance() - distanceAtStartOfWallFollowing
                - distanceTravelledAtClosestPoint) {
            if (wallSide != WallSide.LEFT) {
                allignToWall(WallSide.LEFT, MoveState.GO_TO_CLOSEST_POINT);
            } else {
                state = MoveState.GO_TO_CLOSEST_POINT;
            }
        } else {
            if (wallSide != WallSide.RIGHT) {
                allignToWall(WallSide.RIGHT, MoveState.GO_TO_CLOSEST_POINT);
            } else {
                state = MoveState.GO_TO_CLOSEST_POINT;
            }
        }
    }

    /**
     * The function checks if the robot has completed a full circle by comparing its
     * current position to
     * the starting position.
     * 
     * @return The method is returning a boolean value.
     */
    private boolean hasCompletedFullCircle() {
        return hasLeftWallFollowingStart && telemetry.getRobotPose().distanceTo(wallFollowingStart) < 0.3;
    }
}
