package edits;

import editor.ShapeCanvas;
import shapes.MyShape;

/**
 * Represents an reversible delete operation on a {@link MyShape} within a {@link ShapeCanvas}.
 * <p>
 * This class extends the abstract {@link Edit} base class and provides functionality
 * to undo and redo the deletion of a shape. When an instance of {@code DeleteEdit} is created,
 * it stores a reference to the shape being deleted and the canvas it belongs to.
 * <p>
 * This class is intended to be used as part of an undo/redo system in the drawing application.
 * 
 * @author Soikat
 * @see Edit
 * @see ShapeCanvas
 * @see MyShape
 */
public class DeleteEdit extends Edit {
    
    // ----- CONSTRUCTORS -----
    /**
     * Creates a new {@code DeleteEdit} for the specified shape on the given canvas.
     *
     * @param sc the {@link ShapeCanvas} where the shape resides
     * @param s  the {@link MyShape} that will be deleted and managed for undo/redo
     */
    public DeleteEdit (ShapeCanvas sc, MyShape s)
	{
		super (sc, s);
	}

    // ----- METHODS -----
    /**
     * Undoes the deletion operation by adding the shape back to the canvas
     * and repainting it.
     */
    @Override
	public void undo ()
	{
		canvas.addShape(shape);
		canvas.paint();
	}
    
    /**
     * Redoes the deletion operation by removing the shape from the canvas
     * and repainting it.
     */
    @Override
	public void redo ()
	{
		canvas.deleteShape(shape);
		canvas.paint();
	}
}
