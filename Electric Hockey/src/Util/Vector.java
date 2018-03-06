package Util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Vector {
	private double xPos, yPos;

	private double magnitude;
	private double xComp, yComp;
	
	private double angle;
	
	private Color color;
	
	public Vector() {
		
	}
	
	public Vector(double xPos, double yPos) {
		setXPos(xPos);
		setYPos(yPos);
	}
	
	public Vector(double xPos, double yPos, double magnitude) {
		this(xPos, yPos);
		
		setMagnitude(magnitude);
	}
	
	public Vector(double xPos, double yPos, double xComp, double yComp) {
		this(xPos, yPos);
		
		setXComp(xComp);
		setYComp(yComp);
	}
	
	public void render(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(3));
		
		g2d.drawLine((int) xPos, (int) yPos, (int) (xPos + xComp), (int) (yPos - yComp));
		
//		g2d.drawRect((int) (xPos - 5), (int) (yPos - 5), 10, 10);
	}
	
	public void renderArrow(Graphics2D g2d) {
		double width = 15;
		double height = 10;
		
		g2d.rotate(Math.toRadians(angle), xPos, yPos);
		
		g2d.setStroke(new BasicStroke(2));
		
		g2d.drawRect((int) xPos, (int) yPos, (int) width, (int) height);
		
		Polygon polygon = new Polygon();
		polygon.addPoint((int) (xPos + width), (int) (yPos - width / 4));
		polygon.addPoint((int) (xPos + width), (int) (yPos + height + width / 4));
		polygon.addPoint((int) (xPos + width * 1.6), (int) (yPos + height / 2));
		
		g2d.draw(polygon);
		
		g2d.rotate(-Math.toRadians(angle), xPos, yPos);
	}
	
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
		
		xComp = Math.cos(Math.toRadians(angle)) * magnitude;
		yComp = Math.sin(Math.toRadians(angle)) * magnitude;
	}
	
	public void setComp(double xComp, double yComp) {
		setXComp(xComp);
		setYComp(yComp);
	}
	
	public void setXComp(double xComp) {
		this.xComp = xComp;
		
		magnitude = Math.sqrt((xComp * xComp) + (yComp * yComp));
		angle = Util.getPosAngle(this);
	}
	
	public void setYComp(double yComp) {
		this.yComp = yComp;
		
		magnitude = Math.sqrt((xComp * xComp) + (yComp * yComp));
		angle = Util.getPosAngle(this);
	}
	
	public void multiply(double num) {
		setXComp(xComp * num);
		setYComp(yComp * num);
	}
	
	public void add(Vector add) { setXComp(xComp + add.xComp); setYComp(yComp + add.yComp); }
	
	public double distance(Vector distance) { return Math.sqrt(Math.pow(xComp - distance.xComp, 2) + 
																 Math.pow(yComp - distance.yComp, 2)); }
	
	public double distanceSquared(Vector distance) { return Math.pow(distance(distance), 2); }
	
	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }
	
	public void setAngle(double angle) { this.angle = angle; setMagnitude(magnitude); }
	public void setAngle(Vector target) { setAngle(Util.getPosAngle(this, target)); }
	
	public double getAngle() { return angle; }
	
	public double getXPos() { return xPos; }
	public void setXPos(double xPos) { this.xPos = xPos; }
	
	public double getYPos() { return yPos; }
	public void setYPos(double yPos) { this.yPos = yPos; }
	
	public String toString() { return "X: " + xPos + ", Y: " + yPos + ", X Comp: " + xComp + ", Y Comp: " + yComp; } 
	
	public double getMagnitude() { return magnitude; }
	public double getXComp() { return xComp; }
	public double getYComp() { return yComp; } 
}
