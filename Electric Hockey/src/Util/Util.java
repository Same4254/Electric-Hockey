package Util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Charges.Charge;

public class Util {
	public static float getPosAngle(Vector vector) {
		float angle = (float) Math.toDegrees(Math.atan2(vector.getXComp(), vector.getYComp()));
		angle -= 90;
		
		if(angle < 0)
			angle += 360;
		return angle;
	}
	
	public static float getPosAngle(Vector current, Vector target) {
		float angle = (float) Math.toDegrees(Math.atan2(target.getXPos() - current.getXPos(), target.getYPos() - current.getYPos()));
		angle -= 90;
		
		if(angle < 0)
			angle += 360;
		return angle;
	}
	
	public static double calculateForce(Charge charge1, Charge charge2) {
		return calculateForce(charge1.getRectangle().getX(), charge1.getRectangle().getY(), charge2.getRectangle().getX(), charge2.getRectangle().getY());
	}
	
	public static double calculateForce(double x1, double y1, double x2, double y2) {
//		System.out.println(((1.6 * Math.pow(10, -19) * 1.6 * Math.pow(10, -19) * 8.99 * Math.pow(10, 9)) / Math.pow(distance(x1, y1, x2, y2), 2) * Math.pow(10, 28)));
//		System.out.println(1 / Math.pow(distance(x1, y1, x2, y2), 2));
		
		return ((1.6 * Math.pow(10, -19) * 1.6 * Math.pow(10, -19) * 8.99 * Math.pow(10, 9)) / Math.pow(distance(x1, y1, x2, y2), 2) * Math.pow(10, 28));
	}
	
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + 
				 Math.pow((y2 - y1), 2));
	}
	
	public static Vector getForceVector(Charge charge1, Charge charge2) {
		return getForceVector(charge1.getCenter().getXPos(), charge1.getCenter().getYPos(), charge2);
	}
	
	public static Vector getForceVector(double x1, double y1, Charge charge) {
		Vector force = new Vector();
		
		force.setMagnitude(Util.calculateForce(x1, y1, charge.getCenter().getXPos(), charge.getCenter().getYPos()));
		
		force.setXPos(x1);
		force.setYPos(y1);
		force.setAngle(charge.getCenter());
		
		return force;
	}
	
	public static void main(String[] args) {
		calculateForce(100, 100, 200, 200);
	}
}
