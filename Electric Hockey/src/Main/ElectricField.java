package Main;

import java.awt.Graphics2D;

import Charges.Charge;
import Charges.Charge.Particle;
import Util.Util;
import Util.Vector;

public class ElectricField {
	private Frame frame;
	private Simulation simulation;
	
	public ElectricField(Frame frame, Simulation simulation) {
		this.frame = frame;
		this.simulation = simulation;
	}
	
	public void render(Graphics2D g2d) {
		for(int x = 0; x < frame.getWidth(); x += 40) {
			for(int y = 0; y < frame.getHeight(); y += 40) {
				double xComp = 0;
				double yComp = 0;
				
				for(Charge c : simulation.getCharges()) {
					Vector vector = Util.getForceVector(x, y, c);
					
					if(c.getParticle() == Particle.Proton) {
						xComp -= vector.getXComp();
						yComp -= vector.getYComp();
					} else {
						xComp += vector.getXComp();
						yComp += vector.getYComp();
					}
				} 
				
				Vector vector = new Vector(x, y);
				
				double angle = Util.getPosAngle(new Vector(0, 0, xComp, yComp));
				vector.setMagnitude(15);
				vector.setAngle(angle);
				
				vector.renderArrow(g2d);
			}
		}
	}
}
