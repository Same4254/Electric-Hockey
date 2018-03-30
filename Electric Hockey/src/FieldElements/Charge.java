package FieldElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import Main.Simulation;
import Util.Util;
import Util.Vector;

public class Charge {
	public static double forceScale = 400000;//Math.pow(10, 28);
	public static double velocityScale = .1; 
	
	public static int chargeRadius = 10;
	
	public static enum Particle {
		Proton(1), Electron(-1);
		
		private int value;
		private Particle(int value) {
			this.value = value;
		}
		
		public int getValue() { return value; }
	}
	
	public static Charge currentMoving;
	
	private Simulation simulation;
	private Rectangle2D.Double rectangle;
	private Particle particle;
	
//	private Vector velocity;
	private double velX;
	private double velY;
	
	private double originX, originY;

	public Charge(Simulation simulation, Particle particle) {
		this.simulation = simulation;
		this.particle = particle;

		rectangle = new Rectangle2D.Double(0, 0, chargeRadius * 2, chargeRadius * 2);
	}
	
	public Charge(Simulation simulation, Particle particle, double x, double y) {
		this(simulation, particle);
		
		setX(x);
		setY(y);
		
		setOriginX(x);
		setOriginY(y);
	}
	
	public void update(double delta) {
		for(Charge charge : simulation.getCharges()) {
			if(charge != this) {
				Vector force = Util.getForceVector(this, charge);
				
				if(charge.getParticle().getValue() == particle.getValue())
					force = force.multiply(-1);
				
				force = Util.capBothDirections(force, 200);
				
				velX += force.getXComp();
				velY += force.getYComp();
			} 
		}
		
		setX(getRectangle().getX() + (velX * velocityScale * delta));
		setY(getRectangle().getY() - (velY * velocityScale * delta));
		
		if(rectangle.intersects(simulation.getGoal())) {
			simulation.setRunning(false);
			System.out.println("WIN!");
		} else {
			for(Collider collider : simulation.getColliders()) {
				if(rectangle.intersects(collider.getRectangle())) {
					simulation.setRunning(false);
					System.out.println("Lose");
				}
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		if(particle == Particle.Proton) {
			g2d.setColor(Color.BLUE);
//			g2d.fill(rectangle);
			
			g2d.fillOval((int) rectangle.getX(), (int) rectangle.getY(), chargeRadius * 2, chargeRadius * 2);
		} else {
			g2d.setColor(Color.RED);
//			g2d.fill(rectangle);
			
			g2d.fillOval((int) rectangle.getX(), (int) rectangle.getY(), chargeRadius * 2, chargeRadius * 2);
		}
		
		g2d.setColor(Color.BLACK);
		
		for(Charge charge : simulation.getCharges()) {
			if(charge != this) {
				Vector vector = getCenter();
				vector.setMagnitude(Math.min(75, Util.calculateForce(this, charge)));
				vector.setAngle(charge.getCenter());
				
				if(charge.getParticle().getValue() == particle.getValue())
					vector.multiply(-1);
				
				g2d.setColor(Color.GREEN);
				vector.render(g2d);
			}
		}
		
		g2d.setColor(Color.BLACK);
	}
	
	public void reset() {
		setX(originX);
		setY(originY);
		
		velX = 0;
		velY = 0;
	}
	
	public Rectangle2D getRectangle() { return rectangle; }
	public Vector getCenter() { return new Vector(rectangle.getCenterX(), rectangle.getCenterY()); }
	public Particle getParticle() { return particle; }
	
	public double getOriginX() { return originX; }
	public double getOriginY() { return originY; }

	public void setOriginX(double originX) { this.originX = originX; }
	public void setOriginY(double originY) { this.originY = originY; }
	
	public void setX(double x) { rectangle.x = x; }
	public void addX(double x) { setX(rectangle.getX() + x); }
	public void addY(double y) { setY(rectangle.getY() + y); }
	public void setY(double y) { rectangle.y = y; }
	public void mouseDragged(MouseEvent e) {}
	
	public void mouseMoved(MouseEvent e) {
		if(currentMoving == this) {
			setX(e.getX() - rectangle.getWidth() / 2);
			setY(e.getY() - rectangle.getHeight() / 2);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(currentMoving == null && rectangle.contains(e.getPoint())) 
			currentMoving = this;
		else if(currentMoving != null && currentMoving == this)
			currentMoving = null;
	}
}
