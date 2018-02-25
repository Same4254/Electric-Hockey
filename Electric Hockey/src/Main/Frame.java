package Main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	
	private Thread thread;
	private boolean running;
	
	private Simulation simulation;
	
	public Frame() {
		frame = new JFrame("Electric Hockey");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		simulation = new Simulation(this);
		frame.addKeyListener(simulation);
		
		frame.add(this);
		frame.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, getWidth(), getHeight());
		
		simulation.render(g2d);
	}
	
	public void run() {
		long last = System.currentTimeMillis();
		
		while(running) {
			if(System.currentTimeMillis() > last + 14) {
				simulation.update();
				repaint();
				
				last = System.currentTimeMillis();
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		int fps = 60;
//		double timePerTick =  1000000000 / fps;
//		double delta = 0;
//		long now;
//		long lastTime = System.nanoTime();
//		long timer = 0;
//		int ticks = 0;
//		
//		long last = System.currentTimeMillis();
//		
//		while(running) {
//			now = System.nanoTime();
//			delta += (now - lastTime) / timePerTick;
//			timer += now - lastTime;
//			lastTime = now;
//			
//			if(delta >= 1) {
//				System.out.println(System.currentTimeMillis() - last);
//				last = System.currentTimeMillis();
//				
//				simulation.update();
//				repaint();
//				ticks++;
//				delta--;
//			}
//			
//			if(timer >= 1000000000) {
//				frame.setTitle("Electric Hockey: " + ticks);
//				ticks = 0;
//				timer = 0;
//			}
//			
//		}
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
		new Frame().start();
	}
}