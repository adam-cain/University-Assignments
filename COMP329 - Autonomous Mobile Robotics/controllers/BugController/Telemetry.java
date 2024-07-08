import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.Node;
import com.cyberbotics.webots.controller.PositionSensor;
import com.cyberbotics.webots.controller.Supervisor;

/**
 * The Telemetry class provides methods to retrieve information about the
 * robot's telemetry data.
 */
public class Telemetry {
	private final static int MAX_NUM_SENSORS = 16;
	private final static double MIN_WALL_DISTANCE = 0.3;
	private final static double RADIUS = 0.215;
	private final double WHEEL_RADIUS = 0.0957; // in meters - found using CONFIGURE
	// private final double AXEL_LENGTH = 0.323; // in meters - found using
	// CONFIGURE
	private final double WHEEL_CIRCUM = 2 * Math.PI * WHEEL_RADIUS;
	private final double ENCODER_UNIT = WHEEL_CIRCUM / 6.28;

	private Supervisor robot;
	private Node robotNode; // reference to the robot node

	private PositionSensor leftMotorSensor;
	private PositionSensor rightMotorSensor;

	private double prevLeftPosSensorVal = 0.0;
	private double prevRightPosSensorVal = 0.0;
	private double distanceTravelled = 0.0;

	private Sensor[] sensors;
	private double maxRange;
	private double maxValue;
	/**
	 * Constructs a Telemetry object with the specified robot.
	 *
	 * @param robot the Supervisor object representing the robot
	 */
	public Telemetry(Supervisor robot) {
		this.robot = robot;
		this.robotNode = this.robot.getSelf();

		int timeStep = (int) Math.round(this.robot.getBasicTimeStep());

		// Initialise motor sensors
		leftMotorSensor = robot.getPositionSensor("left wheel sensor");
		rightMotorSensor = robot.getPositionSensor("right wheel sensor");
		leftMotorSensor.enable(timeStep);
		rightMotorSensor.enable(timeStep);
		// Initialize the sensors
		sensors = new Sensor[MAX_NUM_SENSORS];

		double[] psAngleDeg = {
				90, 50, 30, 10,
				-10, -30, -50, -90,

				-90, -130, -150, -170,
				170, 150, 130, 90
		};

		for (int i = 0; i < MAX_NUM_SENSORS; i++) {
			String sensorName = "so" + i;
			DistanceSensor sensor = robot.getDistanceSensor(sensorName);
			sensor.enable(timeStep);

			double theta = Math.toRadians(psAngleDeg[i]);
			Pose pose;
			if (i <= 7) {
				pose = new Pose(0.12 + Math.cos(theta) * RADIUS, Math.sin(theta) * RADIUS, theta);
			} else {
				pose = new Pose(-0.12 + Math.cos(theta) * RADIUS, Math.sin(theta) * RADIUS, theta);
			}

			sensors[i] = new Sensor(sensor, pose, SensorLocation.values()[i]);
		}

		Sensor s0Sensor = sensors[0];
		this.maxRange = s0Sensor.getMaxRange();
		this.maxValue = s0Sensor.getMaxValue();
	}

	/**
	 * Gets the current simulation time.
	 *
	 * @return the current simulation time in seconds
	 */
	public double getTime() {
		return robot.getTime();
	}

	/**
	 * Updates the telemetry data, including the distance travelled by the robot.
	 */
	public void update() {
		// Update distance travelled
		double leftMotorSensorVal = leftMotorSensor.getValue();
		double rightMotorSensorVal = rightMotorSensor.getValue();

		double leftMotorSensorDiff = leftMotorSensorVal - prevLeftPosSensorVal;
		double rightMotorSensorDiff = rightMotorSensorVal - prevRightPosSensorVal;

		if (leftMotorSensorDiff < 0.001) {
			leftMotorSensorDiff = 0.0;
			leftMotorSensorVal = prevLeftPosSensorVal;
		}
		if (rightMotorSensorDiff < 0.001) {
			rightMotorSensorDiff = 0.0;
			rightMotorSensorVal = prevRightPosSensorVal;
		}

		double leftWheelDistanceDiff = leftMotorSensorDiff * ENCODER_UNIT;
		double rightWheelDistanceDiff = rightMotorSensorDiff * ENCODER_UNIT;

		double averageWheelDistanceDiff = (leftWheelDistanceDiff + rightWheelDistanceDiff) / 2.0;
		distanceTravelled += averageWheelDistanceDiff;

		prevLeftPosSensorVal = leftMotorSensorVal;
		prevRightPosSensorVal = rightMotorSensorVal;
	}

