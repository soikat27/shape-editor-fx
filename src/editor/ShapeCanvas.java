package editor;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shapes.MyShape;

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
}
