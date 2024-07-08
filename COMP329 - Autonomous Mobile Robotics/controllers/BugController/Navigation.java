import com.cyberbotics.webots.controller.Motor;
import com.cyberbotics.webots.controller.Supervisor;

/**
 * The Navigation class is an abstract class that provides methods for controlling the movement of the
 * robot, including functions for navigating to a target position, following a wall, and aligning to a
 * wall.
 */
public abstract class Navigation {

    // State machine states for movement
    protected enum MoveState {
        STOP,
        FOLLOW_WALL,
        GO_TO_TARGET,
        ALLIGN_TO_WALL,
        GO_TO_CLOSEST_POINT,
        ALLIGN_TO_WALL_ON_DISTANCE_TO_POINT
    };

    private final double WHEEL_RADIUS = 0.0957; // in meters - found using CONFIGURE
    private final double MAX_TURN_SPEED = 5.0;
    private final double MAX_ANGLE = Math.PI; // in radians

    private double maxVelocity;

    protected MoveState state;

    protected Dashboard dashboard;
    protected Telemetry telemetry;
    protected Vector2D target;

    private Motor leftMotor;
    private Motor rightMotor;

    protected boolean hasLeftWallFollowingStart;
    protected Vector2D wallFollowingStart;
    protected boolean wallFollowingInitialized = false;

    // PID controller variables
    private double Kp = 0.4;
    private double Ki = 0.0;
    private double Kd = 0.0;
    private double prev_error;
    private double total_error;

    protected Point robotStartPosition;

    protected WallSide wallSide;

    // The above code is a constructor for a Navigation class in Java. It takes in
    // several parameters
    // including a Supervisor object, Telemetry object, Dashboard object, and a
    // Point object representing
    // the target position.
    public Navigation(Supervisor robot, Telemetry telemetry, Dashboard dashboard, Point target, WallSide wallSide) {
        this.telemetry = telemetry;
        this.dashboard = dashboard;
        this.target = target.getPosition();
        this.wallSide = wallSide;
        this.robotStartPosition = new Point(telemetry.getRobotPose().toVector(), "Start Position");
        dashboard.addTarget(robotStartPosition);
        this.state = MoveState.GO_TO_TARGET;

        this.leftMotor = robot.getMotor("left wheel");
        this.rightMotor = robot.getMotor("right wheel");
        this.leftMotor.setPosition(Double.POSITIVE_INFINITY);
        this.rightMotor.setPosition(Double.POSITIVE_INFINITY);
        this.leftMotor.setVelocity(0.0);
        this.rightMotor.setVelocity(0.0);

        this.maxVelocity = this.leftMotor.getMaxVelocity() - 0.1;
    }

    /**
     * The function "step" is an abstract method that returns a boolean value. This function
     * will be called on every time step of the simulation and will be used to control the movement
     * and navigation of the robot.
     * 
     * @return A boolean value is being returned signifying if the robot has reached
     *         the target.
     */
    public abstract boolean step();

    /**
     * The function "goToTarget" directs the robot to face the target coordinates
     * and then drive towards it.
     * Stopping when a wall is in front of the robot and transitioning to the next
     * state.
     */
    protected void goToTarget() {
        if (wallInFront()) {
            System.out.println("Wall in front");
            state = MoveState.ALLIGN_TO_WALL;
        } else if (!facingTarget()) {
            faceTarget();
        } else {
            moveForward();
        }
    }

    /**
     * The function checks if the robot has reached its target by comparing the
     * distance between its
     * current pose and the target pose.
     * 
     * @return The method is returns a boolean value.
     */
    protected boolean hasReachedTarget() {
        return telemetry.getRobotPose().distanceTo(target) < 0.1;
    }

    /**
     * The function checks if the robot has moved away from the starting position
     * while wall following.
     * 
     * @return The method is returns a boolean value.
     */
    protected boolean hasLeftStart() {
        return !hasLeftWallFollowingStart && telemetry.getRobotPose().distanceTo(wallFollowingStart) > 0.5;
    }

    /**
     * The "stop" function sets the velocity of both the left and right motors to
     * 0.0, effectively stopping
     * the movement of the robot.
     */
    protected void stop() {
        leftMotor.setVelocity(0.0);
        rightMotor.setVelocity(0.0);
    }

    /**
     * The function sets the velocity of two motors based on a base velocity and a
     * control adjustment, with
     * a maximum velocity limit.
     * 
     * @param base    The base parameter represents the velocity of the wheels in
     *                meters per second. It is the
     *                main velocity that we want to set for the wheels.
     * @param control The "control" parameter is an adjustment on the main velocity.
     *                It allows you to
     *                run each wheels velocity at diffrent speeds causing it to
     *                turn.
     */
    public void setVelocity(double base, double control) {
        // base gives the velocity of the wheels in m/s
        // control is an adjustment on the main velocity
        double base_av = (base / this.WHEEL_RADIUS);
        double lv = base_av;
        double rv = base_av;

        if (control != 0) {
            double control_av = (control / this.WHEEL_RADIUS);
            // Check if we exceed max velocity and compensate
            double correction = 1;
            lv = base_av - control_av;
            rv = base_av + control_av;

            if (lv > this.maxVelocity) {
                correction = this.maxVelocity / lv;
                lv = lv * correction;
                rv = rv * correction;
            }

            if (rv > this.maxVelocity) {
                correction = this.maxVelocity / rv;
                lv = lv * correction;
                rv = rv * correction;
            }
        }
        if (lv > maxVelocity) {
            lv = maxVelocity;
        }
        if (rv > maxVelocity) {
            rv = maxVelocity;
        }
        this.leftMotor.setVelocity(lv);
        this.rightMotor.setVelocity(rv);
    }

