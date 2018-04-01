package Util;

import java.awt.Graphics2D;

import FieldElements.Charge;
import javafx.geometry.Point2D;

public class Force {
	private double xComp;
	private double yComp;
	private double forceFactor = 100000.0;
	
	public Force(Charge charge, Charge charge2) {
		Point2D chargePt = new Point2D(charge.getX(), charge.getY());
		Point2D chargePt2 = new Point2D(charge2.getX(), charge2.getY());
		Point2D displacement = new Point2D(chargePt.getX() - chargePt2.getX(), chargePt.getY() - chargePt2.getY());
		
		double r = Math.max(chargePt.distance(chargePt2), 25.0);
		
		xComp = (forceFactor * charge2.getParticle().getValue() * charge.getParticle().getValue() * displacement.getX() / Math.pow(r, 3));
		yComp = (forceFactor * charge2.getParticle().getValue() * charge.getParticle().getValue() * displacement.getY() / Math.pow(r, 3));
	}
	
	public Force(Point2D point, Charge charge) {
		Point2D chargePt2 = new Point2D(charge.getX(), charge.getY());
		Point2D displacement = new Point2D(point.getX() - chargePt2.getX(), point.getY() - chargePt2.getY());
		
		double r = Math.max(point.distance(chargePt2), 25.0);
		
		xComp = (forceFactor * displacement.getX() / Math.pow(r, 3));
		yComp = (forceFactor * displacement.getY() / Math.pow(r, 3));
	}
	
	public Force(double xComp, double yComp) {
		this.xComp = xComp;
		this.yComp = yComp;
	}
	
	public void render(Graphics2D g2d, double x, double y) {
		g2d.drawLine((int) x, (int) y, (int) (x + xComp), (int) (y + yComp));
	}
	
	public double magnitude() {
		return Math.sqrt(Math.pow(xComp, 2) + Math.pow(yComp, 2));
	}
	
	public double getAngle() {
		return Util.getAngle(0, 0, xComp, yComp);
	}
	
	public Force add(Force force) { return new Force(xComp + force.getXComp(), yComp + force.getYComp()); }
	public double getXComp() { return xComp; }
	public double getYComp() { return yComp; }
}
