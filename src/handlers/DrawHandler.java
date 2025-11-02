package handlers;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

/**
 * The {@code DrawHandler} class manages mouse events during the creation of a new shape
 * on a {@link ShapeCanvas}. It listens for press, drag, and release events to define
 * a {@link MyShape} object’s geometry interactively.
 *
 * <p>This is the base class for specific shape-drawing handlers
 * (e.g., {@code LineHandler}, {@code RectHandler}, {@code OvalHandler}),
 * and can be extended to support different types of shapes.</p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *   <li>Capture the first mouse position to define the starting point of a shape.</li>
 *   <li>Track mouse dragging to dynamically update the shape’s endpoint and redraw the canvas.</li>
 *   <li>Finalize and register the completed shape when the mouse is released.</li>
 * </ul>
 *
 * <p>Implements {@link javafx.event.EventHandler} for {@link javafx.scene.input.MouseEvent}.</p>
 *
 * @see MyShape
 * @see ShapeCanvas
 * @see javafx.scene.input.MouseEvent
 */
public class DrawHandler {
    
    /** The shape currently being drawn. */
    protected MyShape     shape;
    /** The canvas on which shapes are drawn. */
	protected ShapeCanvas canvas;

    // ----- CONSTRUCTORS -----

    /**
     * Constructs a {@code DrawHandler} associated with a specific {@link ShapeCanvas}.
     *
     * @param sc the canvas this handler will interact with
     */
    public DrawHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

    // ----- METHODS -----

    /**
     * Invoked when the mouse is pressed — this marks the beginning of drawing a new shape.
     * The first point ({@code p1}) of the shape is initialized based on the mouse position.
     *
     * @param e the {@link MouseEvent} representing the mouse press
     */
    protected void mousePressed (MouseEvent e)
	{
		if (shape != null)
		{	
			double mx = e.getX();
			double my = e.getY();

			shape.setP1(new Point2D (mx, my));

			canvas.setCurrShape(shape);
		}
	}

    /**
     * Invoked continuously as the mouse is dragged after the initial press.
     * Updates the second point ({@code p2}) of the shape in real time and repaints the canvas
     * to provide immediate visual feedback.
     *
     * @param e the {@link MouseEvent} representing the mouse drag
     */
    protected void mouseDragged (MouseEvent e)
	{
		if (shape != null)
		{
			double mx = e.getX();
			double my = e.getY();

			shape.setP2(mx, my);

			canvas.paint();
		}
	}

    /**
     * Invoked when the mouse button is released — this finalizes the shape.
     * The shape is added to the canvas and the temporary drawing state is cleared.
     *
     * @param e the {@link MouseEvent} representing the mouse release
     */
    protected void mouseReleased (MouseEvent e)
	{
		if (shape != null)
		{
			canvas.addShape(shape);
			canvas.setCurrShape(null);

			shape = null;
		}
	}

    /**
     * The central event dispatcher for this handler.
     * Delegates to the appropriate method based on the {@link MouseEvent} type.
     *
     * @param event the mouse event to handle
     */
    @Override
	public void handle (MouseEvent event)
	{
		String eventName = event.getEventType().getName();
		System.out.println ("event name: " + eventName);

		switch (eventName)
		{
		case "MOUSE_PRESSED":
			mousePressed(event);
			break;
		case "MOUSE_DRAGGED":
			mouseDragged(event);
			break;
		case "MOUSE_RELEASED":
			mouseReleased(event);
			break;
		default:
			break;
		}
	}
}
