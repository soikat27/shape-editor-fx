package shapes;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class ShapeGroup extends MyShape {
    
    // ----- FIELDS -----
    private ArrayList<MyShape> group;

    // ----- CONSTRUCTORS -----
    public ShapeGroup ()
	{
		group = new ArrayList<MyShape> ();
		
		p1 = new Point2D (0, 0);
		p2 = new Point2D (0, 0);
		
		updateCenter();
		updateBounds();
	}

    // ----- METHODS -----
    public void setGroup (ArrayList<MyShape> group)
	{
		this.group = group;
	}

    public boolean isEmpty ()
	{
		if (group != null)
			return group.isEmpty();

		return true;
	}

    public int size ()
	{
		return group.size();
	}

    public void addMember (MyShape shape)
	{
		if (!group.contains(shape))
		{
			group.add(shape);
            updateBounds();
            updateCenter ();
		}
	}

    public void removeMember (MyShape shape)
	{
		if (group.contains(shape))
		{
			group.remove(shape);
            updateBounds();
            updateCenter ();
		}
	}

    public boolean within (MyShape shape)
	{
		return ( (shape.getCenter().getX() >= ulx && shape.getCenter().getX() <= (ulx + width) ) && (shape.getCenter().getY() >= uly && shape.getCenter().getY() <= (uly + height) ) );
	}

    @Override
    public void draw(GraphicsContext gc) {
        // TODO Auto-generated method stub
    }

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
}
