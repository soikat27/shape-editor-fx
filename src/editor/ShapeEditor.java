package editor;

import handlers.LineHandler;
import handlers.OvalHandler;
import handlers.RectHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ShapeEditor extends Application {
    
    private static final int APP_WIDTH     = 800;
	private static final int APP_HEIGHT    = 800;
	private static final int CANVAS_WIDTH  = APP_WIDTH;
	private static final int CANVAS_HEIGHT = 750;

    private BorderPane  mainPane;
	private ShapeCanvas canvas;
	private HBox        controlPanel;
	private Button      bnClear;
	private CheckBox    cbFilled;
	private RadioButton rbLine, rbOval, rbRect;
	private LineHandler lineHandler;
	private RectHandler rectHandler;
	private OvalHandler ovalHandler;

    public void start (Stage mainStage)
	{
		
	}

    public static void main (String[] args)
	{
		launch(args);
	}
}
