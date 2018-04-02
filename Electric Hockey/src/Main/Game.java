package Main;

import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

public abstract class Game extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	protected boolean running;
	
	public Game(GameFrame gameFrame) {
		addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
				start();
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				stop();
//				gameFrame.getCardPanel().remove(Game.this);
				//TODO Check this
			}
		});
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
	
	public abstract void update(double delta);
	public abstract void render(Graphics2D g2d);
}
