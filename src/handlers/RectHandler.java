package handlers;

import javafx.scene.input.MouseEvent;
import shapes.Rect;

/**
 * Mouse event handler for drawing {@link Rect} shapes on a {@link ShapeCanvas}.
 * <p>
 * This class extends {@link DrawHandler} and overrides the {@code mousePressed} method
 * to initialize a new {@link Rect} shape when the user presses the mouse.
 * </p>
 * 
 * @author Soikat Saha
 * @see DrawHandler
 * @see ShapeCanvas
 * @see Rect
 * @see javafx.scene.input.MouseEvent
 */
public class RectHandler extends DrawHandler {
    
    // ----- CONSTRUCTORS -----

    /**
     * Constructs a {@code RectHandler} associated with the given {@link ShapeCanvas}.
     *
     * @param sc the {@link ShapeCanvas} where rectangles will be drawn
     */
    public RectHandler (ShapeCanvas sc)
	{
		super(sc);
	}

    // ----- METHODS -----

    /**
     * Called when the user presses the mouse to start drawing a rectangle.
     * <p>
     * This method creates a new {@link Rect} object and delegates to the
     * parent {@link DrawHandler#mousePressed(MouseEvent)} to handle common behavior.
     * </p>
     *
     * @param e the {@link MouseEvent} representing the mouse press
     * @see DrawHandler#mousePressed(MouseEvent)
     */
    @Override
	public void mousePressed (MouseEvent e)
	{
		shape = new Rect ();

		super.mousePressed (e);
	}
}
