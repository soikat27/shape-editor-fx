package handlers;

import javafx.scene.input.MouseEvent;
import shapes.Oval;

public class OvalHandler extends DrawHandler {
    
    // ----- CONSTRUCTORS -----
    public OvalHandler (ShapeCanvas sc)
	{
		super(sc);
	}

    // ----- METHODS -----
    @Override
	public void mousePressed (MouseEvent e)
	{
		shape = new Oval ();
		
		super.mousePressed (e);
	}
}
