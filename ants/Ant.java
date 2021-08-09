package ants;

import java.awt.Graphics2D;

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
	public Food myFood;
	
	public Graphics2D g2;
	public Thread thread;
	public int delay=20;
	private Field parent;
}