    /**
     * The moveForward() function calculates the speed based on the distance
     * recorded by the forward sensor * values and sets the velocity of the robot so
     * that it is faster when further away from a obstacle.
     */
    private void moveForward() {
        double minSensorVal = telemetry.getMinValueOfSensorsInRange(SensorLocation.FRONT_LEFT_50,
                SensorLocation.FRONT_RIGHT_MINUS_50);

        // Calculate the speed based on the minimum sensor value
        double speed = Math.max(0.2,
                0.4 * Math.min(minSensorVal, 0.2 * target.distanceTo(telemetry.getRobotPose().toVector())));

        setVelocity(speed, 0);
    }

    /**
     * The function "allignToWall" aligns the robot to a wall based on the specified
     * wall side and moves to
     * the next state when the sensors detect that the robot is aligned.
     * 
     * @param wallSide  The wallSide parameter is an enum that represents which side
     *                  of the robot the wall
     *                  is on. It can have two possible values: LEFT or RIGHT.
     * @param nextState The `nextState` parameter is the state that the robot should
     *                  transition to after
     *                  aligning to the wall. It represents the next action or
     *                  behavior that the robot should perform once
     *                  it has successfully aligned itself to the wall.
     */
    protected void allignToWall(WallSide wallSide, MoveState nextState) {
        Double[] sensorvalues;
        if (wallSide == WallSide.LEFT) {
            sensorvalues = telemetry.getValuesOfSensorsInRange(SensorLocation.BACK_LEFT_90,
                    SensorLocation.FRONT_LEFT_90);
            leftMotor.setVelocity(0.5);
            rightMotor.setVelocity(-0.5);
        } else {
            sensorvalues = telemetry.getValuesOfSensorsInRange(SensorLocation.FRONT_RIGHT_MINUS_90,
                    SensorLocation.BACK_RIGHT_MINUS_90);
            leftMotor.setVelocity(-0.5);
            rightMotor.setVelocity(0.5);
        }

        if (sensorvalues[0] < 1 && sensorvalues[1] < 1 && Math.abs(sensorvalues[0] - sensorvalues[1]) <= 0.01) {
            state = nextState;
        }
    }

    /**
     * The function calculates the control signal for a proportional-integral-derivative (PID)
     * controller based on the error and the controller's gains.
     * 
     * @param error The error parameter represents the difference between the
     *              desired value and the actual
     *              value in a control system. It is used to calculate the control
     *              signal in the PID
     *              (Proportional-Integral-Derivative) controller.
     * @return The method is returning the control signal, which is a double value.
     */
    private double pid(double error) {
        double prop = error; // Proportional term
        double diff = error - this.prev_error; // Differential term
        this.total_error += error; // Integral term
        this.prev_error = error;

        double control = (Kp * prop) + (Ki * this.total_error) + (Kd * diff); // Calculate control signal

        // Invert the control for the right wallSide
        if (wallSide == WallSide.RIGHT) {
            control = -control;
        }
        return control; // Return the control signal
    }

    /**
     * The `followWall()` function adjusts the velocity and steering of the robot to follow a wall 
     * at a specified distance.
     */
    protected void followWall() {
        final double robotLinearVelocity = 0.2;
        final double setPoint = 0.4;
        double turnAdjustment = 0.05;
        double closeWallAdjustment = 0.4;

        SensorLocation sensor1 = (wallSide == WallSide.LEFT) ? SensorLocation.FRONT_LEFT_90
                : SensorLocation.FRONT_RIGHT_MINUS_90;
        SensorLocation sensor2 = (wallSide == WallSide.LEFT) ? SensorLocation.BACK_LEFT_90
                : SensorLocation.BACK_RIGHT_MINUS_90;
        SensorLocation rangeStart = (wallSide == WallSide.LEFT) ? SensorLocation.FRONT_LEFT_50
                : SensorLocation.FRONT_LEFT_30;
        SensorLocation rangeEnd = (wallSide == WallSide.LEFT) ? SensorLocation.FRONT_RIGHT_MINUS_30
                : SensorLocation.FRONT_RIGHT_MINUS_50;
        turnAdjustment = (wallSide == WallSide.LEFT) ? turnAdjustment : -turnAdjustment;
        closeWallAdjustment = (wallSide == WallSide.LEFT) ? -closeWallAdjustment : closeWallAdjustment;

        if (telemetry.getMinValueOfSensorsInRange(rangeStart, rangeEnd) < setPoint) {
            this.setVelocity(robotLinearVelocity / 3, closeWallAdjustment);
            return;
        }

        double averageDistance = (telemetry.getSensorValue(sensor1) + telemetry.getSensorValue(sensor2)) / 2;

        if (averageDistance <= setPoint) {
            double error = averageDistance - setPoint;
            adjustSteering(this.pid(error), telemetry.getSensorValue(sensor1) - telemetry.getSensorValue(sensor2));
        } else {
            this.setVelocity(robotLinearVelocity, turnAdjustment);
        }
    }

