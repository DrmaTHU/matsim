package pedCA.utility;

public class Constants {
	//General useful constants
	public static final double SQRT2 = Math.sqrt(2);
	
	//Constant for the random seed
	public static final long RANDOM_SEED = 1;
	
	//Constants for environment
	public static final int ENV_OBSTACLE = -1;
	public static final int ENV_WALKABLE_CELL = 0;
	public static final double MAX_FF_VALUE = Double.POSITIVE_INFINITY;
	
	//Constants for Conflict Management
	public static final double FRICTION_PROBABILITY = 0.7;

	public static final double CELL_SIZE = 0.4;
	
	//Constants for Pedestrian Model
	public static Double KS = 6.0;
	public static Double PHI = 1.0;
}