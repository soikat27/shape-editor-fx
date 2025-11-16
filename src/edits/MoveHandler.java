package edits;

import editor.ShapeCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

/**
 * EventHandler implementation for handling mouse events to move shapes on the canvas.
 *
 * <p>This handler allows the user to click on the closest shape and drag it to a new position.
 * The canvas is updated after every movement.</p>
 */
public class MoveHandler implements EventHandler<MouseEvent> {
    
	/** The canvas containing the shapes to move */
    private ShapeCanvas canvas;
	/** The currently selected shape closest to the mouse */
	private MyShape     closestShape;
	/** Previous mouse X and Y coordinates */
	private double x0, y0;
	/** Current mouse X and Y coordinates */
	private double x1, y1;

    // ----- CONSTRUCTORS -----

	/**
     * Constructs a MoveHandler associated with the given canvas.
     *
     * @param sc The ShapeCanvas to move shapes on
     */
    public MoveHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

    // ----- LOGICAL METHODS -----

	/**
     * Handles mouse press events to select the shape closest to the mouse.
     *
     * @param e The MouseEvent representing the press
     */
    private void mousePressed (MouseEvent e)
	{
		double mx = e.getX();
		double my = e.getY();
			
		closestShape = canvas.closestShape(mx, my);

		if (closestShape != null)
		{
			x0 = mx;
			y0 = my;
		}
	}

	/**
     * Handles mouse drag events to move the currently selected shape.
     *
     * @param e The MouseEvent representing the drag
     */
    private void mouseDragged (MouseEvent e)
	{
		x1 = e.getX();
		y1 = e.getY();
			
		if (closestShape != null)
		{
			double dx = x1 - x0;
			double dy = y1 - y0;
				
			closestShape.move(dx, dy);
			canvas.paint();
		}
			
		x0 = x1;
		y0 = y1;
	}

	/**
     * Handles mouse events by delegating to the appropriate method
     * based on event type (press or drag).
     *
     * @param e The MouseEvent to handle
     */
    @Override
	public void handle (MouseEvent e)
	{
        String eventName = e.getEventType().getName();
		
		switch (eventName)
		{
		case "MOUSE_PRESSED":
			mousePressed(e);
			break;
		case "MOUSE_DRAGGED":
			mouseDragged(e);
			break;
		default:
			break;
		}
	}
}
