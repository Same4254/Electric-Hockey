package Main;

import java.awt.Graphics2D;

import FieldElements.Charge;
import FieldElements.Charge.Particle;
import Util.Arrow;
import Util.Force;
import Util.Util;

public class ElectricField {
	public static final int xStep = 50;
	public static final int yStep = 50;
	
	private Simulation simulation;
	
	private Arrow[][] arrows;
	
	public ElectricField(Simulation simulation) {
		this.simulation = simulation;
		
		arrows = new Arrow[(Window.width / xStep) + 1][(Window.height / yStep) + 1];
		
		for(int x = 0; x < arrows.length; x++) {
			for(int y = 0; y < arrows[0].length; y++) {
				Arrow temp = new Arrow(50);
				temp.setPosition(x * xStep, y * yStep);
//				temp.setX(x * xStep);
//				temp.setY(y * yStep);
				
				arrows[x][y] = temp;
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		for(Arrow[] arrs : arrows) {
			for(Arrow arrow : arrs) {
				double xComp = 0;
				double yComp = 0;
				
				for(Charge c : simulation.getCharges()) {
					Force force = new Force(arrow.getPosition(), c);

//					if(c.getParticle() == Particle.Proton) {
//						xComp -= force.getXComp();
//						yComp -= force.getYComp();
//					} else {
						xComp += force.getXComp() * c.getParticle().getValue();
						yComp += force.getYComp() * c.getParticle().getValue();
//					}
				} 
				
				arrow.setAngle(Util.getAngle(0, 0, xComp, yComp));
				arrow.render(g2d, false);
			}
		}
	}
}
