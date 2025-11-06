package handlers;

import editor.ShapeCanvas;
import javafx.scene.input.MouseEvent;
import shapes.Oval;

/**
 * Mouse event handler for drawing {@link Oval} shapes on a {@link ShapeCanvas}.
 * <p>
 * This class extends {@link DrawHandler} and overrides the {@code mousePressed} method
 * to initialize a new {@link Oval} shape when the user presses the mouse.
 * </p>
 * 
 * @author Soikat Saha
 * @see DrawHandler
 * @see ShapeCanvas
 * @see Oval
 * @see javafx.scene.input.MouseEvent
 */
public class OvalHandler extends DrawHandler {
    
    // ----- CONSTRUCTORS -----

    /**
     * Constructs an {@code OvalHandler} associated with the given {@link ShapeCanvas}.
     *
     * @param sc the {@link ShapeCanvas} where ovals will be drawn
     */
    public OvalHandler (ShapeCanvas sc)
	{
		super(sc);
	}

    // ----- METHODS -----

    /**
     * Called when the user presses the mouse to start drawing an oval.
     * <p>
     * This method creates a new {@link Oval} object and delegates to the
     * parent {@link DrawHandler#mousePressed(MouseEvent)} to handle common behavior.
     * </p>
     *
     * @param e the {@link MouseEvent} representing the mouse press
     * @see DrawHandler#mousePressed(MouseEvent)
     */
    @Override
	public void mousePressed (MouseEvent e)
	{
		shape = new Oval ();
		
		super.mousePressed (e);
	}
}
