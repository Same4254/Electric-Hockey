package Util;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Vector2 {
	public double xComp, yComp;
	public double xPos, yPos;

	public Vector2() {
		
	}
	
	public Vector2(double xComp, double yComp) {
		this.xComp = xComp;
		this.yComp = yComp;
	}
	
	public void render(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(3));
		
		g2d.drawLine((int) xPos, (int) yPos, (int) (xPos + xComp), (int) (yPos - yComp));
		
//		g2d.drawRect((int) (xPos - 5), (int) (yPos - 5), 10, 10);
	}
	
	public void renderArrow(Graphics2D g2d) {
		double width = 15;
		double height = 10;
		
		g2d.rotate(Math.toRadians(getAngle()), xPos, yPos);
		
		g2d.setStroke(new BasicStroke(2));
		
		g2d.drawRect((int) xPos, (int) yPos, (int) width, (int) height);
		
		Polygon polygon = new Polygon();
		polygon.addPoint((int) (xPos + width), (int) (yPos - width / 4));
		polygon.addPoint((int) (xPos + width), (int) (yPos + height + width / 4));
		polygon.addPoint((int) (xPos + width * 1.6), (int) (yPos + height / 2));
		
		g2d.draw(polygon);
		
		g2d.rotate(-Math.toRadians(getAngle()), xPos, yPos);
	}
	
	
	public void setMagnitude(double magnitude, double angle) { 
		xComp = magnitude * Math.cos(angle);
		yComp = magnitude * Math.sin(angle);
	}
	
	public double getAngle() { return Math.atan(yComp / xComp); }
	
	public double getMagnitude() { return Math.sqrt(Math.pow(xComp, 2) + Math.pow(yComp, 2)); }
	
	public void changeAngle(double angle) { setMagnitude(getMagnitude(), angle); }
	
	public void add(Vector2 add) { this.xComp += add.xComp; this.yComp += add.yComp; }
	
	public double distance(Vector2 distance) { return Math.sqrt(Math.pow(xComp - distance.xComp, 2) + 
																 Math.pow(yComp - distance.yComp, 2)); }
	
	public double distanceSquared(Vector2 distance) { return Math.pow(distance(distance), 2); }
	
	public String toString() { return "X: " + xComp + ", Y: " + yComp; }
}
