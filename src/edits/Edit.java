package edits;

import editor.ShapeCanvas;
import shapes.MyShape;

/**
 * The {@code Edit} class represents an abstract editing operation that can be
 * performed on a {@link MyShape} object within a {@link ShapeCanvas}.
 *
 * <p>This class serves as the foundation for the application's undo/redo system.
 * Each concrete subclass represents a specific type of editing action
 * (for example deleting, copying, or moving a shape) and must implement
 * the {@link #undo()} and {@link #redo()} methods to reverse or reapply
 * the operation.</p>
 *
 * <p>Subclasses store references to the affected shape and the canvas on
 * which the operation occurred, allowing the edit to be undone or redone
 * at any time.</p>
 *
 * <p>This design follows the <em>Command Pattern</em>, where each editing
 * action is encapsulated as an object that knows how to execute,
 * undo, and redo itself.</p>
 *
 * @author Soikat
 */
public abstract class Edit {
    
    // ----- FIELDS -----
    /** The shape affected by the editing operation. */
    protected MyShape     shape;
    /** The canvas on which the editing operation occurs. */
	protected ShapeCanvas canvas;

    // ----- CONSTRUCTORS -----
    /**
     * Constructs a new {@code Edit} associated with a specific shape
     * and canvas.
     *
     * @param sc the {@link ShapeCanvas} where the edit occurs
     * @param s  the {@link MyShape} affected by the edit
     */
    public Edit (ShapeCanvas sc, MyShape s)
	{
		shape  = s;
		canvas = sc;
	}

    // ----- METHODS -----
    /**
     * Reverses the editing operation, restoring the previous state.
     * Implementations define how the action is undone.
     */
    public abstract void undo ();

    /**
     * Reapplies the editing operation after it has been undone.
     * Implementations define how the action is redone.
     */
    public abstract void redo ();
}
