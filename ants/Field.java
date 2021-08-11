package ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class Field extends JComponent {

	Graphics2D g2;
	public int foodSize = 50;
	public int antSize = 40;
	public Nest nest;
	public Ant[] ant = new Ant[antSize];
	public Trail[] trail = new Trail[foodSize];
	public Food[] food = new Food[foodSize];

	public Field() {
		this.setSize(600, 600);
		nest = new Nest(this);

		for (int i = 0; i < foodSize; i++) {
			food[i] = new Food(this);
			trail[i] = new Trail(this, food[i]);
		}
		
		for(int i=0;i<antSize;i++) {
			ant[i]=new Ant(this,nest.location);		}
	}

	public void paint(Graphics g) {
		g2 = (Graphics2D) g;
		Shape r = new Rectangle(600, 600);
		g2.setColor(Color.GREEN);
		g2.fill(r);
		g2.draw(r);
		nest.paint(g2);

		for (int i = 0; i < foodSize; i++) {
			trail[i].paint(g2);
			food[i].paint(g2);
		}
		
		for(int i=0;i<antSize;i++) {
			ant[i].paint(g2);
		}
		nest.paint(g2);

	}

	public boolean isFoodNear(FloatPoint location) {
		int x = (int) location.getX();
		int y = (int) location.getY();

		for (int i = 0; i < foodSize; i++) {
			if (food[i].amount > 0) {
				if (distance(location, food[i].location, food[i].amount)) {
					return true;
				}
			}
		}
		return false;
	}

	public Food FoodNear(FloatPoint location) {
		int x = (int) location.getX();
		int y = (int) location.getY();
		for (int i = 0; i < foodSize; i++) {

			if (distance(location, food[i].location, food[i].amount)) {
				return food[i];
			}
		}
		return null;
	}

	private boolean distance(FloatPoint p, FloatPoint location, int amount) {
		if (p.distance(location) - amount / 2 > 5) {
			return true;
		} else {
			return false;
		}
	}

	public Food senseTrail(FloatPoint location) {
		for(int i=0;i<foodSize;i++) {
			Trail t=trail[i];
			for(int j=0;j<t.path.size();j++) {
				if(location.distance(t.path.get(j))<1.0) {
					t.decay(location);
					return t.food;
				}
			}
		}
		return null;
	}
}
