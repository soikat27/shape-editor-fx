package handlers;

import javafx.scene.input.MouseEvent;
import shapes.Rect;

public class RectHandler extends DrawHandler {
    
    // ----- CONSTRUCTORS -----
    public RectHandler (ShapeCanvas sc)
	{
		super(sc);
	}

    // ----- METHODS -----
    @Override
	public void mousePressed (MouseEvent e)
	{
		shape = new Rect ();

		super.mousePressed (e);
	}
}
