package shapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * {@code Oval} is a concrete subclass of {@link MyShape} representing an oval
 * defined by its bounding rectangle (two corner points). It provides methods
 * to draw the oval, move it, and access its properties.
 * <p>
 * This class is part of a JavaFX-based shape editor application.
 * </p>
 * 
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * Point2D topLeft = new Point2D(20, 30);
 * Point2D bottomRight = new Point2D(150, 100);
 * Oval oval = new Oval(topLeft, bottomRight);
 * oval.setColor(Color.BLUE);
 * oval.setFilled(true);
 * oval.draw(graphicsContext);
 * }</pre>
 * 
 * @author Soikat Saha
 */
public class Oval extends MyShape {
    
    /**
     * Default constructor. Creates an oval with undefined corner points.
     * Points must be set later using {@code setP1(...)} and {@code setP2(...)}.
     */
    public Oval ()
	{

	}

    /**
     * Constructs an {@code Oval} with the given corner points.
     *
     * @param point1 the first corner point of the bounding rectangle
     * @param point2 the second corner point of the bounding rectangle
     */
    public Oval (Point2D point1, Point2D point2)
	{
		super(point1, point2);
	}

    /**
     * Constructs an {@code Oval} with the given coordinates for the bounding rectangle.
     *
     * @param x1 X-coordinate of the first corner
     * @param y1 Y-coordinate of the first corner
     * @param x2 X-coordinate of the second corner
     * @param y2 Y-coordinate of the second corner
     */
    public Oval (double x1, double y1, double x2, double y2)
	{
		super(x1, y1, x2, y2);
	}

    /**
     * Returns a string representation of this {@code Oval}.
     * Includes the class name and the string representation of its corner points,
     * color, and fill status.
     *
     * @return a string representation of this {@code Oval}
     */
    @Override
	public String toString ()
	{
		return String.format("Oval %s", super.toString());
	}

    /**
     * Draws this {@code Oval} on the given {@link GraphicsContext}.
     * The method draws the bounding box and the oval itself.
     * If {@link #isFilled()} is {@code true}, the oval is filled with its color.
     * Otherwise, it is drawn as an outline.
     *
     * @param gc the {@code GraphicsContext} used for drawing
     */
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
