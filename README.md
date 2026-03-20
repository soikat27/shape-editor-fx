# ShapeEditorFX

A JavaFX desktop application for drawing and editing 2D shapes with an interactive canvas.

This project lets users draw lines, rectangles, and ovals; edit shapes through delete/move/copy tools; group shapes; and save/load drawings in both text and binary formats. It uses a clean JavaFX UI with a top control panel, central drawing canvas, and bottom file menu.

This ShapeEditorFX implementation was built as a learning and portfolio project. Users can run it as a lightweight drawing tool, while developers can explore the code to understand JavaFX UI composition, event-driven handlers, undo/redo architecture, and object serialization.

#### Key Engineering Concepts used in this project:
- Event-driven programming using JavaFX event handlers
- Strategy-like tool switching via modular handler classes
- Custom canvas rendering and shape management system
- Undo/Redo implementation using action history tracking
- Object persistence using text and binary file serialization

## Getting Started

Follow these steps below to get the project running on your computer:

### **Prerequisites**

You’ll need:

* Java JDK (11 or above recommended), which includes both:
    - **Compiler (`javac`)** – to compile Java source files
    - **Runtime (`java`)** – to run compiled programs

* JavaFX SDK 11+ – required to run JavaFX applications

* A terminal or command prompt (macOS/Linux or Command Prompt/PowerShell on Windows)

#### Check that Java JDK is installed:

```
java -version   # checks the Java runtime
```
and
```
javac -version  # checks the Java compiler
```

#### JavaFX Download Guide (optional if using bundled `lib/`):

1. Visit the official JavaFX website: [https://gluonhq.com/products/javafx](https://gluonhq.com/products/javafx)

2. Select options for your system:  
   - **JavaFX version:** latest stable release  
   - **Operating system:** Windows / macOS / Linux  
   - **Architecture:** your system’s architecture  
   - **Type:** SDK (you need the SDK to compile/run JavaFX programs)

3. Download the SDK and extract it to a folder you can remember.

### **Installing**

#### 1. Clone this repository and navigate to the project directory:

```
git clone https://github.com/soikat27/shape-editor-fx.git
```

```
cd shape-editor-fx
```

#### 2. Compile the program:

* Create a folder for compiled bytecode:

```
mkdir bin
```

* Compile the source code into `.class` files:
```
javac --module-path "/path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml -d bin src/*/*.java
```

** Replace `/path/to/javafx/lib` with the path to your JavaFX SDK `lib` folder.

## Running the program

Run the compiled program:

```
java --module-path "/path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml -cp bin editor.ShapeEditor
```

** Replace `/path/to/javafx/lib` with the path to your JavaFX SDK `lib` folder.

#### Features:

* Draw shapes: **Line**, **Rectangle**, **Oval**
* Shape styling: **Filled toggle** and **Color picker**
* Editing tools: **Delete**, **Move**, **Copy**, **Group**
* History controls: **Undo**, **Redo**, **Clear**
* File operations:
  - **Load / Save** (text format)
  - **Load Binary Format / Save Binary Format** (serialized objects)

#### Usage

* Select a drawing tool (**Line**, **Rectangle**, or **Oval**) and drag on the canvas
* Use **Filled** and the color picker to control appearance of new shapes
* Switch to **Delete**, **Move**, **Copy**, or **Group** for editing operations
* Use **Undo** and **Redo** to navigate edit history
* Save your drawing from **File** menu, then load it later in text or binary format

#### `Upcoming Features:`

- Resize support using selection handles and bounding boxes
- Multi-object selection without grouping dependency
- Export canvas as image formats (e.g., PNG, JPG)

## Deployment

There’s no special deployment required. Compile with Java and run locally from the terminal with the JavaFX module path.

## Built With

* Java 11+ - core programming language
* JavaFX 11+ – GUI framework
* Modular event-handler architecture – tool-based interaction system
* Undo/redo system – action history tracking using stack-based state management
* Text and binary persistence – for saving/loading drawings
* `JavaDoc` comments included in source code (see doc/index.html)


## Contributing

Contributions are welcome! Open an issue or submit a pull request if you want to add features or improve the project.

## Author

* Soikat Saha – Design and implementation

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Special thanks to **Professor Sunghee Kim** for guidance and support throughout this project
* Appreciation to the open-source JavaFX community for documentation and examples
* GenerativeAI-assisted styling refinements for UI consistency (non-functional code support) - only serves as visual purpose