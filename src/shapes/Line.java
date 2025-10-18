package shapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Line extends MyShape{
    
    public Line ()
	{

	}

    public Line (Point2D point1, Point2D point2)
	{
		super(point1, point2);
	}

    public Line (double x1, double y1, double x2, double y2)
	{
		super(x1, y1, x2, y2);
	}

    @Override
	public void draw (GraphicsContext gc)
	{	

	}
}
