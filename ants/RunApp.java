package ants;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;

public class RunApp {
 
	public static void main(String[] args) {

		JFrame frame=new JFrame("Ant Simulation");
		Field field=new Field();
		frame.setResizable(false);
		Container c=frame.getContentPane();
		c.add(field,BorderLayout.CENTER);
	
		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		while(true) {
			try {
				Thread.currentThread().sleep(20);
				field.repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

}
