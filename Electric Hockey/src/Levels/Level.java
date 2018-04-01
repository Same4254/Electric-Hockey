package Levels;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import FieldElements.Charge;
import FieldElements.Charge.Particle;
import Main.Simulation;

public class Level implements KeyListener {
	private Simulation simulation;
	
	public Level() {
		simulation = new Simulation();
	}

	public void update(double delta) {
		simulation.update(delta);
	}
	
	public void render(Graphics2D g2d) {
		simulation.render(g2d);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Hello");
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
//			if(stop && (charges.get(0).getRectangle().getX() != startX || charges.get(0).getRectangle().getY() != startY)) {
//				charges.get(0).setX(startX);
//				charges.get(0).setY(startY);
//				
//				charges.get(0).setVelX(0);
//				charges.get(0).setVelY(0);
//			} else
				simulation.alternateRunState();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_E) {
			if(Charge.currentDragging == null) {
				Charge charge = new Charge(simulation, Particle.Electron);
				
				simulation.addCharge(charge);
				Charge.currentDragging = charge;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P) {
			if(Charge.currentDragging == null) {
				Charge charge = new Charge(simulation, Particle.Proton);
				
				simulation.addCharge(charge);
				Charge.currentDragging = charge;
			}
		}
	}
	
	public Simulation getSimulation() { return simulation; }
}
