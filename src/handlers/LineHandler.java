package handlers;

import editor.ShapeCanvas;
import javafx.scene.input.MouseEvent;
import shapes.Line;

/**
 * Mouse event handler for drawing {@link Line} shapes on a {@link ShapeCanvas}.
 * <p>
 * This class extends {@link DrawHandler} and overrides the {@code mousePressed} method
 * to initialize a new {@link Line} shape when the user presses the mouse.
 * </p>
 *
 * @author Soikat Saha
 * @see DrawHandler
 * @see ShapeCanvas
 * @see Line
 * @see javafx.scene.input.MouseEvent
 */
public class LineHandler extends DrawHandler{
    
    // ----- CONSTRUCTORS -----

    /**
     * Constructs a {@code LineHandler} associated with the given {@link ShapeCanvas}.
     *
     * @param sc the {@link ShapeCanvas} where lines will be drawn
     */
    public LineHandler (ShapeCanvas sc)
	{
		super(sc);
	}

    // ----- METHODS -----

    /**
     * Called when the user presses the mouse to start drawing a line.
     * <p>
     * This method creates a new {@link Line} object and delegates to the
     * parent {@link DrawHandler#mousePressed(MouseEvent)} to handle common behavior.
     * </p>
     *
     * @param e the {@link MouseEvent} representing the mouse press
     * @see DrawHandler#mousePressed(MouseEvent)
     */
    @Override
	public void mousePressed (MouseEvent e)
	{
		shape = new Line ();

		super.mousePressed (e);
	}
}
