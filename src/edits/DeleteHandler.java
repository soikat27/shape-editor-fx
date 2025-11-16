package edits;

import editor.ShapeCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

public class DeleteHandler implements EventHandler<MouseEvent> {
    
    private ShapeCanvas canvas;

    // ----- CONSTRUCTORS -----
    public DeleteHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

    // ----- LOGICAL METHODS -----
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
