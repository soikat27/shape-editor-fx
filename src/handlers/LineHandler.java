package handlers;

import javafx.scene.input.MouseEvent;
import shapes.Line;

public class LineHandler extends DrawHandler{
    
    // ----- CONSTRUCTORS -----
    public LineHandler (ShapeCanvas sc)
	{
		super(sc);
	}

    // ----- METHODS -----
    @Override
	public void mousePressed (MouseEvent e)
	{
		shape = new Line ();

		super.mousePressed (e);
	}
}
