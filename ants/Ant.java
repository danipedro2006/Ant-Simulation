package ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class Ant extends JComponent implements Runnable {
	
	public enum AntState {
		FORAGING, HAULING, FOLLOWING, EATING;
	}
	
	double x;
	double y;
	private AntState state;
	public int vector;
	public boolean alive;
	public static int number;
	public String name;
	public FloatPoint myTarget;
	public FloatPoint location;
	public Trail myTrail;
	public Food myFood;
	public Graphics2D g2;
	public Thread t;
	public int delay=20;
	private Field parent;
	
	
	public Ant (Field field, FloatPoint p) {
		parent=field;
		state=AntState.FORAGING;
		location=p;
		//vector represent clockwise motion direction 0-7, -1 no motion, 0-N, 1-NE, 2-E, 3-SE
		vector=-1;
		alive=true;
		t=new Thread(this);
		t.setName("ant"+ ++number);
		t.start();
		name="Ant #"+number;
		
	}
	
	public void run() {
		while(alive) {
			switch (state) {
			case FORAGING:
				//move randomly, look for food
				location=moveFrom(location);
				if(senseFood()) {
					state=AntState.EATING;
				}
				Food foodHint=parent.senseTrail(location);
				if(foodHint !=null) {
					state=AntState.FOLLOWING;
					myTarget=foodHint.location;
					myTrail=foodHint.trail;
				}
				break;
				
			case HAULING:
				//move to nest, leave chemical trail
				
				if(location.distance(parent.nest.location)<5) {
					state=AntState.FORAGING;
				}
				else {
					myFood.trail.addStep(location);
					location=moveToTrig(parent.nest.location);
				}
				break;
				
			case FOLLOWING:
				if(location.distance(myTarget)<5) {
					state=AntState.FORAGING;
					myTarget=null;
				}
				else {
					location=moveToTrig(myTarget);
					myTrail.decay(location);
				}
				break;
				
			case EATING:
				//take a bite and haul
				myFood=parent.FoodNear(location);
				if(myFood !=null) {
					myFood.takeBite();
					state=AntState.HAULING;
				}
				else {
					System.out.println(name+" : where did the food go?");
					state=AntState.FORAGING;
				}
				break;
			}
			try {
				Thread.currentThread().sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	private FloatPoint moveToTrig(FloatPoint location2) {
		 double ax=location.getX();
		 double ay=location.getY();
		 double bx=location2.getX();
		 double by=location2.getY();
		 double dx=bx-ax;
		 double dy=by-ay;
		 double course=0;
		 double tangent;
		 double sx;
		 double sy;
		 if(dx>=0) {
			 if(dy>=0) {
				 tangent=Math.atan(Math.abs(dy/dx));
				 course=Math.PI/2;
				 sx=Math.sin(Math.PI-course);
				 sy=Math.sin(course-Math.PI/2);
			 }
			 else {
				 tangent=Math.atan(Math.abs(dy/dx));
				 course=Math.PI/2+tangent;
				 sx=Math.sin(course);
				 sy=-Math.sin(Math.PI/2-course);
			 }
		 }
		 else {
			 if(dy>=0) {
				 tangent=Math.atan(Math.abs(dy/dx));
				 course=3*Math.PI/2-tangent;
				 sx=-Math.sin(2*Math.PI-course);
				 sy=-Math.sin((course-3*Math.PI)/2);
			 }
			 else {
				 tangent=Math.atan(Math.abs(dy/dx));
				 course=3*Math.PI/2+tangent;
				 sy=-Math.sin(course-(3*Math.PI)/2);
				 sx=-Math.sin(2*Math.PI-course);
			 }
		 }

		 return new FloatPoint((ax+sx),(ay+sy));
	}
private FloatPoint moveTo(FloatPoint floatpoint) {
	double ax=location.getX();
	 double ay=location.getY();
	 double bx=floatpoint.getX();
	 double by=floatpoint.getY();
	 double dx=bx-ax;
	 double dy=by-ay;
	 double sx;
	 double sy;
	 double scaleFactor=Math.max(Math.abs(dx), Math.abs(dy));
	 sx=dx/scaleFactor;
	 sy=dy/scaleFactor;
	 double nx=((ax+sx));
	 double ny=((ay+sy));
		return new FloatPoint(nx, ny);
	}


	private boolean senseFood() {
		 if(parent.isFoodNear(location)) {
			 return true;
		 }
		 else {
			 return false;
		 }
		
	}

	private FloatPoint moveFrom(FloatPoint location2) {
		 if(vector==-1) {
			 vector=(int) (Math.random()*8.0)+1;
		 }
		 else {
			 int turn=(int) (Math.random()*3.0)-1;
			 vector=vector+turn;
			 if(vector>7) {
				 vector=vector%8;
			 }
			 if(vector<0) {
				 vector=vector+8;
			 }
		 }
		 
		 double x=location.getX();
		 double y=location.getY();
		 switch (vector) {
		 case 0:
			 y=y-1;
			 break;
			 
		 case 1:
			 x=x+1;
			 y=y-1;
			 break;
		 case 2:
			 x=x+1;
			 break;
		 case 3:
			 x=x+1;
			 y=y+1;
			 break;
		 case 4:
			 y=y+1;
			 break;
		 case 5:
			 x=x-1;
			 y=y+1;
			 break;
		 case 6:
			 x=x-1;
			 break;
		 case 7:
			 x=x-1;
			 y=y-1;
		 }
		 if(x>parent.getWidth()) x=parent.getWidth()-1;
		 if(x<0) x=0;
		 if(y>parent.getY()) y=parent.getHeight()-1;
		 if(y<0) y=0;
		 location=new FloatPoint(x,y);
		return location;
	}
	
	public void paint(Graphics g) {
		Color c;
		switch (state) {
		case EATING:
			c=Color.yellow;
			break;
			
		case HAULING:
			c=Color.pink;
			break;
			
		case FOLLOWING:
			c=Color.white;
			break;
			
		default:
			c=Color.black;
			
		}
		
		g2=(Graphics2D) g;
		Shape n=new Ellipse2D.Float((int) location.getX()-1, (int) location.getY()-1,2,2);
		g2.setPaint(c);
		g2.fill(n);
		g2.draw(n);
	
	}
}
