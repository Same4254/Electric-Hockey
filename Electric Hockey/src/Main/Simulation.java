package Main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;

import Charges.Charge;
import Charges.Charge.Particle;
import Util.Vector;

public class Simulation implements KeyListener {
	public static final double startX = 100, startY = 100; 
	
	private Frame frame;
	private ElectricField field;
	
	private ArrayList<Charge> charges;
	private ArrayList<Rectangle2D.Double> colliders; 
	private Rectangle2D.Double goal;
	
	private boolean stop;
	
	public Simulation(Frame frame) {
		this.frame = frame;
		
		charges = new ArrayList<>();
		colliders = new ArrayList<>();
		field = new ElectricField(frame, this);
		
		charges.add(new Charge(this, Particle.Proton, startX, startY));
		
//		charges.add(new Charge(this, Particle.Electron, 200, 100));
		
//		colliders.add(new Rectangle2D.Double(190, 190, 40, 10));
//		colliders.add(new Rectangle2D.Double(230, 190, 10, 60));
//		colliders.add(new Rectangle2D.Double(190, 250, 50, 10));
		
		goal = new Rectangle2D.Double(600, 6005, 5, 40);
		
		stop = true;
	}
	
	public void update() {
		if(!stop)
			charges.get(0).update();
	}
	
	public void render(Graphics2D g2d) {
//		new Vector(100, 100, 20).render(g2d);
		field.render(g2d);
		
		for(Charge charge : charges)
			charge.render(g2d);
		
		for(Rectangle2D rectangle : colliders)
			g2d.fill(rectangle);
		
		g2d.fill(goal);
	}
	
	public void stop() { this.stop = true; }
	public ArrayList<Charge> getCharges() { return charges; }
	public ArrayList<Rectangle2D.Double> getColliders() { return colliders; }
	public Rectangle2D getGoal() { return goal; }

	public Frame getFrame() { return frame; }
	public JFrame getJFrame() { return frame.getFrame(); }
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(stop && (charges.get(0).getRectangle().getX() != startX || charges.get(0).getRectangle().getY() != startY)) {
				charges.get(0).setX(startX);
				charges.get(0).setY(startY);
				
				charges.get(0).setVelX(0);
				charges.get(0).setVelY(0);
			} else
				stop = !stop;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_E) {
			if(Charge.currentMoving == null) {
				Charge charge = new Charge(this, Particle.Electron);
				
				charges.add(charge);
				Charge.currentMoving = charge;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P) {
			if(Charge.currentMoving == null) {
				Charge charge = new Charge(this, Particle.Proton);
				
				charges.add(charge);
				Charge.currentMoving = charge;
			}
		}
	}
}
