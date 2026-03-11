package edits;

import java.util.ArrayList;
import java.util.Iterator;

import editor.ShapeCanvas;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;
import shapes.ShapeGroup;

public class GroupHandler implements EventHandler<MouseEvent> {
    
    // ----- Fields -----
    private ShapeCanvas canvas;
	private ShapeGroup group;

    // ----- CONSTRUCTORS -----
    public GroupHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

    // ----- CONSTRUCTORS -----
    protected void mousePressed (MouseEvent e)
	{
		group = new ShapeGroup ();
		
		double mx = e.getX();
		double my = e.getY();
		
		group.setP1(mx, my);
		canvas.setCurrShape(group);
	}

    // ----- METHODS -----
    protected void mouseDragged (MouseEvent e)
	{
		double mx = e.getX();
		double my = e.getY();

		group.setP2(mx, my);
		canvas.paint();
	}

    protected void mouseReleased (MouseEvent e)
	{
        ArrayList<MyShape> canvasShapes = canvas.getShapes();
        Iterator<MyShape> iter = canvasShapes.iterator();
        while (iter.hasNext()) 
        {
            MyShape curShape = iter.next();
            if (group.within(curShape)) 
            {
                iter.remove();            
                group.addMember(curShape);
            }
        }

		if (!group.isEmpty())
		{
			canvas.addShape(group);
			canvas.setCurrShape(null);
		}
		
		group = null;
	}

    @Override
    public void handle(MouseEvent arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

    

}
