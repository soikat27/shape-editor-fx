package editor;

import handlers.LineHandler;
import handlers.OvalHandler;
import handlers.RectHandler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
		mainPane = new BorderPane ();
		setupCanvas ();
		setupControls ();

		Scene scene = new Scene (mainPane, APP_WIDTH, APP_HEIGHT);

		mainStage.setScene(scene);
		mainStage.setTitle("Shape Editor");

		mainStage.show();
	}

    public void setupControls ()
    {
        controlPanel = new HBox (40);

		// Add radio buttons
		rbLine = new RadioButton ("Line");
		rbOval = new RadioButton ("Oval");
		rbRect = new RadioButton ("rectangle");

		rbLine.setSelected(true);

		ToggleGroup group = new ToggleGroup ();
		rbLine.setToggleGroup(group);
		rbOval.setToggleGroup(group);
		rbRect.setToggleGroup(group);

        lineHandler = new LineHandler (canvas);
		rectHandler = new RectHandler (canvas);
        ovalHandler = new OvalHandler (canvas);

		rbLine.setOnAction(e -> {
			canvas.replaceMouseHandler (lineHandler);
		});
		rbOval.setOnAction(e -> {
			canvas.replaceMouseHandler (ovalHandler);
		});
		rbRect.setOnAction(e -> {
			canvas.replaceMouseHandler (rectHandler);
		});

		// Add checkBox
		cbFilled = new CheckBox ("Filled");
		cbFilled.setOnAction(e -> {
			canvas.setCurrFilled(cbFilled.isSelected());
		});

		// implement clear button
		bnClear = new Button ("Clear");
		bnClear.setOnAction(e -> {
			canvas.clear();
		});

		controlPanel.getChildren().addAll(rbLine, rbRect, rbOval, cbFilled, bnClear);
		controlPanel.setAlignment(Pos.BASELINE_LEFT);
        
		mainPane.setTop(controlPanel);
    }

    public void setupCanvas ()
	{
		canvas = new ShapeCanvas (CANVAS_WIDTH, CANVAS_HEIGHT);
		mainPane.setCenter(canvas);
	}

    public static void main (String[] args)
	{
		launch(args);
	}
}
