package edits;

import editor.ShapeCanvas;
import shapes.MyShape;

/**
 * This class represents a drawing action on a {@link ShapeCanvas} that can be undone or redone.
 * <p>
 * This class extends {@link Edit} and is used to encapsulate the action of adding a new shape
 * to the canvas. It integrates with the canvas's undo/redo system so that users can revert
 * or reapply the drawing operation.
 * </p>
 * 
 * @author Soikat
 */
public class DrawEdit extends Edit {
    
    // ----- CONSTRUCTORS -----
    /**
     * Constructs a new DrawEdit for the specified shape on the given canvas.
     *
     * @param sc    the {@link ShapeCanvas} on which the shape is drawn
     * @param s     the {@link MyShape} that was drawn
     */
    public DrawEdit (ShapeCanvas sc, MyShape s)
	{
		super (sc, s);
	}

    // ----- METHODS -----
    /**
     * Undoes the drawing action by removing the shape from the canvas.
     * Repaints the canvas to reflect the removal.
     */
    @Override
	public void undo ()
	{
		canvas.deleteShape(shape);
		canvas.paint();
	}

    /**
     * Redoes the drawing action by adding the shape back to the canvas.
     * Repaints the canvas to ensure it is visible.
     */
    @Override
	public void redo ()
	{
		canvas.addShape(shape);
        canvas.paint();
	}
}
