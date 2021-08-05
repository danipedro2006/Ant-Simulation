package ants;

public class Ant {
	
	public enum AntState {
		FORAGING, HAULINH, FOLLOWING, EATING;
	}
	
	double x;
	double y;
	private AntState state;
	public int vector;
	public boolean alive;
	public static int number;
	String name;
	public Point target;
	public Trail myTrail;
}
