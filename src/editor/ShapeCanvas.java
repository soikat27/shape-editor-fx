package editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import edits.Edit;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shapes.*;

/**
 * The ShapeCanvas class provides a custom drawing surface for creating,
 * rendering, and managing shapes in the "ShapeEditor", a JavaFX application.
 * <p>
 * It extends {@link javafx.scene.canvas.Canvas} and maintains an internal
 * collection of {@link shapes.MyShape} objects, which are manually rendered
 * using a {@link javafx.scene.canvas.GraphicsContext}.
 *<p>
 * <h2>Features:</h2>
 * <ul>
 *     <li>Draw and display shapes (lines, rectangles, ovals, and groups)</li>
 *     <li>Interactive editing via interchangeable mouse event handlers</li>
 *     <li>Undo/redo support using a command-based edit system</li>
 *     <li>Shape selection (e.g., closest shape detection)</li>
 *     <li>Persistence via text and binary file formats</li>
 * </ul>
 *
 * <h2>Design Notes:</h2>
 * <ul>
 *     <li><b>Rendering:</b> The canvas is not reactive; all updates require explicit repainting via {@link #paint()}.</li>
 *     <li><b>Edit System:</b> Uses stacks of {@link edits.Edit} objects to support undo/redo operations.</li>
 *     <li><b>Event Handling:</b> Mouse interactions are dynamically assigned through handler replacement.</li>
 *     <li><b>Composite Shapes:</b> Supports grouped shapes using {@link shapes.ShapeGroup}.</li>
 * </ul>
 *
 * @author Soikat
 */
public class ShapeCanvas extends Canvas {

	// ----- FIELDS -----
	/** Slight off-white “paper” to represent the drawable area. This is used as a visual background aid during {@link #paint()} */
	private static final Color CANVAS_PAPER = Color.web ("#f9f9fb");
	/** Thin neutral frame drawn around the drawable area during {@link #paint()}, defining the canvas edge */
	private static final Color CANVAS_FRAME = Color.web ("#dfe0e6");

	/** The width & height of the canvas */
    private double             width, height;
	/** The GraphicsContext used for rendering shapes onto the canvas */
	private GraphicsContext    gc;
	/** The list of shapes currently displayed on the canvas */
	private ArrayList<MyShape> shapes;
	/** The shape currently being drawn or edited by the user */
	private MyShape            currShape;
	/** The current drawing color applied to new shapes */
	private Color              currColor;
	/** Indicates whether newly created shapes should be filled */
	private boolean            filled;

	/** Stack storing edit operations for undo functionality */
	private Stack<Edit> stackUndo;
	/** Stack storing undone edit operations for redo functionality */
	private Stack<Edit> stackRedo;

    // ----- CONSTRUCTORS -----
	/**
	 * Constructs a ShapeCanvas with the specified dimensions.
	 * 
	 * It initializes the drawing surface, graphics context, shape storage,
	 * default drawing properties, and undo/redo stacks.
	 * 
	 * <ul>
	 *     <li>Default color is set to black</li>
	 *     <li>Shapes are unfilled by default</li>
	 *     <li>Undo/redo stacks are initialized as empty</li>
	 *     <li>Draws the initial canvas “paper” background and frame</li>
	 * </ul>
	 *
	 * @param w The width of the canvas
	 * @param h The height of the canvas
	 */
    public ShapeCanvas (double w, double h)
	{
		super(w, h);

		gc = this.getGraphicsContext2D();
		shapes = new ArrayList<> ();
		currColor = Color.BLACK;
		filled = false;
		width  = w;
		height = h;

		stackUndo = new Stack<> ();
		stackRedo = new Stack<> ();

		// draws the first frame here so the artboard/drawable-area shows at startup.
		paint ();
	}

    // ----- GETTER & SETTER METHODS -----
	/**
	 * Returns the list of shapes currently on the canvas.
	 *
	 * @return an ArrayList of MyShape objects representing all shapes on the canvas
	 */
	public ArrayList<MyShape> getShapes ()
	{
		return shapes;
	}
	
	/**
	 * Returns the shape currently being drawn or edited.
	 *
	 * @return the current MyShape, or null if no shape is active
	 */
    public MyShape getCurrShape ()
	{
		return currShape;
	}

	/**
	 * Returns the current drawing color applied to new shapes.
	 *
	 * @return the current Color
	 */
    public Color getCurrColor ()
	{
		return currColor;
	}

	/**
	 * Returns whether newly created shapes are filled.
	 *
	 * @return true if shapes are filled, false otherwise
	 */
    public boolean getCurrFilled ()
	{
		return filled;
	}

	/**
	 * Sets the current drawing color for new shapes.
	 *
	 * @param c the Color to set as the current drawing color
	 */
    public void setCurrColor (Color c)
	{
		currColor = c;
	}

	/**
	 * Sets whether newly created shapes should be filled.
	 *
	 * @param filled true to fill shapes, false for outline only
	 */
    public void setCurrFilled (boolean filled)
	{
		this.filled = filled;
	}

