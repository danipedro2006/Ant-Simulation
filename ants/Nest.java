package ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Nest {
	
	FloatPoint location;
	Graphics2D g2;
	
	public Nest(Field field) {
		int x=(int) ((Math.random()*(double) field.getWidth()*0.75)+field.getHeight()*0.25);
		int y=(int) ((Math.random()*(double) field.getWidth()*0.75)+field.getHeight()*0.25);
		
		location=new FloatPoint(x,y);
	}
	
	public void paint(Graphics g) {
		
		g2=(Graphics2D) g;
		Shape s=new Ellipse2D.Float((int)location.getX()-3, (int) location.getY()-2,6,6);
		g2.setColor(Color.BLUE);
		g2.fill(s);
		g2.draw(s);
	}
}
