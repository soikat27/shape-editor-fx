package handlers;

import java.util.ArrayList;
import java.util.Iterator;

import editor.ShapeCanvas;
import edits.GroupEdit;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import shapes.MyShape;
import shapes.ShapeGroup;

/**
 * EventHandler implementation for creating and managing groups of shapes on a canvas.
 * <p>
 * The {@code GroupHandler} allows the user to click and drag to create a bounding box.
 * Any shapes fully contained within this box are added to a {@link ShapeGroup}. 
 * The grouped shapes are then treated as a single composite shape for further manipulation.
 * </p>
 * <p>
 * This handler supports the standard JavaFX mouse events:
 * <ul>
 *   <li>{@code MOUSE_PRESSED} – Starts a new ShapeGroup at the initial mouse position.</li>
 *   <li>{@code MOUSE_DRAGGED} – Updates the bounding box of the ShapeGroup as the mouse moves.</li>
 *   <li>{@code MOUSE_RELEASED} – Finalizes the group by adding all shapes contained in the bounding box.</li>
 * </ul>
 * </p>
 * <p>
 * The handler modifies the canvas directly by adding or removing shapes from the {@link ShapeCanvas}.
 * </p>
 * 
 * @author Soikat
 */
public class GroupHandler implements EventHandler<MouseEvent> {
    
    // ----- Fields -----
    /** The canvas on which shapes are drawn and grouped. */
    private ShapeCanvas canvas;
    /** The currently active ShapeGroup being created. */
	private ShapeGroup group;

    // ----- CONSTRUCTORS -----
    /**
     * Constructs a GroupHandler for the specified canvas.
     * 
     * @param sc the ShapeCanvas this handler operates on
     */
    public GroupHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

    // ----- CONSTRUCTORS -----
    /**
     * Called when the user presses the mouse to start a new group.
     * Initializes a new ShapeGroup with its first corner at the mouse coordinates.
     * 
     * @param e the MouseEvent triggered on mouse press
     */
    protected void mousePressed (MouseEvent e)
	{
		group = new ShapeGroup ();
		group.setP1(e.getX(), e.getY());

		canvas.setCurrShape(group);
	}

    // ----- METHODS -----
    /**
     * Called when the user drags the mouse to resize the bounding box of the group.
     * Updates the second corner of the ShapeGroup and repaints the canvas.
     * 
     * @param e the MouseEvent triggered on mouse drag
     */
    protected void mouseDragged (MouseEvent e)
	{
		group.setP2(e.getX(), e.getY());
		canvas.paint();
	}
    /**
     * Called when the user releases the mouse to finalize the ShapeGroup.
     * Iterates through the shapes on the canvas, adds any fully contained shapes to the group,
     * removes them from the canvas, and adds the new ShapeGroup if it contains any members.
     * 
     * @param e the MouseEvent triggered on mouse release
     */
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
            canvas.addEdit(new GroupEdit (canvas, group));
		}
		
        canvas.setCurrShape(null);
		group = null;
        canvas.paint();
	}

    /**
     * Handles all mouse events for grouping shapes.
     * Delegates to {@link #mousePressed(MouseEvent)}, {@link #mouseDragged(MouseEvent)}, 
     * and {@link #mouseReleased(MouseEvent)} based on the event type.
     * 
     * @param event the MouseEvent to handle
     */
    @Override
    public void handle(MouseEvent event) 
    {
        String eventName = event.getEventType().getName();

		switch (eventName)
		{
            case "MOUSE_PRESSED":
                mousePressed(event);
                break;
            case "MOUSE_DRAGGED":
                mouseDragged(event);
                break;
            case "MOUSE_RELEASED":
                mouseReleased(event);
                break;
            default:
                break;
		}
    }
}
