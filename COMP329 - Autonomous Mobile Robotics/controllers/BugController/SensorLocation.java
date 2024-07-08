// An Enum to help visualise the sensor locations on the robot
public enum SensorLocation {
    FRONT_LEFT_90("so0"), 
    FRONT_LEFT_50("so1"), 
    FRONT_LEFT_30("so2"), 
    FRONT_LEFT_10("so3"), 

    FRONT_RIGHT_MINUS_10("so4"), 
    FRONT_RIGHT_MINUS_30("so5"), 
    FRONT_RIGHT_MINUS_50("so6"), 
    FRONT_RIGHT_MINUS_90("so7"), 

    BACK_RIGHT_MINUS_90("so8"), 
    BACK_RIGHT_MINUS_130("so9"), 
    BACK_RIGHT_MINUS_150("so10"), 
    BACK_RIGHT_MINUS_170("so11"), 
	
    BACK_LEFT_170("so12"), 
    BACK_LEFT_150("so13"), 
    BACK_LEFT_130("so14"), 
    BACK_LEFT_90("so15");

    private final String sensorName;

    SensorLocation(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorName() {
        return sensorName;
    }

    // For Reference Of sensor naems locations etc
    // String[] sensorName = {
    // "so0", "so1", "so2", "so3",
    // "so4", "so5", "so6", "so7",

    // "so8", "so9", "so10", "so11",
    // "so12", "so13", "so14", "so15"
    // };

    // String[] sensorLocation = {
    // "Front Left 90", "Front Left 50", "Front Left 30", "Front Left 10",
    // "Front Right -10", "Front Right -30", "Front Right -50", "Front Right -90",

    // "Back Right -90", "Back Right -130", "Back Right -150", "Back Right -170",
    // "Back Left 170", "Back Left 150", "Back Left 130", "Back Left 90" };
}