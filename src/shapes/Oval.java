package shapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Oval extends MyShape {
    
    public Oval ()
	{

	}

    public Oval (Point2D point1, Point2D point2)
	{
		super(point1, point2);
	}

    public Oval (double x1, double y1, double x2, double y2)
	{
		super(x1, y1, x2, y2);
	}

    @Override
	public String toString ()
	{
		return String.format("Oval %s", super.toString());
	}

    @Override
	public void draw (GraphicsContext gc)
	{
        // drawing the bounding box

		drawBounds (gc);

		// drawing the shape

		if (filled) 
		{
			gc.setFill(color);
			gc.fillOval(ulx, uly, width, height);
		} 

		else 
		{
			gc.setStroke(color);
			gc.setLineDashes(null);
			gc.strokeOval(ulx, uly, width, height);
		}
	}
}
