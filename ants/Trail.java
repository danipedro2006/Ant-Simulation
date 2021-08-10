package ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Date;
import java.util.Vector;

public class Trail {
	public Vector<FloatPoint> path;
	Graphics2D g2;
	Food food;
	Field field;
	long created;
	long maxTime=10000;//10 second
	
	public Trail(Field fld, Food f) {
		 this.field=fld;
		 path=new Vector<FloatPoint>();
		 this.food=f;
		 f.trail=this;
		 Date now=new Date();
		 created=now.getTime();
	}
	
	public void addStep(FloatPoint location) {
		FloatPoint p=new FloatPoint((int) location.getIntX(), (int) location.getIntY());
		path.add(p);
		Long now=new Date().getTime();
		created=now;
	}
	
	public void paint(Graphics g) {
		g2=(Graphics2D) g;
		g2.setColor(Color.yellow);
		for(int i=0;i<path.size();i++) {
			g2.draw(new Ellipse2D.Double(path.get(i).x, path.get(i).y, 1, 1));
		}
	}
	
	public synchronized void decay(FloatPoint location) {
		 Point p=new Point(location.getIntY(),location.getIntY());
		 path.remove(p);
		 Long now=new Date().getTime();
		 if(now-created>maxTime) {
			 path.removeAllElements();
		 }
		
	}
} 
