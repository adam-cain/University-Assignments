import com.cyberbotics.webots.controller.DistanceSensor;

/**
 * The Sensor class is a wrapper class for the DistanceSensor class.
 * It provides additional functionality such as scaling the sensor value.
 */
public class Sensor {
  private DistanceSensor sensor;
  private Pose pose;
  private SensorLocation location;
  private double maxRange;
  private double maxValue;

  /**
   * Constructs a Sensor object with the specified parameters.
   * 
   * @param sensor   the distance sensor used by the sensor
   * @param pose     the pose of the sensor
   * @param location the location of the sensor
   */
  public Sensor(DistanceSensor sensor, Pose pose, SensorLocation location) {
    this.sensor = sensor;
    this.pose = pose;
    this.location = location;

    double[] lookupTable = sensor.getLookupTable();
    maxRange = lookupTable[0];
    maxValue = sensor.getMaxValue();

    double[] lt = sensor.getLookupTable();
    this.maxRange = 0.0;
    for (int i = 0; i < lt.length; i++) {
      if ((i % 3) == 0)
        this.maxRange = lt[i];
    }
    this.maxValue = sensor.getMaxValue();
  }

  /**
   * Returns the value of the sensor after applying a scaling factor.
   * The scaling factor is calculated as the difference between the maximum range
   * and the product of the maximum range and the maximum value, divided by the
   * value of the sensor.
   * 
   * @return the scaled value of the sensor
   */
  public double getValue() {
    return maxRange - (maxRange / maxValue * sensor.getValue());
    // return sensor.getValue();
  }

  /**
   * Returns a string representation of the Sensor object.
   * The string includes the location and the sensor name.
   *
   * @return a string representation of the Sensor object.
   */
  public String toString() {
    return location.toString() + "(" + location.getSensorName() + ")";
  }

  /**
   * Returns the pose of the sensor.
   *
   * @return the pose of the sensor
   */
  public Pose getPose() {
    return pose;
  }

  /**
   * Returns the DistanceSensor object.
   *
   * @return the DistanceSensor object.
   */
  public DistanceSensor getSensor() {
    return sensor;
  }

  /**
   * Returns the location of the sensor.
   *
   * @return the location of the sensor
   */
  public SensorLocation getLocation() {
    return location;
  }

  /**
   * Returns the maximum range of the sensor.
   *
   * @return the maximum range of the sensor
   */
  public double getMaxRange() {
    return maxRange;
  }

  /**
   * Returns the maximum value of the sensor.
   *
   * @return the maximum value of the sensor
   */
  public double getMaxValue() {
    return maxValue;
  }
}