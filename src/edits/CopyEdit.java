package edits;

import editor.ShapeCanvas;
import shapes.MyShape;
/**
 * Represents a copy operation on a shape in the canvas. It's basically part of the undo/redo system. 
 * 
 * @author Soikat
 */
public class CopyEdit extends Edit {
    
    // ----- CONSTRUCTORS -----
    /**
     * Constructs a CopyEdit for a given canvas and shape.
     *
     * @param sc The canvas where the shape is located
     * @param s The shape that was copied
     */
    public CopyEdit (ShapeCanvas sc, MyShape s)
	{
		super (sc, s);
	}

    // ----- METHODS -----
    /**
     * Undoes the copy operation by removing the shape from the canvas
     * and repainting the canvas.
     */
    @Override
	public void undo ()
	{
		canvas.deleteShape(shape);
		canvas.paint();
	}

    /**
     * Redoes the copy operation by adding the shape back to the canvas
     * and repainting it to reflect the change.
     */
    @Override
	public void redo ()
	{
		canvas.addShape(shape);
        canvas.paint();
	}
}
