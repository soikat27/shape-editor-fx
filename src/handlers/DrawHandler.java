package handlers;

import javafx.geometry.Point2D;
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
}
