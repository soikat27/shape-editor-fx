package editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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
	}

    // ----- GETTER & SETTER METHODS -----
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
		paint();
	}

    public void replaceMouseHandler(EventHandler<MouseEvent> listener) 
	{
		setOnMousePressed(listener);
		setOnMouseDragged(listener);
		setOnMouseReleased(listener);
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
			
			int nShapes = reader.nextInt();
			
			for (int i = 0; i < nShapes; i++)
			{
				String type = reader.next();

				double x1 = reader.nextDouble();
				double y1 = reader.nextDouble();
				double x2 = reader.nextDouble();
				double y2 = reader.nextDouble();
				
				String isFilled = reader.next();
				
				double r = reader.nextDouble();
				double g = reader.nextDouble();
				double b = reader.nextDouble();
				
				MyShape shape;
				
				if (type.equalsIgnoreCase("Line")) 			shape = new Line (x1, y1, x2, y2);
				else if (type.equalsIgnoreCase("Rectangle")) shape = new Rect (x1, y1, x2, y2);
				else 													    shape = new Oval (x1, y1, x2, y2);
				
				if (isFilled.equalsIgnoreCase("true")) shape.setFilled(true);
				
				shape.setColor(Color.color(r, g, b));

				shapes.add(shape);
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
}