	/**
	 * Gets the distance travelled by the robot.
	 *
	 * @return the distance travelled by the robot in meters
	 */
	public double getDistance() {
		return distanceTravelled;
	}

	/**
	 * Gets the pose (position and orientation) of the robot.
	 *
	 * @return the pose of the robot
	 */
	public Pose getRobotPose() {
		double[] realPos = robotNode.getPosition();
		double[] rot = robotNode.getOrientation();
		double theta1 = Math.atan2(-rot[0], rot[3]);
		double halfPi = Math.PI / 2;
		double theta2 = theta1 + halfPi;
		if (theta1 > halfPi) {
			theta2 = -(3 * halfPi) + theta1;
		}
		return new Pose(realPos[0], realPos[1], theta2);
	}

	/**
	 * Gets all the sensors of the robot.
	 *
	 * @return an array of Sensor objects representing the sensors of the robot
	 */
	public Sensor[] getSensors() {
		return sensors;
	}

	/**
	 * Gets the values of the sensors within the specified range of locations.
	 *
	 * @param firstIndex the first location index (inclusive)
	 * @param lastIndex  the last location index (inclusive)
	 * @return an array of Double objects representing the values of the sensors
	 *         within the specified range
	 */
	public Double[] getValuesOfSensorsInRange(SensorLocation firstIndex, SensorLocation lastIndex) {
		int first = firstIndex.ordinal();
		int last = lastIndex.ordinal();
		int sensorsLength = sensors.length; // Assuming 'sensors' is an array of Sensor objects

		// Calculate the number of elements to be retrieved
		int numberOfElements = (last - first + sensorsLength) % sensorsLength + 1;
		Double[] sensorsValues = new Double[numberOfElements];

		// Use modular arithmetic to handle both normal and wrap-around scenarios
		for (int i = 0; i < numberOfElements; i++) {
			int sensorIndex = (first + i) % sensorsLength;
			sensorsValues[i] = sensors[sensorIndex].getValue();
		}

		return sensorsValues;
	}

	/**
	 * Gets the minimum value among the sensors within the specified range of
	 * locations.
	 *
	 * @param firstIndex the first location index (inclusive)
	 * @param lastIndex  the last location index (inclusive)
	 * @return the minimum value among the sensors within the specified range
	 */
	public double getMinValueOfSensorsInRange(SensorLocation firstIndex, SensorLocation lastIndex) {
		double min = Double.MAX_VALUE;
		Double[] sensorsValues = getValuesOfSensorsInRange(firstIndex, lastIndex);
		for (Double value : sensorsValues) {
			if (value < min) {
				min = value;
			}
		}
		return min;
	}

	/**
	 * Gets the value of the sensor at the specified location.
	 *
	 * @param sensorLocation the location of the sensor
	 * @return the value of the sensor at the specified location
	 */
	public double getSensorValue(SensorLocation sensorLocation) {
		return sensors[sensorLocation.ordinal()].getValue();
	}

	/**
	 * Gets the minimum value among the sensors at the specified locations.
	 *
	 * @param sensorLocations the locations of the sensors
	 * @return the minimum value among the sensors at the specified locations
	 */
	public double getMaxRange() {
		return maxRange;
	}

	/**
	 * Gets the maximum value among the sensors at the specified locations.
	 *
	 * @param sensorLocations the locations of the sensors
	 * @return the maximum value among the sensors at the specified locations
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * Gets the radius of the robot.
	 *
	 * @return the radius of the robot in meters
	 */
	public double getRadius() {
		return RADIUS;
	}

	/**
	 * Gets the distance offset of the robot.
	 *
	 * @return the distance offset of the robot in meters
	 */
	public double getMinWallDistance() {
		return MIN_WALL_DISTANCE;
	}
}