package edits;

import editor.ShapeCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;

public class CopyHandler implements EventHandler<MouseEvent> {
    
    private ShapeCanvas canvas;
	private MyShape     closestShape;
	private MyShape     copyShape;
	private double x0, y0;
	
    // ----- CONSTRUCTORS -----
    public CopyHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

    // ----- LOGICAL METHODS -----
    @Override
	public void handle (MouseEvent event)
	{
        
	}
}
