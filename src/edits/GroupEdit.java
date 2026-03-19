package edits;

import java.util.ArrayList;

import editor.ShapeCanvas;
import shapes.MyShape;
import shapes.ShapeGroup;

/**
 * The GroupEdit class represents an edit operation for grouping shapes on a canvas.
 * It extends the Edit class and provides functionality for undoing and redoing
 * the grouping of multiple shapes into a single ShapeGroup.
 * 
 * @author Soikat
 */
public class GroupEdit extends Edit {
    
    // ----- CONSTRUCTORS -----
    /**
     * Constructs a new GroupEdit object with the specified ShapeCanvas and ShapeGroup.
     *
     * @param sc The ShapeCanvas object representing the canvas where the grouping occurs.
     * @param s  The ShapeGroup object representing the grouped shape.
     */
    public GroupEdit (ShapeCanvas sc, MyShape s)
	{
		super (sc, s);
	}

    // ----- METHODS -----
    /**
     * Undoes the grouping operation by removing the ShapeGroup from the canvas
     * and restoring its individual member shapes.
     */
    @Override
	public void undo ()
	{
		ShapeGroup shapeGroup = (ShapeGroup) shape;
		ArrayList<MyShape> shapes = shapeGroup.getMembers();
		
		for (MyShape curShape : shapes)
			canvas.addShape(curShape);
		
		canvas.deleteShape(shapeGroup);
		canvas.paint();
	}

    /**
     * Redoes the grouping operation by removing the individual member shapes
     * from the canvas and restoring the ShapeGroup as a single shape.
     */
    @Override
	public void redo ()
	{
		ShapeGroup shapeGroup = (ShapeGroup) shape;
		ArrayList<MyShape> shapes = shapeGroup.getMembers();
		
		for (MyShape curShape : shapes)
			canvas.deleteShape(curShape);
		
		canvas.addShape(shapeGroup);
        canvas.paint();
	}
}