    /**
     * The adjustSteering function adjusts the steering of the robot based on a PID
     * output and a secondary
     * error, taking into account a base speed and maximum control adjustment.
     * 
     * @param pidOutput      The pidOutput parameter represents the output of a PID
     *                       controller. It is a value
     *                       that indicates the control adjustment needed to correct
     *                       the error between the desired and actual
     *                       values.
     * @param secondaryError The secondaryError parameter represents the error or
     *                       difference between the
     *                       desired orientation and the current orientation of the
     *                       robot. It is used to calculate the
     *                       orientation adjustment factor, which influences the
     *                       control adjustment.
     */
    private void adjustSteering(double pidOutput, double secondaryError) {
        double baseSpeed = 0.2; // Base speed of the robot
        double maxControlAdjustment = 0.3; // Maximum control adjustment
        double orientationAdjustmentFactor = 0.1; // Influence of orientation

        // Calculate control adjustment
        double controlAdjustment = Math.min(Math.max(pidOutput, -maxControlAdjustment), maxControlAdjustment);
        double orientationAdjustment = secondaryError * orientationAdjustmentFactor;

        // Combine control adjustments
        double totalControl = controlAdjustment + orientationAdjustment;

        // Apply the base speed and control adjustment to the robot's motors
        setVelocity(baseSpeed, totalControl);
    }

    /**
     * The function checks if there is a wall in front of the robot by comparing the
     * sensor values to the
     * minimum wall distance.
     * 
     * @return The method is returning a boolean value.
     */
    private boolean wallInFront() {
        Double[] sensorvalues = telemetry.getValuesOfSensorsInRange(SensorLocation.FRONT_LEFT_50,
                SensorLocation.FRONT_RIGHT_MINUS_50);
        for (Double value : sensorvalues) {
            if (value <= telemetry.getMinWallDistance()) {
                return true;
            }
        }
        return false;
    }

    /**
     * The function "faceTarget" adjusts the turn speed of two motors to face a
     * target based on the current
     * pose and target bearing.
     */
    private void faceTarget() {
        Pose currentPose = telemetry.getRobotPose();
        double targetBearing = calculateTargetBearing(currentPose, target);

        double angleDifference = Math.IEEEremainder(targetBearing - currentPose.getTheta(), 2 * Math.PI);
        boolean clockwise = angleDifference > 0;

        // Proportional control: adjust turn speed based on the angle difference
        double proportionalSpeed = Math.min(Math.abs(angleDifference) / MAX_ANGLE * MAX_TURN_SPEED, MAX_TURN_SPEED);

        if (clockwise) {
            leftMotor.setVelocity(-proportionalSpeed);
            rightMotor.setVelocity(proportionalSpeed);
        } else {
            leftMotor.setVelocity(proportionalSpeed);
            rightMotor.setVelocity(-proportionalSpeed);
        }
    }

    /**
     * The function determines whether the robot is facing the target within a
     * certain angle threshold.
     * 
     * @return The method is returning a boolean value, which indicates whether the
     *         robot is facing the
     *         target or not.
     */
    private boolean facingTarget() {
        Pose currentPose = telemetry.getRobotPose();
        double targetBearing = calculateTargetBearing(currentPose, target);
        double robotOrientation = currentPose.getTheta();
        double angleDifference = Math.IEEEremainder(targetBearing - robotOrientation, 2 * Math.PI);
        return Math.abs(angleDifference) < 0.15;
    }

    /**
     * The function calculates the target bearing angle from the robot's current
     * pose to a given target point.
     * 
     * @param robotPose   The robotPose parameter represents the current pose
     *                    (position and orientation) of
     *                    the robot. It contains the x and y coordinates of the
     *                    robot's position, as well as the orientation
     *                    angle (theta) of the robot.
     * @param targetPoint The targetPoint parameter is a Vector2D object that
     *                    represents the coordinates of
     *                    the target point. It contains the x and y coordinates of
     *                    the target point.
     * @return The method is returning the angle to the target point in radians.
     */
    private double calculateTargetBearing(Pose robotPose, Vector2D targetPoint) {
        // Unpack the poses
        double x1 = robotPose.getX();
        double y1 = robotPose.getY();
        // double theta1 = robotPose.getTheta();

        double x2 = targetPoint.getX();
        double y2 = targetPoint.getY();

        // Calculate direction vector
        double dx = x2 - x1;
        double dy = y2 - y1;

        // Calculate the angle to the target
        double angleToTarget = Math.atan2(dy, dx);

        return angleToTarget;
    }
}
