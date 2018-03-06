package Main;

import java.awt.Graphics2D;

import FieldElements.Charge;
import FieldElements.Charge.Particle;
import Util.Util;
import Util.Vector;

public class ElectricField {
	public static final int xStep = 50;
	public static final int yStep = 50;
	
	private Simulation simulation;
	
	private Vector[][] vectors;
	
	public ElectricField(Simulation simulation) {
		this.simulation = simulation;
		
		vectors = new Vector[(Window.width / xStep) + 1][(Window.height / yStep) + 1];
		
		for(int x = 0; x < vectors.length; x++) {
			for(int y = 0; y < vectors[0].length; y++) {
				Vector temp = new Vector();
				temp.setXPos(x * xStep);
				temp.setYPos(y * yStep);
				
				vectors[x][y] = temp;
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		for(Vector[] vects : vectors) {
			for(Vector v : vects) {
				double xComp = 0;
				double yComp = 0;
				
				for(Charge c : simulation.getCharges()) {
					Vector vector = Util.getForceVector(v.getXPos(), v.getYPos(), c);
					
					if(c.getParticle() == Particle.Proton) {
						xComp -= vector.getXComp();
						yComp -= vector.getYComp();
					} else {
						xComp += vector.getXComp();
						yComp += vector.getYComp();
					}
				} 
				
				double angle = Util.getPosAngle(new Vector(0, 0, xComp, yComp));
				v.setMagnitude(15);
				v.setAngle(angle);
				
				v.renderArrow(g2d);
			}
		}
	}
}
