package handlers;

import editor.ShapeCanvas;
import edits.DeleteEdit;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

/**
 Event handler responsible for deleting shapes from a {@link ShapeCanvas}.
 *
 * <p>When the user clicks on the canvas, the shape closest to the click
 * location is identified and removed from the canvas. The deletion is
 * recorded as a {@link DeleteEdit} so that the action can be undone
 * or redone through the application's undo/redo system.</p>
 *
 * @author Soikat
 */
public class DeleteHandler implements EventHandler<MouseEvent> {
    
	// ----- FIELDS -----
    /**
     * The ShapeCanvas on which shapes are drawn and deleted 
	 */
    private ShapeCanvas canvas;

    // ----- CONSTRUCTORS -----
    /**
     * Constructs a DeleteHandler with the specified ShapeCanvas.
     *
     * @param sc The ShapeCanvas to associate with this handler
     */
    public DeleteHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

    // ----- METHODS -----
    /**
     * Handles mouse click events to delete the shape closest to the click location and record the deletion in undo stack of the canvas.
     *
     * @param e The MouseEvent representing the mouse click
     */
    @Override
	public void handle (MouseEvent e)
	{
		String eventName = e.getEventType().getName();
		
		if (eventName.equalsIgnoreCase("MOUSE_CLICKED"))
		{
			MyShape selectedShape = canvas.closestShape(e.getX(), e.getY());
			
			if (selectedShape != null)
			{
				canvas.addEdit(new DeleteEdit (canvas, selectedShape));
				canvas.deleteShape(selectedShape);
				canvas.paint();
			}
		}
	}
}
