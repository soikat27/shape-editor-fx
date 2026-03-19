package handlers;

import editor.ShapeCanvas;
import edits.CopyEdit;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

/**
 * EventHandler implementation for copying shapes on a ShapeCanvas.
 * 
 * <p>
 * Clicking on a shape creates a copy, which can be dragged to a new location.
 * The operation is added to the canvas's undo/redo system via {@link CopyEdit}. This class ensures that each copied shape can be manipulated independently
 * while preserving the original shape.
 * </p>
 * 
 * @author Soikat
 */
public class CopyHandler implements EventHandler<MouseEvent> {
    
	// ----- FIELDS -----
    /** The canvas on which shapes are drawn. */
    private ShapeCanvas canvas;
    /** The selected shape - closest to the mouse press location. */
	private MyShape     selectedShape;
    /** The cloned copy of the shape being selected. */
	private MyShape     copiedShape;
    /** Mouse coordinates: last recorded position */
	private double lastX, lastY;
	
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

    // ----- METHODS -----
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
		double x = e.getX();
        double y = e.getY();	
		selectedShape = canvas.closestShape(x, y);

		if (selectedShape != null)
		{
			copiedShape = (MyShape) selectedShape.clone();
			canvas.addShape(copiedShape);
				
			lastX = x;
            lastY = y;
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
		if (copiedShape != null)
		{
			double dx = e.getX() - lastX;
			double dy = e.getY() - lastY;
				
			copiedShape.move(dx, dy);
			canvas.paint();

			lastX = e.getX();
			lastY = e.getY();
		}
	}

	/**
	 * Handles mouse release to register the copy operation in undo/redo system.
	 * 
	 * @param e the MouseEvent representing the mouse release
	 */
	private void mouseReleased (MouseEvent e)
	{
		if (copiedShape != null)
			canvas.addEdit(new CopyEdit (canvas, copiedShape));
	}

    /**
     * Handles the mouse event by delegating to {@link #mousePressed},
     * {@link #mouseDragged} or {@link #mouseReleased} based on the type of mouse event.
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
			case "MOUSE_RELEASED":
				mouseReleased(e);
            	break;
			default:
				break;
		}
	}
}
