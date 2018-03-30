package Main;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import FieldElements.Charge;
import FieldElements.Charge.Particle;
import FieldElements.Collider;

public class Simulation {
	public static final double startX = 100, startY = 100; 
	
	private ElectricField field;
	
	private ArrayList<Charge> charges;
	private ArrayList<Collider> colliders; 
	private Rectangle2D.Double goal;
	
	private boolean running;
	
	public Simulation() {
		charges = new ArrayList<>();
		colliders = new ArrayList<>();
		field = new ElectricField(this);
		
		charges.add(new Charge(this, Particle.Proton, startX, startY));
		charges.add(new Charge(this, Particle.Proton, startX - 10, startY - 70));
		
		charges.add(new Charge(this, Particle.Electron, 200, 300));
		
		colliders.add(new Collider(390, 190, 40, 10));
		colliders.add(new Collider(430, 190, 10, 60));
		colliders.add(new Collider(390, 250, 50, 10));
		
		goal = new Rectangle2D.Double(400, 205, 5, 40);
	}
	
	public void update(double delta) {
		if(running) {
			charges.get(0).update(delta);
		
			for(Collider collider : colliders)
				collider.update(delta);
		}
	}
	
	public void render(Graphics2D g2d) {
		field.render(g2d);
		
		for(Charge charge : charges)
			charge.render(g2d);
		
		for(Collider collider : colliders)
			collider.render(g2d);
		
		g2d.fill(goal);
	}
	
	public void reset() {
		for(Charge c : charges) {
			c.reset();
		}
		
		running = false;
	}

	public boolean isRunning() { return running; }
	public void setRunning(boolean running) { this.running = running; }
	public void alternateRunState() { running = !running; }
	
	public ArrayList<Charge> getCharges() { return charges; }
	public ArrayList<Collider> getColliders() { return colliders; }
	public Rectangle2D getGoal() { return goal; }
	
	public void addCharge(Charge charge) { charges.add(charge); }
	public void addCollider(Collider collider) { colliders.add(collider); }
}
