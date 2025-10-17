package shapes;

import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
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

        updateBounds();
		updateCenter();

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

        updateBounds();
		updateCenter();
	}

	public void setP2 (double x, double y)
	{
		p2 = new Point2D (x, y);

        updateBounds();
		updateCenter();
	}

	public void setColor (Color color)
	{
		this.color = color;
	}

	public void setFilled (boolean filled)
	{
		this.filled = filled;
	}

    @Override
    public String toString ()
	{
		return String.format("%.0f %.0f %.0f %.0f %b %.3f %.3f %.3f\n", p1.getX(), p1.getY(), p2.getX(), p2.getY(), filled, color.getRed(), color.getGreen(), color.getBlue());
	}

    public void updateBounds ()
	{
		ulx = Math.min(p1.getX(), p2.getX());
		uly = Math.min(p1.getY(), p2.getY());

		width  = Math.abs(p2.getX() - p1.getX());
		height = Math.abs(p2.getY() - p1.getY());
	}

	public void updateCenter ()
	{
		center = p1.midpoint(p2);
	}

	public double distance (double x, double y)
	{
		double distance = center.distance(x, y);

		return distance;
	}
	
	public void move (double dx, double dy)
	{
		p1 = p1.add(dx, dy);
		p2 = p2.add(dx, dy);
		
		updateBounds();
		updateCenter();
	}

    @Override
	public Object clone ()
	{
		try
		{
			MyShape copy = (MyShape) super.clone();
			
			copy.setP1(new Point2D(p1.getX(), p1.getY()));
			copy.setP2(new Point2D(p2.getX(), p2.getY()));
			
			copy.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 1));
			
			return copy;
		}
		
		catch (CloneNotSupportedException e)
		{
			System.err.println("MyShape cloning is unsuccessful");
			return null;
		}
	}

    public void drawBounds (GraphicsContext gc)
	{
		gc.setLineDashes(4);
		gc.setStroke(bBoxColor);
		gc.strokeRect(ulx, uly, width, height);
	}

    public abstract void draw (GraphicsContext gc);
}
