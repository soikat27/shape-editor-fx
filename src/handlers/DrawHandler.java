package handlers;

import shapes.MyShape;

public class DrawHandler {
    
    protected MyShape     shape;
	protected ShapeCanvas canvas;

    public DrawHandler (ShapeCanvas sc)
	{
		canvas = sc;
	}

}