    // ----- OTHER BEHAVIORAL METHODS -----
	/**
	 * Repaints the entire canvas by clearing the drawing area and rendering
	 * all shapes currently stored in the canvas.
	 * 
	 * This method also renders a subtle background “paper” fill and a thin
	 * frame around the drawable region as a visual aid.
	 * 
	 * If a shape is actively being created or edited, it is drawn on top
	 * of existing shapes.
	 */
    public void paint ()
	{
		// clear canvas
		gc.clearRect(0, 0, width, height);

		// draws the artboard (Generated by GenAI) – only serves as visual aid
		gc.setFill (CANVAS_PAPER);
		gc.fillRect (0, 0, width, height);

		// draw all strokes
		for (MyShape shape : shapes)
			shape.draw(gc);

		// draw current shape if exits
		if (currShape != null)
			currShape.draw(gc);

		// draws the frame (Generated by GenAI) – only serves as visual aid
		gc.setLineDashes ((double[]) null);
		gc.setLineWidth (1);
		gc.setStroke (CANVAS_FRAME);
		gc.strokeRect (0.5, 0.5, width - 1, height - 1);
	}

	/**
	 * Adds a shape to the canvas and immediately refreshes the display.
	 *
	 * @param s the shape to be added
	 */
    public void addShape (MyShape s)
	{
		shapes.add(s);
		paint();
	}

	/**
	 * Sets the current active shape being drawn or edited.
	 * If the shape is not null, it inherits the current color and fill settings.
	 *
	 * @param s the shape to set as the current shape
	 */
    public void setCurrShape (MyShape s)
	{
		currShape = s;

		if (currShape != null)
		{
			currShape.setColor(currColor);
			currShape.setFilled(filled);
		}
	}

	/**
	 * Clears all shapes from the canvas and resets undo/redo history.
	 * The canvas is then repainted to reflect the empty state.
	 */
    public void clear ()
	{
		shapes.clear();
		stackUndo.clear();
		stackRedo.clear();
		paint();
	}

	/**
	 * Replaces all mouse event handlers on the canvas with a single shared listener.
	 * 
	 * This allows dynamic switching between different interaction modes
	 * (e.g., drawing, selecting, editing).
	 *
	 * @param listener the event handler for all mouse events
	 */
    public void replaceMouseHandler(EventHandler<MouseEvent> listener) 
	{
		setOnMousePressed(listener);
		setOnMouseDragged(listener);
		setOnMouseReleased(listener);
		setOnMouseClicked(listener);
	}

	/**
	 * Finds the shape whose center is closest to the given (x, y) coordinate.
	 * This is used for selection and interaction operations.
	 *
	 * @param x the x-coordinate of the reference point
	 * @param y the y-coordinate of the reference point
	 * @return the closest shape, or null if no shapes exist
	 */
	public MyShape closestShape (double x, double y)
	{
		if (shapes.isEmpty())
			return null;

		MyShape closestShape   = shapes.get(0);
		double closestDistance = closestShape.getCenter().distance(x, y);

		for (int i = 1; i < shapes.size(); i++)
		{
			MyShape currShape   = shapes.get(i);
			double currDistance = currShape.getCenter().distance(x, y);

			if (currDistance < closestDistance)
			{
				closestDistance = currDistance;
				closestShape = currShape;
			}
		}

		return closestShape;
	}

	/**
	 * Removes a shape from the canvas without repainting.
	 *
	 * @param s the shape to be removed
	 */
	public void deleteShape (MyShape s)
	{
		shapes.remove(s);
	}

	/**
	 * Pushes a new edit operation onto the undo stack.
	 *
	 * @param edit the edit operation to store
	 */
	public void addEdit (Edit edit)
	{
		stackUndo.push(edit);
	}

	/**
	 * Undoes the most recent edit operation, if available.
	 * 
	 * The undone operation is moved to the redo stack.
	 */
	public void undo ()
	{
		if (!stackUndo.empty())
		{
			Edit latest = stackUndo.pop();
			latest.undo();
			stackRedo.push(latest);
		}
	}

	/**
	 * Redoes the most recently undone operation, if available.
	 * 
	 * The redone operation is moved back to the undo stack.
	 */
	public void redo ()
	{
		if (!stackRedo.empty())
		{
			Edit latest = stackRedo.pop();
			latest.redo();			
			stackUndo.push(latest);
		}
	}

	/**
	 * Loads a single (non-group) shape from a text file representation.
	 * The method reconstructs a shape (Line, Rect, or Oval) using raw
	 * coordinate and styling data, then applies fill and color attributes.
	 *
	 * @param reader the Scanner used to read serialized shape data
	 * @param shapeType the type of shape being loaded (e.g., "Line", "Rect", "Oval")
	 * @return a fully constructed MyShape instance
	 */
	private MyShape loadSingletonText (Scanner reader, String shapeType)
	{
		double x1 = reader.nextDouble();
		double y1 = reader.nextDouble();
		double x2 = reader.nextDouble();
		double y2 = reader.nextDouble();
		String isFilled = reader.next();
		double r = reader.nextDouble();
		double g = reader.nextDouble();
		double b = reader.nextDouble();

		MyShape shape;
		if (shapeType.equalsIgnoreCase("Line"))
			shape = new Line (x1, y1, x2, y2);

		else if (shapeType.equalsIgnoreCase("Rect"))
			shape = new Rect (x1, y1, x2, y2);

		else
			shape = new Oval (x1, y1, x2, y2);

		if (isFilled.equalsIgnoreCase("true"))
			shape.setFilled(true);

		shape.setColor(Color.color(r, g, b));
		return shape;
	}

