package Levels;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import FieldElements.Collider;
import Main.Window;

public class LevelDesigner extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static Collider editCollider;
	
	private LevelDesignerValuesPanel valuePanel;
	private LevelViewPanel viewPanel;
	
	public LevelDesigner(Window window) {
		viewPanel = new LevelViewPanel(window);
		valuePanel = new LevelDesignerValuesPanel(viewPanel);
		
		viewPanel.init(valuePanel);
		
		setLayout(new BorderLayout());
		add(valuePanel, BorderLayout.NORTH);
		add(viewPanel, BorderLayout.CENTER);
	}
	
	public void update(double delta) {
		viewPanel.update(delta);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, getWidth(), getHeight());
		
		viewPanel.repaint();
		valuePanel.repaint();
	}
	
	public LevelViewPanel getViewPanel() { return viewPanel; }
	public LevelDesignerValuesPanel getValuePanel() { return valuePanel; }
}
