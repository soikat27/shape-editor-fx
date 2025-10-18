package shapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Rect extends MyShape {
    
    public Rect ()
	{

	}

    public Rect (Point2D point1, Point2D point2)
	{
		super(point1, point2);
	}

    public Rect (double x1, double y1, double x2, double y2)
	{
		super(x1, y1, x2, y2);
	}

    @Override
	public void draw (GraphicsContext gc)
	{
		
	}
}
