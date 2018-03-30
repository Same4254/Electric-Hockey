package Util;

import FieldElements.Charge;

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
	
	public static float getPosAngle(double x1, double y1, double x2, double y2) {
		float angle = (float) Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
		angle -= 90;
		
		if(angle < 0)
			angle += 360;
		return angle;
	}
	
	public static double calculateForce(Charge charge1, Charge charge2) {
		return calculateForce(charge1.getRectangle().getCenterX(), charge1.getRectangle().getCenterY(), charge2.getRectangle().getCenterX(), charge2.getRectangle().getCenterY());
	}
	
	public static double calculateForce(double x1, double y1, double x2, double y2) {
//		System.out.println(((1.6 * Math.pow(10, -19) * 1.6 * Math.pow(10, -19) * 8.99 * Math.pow(10, 9)) / Math.pow(distance(x1, y1, x2, y2), 2) * Math.pow(10, 28)));
//		System.out.println(1 / Math.pow(distance(x1, y1, x2, y2), 2));
		
		return (1 / Math.pow(distance(x1, y1, x2, y2), 2)) * Charge.forceScale; //((1.6 * Math.pow(10, -19) * 1.6 * Math.pow(10, -19) * 8.99 * Math.pow(10, 9)) / Math.pow(distance(x1, y1, x2, y2), 2)) * Charge.forceScale;
	}
	
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + 
						 Math.pow(y2 - y1, 2));
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
	
	public static double capBothDirections(double num, double cap) {
		double sign = Math.copySign(1, num);
		
		return Math.min(Math.abs(num), cap) * sign;
	}
	
	public static Vector capBothDirections(Vector vector, double cap) {
		return new Vector(vector.getXPos(), vector.getYPos(), capBothDirections(vector.getXComp(), cap), capBothDirections(vector.getYComp(), cap));
	}
	
	public static Vector getForceVector(double x1, double y1, double x2, double y2) {
		Vector force = new Vector();
		
		force.setMagnitude(Util.calculateForce(x1, y1, x2, y2));
		
		force.setXPos(x1);
		force.setYPos(y1);
		
		force.setAngle(getPosAngle(x1, y1, x2, y2));
		
		return force;
	}
}
