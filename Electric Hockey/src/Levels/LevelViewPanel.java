package Levels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import FieldElements.Charge;
import FieldElements.Collider;
import Main.Window;

public class LevelViewPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Level level;
	
	public LevelViewPanel(Window window) {
		setSize(Window.width, Window.height);
		setFocusable(true);
		setRequestFocusEnabled(true);
		
		level = new Level();
		window.getFrame().addKeyListener(level);
	}
	
	public void init(LevelDesignerValuesPanel valuePanel) {
		MouseControl mouseControl = new MouseControl(valuePanel);
		
		addMouseListener(mouseControl);
		addMouseMotionListener(mouseControl);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, getWidth(), getHeight());
		
		if(level != null)
			level.render(g2d);
	}
	
	public void update(double delta) {
		if(level != null)
			level.update(delta);
	}
	
	public Level getLevel() { return level; }
	public void setLevel(Level level) { this.level = level; }

	class MouseControl extends MouseAdapter {
		private LevelDesignerValuesPanel valuePanel;
		
		private MouseControl(LevelDesignerValuesPanel valuePanel) {
			this.valuePanel = valuePanel;
		}
		
		public void mouseDragged(MouseEvent e) {
			if(level != null)
				for(Charge charge : level.getSimulation().getCharges())
					charge.mouseDragged(e);
		}
		
		public void mouseMoved(MouseEvent e) {
			if(level != null)
				for(Charge charge : level.getSimulation().getCharges())
					charge.mouseMoved(e);
		}
		
		public void mouseReleased(MouseEvent e) {
			if(level != null) {
				for(Charge charge : level.getSimulation().getCharges())
					charge.mouseReleased(e);
				for(Collider collider : level.getSimulation().getColliders()) 
					collider.mouseReleased(valuePanel, e);
			}
		}
	}
}
