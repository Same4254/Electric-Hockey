package FieldElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import Main.Simulation;
import Util.Arrow;
import Util.Force;

public class Charge {
	public static double forceFactor = 1500; 
	public static int chargeRadius = 10;
	
	public static enum Particle {
		Proton(1), Electron(-1);
		
		private int value;
		private Particle(int value) {
			this.value = value;
		}
		
		public int getValue() { return value; }
	}
	
	public static Charge currentDragging;
	
	private Simulation simulation;
	private Rectangle2D.Double rectangle;
	private Particle particle;

	private Force netForce;
	private double originX, originY;
	private double xBefore, yBefore;
	private boolean starting;
	private double mass;

	public Charge(Simulation simulation, Particle particle) {
		this.simulation = simulation;
		this.particle = particle;

		rectangle = new Rectangle2D.Double(0, 0, chargeRadius * 2, chargeRadius * 2);
		mass = 25;
		starting = true;
	}
	
	public Charge(Simulation simulation, Particle particle, double x, double y) {
		this(simulation, particle);
		
		setX(x);
		setY(y);
		
		setOriginX(x);
		setOriginY(y);
	}
	
	public void update(double delta) {
		updateNetForce();
		updatePosition(delta);
		
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
		if(particle == Particle.Proton)
			g2d.setColor(Color.BLUE);
		else
			g2d.setColor(Color.RED);
		
		g2d.fillOval((int) rectangle.getX(), (int) rectangle.getY(), chargeRadius * 2, chargeRadius * 2);

		g2d.setColor(Color.BLACK);
		
		for(Charge charge : simulation.getCharges()) {
			if(charge != this) {
				Force force = new Force(this, charge);

				Arrow arrow = new Arrow((int) force.magnitude());
				arrow.setAngle(force.getAngle());
				arrow.setPosition(getCenterX(), getCenterY());
				
				arrow.render(g2d, true);
			}
		}
	}
	
	public void updateNetForce() {
		Force netForce = new Force(0, 0);
		
		for(Charge charge : simulation.getCharges()) {
			if(charge != this) {
				Force localForce = new Force(this, charge);
				netForce = netForce.add(localForce);
			} 
		}
		
		this.netForce = netForce;
	}
	
	private void updatePosition(double delta) {
		if(this.starting) {
			xBefore = getX();
			yBefore = getY();

			addX(forceFactor * netForce.getXComp() / (2.0 * mass) * delta * delta);
			addY(forceFactor * netForce.getYComp() / (2.0 * mass) * delta * delta);

			starting = false;
		} else {
			double xTemp = getX();
			double yTemp = getY();

			setX((2.0 * getX() - this.xBefore + forceFactor * netForce.getXComp() / mass * delta * delta));
			setY((2.0 * getY() - this.yBefore + forceFactor * netForce.getYComp() / mass * delta * delta));

			xBefore = xTemp;
			yBefore = yTemp;
		}
	}
	
	public void reset() {
		setX(originX);
		setY(originY);
		
		starting = true;
	}
	
	public double getCenterX() { return rectangle.getCenterX(); }
	public double getCenterY() { return rectangle.getCenterY(); }
	
	public Rectangle2D getRectangle() { return rectangle; }
	public Particle getParticle() { return particle; }
	
	public double getOriginX() { return originX; }
	public double getOriginY() { return originY; }
	
	public double getX() { return rectangle.getX(); }
	public double getY() { return rectangle.getY(); }

	public void setOriginX(double originX) { this.originX = originX; }
	public void setOriginY(double originY) { this.originY = originY; }
	
	public void setX(double x) { rectangle.x = x; }
	public void addX(double x) { setX(rectangle.getX() + x); }
	public void addY(double y) { setY(rectangle.getY() + y); }
	public void setY(double y) { rectangle.y = y; }
	public void mouseDragged(MouseEvent e) {}
	
	public void mouseMoved(MouseEvent e) {
		if(currentDragging == this) {
			setX(e.getX() - rectangle.getWidth() / 2);
			setY(e.getY() - rectangle.getHeight() / 2);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(currentDragging == null && rectangle.contains(e.getPoint())) 
			currentDragging = this;
		else if(currentDragging != null && currentDragging == this)
			currentDragging = null;
	}
}
