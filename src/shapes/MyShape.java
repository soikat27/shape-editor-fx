package shapes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * {@code MyShape} is an abstract base class representing a 2D shape defined by two points.
 * It provides common functionality for geometric shapes, such as bounding box calculation,
 * center point computation, color, fill properties, movement, distance calculation, cloning, 
 * and custom serialization.
 * <p>
 * Subclasses must implement the {@link #draw(GraphicsContext)} method to define how the
 * shape is rendered on a JavaFX {@link GraphicsContext}.
 * <p>
 * Implements {@link Serializable} for object persistence and {@link Cloneable} for deep copying.
 * 
 * @author Soikat Saha
 */
public abstract class MyShape implements Serializable, Cloneable {
    
    /** Default bounding box color used when drawing selection outlines. */
    protected static final Color bBoxColor = Color.BLACK;

    /** First(p1), Second(p2), Center points of the shape (transient for custom serialization). */
    protected transient Point2D p1, p2, center;
    /** Color of the shape (transient for custom serialization). */
	protected transient Color color;
    /** True if the shape is filled; false if only outlined. */
	protected boolean filled;
    /** Upper-left X-coordinate(ulx), Upper-left Y-coordinate(uly), Width, Height of the shape’s bounding box. */
	protected double  ulx, uly, width, height;

    // ----- CONSTRUCTORS -----

    /**
     * Default no-argument constructor.
     * Initializes a shape without defined points.
     */
    public MyShape ()
	{

	}

    /**
     * Constructs a shape from two points.
     * 
     * @param point1 The first defining point
     * @param point2 The second defining point
     */
    public MyShape (Point2D point1, Point2D point2)
	{
		p1 = point1;
		p2 = point2;

        updateBounds();
		updateCenter();

		filled = false;
	}

    /**
     * Constructs a shape from four coordinates.
     * 
     * @param x1 X-coordinate of the first point
     * @param y1 Y-coordinate of the first point
     * @param x2 X-coordinate of the second point
     * @param y2 Y-coordinate of the second point
     */
    public MyShape (double x1, double y1, double x2, double y2)
	{
		this(new Point2D (x1, y1), new Point2D (x2, y2));
	}

    // ----- GETTER & SETTER METHODS -----

    /** @return the first defining point of the shape */
    public Point2D getP1 ()
	{
		return p1;
	}

    /** @return the second defining point of the shape */
	public Point2D getP2 ()
	{
		return p2;
	}

    /** @return the color of the shape */
	public Color getColor ()
	{
		return color;
	}

    /** @return true if the shape is filled, false otherwise */
	public boolean isFilled ()
	{
		return filled;
	}

    /** @return X-coordinate of the upper-left corner of the bounding box */
	public double getULX ()
	{
		return ulx;
	}

    /** @return Y-coordinate of the upper-left corner of the bounding box */
	public double getULY ()
	{
		return uly;
	}

    /** @return width of the bounding box */
	public double getWidth ()
	{
		return width;
	}

    /** @return height of the bounding box */
	public double getHeight ()
	{
		return height;
	}

    /** @return the center point of the shape */
	public Point2D getCenter ()
	{
		return center;
	}

    /** Sets the first defining point. */
	public void setP1 (Point2D point1)
	{
		p1 = point1;
	}

    /** Sets the first defining point using coordinates. */
	public void setP1 (double x, double y)
	{
		p1 = new Point2D (x, y);
	}

    /** Sets the first defining point using coordinates. */
	public void setP2 (Point2D point2)
	{
		p2 = point2;

        updateBounds();
		updateCenter();
	}

    /** Sets the second defining point and updates bounds and center. */
	public void setP2 (double x, double y)
	{
		p2 = new Point2D (x, y);

        updateBounds();
		updateCenter();
	}

    /** Sets the shape color. */
	public void setColor (Color color)
	{
		this.color = color;
	}

    /** Sets whether the shape is filled. */
	public void setFilled (boolean filled)
	{
		this.filled = filled;
	}

    /**
     * Returns a formatted string representing the shape's points, fill state, and color components.
     */
    @Override
    public String toString ()
	{
		return String.format("%.0f %.0f %.0f %.0f %b %.3f %.3f %.3f\n", p1.getX(), p1.getY(), p2.getX(), p2.getY(), filled, color.getRed(), color.getGreen(), color.getBlue());
	}

    /**
     * Updates the bounding box based on current points.
     */
    public void updateBounds ()
	{
		ulx = Math.min(p1.getX(), p2.getX());
		uly = Math.min(p1.getY(), p2.getY());

		width  = Math.abs(p2.getX() - p1.getX());
		height = Math.abs(p2.getY() - p1.getY());
	}

    /** Updates the center point based on current points. */
	public void updateCenter ()
	{
		center = p1.midpoint(p2);
	}

    /**
     * Calculates the Euclidean distance from a point to the center of the shape.
     * @param x X-coordinate of the point
     * @param y Y-coordinate of the point
     * @return distance from the shape’s center
     */
	public double distance (double x, double y)
	{
		double distance = center.distance(x, y);

		return distance;
	}
	
    /**
     * Moves the shape by the specified offsets.
     * @param dx offset along X-axis
     * @param dy offset along Y-axis
     */
	public void move (double dx, double dy)
	{
		p1 = p1.add(dx, dy);
		p2 = p2.add(dx, dy);
		
		updateBounds();
		updateCenter();
	}

    /**
     * Creates a deep copy of this shape.
     * @return a cloned {@code MyShape} object
     */
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

    /**
     * Draws the bounding box for selection or visual aid.
     * @param gc JavaFX GraphicsContext used for rendering
     */
    public void drawBounds (GraphicsContext gc)
	{
		gc.setLineDashes(4);
		gc.setStroke(bBoxColor);
		gc.strokeRect(ulx, uly, width, height);
	}

    /**
     * Abstract method for rendering the shape.
     * Subclasses must provide the drawing implementation.
     * @param gc JavaFX GraphicsContext used for rendering
     */
    public abstract void draw (GraphicsContext gc);

    /**
     * Custom serialization to handle transient fields (points and color).
     * @param oos ObjectOutputStream to write the object state
     * @throws IOException if an I/O error occurs
     */
    private void writeObject (ObjectOutputStream oos) throws IOException
	{
		// write co-ordinates of p1, p2
		
		oos.writeDouble(p1.getX());
		oos.writeDouble(p1.getY());
		
		oos.writeDouble(p2.getX());
		oos.writeDouble(p2.getY());
		
		// performs default serialization
		
		oos.defaultWriteObject();
		
		// writes components of color
		
		oos.writeDouble(color.getRed());
		oos.writeDouble(color.getGreen());
		oos.writeDouble(color.getBlue());
	}

    /**
     * Custom deserialization to restore transient fields.
     * @param ois ObjectInputStream to read the object state
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if a class cannot be found
     */
    private void readObject (ObjectInputStream ois) throws ClassNotFoundException, IOException
	{
		// read co-ordinates of p1, p2
		
		double x1 = ois.readDouble();
		double y1 = ois.readDouble();
		
		setP1 (x1, y1);
		
		double x2 = ois.readDouble();
		double y2 = ois.readDouble();
		
		setP2 (x2, y2);
		
		// performs default deserialization
		
		ois.defaultReadObject();
		
		// read each color component
		
		double r = ois.readDouble();
		double g = ois.readDouble();
		double b = ois.readDouble();
		
		color = Color.color(r, g, b);
	}
}
