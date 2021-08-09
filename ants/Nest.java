package ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Nest {
	
	Point location;
	Graphics2D g2;
	
	public Nest(Field field) {
		int x=10;
		int y=20;
		location=new Point(x,y);
	}
	
	public void paint(Graphics g) {
		
		g2=(Graphics2D) g;
		Shape s=new Ellipse2D.Float((int)location.getX()-3, (int) location.getY()-2,6,6);
		g2.setColor(Color.BLUE);
		g2.fill(s);
		g2.draw(s);
	}
}
