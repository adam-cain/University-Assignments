import com.cyberbotics.webots.controller.Supervisor;

/**
 * The Bug1Navigation class is a subclass of Navigation that implements the Bug
 * 2 algorithm for robot navigation.
 */
/**
 * The Bug2Navigation class extends the Navigation class and provides the implementation
 * for the Bug2 navigation algorithm. It controls the movement of the robot by switching
 * between different states and executing corresponding actions based on the current state.
 */
public class Bug2Navigation extends Navigation {

    private Vector2D robotStart;

    /**
     * Constructs a Bug2Navigation object with the specified parameters.
     *
     * @param robot     The Supervisor object representing the robot.
     * @param telemetry The Telemetry object for collecting robot data.
     * @param dashboard The Dashboard object for displaying information.
     * @param target    The target point that the robot is trying to reach.
     */
    public Bug2Navigation(Supervisor robot, Telemetry telemetry, Dashboard dashboard, Point target, WallSide wallSide) {
        super(robot, telemetry, dashboard, target, wallSide);
        robotStart = robotStartPosition.getPosition();
        dashboard.addLine(robotStart, target.getPosition());
    }

    /**
     * The step() method controls the movement of the robot by switching between different
     * states and executing corresponding actions based on the current state.
     *
     * @return true if the robot has reached the target, false otherwise.
     */
    @Override
    public boolean step() {
        if (hasReachedTarget()) {
            state = MoveState.STOP;
            stop();
            return true;
        } else if (robotStart == null) {
            robotStart = telemetry.getRobotPose().toVector();
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
            case STOP:
                stop();
                return true;
            default:
                break;
        }
        return false;
    }

    /**
     * The traverseObstacle() method traverses an obstacle by following its wall until
     * it reaches the MLine.
     */
    protected void traverseObstacle() {
        initializeWallFollowing();
        if (hasLeftWallFollowingStart && isOnMLine()) {
            state = MoveState.GO_TO_TARGET;
            wallFollowingInitialized = false;
            System.out.println("Reached MLine");
            return;
        } else if (hasLeftStart()) {
            hasLeftWallFollowingStart = true;
            System.out.println("Left start");
        }
        followWall();
    }

    /**
     * The initializeWallFollowing() method initializes the wall following algorithm by
     * storing the robot's starting position and setting a flag.
     */
    private void initializeWallFollowing() {
        if (!wallFollowingInitialized) {
            wallFollowingStart = telemetry.getRobotPose().toVector();
            hasLeftWallFollowingStart = false;
            wallFollowingInitialized = true;
        }
    }

    /**
     * The isOnMLine() method checks if the robot is approximately on the M Line, the line
     * between the start and finish of the navigation.
     *
     * @return true if the robot is approximately on the M Line, false otherwise.
     */
    public boolean isOnMLine() {
        // Calculate the slopes
        double slopeAB = calculateSlope(robotStart, target);
        double slopeAC = calculateSlope(robotStart, telemetry.getRobotPose().toVector());
        double slopeBC = calculateSlope(target, telemetry.getRobotPose().toVector());

        // Check if C is collinear with A and B
        if (isApproximatelyEqual(slopeAB, slopeAC) && isApproximatelyEqual(slopeAB, slopeBC)) {
            // Check if C's coordinates are within the range of A and B
            return isBetween(robotStart.getX(), target.getX(), telemetry.getRobotPose().toVector().getX()) &&
                    isBetween(robotStart.getY(), target.getY(), telemetry.getRobotPose().toVector().getY());
        }
        return false;
    }

    /**
     * The calculateSlope() method calculates the slope between two points in a 2D vector space.
     *
     * @param p1 The first point on a 2D coordinate system.
     * @param p2 The second point on a 2D coordinate system.
     * @return The slope of the line passing through the two given points.
     */
    public static double calculateSlope(Vector2D p1, Vector2D p2) {
        if (isApproximatelyEqual(p1.getX(), p2.getX()))
            return Double.MAX_VALUE;
        return (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    }

    /**
     * The isApproximatelyEqual() method checks if two double values are approximately equal
     * within a given tolerance.
     *
     * @param a The first double value to compare.
     * @param b The second double value to compare.
     * @return true if the two double values are approximately equal, false otherwise.
     */
    public static boolean isApproximatelyEqual(double a, double b) {
        // Check if two double values are approximately equal
        double tolerance = 0.05;
        return Math.abs(a - b) < tolerance;
    }

    /**
     * The isBetween() method checks if a given value is between two other values.
     *
     * @param robotStart The starting position of the robot.
     * @param target     The target value that the robot is trying to reach or stay within.
     * @param robot      The current position of the robot.
     * @return true if the robot is between the robotStart and target values, false otherwise.
     */
    public static boolean isBetween(double robotStart, double target, double robot) {
        // Check if robot is between robotStart and target
        return (robot >= Math.min(robotStart, target) && robot <= Math.max(robotStart, target));
    }
}
