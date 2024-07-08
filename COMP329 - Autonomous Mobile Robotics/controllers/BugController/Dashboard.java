import java.util.ArrayList;
import java.util.List;

import com.cyberbotics.webots.controller.Display;

/**
 * The Dashboard class represents a graphical dashboard for displaying robot telemetry data and visualizing the robot's environment.
 * It provides methods for painting the dashboard, drawing the robot, drawing detected walls, drawing targets, and drawing lines.
 * The dashboard is initialized with a display and telemetry object.
 */
public class Dashboard {
  private Display display;

  private int deviceWidth;
  private int deviceHeight;
  private double scaleFactor;

  private Telemetry telemetry;

  private double radius;

  private double lastUpdateTime = 0;
  private final double updateInterval = 0.2;

  private List<Vector2D> detectedWall;
  private List<Vector2D> robotTrail;
  private List<Point> points;
  private List<Line> lines;

  private final static int DARKGREY = 0x3C3C3C;
  private final static int BLACK = 0x000000;
  private final static int WHITE = 0xFFFFFF;
  private final static int RED = 0xFF0000;
  private final static int GREEN = 0x00FF00;
  private final static int BLUE = 0x0000FF;
  private final static int ORANGE = 0xFFA500;

    /**
   * Constructs a Dashboard object with the specified display and telemetry.
   *
   * @param display the display used to render the dashboard
   * @param telemetry the telemetry object containing robot data
   * @throws IllegalArgumentException if the display is null or not found
   */
  public Dashboard(Display display, Telemetry telemetry) {
    if (display == null) {
      throw new IllegalArgumentException("Display is null or not found");
    }
    this.display = display;
    this.display.setFont("Arial", 14, true);
    this.telemetry = telemetry;
    this.radius = telemetry.getRadius();
    this.deviceWidth = this.display.getWidth();
    this.deviceHeight = this.display.getHeight();
    this.scaleFactor = Math.min(this.deviceWidth, this.deviceHeight) / (2 * (telemetry.getMaxRange() + this.radius));

    detectedWall = new ArrayList<>();
    robotTrail = new ArrayList<>();
    points = new ArrayList<>();
    lines = new ArrayList<>();
  }

    /**
   * Paints the dashboard by rendering the arena, robot, sensors, detected walls, robot trail, points and lines.
   */
  public void paint() {
    Pose robotPose = this.telemetry.getRobotPose();
    double robotTheta = robotPose.getTheta();

    // Add the robot's current position to the trail
    Vector2D currentPosition = new Vector2D(robotPose.getX(), robotPose.getY());
    robotTrail.add(currentPosition);

    // limit the size of the trail
    // int maxTrailSize = 100; // Adjust as needed
    // if (robotTrail.size() > maxTrailSize) {
    // robotTrail.remove(0); // Remove the oldest position
    // }

    drawArena();
    drawRobot(robotPose, robotTheta);
    processAndDrawSensors(robotPose, robotTheta);
    drawRobotTrail();
    drawTargets();
    drawDetectedWalls();
    drawLines();
  }

  /**
   * Draws the arena boundaries on the display.
   */
  private void drawArena() {
    this.display.setColor(0xF0F0F0); // Off White
    this.display.fillRectangle(0, 0, this.deviceWidth, this.deviceHeight);
    this.display.setColor(BLACK);
    double width = scale(12);
    double height = scale(9);
    double x = (deviceWidth - width) / 2;
    double y = (deviceHeight - height) / 2;
    this.display.drawRectangle((int) x, (int) y, (int) width, (int) height);
    this.display.drawRectangle((int) x, (int) y, (int) width + 1, (int) height + 1);
    this.display.drawRectangle((int) x - 1, (int) y - 1, (int) width, (int) height);
  }