	/**
	 * Recursively loads a ShapeGroup from a text file.
	 * A ShapeGroup may contain nested groups or individual shapes.
	 * Each member is reconstructed and added to the group structure,
	 * preserving hierarchical composition.
	 *
	 * @param reader the Scanner used to read serialized group data
	 * @return a fully constructed ShapeGroup as a MyShape
	 */
	private MyShape loadGroupText (Scanner reader)
	{
		int nShapes = reader.nextInt();
		double x1 = reader.nextDouble();
		double y1 = reader.nextDouble();
		double x2 = reader.nextDouble();
		double y2 = reader.nextDouble();

		ShapeGroup shapeGroup = new ShapeGroup ();

		shapeGroup.setP1(x1, y1);
		shapeGroup.setP2(x2, y2);

		for (int i = 0; i < nShapes; i++)
		{
			String curShapeType = reader.next();
			MyShape curShape;

			if (curShapeType.equalsIgnoreCase("ShapeGroup"))
				curShape = loadGroupText (reader);

			else
				curShape = loadSingletonText (reader, curShapeType);

			shapeGroup.addMember(curShape);
		}

		return shapeGroup;
	}

	/**
	 * Saves all shapes currently on the canvas to a human-readable text file.
	 * Each shape is written using its {@code toString()} representation,
	 * preceded by the total number of shapes in the file.
	 *
	 * @param fileObj the file to which shape data will be written
	 */
	public void toTextFile (File fileObj)
	{
		try
		{	
			PrintWriter writer = new PrintWriter (fileObj);
			writer.println(shapes.size());
			
			for (MyShape shape : shapes)
				writer.println(shape.toString());
			
			writer.close();
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println(fileObj + "couldn't be opened/created for writing");
			e.printStackTrace();
		}
	}

	/**
	 * Loads shapes from a text file and replaces the current canvas content.
	 * It supports both singleton shapes and nested ShapeGroups. Existing canvas
	 * state is cleared before loading to ensure consistency.
	 *
	 * @param fileObj the file to read shape data from
	 */
	public void fromTextFile (File fileObj)
	{
		try
		{
			Scanner reader = new Scanner (fileObj);
			clear();
			
			if (reader.hasNext())
			{
				int nShapes = reader.nextInt();
				for (int i = 0; i < nShapes; i++)
				{
					String curType = reader.next();
					MyShape curShape;

					if (curType.equalsIgnoreCase("ShapeGroup"))
						curShape = loadGroupText (reader);

					else
						curShape = loadSingletonText (reader, curType);

					shapes.add(curShape);
				}
			}
			
			reader.close();
			paint();
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println (fileObj + "couldn't be loaded for reading");
			e.printStackTrace();
		}
	}

	/**
	 * Serializes and saves all shapes on the canvas to a binary file.
	 * Uses Java object serialization to store shape objects efficiently.
	 * Includes the number of shapes followed by each serialized object.
	 *
	 * @param fileObj the file to which binary data will be written
	 */
	public void toBinaryFile (File fileObj)
	{
		try
		{
			FileOutputStream fOut = new FileOutputStream (fileObj);
			ObjectOutputStream writer = new ObjectOutputStream (fOut);
			
			writer.writeInt(shapes.size());
			for (MyShape shape : shapes)
				writer.writeObject(shape);
			
			writer.close();
			fOut.close();
		}
		
		catch (IOException e)
		{
			System.out.println(fileObj + "couldn't be opened/created for writing");
			e.printStackTrace();
		}
	}

	/**
	 * Loads shapes from a binary file using Java object deserialization.
	 * <p>
	 * Clears existing canvas content before restoring saved shapes.
	 * Restores the full object graph of all shapes including groups.
	 *
	 * @param fileObj the file to read binary shape data from
	 */
	public void fromBinaryFile (File fileObj)
	{
		try
		{
			FileInputStream fIn = new FileInputStream (fileObj);
			ObjectInputStream reader = new ObjectInputStream (fIn);
			
			clear();
			int nShapes = reader.readInt();
			
			for (int i = 0; i < nShapes; i++)
			{
				MyShape shape = (MyShape) reader.readObject();
				shapes.add(shape);
			}
			
			fIn.close();
			reader.close();
			paint();
		}
		
		catch (IOException e)
		{
			System.out.println(fileObj + "couldn't be loaded for reading");
			e.printStackTrace();
		}
		
		catch (ClassNotFoundException e)
		{
			System.out.println(fileObj + "couldn't be loaded for reading");
			e.printStackTrace();
		}
	}
}
