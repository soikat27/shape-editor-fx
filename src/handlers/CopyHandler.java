package handlers;

import editor.ShapeCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

/**
 * EventHandler implementation for copying shapes on a ShapeCanvas.
 * <p>
 * When the user clicks on the canvas with "copy" radio button on, this handler identifies the shape 
 * closest to the mouse cursor, creates a clone of it, and allows the user 
 * to drag the cloned shape to a new position.
 * </p>
 */
public class CopyHandler implements EventHandler<MouseEvent> {
    
    /** The canvas on which shapes are drawn. */
    private ShapeCanvas canvas;
    /** The shape closest to the mouse press location. */
	private MyShape     closestShape;
    /** The cloned copy of the shape being dragged. */
	private MyShape     copyShape;
    /** Previous mouse X and Y coordinates during dragging. */
	private double x0, y0;
    /** Current mouse X and Y coordinates during dragging. */
    private double x1, y1;
	
    // ----- CONSTRUCTORS -----

    /**
     * Constructs a CopyHandler associated with the specified ShapeCanvas.
     *
     * @param sc the ShapeCanvas to attach this handler to
     */
    public CopyHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

    // ----- LOGICAL METHODS -----

    /**
     * Handles the mouse pressed event.
     * <p>
     * Finds the closest shape to the mouse press, clones it, adds the clone 
     * to the canvas, and records the initial mouse coordinates for dragging.
     * </p>
     *
     * @param e the MouseEvent representing the mouse press
     */
    private void mousePressed (MouseEvent e)
	{
		double mx = e.getX();
		double my = e.getY();
			
		closestShape = canvas.closestShape(mx, my);

		if (closestShape != null)
		{
			copyShape = (MyShape) closestShape.clone();
			canvas.addShape(copyShape);
				
			x0 = mx;
			y0 = my;
		}
	}

    /**
     * Handles the mouse dragged event.
     * <p>
     * Moves the cloned shape according to the mouse drag distance and repaints
     * the canvas.
     * </p>
     *
     * @param e the MouseEvent representing the mouse drag
     */
    private void mouseDragged (MouseEvent e)
	{
		x1 = e.getX();
		y1 = e.getY();
			
		if (copyShape != null)
		{
			double dx = x1 - x0;
			double dy = y1 - y0;
				
			copyShape.move(dx, dy);
			canvas.paint();
		}
			
		x0 = x1;
		y0 = y1;
	}

    /**
     * Handles the mouse event by delegating to {@link #mousePressed} or
     * {@link #mouseDragged} based on the type of mouse event.
     *
     * @param e the MouseEvent to handle
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
