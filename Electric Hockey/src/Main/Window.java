package Main;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Levels.LevelDesigner;

public class Window extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final int width = 800, height = 500;
	
	private JFrame frame;
	
	private Thread thread;
	private boolean running;
	
	LevelDesigner levelDesigner;
	
	public Window() {
		frame = new JFrame("Electric Hockey");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		levelDesigner = new LevelDesigner(this);
		
		setLayout(new BorderLayout());
		add(levelDesigner, BorderLayout.CENTER);
		
		frame.add(this, BorderLayout.CENTER);
		
		frame.pack();
		frame.setSize(width, height + levelDesigner.getValuePanel().getHeight());
		
		frame.add(this);
		frame.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, getWidth(), getHeight());
	}
	
	public void update(double delta) {
		levelDesigner.update(delta);
	}
	
	public void run() {
		long last = System.currentTimeMillis();
		
		while(running) {
			if(System.currentTimeMillis() > last + 14) {
				update((System.currentTimeMillis() - last) / 1000.0);
				repaint();
				
				last = System.currentTimeMillis();
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		stop();
	}
	
	public synchronized void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
    public synchronized void stop() {
    	if(!running)
    		return;
    	
    	running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    
    public JFrame getFrame() { return frame; }
    
    public static void main(String[] args) {
		new Window().start();
	}
}