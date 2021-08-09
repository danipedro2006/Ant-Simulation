package ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Food {
	
	Point location;
	public String  name;
	public int amount;
	public int index;
	static int number;
	Trail trail;
	Graphics2D g2;
	
	public Food(Field field) {
		amount=(int) (Math.random()*7.0+3.0);
		location=new Point((int) (Math.random()*field.getWidth()),(int) (Math.random()*field.getHeight()));
		name="Food #"+ ++number;
		index=number;
		
		
	}
	
public void paint(Graphics g) {
		
		g2=(Graphics2D) g;
		Shape n=new Ellipse2D.Float((int)location.getX()-amount/2, (int) location.getY()-amount/2,amount,amount);
		g2.setColor(Color.red);
		g2.fill(n);
		g2.draw(n);
	}

}
