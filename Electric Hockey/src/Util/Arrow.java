package Util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.geometry.Point2D;

public class Arrow {
	private Shape arrow;
	private double angle;
	
	private int x, y;
	
	public Arrow(int magnitude) {
		arrow = createArrowShape(new Point(0, 0), new Point(magnitude, 0));
		
		setPosition(x, y);
	}
	
	public void render(Graphics2D g2d, boolean fill) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.rotate(Math.toRadians(angle), arrow.getBounds2D().getCenterX(), arrow.getBounds2D().getCenterY());
		
		if(fill) 
			g2d.fill(arrow);
		else
			g2d.draw(arrow);
		
		g2d.rotate(-Math.toRadians(angle), arrow.getBounds2D().getCenterX(), arrow.getBounds2D().getCenterY());
	}
	
	public Shape createArrowShape(Point fromPt, Point toPt) {
	    Polygon arrowPolygon = new Polygon();
	    arrowPolygon.addPoint(-6,1);
	    arrowPolygon.addPoint(3,1);
	    arrowPolygon.addPoint(1,3);
	    arrowPolygon.addPoint(6,0);
	    arrowPolygon.addPoint(1,-3);
	    arrowPolygon.addPoint(3,-1);
	    arrowPolygon.addPoint(-6,-1);

	    Point midPoint = midpoint(fromPt, toPt);

//	    double rotate = Math.atan2(toPt.y - fromPt.y, toPt.x - fromPt.x);

	    AffineTransform transform = new AffineTransform();
	    transform.translate(midPoint.x, midPoint.y);
	    double ptDistance = fromPt.distance(toPt);
	    double scale = ptDistance / 15.0; // 12 because it's the length of the arrow polygon.
	    transform.scale(scale, scale);
//	    transform.rotate(rotate);

	    return transform.createTransformedShape(arrowPolygon);
	}

	private Point midpoint(Point p1, Point p2) {
	    return new Point((int) ((p1.x + p2.x) / 2.0), 
	                     (int) ((p1.y + p2.y) / 2.0));
	}
	
	public void setPosition(double x, double y) {
		AffineTransform transform = AffineTransform.getTranslateInstance(x - this.x, y - this.y);
		
		this.x = (int) x;
		this.y = (int) y;
		
		arrow = transform.createTransformedShape(arrow);
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public void setAngle(int targetX, int targetY) {
		double angle = Math.toDegrees(Math.atan2(targetX - arrow.getBounds2D().getCenterX(), arrow.getBounds2D().getCenterY() - targetY));
		angle -= 90;
		
		setAngle(angle);
	}
	
	public Point2D getPosition() {
		return new Point2D(arrow.getBounds2D().getCenterX(), arrow.getBounds2D().getCenterY());
	}
	
//	public static void main(String[] args) {
//		new Test();
//	}
}

class Test extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private Arrow arrow;
	
	public Test() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);

		addMouseMotionListener(this);
		
		arrow = new Arrow(100);
		arrow.setPosition(100, 100);
		arrow.setAngle(200, 0);
		
		frame.add(this);
		frame.setVisible(true);
	}
	
	double x;
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		
		arrow.render(g2d, false);
		
		Rectangle.Double rect = new Rectangle2D.Double(200, 000, 10, 10);
		g2d.fill(rect);
		
//		x += .001;
//		arrow.setPosition(x, 100);
		
//		arrow.setAngle(Math.toDegrees(Math.cos(x)));
		
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		arrow.setAngle(e.getX(), e.getY());
	}
}