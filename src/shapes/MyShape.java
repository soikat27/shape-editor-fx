package shapes;

import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public abstract class MyShape implements Serializable, Cloneable {
    
    protected static final Color bBoxColor = Color.BLACK;

    protected transient Point2D p1, p2, center;
	protected transient Color color;
	protected boolean filled;
	protected double  ulx, uly, width, height;

    public MyShape ()
	{

	}

    public MyShape (Point2D point1, Point2D point2)
	{
		p1 = point1;
		p2 = point2;

		filled = false;
	}

    public MyShape (double x1, double y1, double x2, double y2)
	{
		this(new Point2D (x1, y1), new Point2D (x2, y2));
	}
}
