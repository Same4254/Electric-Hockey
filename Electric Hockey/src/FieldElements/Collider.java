package FieldElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import Levels.LevelDesigner;
import Levels.LevelDesignerValuesPanel;
import Levels.LevelViewPanel;

public class Collider {
	private Rectangle rectangle;
	
	private double originX, originY;
	private double xMax, xMin, xStep;
	private double yMax, yMin, yStep;
	private double angleRate;
	
	public Collider(int x, int y, int width, int height) {
		rectangle = new Rectangle(x, y, width, height);
		
		setOriginX(x);
		setOriginY(y);
	}
	
	public Collider(int x, int y) {
		this(x, y, 0, 0);
	}
	
	public Collider() {
		this(0, 0, 0, 0);
	}	
	
	public void update(double delta) {
		if(rectangle.getX() < originX - xMin) 
			xStep = Math.abs(xStep);
		else if(rectangle.getX() > originX + xMax) 
			xStep = -Math.abs(xStep);
		
		if(rectangle.getY() > originY + yMin) 
			yStep = -Math.abs(yStep);
		else if(rectangle.getY() < originY - yMax) 
			yStep = Math.abs(yStep);
		
		setX(rectangle.getX() + (xStep * delta));
		setY(rectangle.getY() + (yStep * delta));
		
//		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angleRate), rectangle.getCenterX(), rectangle.getCenterY());
		
//		double width = rectangle.getWidth();
//		double height = rectangle.getHeight();
		
//		rectangle.setRect(at.createTransformedShape(rectangle).getBounds2D());
//		rectangle.setRect(rectangle.getCenterX(), rectangle.getCenterY(), width, height);
	}

	public void render(Graphics2D g2d) {
		if(LevelDesigner.editCollider == this)
			g2d.setColor(Color.CYAN);
		
		g2d.fill(rectangle);
		
		g2d.setColor(Color.BLACK);
	}
	
	public void mouseDragged(LevelDesignerValuesPanel valuePanel, MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			System.out.println("Here");
		}
	}
	
	public void mouseMoved(LevelDesignerValuesPanel valuePanel, MouseEvent e) {
		if(LevelDesigner.editCollider == this) {
			setOriginX(e.getX() - rectangle.getWidth() / 2);
			setOriginY(e.getY() - rectangle.getHeight() / 2);
		}
	}
	
	public void mouseReleased(LevelDesignerValuesPanel valuePanel, MouseEvent e) {
		if(rectangle.contains(e.getPoint())) {
			if(LevelDesigner.editCollider == null) {
				LevelDesigner.editCollider = this;
				
				if(valuePanel != null) 
					valuePanel.changeCollider(this);
			} else {
				LevelDesigner.editCollider = null;
				
				if(valuePanel != null) 
					valuePanel.changeCollider(null);
			}
		}
	}
	
	public void setWidth(double width) { rectangle.setBounds((int) rectangle.getX(), (int) rectangle.getY(), (int) width, (int) rectangle.getHeight()); }
	public void setHeight(double height) { rectangle.setBounds((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) height); }
	public void setX(double x) { rectangle.setBounds((int) x, (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight()); }
	public void setY(double y) { rectangle.setBounds((int) rectangle.getX(), (int) y, (int) rectangle.getWidth(), (int) rectangle.getHeight()); }
	public double getOriginX() { return originX; }
	public void setOriginX(double originX) { this.originX = originX; setX(originX); }
	public double getOriginY() { return originY; } 
	public void setOriginY(double originY) { this.originY = originY; setY(originY); }
	public double getxMax() { return xMax; }
	public void setxMax(double xMax) { this.xMax = xMax; }
	public double getxMin() { return xMin; }
	public void setxMin(double xMin) { this.xMin = xMin; }
	public double getxStep() { return xStep; }
	public void setxStep(double xStep) { this.xStep = xStep; }
	public double getyMax() { return yMax; }
	public void setyMax(double yMax) { this.yMax = yMax; }
	public double getyMin() { return yMin; }
	public void setyMin(double yMin) { this.yMin = yMin; }
	public double getyStep() { return yStep; }
	public void setyStep(double yStep) { this.yStep = yStep; }
	public double getAngleRate() { return angleRate; }
	public void setAngleRate(double angleRate) { this.angleRate = angleRate; }
	public Rectangle getRectangle() { return rectangle; }

	public boolean intersects(Rectangle2D.Double rectangle) { return rectangle.intersects(rectangle); }
}
