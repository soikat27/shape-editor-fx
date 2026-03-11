package shapes;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

    @Override
	public void move (double dx, double dy)
	{
		for (MyShape shape : group)
		{
			shape.move(dx, dy);
		}
		
		super.move(dx, dy);
	}

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