  /**
   * Draws the detected walls on the display.
   */
  private void drawDetectedWalls() {
    for (Vector2D coord : detectedWall) {
      this.display.setColor(RED);
      this.display.fillOval((int) coord.getX(), (int) coord.getY(), 5, 5);
    }
    this.display.setColor(BLACK);
  }

  /**
   * Draws the targets on the display.
   */
  private void drawTargets() {
    for (Point point : points) {
      if (!point.isHidden()) {
        Vector2D position = point.getPosition();
        this.display.setColor(GREEN);
        this.display.fillOval(mapX(position.getX()), mapY(position.getY()), 10, 10);
        this.display.setColor(BLACK);
        this.display.drawText(point.getText(), mapX(position.getX()) + 15, mapY(position.getY()) - 6);
      }
    }
  }

  /**
   * Adds a target point to the dashboard.
   *
   * @param point the target point to be added
   */
  public void addTarget(Point point) {
    this.points.add(point);
  }

  /**
   * Adds a line to the dashboard.
   * 
   * @param start the starting point of the line
   * @param end the ending point of the line
   */
  public void addLine(Vector2D start, Vector2D end){
    this.lines.add(new Line(start, end));
  }

  /**
   * Draws lines on the display.
   */
  public void drawLines() {
    this.display.setColor(ORANGE);
    for (Line line : lines) {
      Vector2D start = line.getStart();
      Vector2D end = line.getEnd();
      this.display.drawLine(mapX(start.getX()), mapY(start.getY()), mapX(end.getX()), mapY(end.getY()));
    }
    this.display.setColor(BLACK);
  }

  /**
   * Draws the trail of the robot on the display.
   * The trail is represented by a series of line segments connecting the previous and current positions of the robot.
   * The color of the trail is set to black.
   */
  private void drawRobotTrail() {
    this.display.setColor(BLACK); // Set color for the trail
    for (int i = 1; i < robotTrail.size(); i++) {
      Vector2D prev = robotTrail.get(i - 1);
      Vector2D current = robotTrail.get(i);
      int x1 = mapX(prev.getX());
      int y1 = mapY(prev.getY());
      int x2 = mapX(current.getX());
      int y2 = mapY(current.getY());
      this.display.setColor(BLUE);
      this.display.drawLine(x1, y1, x2, y2);
    }
  }

  /**
   * Draws the robot on the dashboard.
   * 
   * @param robotPose The pose (position and orientation) of the robot.
   * @param robotTheta The angle of the robot's orientation.
   */
  private void drawRobot(Pose robotPose, double robotTheta) {
    this.display.setColor(WHITE);
    this.display.fillOval(mapXFromRobot(), mapYFromRobot(), scale(this.radius), scale(this.radius));
    this.display.setColor(DARKGREY);
    this.display.drawOval(mapXFromRobot(), mapYFromRobot(), scale(this.radius), scale(this.radius));
    this.display.drawLine(mapXFromRobot(), mapYFromRobot(), mapXFromRobot(Math.cos(robotTheta) * this.radius),
        mapYFromRobot(Math.sin(robotTheta) * this.radius));
  }

  /**
   * Processes and draws sensor data for the given robot pose and theta.
   * 
   * @param robotPose The pose of the robot.
   * @param robotTheta The theta value of the robot.
   */
  private void processAndDrawSensors(Pose robotPose, double robotTheta) {
    Sensor[] sensors = telemetry.getSensors();

    for (Sensor sensor : sensors) {
      double distance = sensor.getValue();
      setColorBasedOnDistance(distance);
      Pose sensorPose = sensor.getPose();
      double[] xd = calculateXCoordinates(sensorPose, distance);
      double[] yd = calculateYCoordinates(sensorPose, distance);
      drawSensorData(xd, yd, robotTheta, distance);
      addDetectedWall(xd[3], yd[3], robotTheta, distance);
    }
  }

