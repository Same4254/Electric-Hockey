package Charges;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import Main.Simulation;
import Util.Util;
import Util.Vector;

public class Charge extends MouseAdapter {
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
	
	private double velX, velY;

	public Charge(Simulation simulation, Particle particle) {
		this.simulation = simulation;
		this.particle = particle;
		
		rectangle = new Rectangle2D.Double(0, 0, 20, 20);
		
		simulation.getFrame().addMouseListener(this);
		simulation.getFrame().addMouseMotionListener(this);
	}
	
	public Charge(Simulation simulation, Particle particle, double x, double y) {
		this(simulation, particle);
		
		setX(x);
		setY(y);
	}
	
	public void update() {
		for(Charge charge : simulation.getCharges()) {
			if(charge != this) {
				Vector force = Util.getForceVector(this, charge);
				
				if(force.getMagnitude() > .001)
					continue;
				
				double scale = 2000;
				
				if(charge.getParticle().getValue() == particle.getValue())
					force.multiply(-1);
				
				velX += force.getXComp() * scale;
				velY -= force.getYComp() * scale;
			} 
		}
		
		setX(getRectangle().getX() + velX);
		setY(getRectangle().getY() + velY);
		
		if(rectangle.intersects(simulation.getGoal())) {
			simulation.stop();
			System.out.println("WIN!");
		} else {
			for(Rectangle2D.Double rect : simulation.getColliders()) {
				if(rectangle.intersects(rect)) {
					simulation.stop();
					System.out.println("Lose");
				}
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		if(particle == Particle.Proton) {
			g2d.setColor(Color.BLUE);
			g2d.fill(rectangle);
		} else {
			g2d.setColor(Color.RED);
			g2d.fill(rectangle);
		}
		
		g2d.setColor(Color.BLACK);
		
		for(Charge charge : simulation.getCharges()) {
			if(charge != this) {
				Vector vector = getCenter();
				vector.setMagnitude(Math.min(50, Util.calculateForce(this, charge) * 1000000));
				vector.setAngle(charge.getCenter());
				
				if(charge.getParticle().getValue() == particle.getValue())
					vector.multiply(-1);
				
				g2d.setColor(Color.GREEN);
				vector.render(g2d);
			}
		}
		
		g2d.setColor(Color.BLACK);
	}
	
	public Rectangle2D getRectangle() { return rectangle; }
	public Vector getCenter() { return new Vector(rectangle.getCenterX(), rectangle.getCenterY()); }
	public Particle getParticle() { return particle; }
	
	public void setX(double x) { 
		rectangle.x = x;
	}
	
	public void addX(double x) {
		setX(rectangle.getX() + x);
	}
	
	public void addY(double y) {
		setY(rectangle.getY() + y);
	}
	
	public void setY(double y) {
		rectangle.y = y;
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}
	
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		if(currentMoving == this) {
			setX(e.getX());
			setY(e.getY());
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(currentMoving == null && rectangle.contains(e.getPoint())) 
			currentMoving = this;
		else if(currentMoving != null && currentMoving == this)
			currentMoving = null;
	}
}
