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

public class ShapeCanvas extends Canvas {
    
    private double             width, height;
	private GraphicsContext    gc;
	private ArrayList<MyShape> shapes;
	private MyShape            currShape;
	private Color              currColor;
	private boolean            filled;

	private Stack<Edit> stackUndo;
	private Stack<Edit> stackRedo;

    // ----- CONSTRUCTORS -----
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
	}

    // ----- GETTER & SETTER METHODS -----
	public ArrayList<MyShape> getShapes ()
	{
		return shapes;
	}
	
    public MyShape getCurrShape ()
	{
		return currShape;
	}

    public Color getCurrColor ()
	{
		return currColor;
	}

    public boolean getCurrFilled ()
	{
		return filled;
	}

    public void setCurrColor (Color c)
	{
		currColor = c;
	}

    public void setCurrFilled (boolean filled)
	{
		this.filled = filled;
	}

    // ----- OTHER BEHAVIORAL METHODS -----
    public void paint ()
	{
		// clear canvas
		gc.clearRect(0, 0, width, height);

		// draw all strokes
		for (MyShape shape : shapes)
		{
			shape.draw(gc);
		}

		// draw current shape if exits
		if (currShape != null)
		{
			currShape.draw(gc);
		}
	}

    public void addShape (MyShape s)
	{
		shapes.add(s);
		paint();
	}

    public void setCurrShape (MyShape s)
	{
		currShape = s;

		if (currShape != null)
		{
			currShape.setColor(currColor);
			currShape.setFilled(filled);
		}
	}

    public void clear ()
	{
		shapes.clear();
		stackUndo.clear();
		stackRedo.clear();
		paint();
	}

    public void replaceMouseHandler(EventHandler<MouseEvent> listener) 
	{
		setOnMousePressed(listener);
		setOnMouseDragged(listener);
		setOnMouseReleased(listener);
	}

	public MyShape closestShape (double x, double y)
	{
		if (shapes.isEmpty())
		{
			return null;
		}

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

	public void deleteShape (MyShape s)
	{
		shapes.remove(s);
	}

	public void addEdit (Edit edit)
	{
		stackUndo.push(edit);
	}

	public void undo ()
	{
		if (!stackUndo.empty())
		{
			Edit latest = stackUndo.pop();
			latest.undo();
			stackRedo.push(latest);
		}
	}

	public void redo ()
	{
		if (!stackRedo.empty())
		{
			Edit latest = stackRedo.pop();
			latest.redo();			
			stackUndo.push(latest);
		}
	}

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
		{
			shape = new Line (x1, y1, x2, y2);

		}
		else if (shapeType.equalsIgnoreCase("Rect"))
		{
			shape = new Rect (x1, y1, x2, y2);
		}
		else
		{
			shape = new Oval (x1, y1, x2, y2);
		}

		if (isFilled.equalsIgnoreCase("true"))
		{
			shape.setFilled(true);
		}

		shape.setColor(Color.color(r, g, b));
		return shape;
	}

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
			MyShape curshape;

			if (curShapeType.equalsIgnoreCase("ShapeGroup"))
			{
				curshape = loadGroupText (reader);
			}

			else
			{
				curshape = loadSingletonText (reader, curShapeType);
			}

			shapeGroup.addMember(curshape);
		}

		return shapeGroup;
	}

	public void toTextFile (File fileObj)
	{
		try
		{	
			PrintWriter writer = new PrintWriter (fileObj);
			writer.println(shapes.size());
			
			for (MyShape shape : shapes)
			{
				writer.println(shape.toString());
			}
			
			writer.close();
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println(fileObj + "couldn't be opened/created for writing");
			e.printStackTrace();
		}
	}

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
					{
						curShape = loadGroupText (reader);
					}

					else
					{
						curShape = loadSingletonText (reader, curType);
					}

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

	public void toBinaryFile (File fileObj)
	{
		try
		{
			FileOutputStream fOut = new FileOutputStream (fileObj);
			ObjectOutputStream writer = new ObjectOutputStream (fOut);
			
			writer.writeInt(shapes.size());
			for (MyShape shape : shapes)
			{
				writer.writeObject(shape);
			}
			
			writer.close();
			fOut.close();
		}
		
		catch (IOException e)
		{
			System.out.println(fileObj + "couldn't be opened/created for writing");
			e.printStackTrace();
		}
	}

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
				MyShape s = (MyShape) reader.readObject();
				shapes.add(s);
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
