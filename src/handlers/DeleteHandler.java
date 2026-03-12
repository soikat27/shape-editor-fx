package handlers;

import editor.ShapeCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

/**
 * EventHandler implementation for handling mouse events to delete shapes from a canvas.
 * 
 * When the user clicks on the canvas, the shape closest to the click location
 * is removed from the canvas.
 */
public class DeleteHandler implements EventHandler<MouseEvent> {
    
    /** The ShapeCanvas on which shapes are drawn and deleted */
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

    // ----- LOGICAL METHODS -----

    /**
     * Handles mouse click events to delete the shape closest to the click location.
     *
     * @param e The MouseEvent representing the mouse click
     */
    @Override
	public void handle (MouseEvent e)
	{
		String eventName = e.getEventType().getName();
		
		if (eventName.equalsIgnoreCase("MOUSE_CLICKED"))
		{	
			double mx = e.getX();
			double my = e.getY();
			
			MyShape closestShape = canvas.closestShape(mx, my);
			
			if (closestShape != null)
			{
				canvas.deleteShape(closestShape);
				canvas.paint();
			}
		}
	}
}