  /**
   * Adds a detected wall to the list of detected walls if the distance is less than 4.5 units.
   * 
   * @param x The x-coordinate of the detected wall.
   * @param y The y-coordinate of the detected wall.
   * @param theta The angle of the detected wall.
   * @param distance The distance to the detected wall.
   */
  private void addDetectedWall(double x, double y, double theta, double distance) {
    double currentTime = telemetry.getTime();
    if ((currentTime - lastUpdateTime) >= updateInterval && distance < 4.5) {
      lastUpdateTime = currentTime;
      detectedWall.add(new Vector2D(mapXFromRobot(rotX(x, y, theta)), mapYFromRobot(rotY(x, y, theta))));
    }
  }

  /**
   * Sets the color of the display based on the given distance.
   * If the distance is less than the minimum wall distance, the color is set to RED.
   * Otherwise, the color is set to BLACK.
   *
   * @param distance the distance to be checked against the minimum wall distance
   */
  private void setColorBasedOnDistance(double distance) {
    if (distance < telemetry.getMinWallDistance()) {
      this.display.setColor(RED);
    } else {
      this.display.setColor(BLACK);
    }
  }

  /**
   * Calculates the x-coordinates of the sensor polygon based on the sensor pose and distance.
   * 
   * @param sensorPose the pose of the sensor
   * @param distance the distance value
   * @return an array of x-coordinates
   */
  private double[] calculateXCoordinates(Pose sensorPose, double distance) {
    double[] xd = new double[4];
    xd[0] = sensorPose.getX();
    xd[1] = sensorPose.getX() + (distance * Math.cos(sensorPose.getTheta() - (Math.PI / 18.0)));
    xd[2] = sensorPose.getX() + (distance * Math.cos(sensorPose.getTheta() + (Math.PI / 18.0)));
    xd[3] = sensorPose.getX() + (distance * Math.cos(sensorPose.getTheta()));
    return xd;
  }

  /**
   * Calculates the y-coordinates of the sensor polygon based on the sensor pose and distance.
   * 
   * @param sensorPose the pose of the sensor
   * @param distance the distance value
   * @return an array of y-coordinates
   */
  private double[] calculateYCoordinates(Pose sensorPose, double distance) {
    double[] yd = new double[4];
    yd[0] = sensorPose.getY();
    yd[1] = sensorPose.getY() + (distance * Math.sin(sensorPose.getTheta() - (Math.PI / 18.0)));
    yd[2] = sensorPose.getY() + (distance * Math.sin(sensorPose.getTheta() + (Math.PI / 18.0)));
    yd[3] = sensorPose.getY() + (distance * Math.sin(sensorPose.getTheta()));
    return yd;
  }

  /**
   * Draws the sensor data on the dashboard.
   * 
   * @param xd The x-coordinates of the sensor data.
   * @param yd The y-coordinates of the sensor data.
   * @param robotTheta The angle of the robot.
   * @param distance The distance measured by the sensor.
   */
  private void drawSensorData(double[] xd, double[] yd, double robotTheta, double distance) {
    int[] xArc = new int[4];
    int[] yArc = new int[4];

    for (int j = 0; j < 4; j++) {
      xArc[j] = mapXFromRobot(rotX(xd[j], yd[j], robotTheta));
      yArc[j] = mapYFromRobot(rotY(xd[j], yd[j], robotTheta));

      // Tried to implement a full polygon clipping algorithm like Sutherland-Hodgman
      // But didnt work out, so I just clipped the coordinates manually, which is not
      // very elegant, but works for now compared to without.

      // Clip X coordinate
      if (xArc[j] < 0) {
        xArc[j] = 0;
      } else if (xArc[j] > deviceWidth) {
        xArc[j] = deviceWidth;
      }

      // Clip Y coordinate
      if (yArc[j] < 0) {
        yArc[j] = 0;
      } else if (yArc[j] > deviceHeight) {
        yArc[j] = deviceHeight;
      }
    }

    this.display.setOpacity(0.5);
    this.display.fillPolygon(xArc, yArc, 3);
    this.display.setOpacity(1);
    this.display.drawText(String.format("%.02f", distance), xArc[3], yArc[3]);
  }

