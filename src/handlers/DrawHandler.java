package handlers;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

public class DrawHandler {
    
    protected MyShape     shape;
	protected ShapeCanvas canvas;

    // ----- CONSTRUCTORS -----
    public DrawHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

    // ----- METHODS -----
    protected void mousePressed (MouseEvent e)
	{
		if (shape != null)
		{	
			double mx = e.getX();
			double my = e.getY();

			shape.setP1(new Point2D (mx, my));

			canvas.setCurrShape(shape);
		}
	}

    protected void mouseDragged (MouseEvent e)
	{
		if (shape != null)
		{
			double mx = e.getX();
			double my = e.getY();

			shape.setP2(mx, my);

			canvas.paint();
		}
	}

    protected void mouseReleased (MouseEvent e)
	{
		if (shape != null)
		{
			canvas.addShape(shape);
			canvas.setCurrShape(null);

			shape = null;
		}
	}

    @Override
	public void handle (MouseEvent event)
	{
		String eventName = event.getEventType().getName();
		System.out.println ("event name: " + eventName);

		switch (eventName)
		{
		case "MOUSE_PRESSED":
			mousePressed(event);
			break;
		case "MOUSE_DRAGGED":
			mouseDragged(event);
			break;
		case "MOUSE_RELEASED":
			mouseReleased(event);
			break;
		default:
			break;
		}
	}
}
