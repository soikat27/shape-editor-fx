package shapes;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a composite shape that groups multiple {@link MyShape} objects
 * and allows them to be treated as a single entity. 
 * 
 * <p>A {@code ShapeGroup} supports operations such as adding or removing shapes,
 * moving all member shapes simultaneously, checking if a shape lies within its
 * bounding box, and drawing the group along with its bounding box.</p>
 * 
 * <p>This class extends {@link MyShape}, so it inherits basic shape properties
 * like {@code p1}, {@code p2}, {@code center}, and color. All group operations
 * automatically update the group's bounding box and center.</p>
 * 
 * @author Soikat
 */
public class ShapeGroup extends MyShape {
    
    // ----- FIELDS -----
    /** List of member shapes in this group. */
    private ArrayList<MyShape> group;

    // ----- CONSTRUCTORS -----
    /**
     * Default Constructor:
     * Constructs an empty {@code ShapeGroup} with initial coordinates at (0,0)
     * and an empty member list.
     */
    public ShapeGroup ()
	{
		group = new ArrayList<MyShape> ();
		
		p1 = new Point2D (0, 0);
		p2 = new Point2D (0, 0);
		
		updateCenter();
		updateBounds();
	}

    // ----- METHODS -----
    /**
     * Sets the list of shapes in this group.
     * 
     * @param group the list of {@link MyShape} objects to set
     */
    public void setGroup (ArrayList<MyShape> group)
	{
		this.group = group;
	}

    /**
     * Checks if this group contains no shapes.
     * 
     * @return {@code true} if the group is empty or uninitialized, {@code false} otherwise
     */
    public boolean isEmpty ()
	{
		if (group != null)
			return group.isEmpty();

		return true;
	}

    /**
     * Returns the number of shapes in this group.
     * 
     * @return the size of the group
     */
    public int size ()
	{
		return group.size();
	}

    /**
     * Adds a shape to this group if it is not already a member.
     * Updates the group's center and bounding box.
     * 
     * @param shape the {@link MyShape} to add
     */
    public void addMember (MyShape shape)
	{
		if (!group.contains(shape))
		{
			group.add(shape);
            updateBounds();
            updateCenter ();
		}
	}

    /**
     * Removes a shape from this group if it is a member.
     * Updates the group's center and bounding box.
     * 
     * @param shape the {@link MyShape} to remove
     */
    public void removeMember (MyShape shape)
	{
		if (group.contains(shape))
		{
			group.remove(shape);
            updateBounds();
            updateCenter ();
		}
	}

    /**
     * Checks if a shape's center lies within the group's bounding box.
     * 
     * @param shape the {@link MyShape} to test
     * @return {@code true} if the shape is within the group's bounds, {@code false} otherwise
     */
    public boolean within (MyShape shape)
	{
		return ( (shape.getCenter().getX() >= ulx && shape.getCenter().getX() <= (ulx + width) ) && (shape.getCenter().getY() >= uly && shape.getCenter().getY() <= (uly + height) ) );
	}

    /**
     * Updates the group's center point based on the centers of all member shapes.
     */
    @Override
    public void updateCenter ()
	{
		double totalX = 0;
		double totalY = 0;

		for (int i = 0; i < group.size(); i++)
		{
			totalX += group.get(i).getCenter().getX();
			totalY += group.get(i).getCenter().getY();
		}

		double avgX = totalX/group.size();
		double avgY = totalY/group.size();

		center = new Point2D (avgX, avgY);
	}

    /**
     * Moves all member shapes by the specified offsets.
     * 
     * @param dx offset in the X direction
     * @param dy offset in the Y direction
     */
    @Override
	public void move (double dx, double dy)
	{
		for (MyShape shape : group)
		{
			shape.move(dx, dy);
		}
		
		super.move(dx, dy);
	}

    /**
     * Draws the group and its bounding box onto the provided graphics context.
     * 
     * @param gc the {@link GraphicsContext} used for drawing
     */
    @Override
    public void draw(GraphicsContext gc) 
    {
        // drawing the bounding box
		gc.setLineDashes(4);
		gc.setStroke(Color.LIGHTGREY);
		gc.strokeRect(ulx, uly, width, height);

		// drawing the shape
		for (MyShape shape : group)
		{
			shape.draw(gc);
		}
    }

    /**
     * Returns a deep copy of this {@code ShapeGroup}, including clones of all
     * member shapes.
     * 
     * @return a new {@code ShapeGroup} with cloned members
     */
    @Override
	public Object clone()
	{
		ShapeGroup copy = (ShapeGroup) super.clone();
		
		ArrayList<MyShape> copyArray = new ArrayList<> ();
		
		for (MyShape shape : group)
		{
			copyArray.add((MyShape)shape.clone());
		}
		
		copy.setGroup (copyArray);
		
		return copy;
	}

    /**
     * Returns a string representation of the group including the number of
     * shapes and the string representation of each member.
     * 
     * @return a {@link String} describing this {@code ShapeGroup}
     */
    @Override
    public String toString ()
	{
		String fName = String.format("ShapeGroup %d %.0f %.0f %.0f %.0f\n", size(), p1.getX(), p1.getY(), p2.getX(), p2.getY());
		
		if (group != null)
		{
			for (MyShape shape : group)
			{
				fName += shape.toString();
			}
		}
		
		return fName;
	}
}
