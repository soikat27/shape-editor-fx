package edits;

import editor.ShapeCanvas;
import shapes.MyShape;

/**
 * Represents an undo-able move operation on a {@link MyShape} within a {@link ShapeCanvas}.
 * <p>
 * This class extends the abstract {@link Edit} base class and provides functionality
 * to undo and redo the movement of a shape. When an instance of {@code MoveEdit} is created,
 * it stores a reference to the shape being moved, the canvas it belongs to, and the
 * displacement applied to the shape.
 * The displacement is represented by the horizontal and vertical offsets ({@code dx} and {@code dy}),
 * which are applied during redo and reversed during undo.
 * <p>
 * 
 * @author Soikat
 * @see Edit
 * @see ShapeCanvas
 * @see MyShape
 */
public class MoveEdit extends Edit {
    
    // ----- FIELDS -----
	/** Horizontal & Vertical displacement applied to the shape */
    private double dx, dy;

    // ----- CONSTRUCTORS -----
	/**
     * Creates a new {@code MoveEdit} representing a movement applied to a shape.
     *
     * @param sc the {@link ShapeCanvas} where the shape resides
     * @param s  the {@link MyShape} that was moved
     * @param x  the horizontal displacement applied to the shape
     * @param y  the vertical displacement applied to the shape
     */
    public MoveEdit (ShapeCanvas sc, MyShape s, double x, double y)
	{
		super (sc, s);
		dx = x;
		dy = y;
	}

	// ----- METHODS -----
	/**
     * Undoes the move operation by translating the shape in the opposite direction
     * of the original displacement, then repainting the canvas.
     */
	@Override
	public void undo ()
	{
		shape.move(-dx, -dy);
		canvas.paint();
	}

	/**
     * Redoes the move operation by applying the stored displacement to the shape
     * and repainting the canvas.
     */
	@Override
	public void redo ()
	{
		shape.move(dx, dy);
		canvas.paint();
	}
}
