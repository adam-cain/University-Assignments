import com.cyberbotics.webots.controller.Supervisor;

public class BugController {
    private static Supervisor robot;

    private static Telemetry telemetry;
    private static Dashboard dashboard;
    private static Navigation navigation;
    // Final Target
    private final static Point target = new Point(new Vector2D(2.75, -3.26), "Final Target");
    // Which side the robot should follow around obstacles
    private final static WallSide wallSide = WallSide.RIGHT;
    public static void main(String[] args) {
        robot = new Supervisor();
        int timeStep = (int) Math.round(robot.getBasicTimeStep());

        telemetry = new Telemetry(robot);
        dashboard = new Dashboard(robot.getDisplay("dashboard"), telemetry);
        dashboard.addTarget(target);
        
        navigation = new Bug1Navigation(robot, telemetry, dashboard, target, wallSide);
        // navigation = new Bug2Navigation(robot, telemetry, dashboard, target, wallSide);

        while (robot.step(timeStep) != -1) {
            telemetry.update();
            dashboard.paint();
            if(navigation.step()){
                break;
            }
        }
        navigation.stop();
        System.out.println("Distance Travelled: " + telemetry.getDistance());
        robot.simulationSetMode(Supervisor.SIMULATION_MODE_PAUSE);
        System.exit(0);
    }
}