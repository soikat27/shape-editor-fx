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

    // ----- GETTER & SETTER METHODS -----

    public Point2D getP1 ()
	{
		return p1;
	}

	public Point2D getP2 ()
	{
		return p2;
	}

	public Color getColor ()
	{
		return color;
	}

	public boolean isFilled ()
	{
		return filled;
	}

	public double getULX ()
	{
		return ulx;
	}

	public double getULY ()
	{
		return uly;
	}

	public double getWidth ()
	{
		return width;
	}

	public double getHeight ()
	{
		return height;
	}

	public Point2D getCenter ()
	{
		return center;
	}

	public void setP1 (Point2D point1)
	{
		p1 = point1;
	}

	public void setP1 (double x, double y)
	{
		p1 = new Point2D (x, y);
	}

	public void setP2 (Point2D point2)
	{
		p2 = point2;
	}

	public void setP2 (double x, double y)
	{
		p2 = new Point2D (x, y);
	}

	public void setColor (Color color)
	{
		this.color = color;
	}

	public void setFilled (boolean filled)
	{
		this.filled = filled;
	}
}
