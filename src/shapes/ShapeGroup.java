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
    @Override
    public void draw(GraphicsContext gc) {
        // TODO Auto-generated method stub
    }
}
