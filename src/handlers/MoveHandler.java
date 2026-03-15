package handlers;

import editor.ShapeCanvas;
import edits.MoveEdit;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

/**
 * {@code MoveHandler} is a JavaFX {@link EventHandler} implementation that enables
 * interactive movement of shapes on a {@link ShapeCanvas}.
 *
 * <p>
 * When attached to a canvas, this handler allows a user to click on the closest shape
 * to the mouse cursor, drag it across the canvas, and release it. Each movement is
 * captured as a {@link MoveEdit}, enabling undo/redo functionality.
 * </p>
 */
public class MoveHandler implements EventHandler<MouseEvent> {
    
	/** The canvas containing the shapes to move */
    private ShapeCanvas canvas;
	/** The currently selected shape closest to the mouse */
	private MyShape     selectedShape;
	/** Initial mouse press coordinates */
	private double startX, startY;
	/** Initial mouse press coordinates */
	private double lastX, lastY;

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
		double x = e.getX();
		double y = e.getY();
		selectedShape = canvas.closestShape(x, y);

		if (selectedShape != null)
		{
			lastX = startX = x;
			lastY = startY = y;
		}
	}

	/**
     * Handles mouse drag events to move the currently selected shape.
     *
     * @param e The MouseEvent representing the drag
     */
    private void mouseDragged (MouseEvent e)
	{			
		if (selectedShape != null)
		{
			double dx = e.getX() - lastX;
			double dy = e.getY() - lastY;
				
			selectedShape.move(dx, dy);
			canvas.paint();

			lastX = e.getX();
			lastY = e.getY();
		}
	}

	/**
     * Handles mouse release events to record the full movement as a {@link MoveEdit}.
     *
     * @param e The representing the release
     */
	private void mouseReleased (MouseEvent e)
	{
		if (selectedShape != null)
		{
			double totalX = lastX - startX;
            double totalY = lastY - startY;
			canvas.addEdit(new MoveEdit(canvas, selectedShape, totalX, totalY));
		}
	}

	/**
     * Handles mouse events by delegating to the appropriate method
     * based on event type (press or drag or Release).
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
			case "MOUSE_RELEASED":
				mouseReleased(e);
            	break;
			default:
				break;
		}
	}
}
