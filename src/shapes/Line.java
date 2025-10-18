package shapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * {@code Line} is a concrete subclass of {@link MyShape} representing a line
 * defined by two endpoints. It provides methods to draw the line, move it, and
 * access its properties. 
 * <p>
 * This class is part of a JavaFX-based shape editor application.
 * </p>
 * 
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * Point2D start = new Point2D(10, 20);
 * Point2D end = new Point2D(50, 60);
 * Line line = new Line(start, end);
 * line.setColor(Color.BLUE);
 * line.draw(graphicsContext);
 * }</pre>
 * 
 * @author Soikat Saha
 */
public class Line extends MyShape{
    
    /**
     * Default constructor. Creates a line with undefined endpoints.
     * Endpoints must be set later using {@code setP1(...)} and {@code setP2(...)}.
     */
    public Line ()
	{

	}

    /**
     * Constructs a {@code Line} with the given endpoints.
     *
     * @param point1 the first endpoint of the line
     * @param point2 the second endpoint of the line
     */
    public Line (Point2D point1, Point2D point2)
	{
		super(point1, point2);
	}

    /**
     * Constructs a {@code Line} with the given coordinates for endpoints.
     *
     * @param x1 X-coordinate of the first endpoint
     * @param y1 Y-coordinate of the first endpoint
     * @param x2 X-coordinate of the second endpoint
     * @param y2 Y-coordinate of the second endpoint
     */
    public Line (double x1, double y1, double x2, double y2)
	{
		super(x1, y1, x2, y2);
	}

    /**
     * Returns a string representation of this {@code Line}.
     * Includes the class name and the string representation of its endpoints,
     * color, and fill status.
     *
     * @return a string representation of this {@code Line}
     */
    @Override
	public String toString ()
	{
		return String.format("Line %s", super.toString());
	}

    /**
     * Draws this {@code Line} on the given {@link GraphicsContext}.
     * The method draws the bounding box and the line itself.
     *
     * @param gc the {@code GraphicsContext} used for drawing
     */
    @Override
	public void draw (GraphicsContext gc)
	{	
		// drawing the bounding box

		drawBounds (gc);

		// drawing the shape

		double startX = p1.getX();
		double startY = p1.getY();

		double endX   = p2.getX();
		double endY   = p2.getY();
		
		gc.setLineDashes(null);
		gc.setStroke(color);
		gc.strokeLine(startX, startY, endX, endY);
	}
}