  /**
   * Scales a given value based on the scaleFactor.
   * 
   * @param l the value to be scaled
   * @return the scaled value
   */
  private int scale(double l) {
    return (int) (this.scaleFactor * l);
  }

  /**
   * Maps the x-coordinate from the robot's frame of reference to the screen's frame of reference.
   * 
   * @param x the x-coordinate in the robot's frame of reference
   * @return the mapped x-coordinate in the screen's frame of reference
   */
  private int mapXFromRobot(double x) {
    double robotX = telemetry.getRobotPose().getX();
    return (int) ((deviceWidth / 2.0) + scale(x + robotX));
  }

  /**
   * Maps the y-coordinate from the robot's pose to the screen coordinate system.
   * 
   * @param y the y-coordinate to be mapped
   * @return the mapped y-coordinate in the screen coordinate system
   */
  private int mapYFromRobot(double y) {
    double robotY = telemetry.getRobotPose().getY();
    return (int) ((deviceHeight / 2.0) - scale(y + robotY));
  }

  /**
   * Maps the X coordinate of the robot's pose to the corresponding X coordinate on the screen.
   * 
   * @return The mapped X coordinate on the screen.
   */
  private int mapXFromRobot() {
    double robotX = telemetry.getRobotPose().getX();
    return (int) ((deviceWidth / 2.0) + scale(robotX));
  }

  /**
   * Maps the Y coordinate of the robot's pose to the Y coordinate on the map.
   * 
   * @return The mapped Y coordinate on the map.
   */
  private int mapYFromRobot() {
    double robotY = telemetry.getRobotPose().getY();
    return (int) ((deviceHeight / 2.0) - scale(robotY));
  }

  /**
   * Maps a given x-coordinate to the corresponding screen position.
   * 
   * @param x the x-coordinate to be mapped
   * @return the mapped screen position
   */
  private int mapX(double x) {
    return (int) ((deviceWidth / 2.0) + scale(x));
  }

  /**
   * Maps the given y-coordinate to the corresponding screen position.
   * 
   * @param y the y-coordinate to be mapped
   * @return the mapped screen position
   */
  private int mapY(double y) {
    return (int) (deviceHeight - ((deviceHeight / 2.0) + scale(y)));
  }

  /**
   * Rotates the given coordinates (x, y) by the specified angle (theta) around the origin.
   *
   * @param x The x-coordinate of the point.
   * @param y The y-coordinate of the point.
   * @param theta The angle (in radians) by which the point should be rotated.
   * @return The rotated x-coordinate.
   */
  private double rotX(double x, double y, double theta) {
    return Math.cos(theta) * x - Math.sin(theta) * y;
  }

  /**
   * Rotates the given coordinates (x, y) by the specified angle (theta) around the origin.
   *
   * @param x the x-coordinate of the point.
   * @param y the y-coordinate of the point.
   * @param theta The angle (in radians) by which the point should be rotated.
   * @return the rotated y-coordinate.
   */
  private double rotY(double x, double y, double theta) {
    return Math.sin(theta) * x + Math.cos(theta) * y;
  }

    /**
   * Represents a line segment in a two-dimensional space.
   */
  class Line {
    private Vector2D start;
    private Vector2D end;

    /**
     * Constructs a Line object with the specified start and end points.
     *
     * @param start the starting point of the line
     * @param end the ending point of the line
     */
    public Line(Vector2D start, Vector2D end) {
      this.start = start;
      this.end = end;
    }

    /**
     * Returns the starting point of the line.
     *
     * @return the starting point of the line
     */
    public Vector2D getStart() {
      return this.start;
    }

    /**
     * Returns the ending point of the line.
     *
     * @return the ending point of the line
     */
    public Vector2D getEnd() {
      return this.end;
    }
  }
}
