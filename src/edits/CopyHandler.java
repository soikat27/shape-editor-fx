package edits;

import editor.ShapeCanvas;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

public class CopyHandler implements EventHandler<MouseEvent> {
    
    private ShapeCanvas canvas;
	private MyShape     closestShape;
	private MyShape     copyShape;
	private double x0, y0;
    private double x1, y1;
	
    // ----- CONSTRUCTORS -----
    public CopyHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

    // ----- LOGICAL METHODS -----
    private void mousePressed (MouseEvent e)
	{
		double mx = e.getX();
		double my = e.getY();
			
		closestShape = canvas.closestShape(mx, my);

		if (closestShape != null)
		{
			copyShape = (MyShape) closestShape.clone();
			canvas.addShape(copyShape);
				
			x0 = mx;
			y0 = my;
		}
	}

    private void mouseDragged (MouseEvent e)
	{
		x1 = e.getX();
		y1 = e.getY();
			
		if (copyShape != null)
		{
			double dx = x1 - x0;
			double dy = y1 - y0;
				
			copyShape.move(dx, dy);
			canvas.paint();
		}
			
		x0 = x1;
		y0 = y1;
	}

    @Override
	public void handle (MouseEvent e)
	{
        String eventName = e.getEventType().getName();
		
		switch (eventName)
		{
		case "MOUSE_PRESSED":
			mousePressed(e);
			break;
		case "MOUSE_DRAGGED":
			mouseDragged(e);
			break;
		default:
			break;
		}
	}
}
