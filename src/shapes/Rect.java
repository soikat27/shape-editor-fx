package shapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * {@code Rect} is a concrete subclass of {@link MyShape} representing a rectangle
 * defined by two corner points. It provides methods to draw the rectangle,
 * move it, and access its properties.
 * <p>
 * This class is part of a JavaFX-based shape editor application.
 * </p>
 * 
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * Point2D topLeft = new Point2D(10, 20);
 * Point2D bottomRight = new Point2D(100, 80);
 * Rect rect = new Rect(topLeft, bottomRight);
 * rect.setColor(Color.RED);
 * rect.setFilled(true);
 * rect.draw(graphicsContext);
 * }</pre>
 * 
 * @author Soikat Saha
 */
public class Rect extends MyShape {
    
    /**
     * Default constructor. Creates a rectangle with undefined corner points.
     * Points must be set later using {@code setP1(...)} and {@code setP2(...)}.
     */
    public Rect ()
	{

	}

    /**
     * Constructs a {@code Rect} with the given corner points.
     *
     * @param point1 the first corner point of the rectangle
     * @param point2 the second corner point of the rectangle
     */
    public Rect (Point2D point1, Point2D point2)
	{
		super(point1, point2);
	}

    /**
     * Constructs a {@code Rect} with the given coordinates for the corner points.
     *
     * @param x1 X-coordinate of the first corner
     * @param y1 Y-coordinate of the first corner
     * @param x2 X-coordinate of the second corner
     * @param y2 Y-coordinate of the second corner
     */
    public Rect (double x1, double y1, double x2, double y2)
	{
		super(x1, y1, x2, y2);
	}

    /**
     * Returns a string representation of this {@code Rect}.
     * Includes the class name and the string representation of its corner points,
     * color, and fill status.
     *
     * @return a string representation of this {@code Rect}
     */
    @Override
	public String toString ()
	{
		return String.format("Rect %s", super.toString());
	}

    /**
     * Draws this {@code Rect} on the given {@link GraphicsContext}.
     * The method draws the bounding box and the rectangle itself.
     * If {@link #isFilled()} is {@code true}, the rectangle is filled with its color.
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
			gc.fillRect(ulx, uly, width, height);
		} 

		else 
		{
			gc.setStroke(color);
			gc.setLineDashes(null);
			gc.strokeRect(ulx, uly, width, height);
		}
	}
}
