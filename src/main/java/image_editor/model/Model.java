package image_editor.model;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Observable;

import ascii_panel.AsciiRaster;


/**
 * Model class of the image editor application.
 * @author danie
 *
 */
public class Model extends Observable{

	/**
	 * SINGLETON DESIGN PATTERN
	 * Instance of the model.
	 */
	private static Model instance;
	
	/**
	 * Matrix having all the ascii chars that are going to
	 */
	private AsciiChar[][] matrix;
	
	/**
	 * matrix containing the indexes (x,y) of the pointer in the matrix.
	 * the thrid (and additional) number is for the action made on the matrix.
	 * - 0 = paint
	 * - 1 = erase
	 * - 2 = fill
	 * - 3 = unfill
	 * - 4 = clear
	 */
	private int[] coordinates;
	
	/**
	 * integer indicating the x coordinate in the matrix of the pointer.
	 */
	private int coordinateXPointer;
	
	/**
	 * integer indicating the y coordinate in the matrix of the pointer.
	 */
	private int coordinateYPointer;
	
	/**
	 * AsciiChar in use, It has the values:
	 * - id = integer of the ascii char
	 * - Background color
	 * - Foreground Color
	 */
	private AsciiChar charInUse;
	
	/**
	 * Width of the matrix.
	 */
	private int matrixColumns;
	
	/**
	 * Height of the matrix.
	 */
	private int matrixRows;
	
	/**
	 * Tool in use.
	 */
	private Integer tool;
	
	/**
	 * Buffered image of the image (if) imported by the user.
	 */
	private BufferedImage importedImage;
	
	/**
	 * Default value for the background colour of the chars.
	 */
	private final Color defaultCharForeColor = Color.WHITE;
	
	/**
	 * Default value for the foreground color of the chars.
	 */
	private final Color defaultCharBackColor = Color.BLACK;
	
	/**
	 * Foregrounde Color for the char in use.
	 */
	private Color charForeColorInUse;
	
	/**
	 * Background Color for the char in use.
	 */
	private Color charBackColorInUse;
	
	/**
	 * String that is written at the beggining of the application.
	 */
	private final String startingString;
	
	/**
	 * starting x coordinate for the select tool.
	 */
	private int x0SelectCoordinate;
	
	/**
	 *  starting y coordinate for the select tool.
	 */
	private int y0SelectCoordinate;
	
	/**
	 * ending x coordinate for the select tool.
	 */
	private int x1SelectCoordinate;
	
	/**
	 * ending y coordinate for the select tool.
	 */
	private int y1SelectCoordinate;
	
	/**
	 * Matrix of AsciiChar that holds the area selected by the tool select.
	 */
	private AsciiChar[][] selectHolder;
	
	/**
	 * Matrix of AsciiChar that holds the area first select and then copied.
	 */
	private AsciiChar[][] copyHolder;
	
	/**
	 * int x coordinate for the clone action of the tool cloneStamp.
	 */
	private int xCloneStampCoordinatePointer;
	
	/**
	 * int y coordinate for the clone action of the tool cloneStamp.
	 */
	private int yCloneStampCoordinatePointer;
	
	/**
	 * int x coordinate for the stamp action of the tool cloneStamp.
	 */
	private int xCloneStampCoordinateWriter;
	
	/**
	 * int y coordinate for the stamp action of the tool cloneStamp.
	 */
	private int yCloneStampCoordinateWriter;
	
	/**
	 * boolean for the tool cloneStamp to verify if the pointer of the clone action is settled.
	 */
	private boolean pointerSetted;
	
	/**
	 * Matrix of ascciChar holding the clone matrix for the stamp.
	 */
	private AsciiChar[][] cloneStampHolder;
	
	/**
	 * Asciichar for the pointer in the tool cloneStamp.
	 */
	private AsciiChar pointerChar;
	
	/**
	 * x finishing coordinate for the figure to draw.
	 */
	private int x1Figure;
	
	/**
	 * y finishing coordinate for the figure to draw.
	 */
	private int y1Figure;
	
	/**
	 * Deque of AsciiChar that has all the history (matrix of asciiChar) of the actions made.
	 */
	private Deque<AsciiChar[][]> historyUndo;
	
	/**
	 * Deque of AsciiChar that has all the history (matrix of asciiChar) of the undo's made.
	 */
	private Deque<AsciiChar[][]> historyRedo;
	
	/**
	 * SINGLETON DESIGN PATTERN
	 * Method that retrives the only instance of the Model, if absent it creates one.
	 * @return instance Model.
	 */
	public static Model getInstance() {
		if (instance == null)
			instance = new Model();
		return instance;
	}
	
	/**
	 * Constructor of the Model.
	 */
	private Model() {
		this.charBackColorInUse = defaultCharBackColor;
		this.charForeColorInUse = defaultCharForeColor;
		this.matrixRows = 60;
		this.matrixColumns = 80;
		this.matrix = createMatrix();
		this.tool = 0;
		this.coordinateXPointer = 0;
		this.coordinateYPointer = 0;
		this.charInUse = new AsciiChar(1, charBackColorInUse, charForeColorInUse);
		this.coordinates = new int[]{coordinateXPointer, coordinateYPointer, 0};
		this.importedImage = null;
		this.startingString = "Empty";
		this.x0SelectCoordinate = 0;
		this.x1SelectCoordinate = 0;
		this.y0SelectCoordinate = 0;
		this.y1SelectCoordinate = 0;
		this.selectHolder = null;
		this.copyHolder = null;
		this.xCloneStampCoordinatePointer = 0;
		this.yCloneStampCoordinatePointer = 0;
		this.xCloneStampCoordinateWriter = 0;
		this.yCloneStampCoordinateWriter = 0;
		this.pointerSetted = false;
		this.cloneStampHolder = null;
		this.pointerChar = null;
		this.historyUndo = new ArrayDeque<AsciiChar[][]>();
		this.historyRedo = new ArrayDeque<AsciiChar[][]>();

		
	}
	
	
// Operazioni
	
	//nuovo Image editor
	/**
	 * OBSERVER/OBSERVABLE DESING PATTERN
	 * Method that creates a new matrix replacing the old one with the possibility to change width and height, it informs the observers the new matrix.
	 * @param width int.
	 * @param height int.
	 */
	public void createNewMatrix(int width, int height) {
		setMatrixColumns(width);
		setMatrixRows(height);
		setMatrix(createMatrix());
		//System.out.println("ho cambiato le dimensioni della matrice creando una nuova , notifico gli observer");
		setChanged();
		notifyObservers(matrix);
		setHistory();
	}
	
	//write
	/**
	 * OBSERVER/OBSERVABLE DESING PATTERN
	 * Method that sets the ascii char in use in the position of the pointer in the matrix.
	 * It notifies the observers of the coordinates where the char is been changed and the value 0 that is the paint value.
	 */
	public void writeCharOnPointer() {
		matrix[coordinateYPointer][coordinateXPointer] = charInUse;
		updateCoordinates(0);
		//System.out.println("ho scritto il carattere nella matrice , notifico gli observer");
		//printMatrix();
		setChanged();
		notifyObservers(coordinates);
		if(getTool()==0)addItemToHistoryUndo(matrix.clone());
		
	}
	
	
	/**
	 * Method that allows the user to write a preferred string into the matrix at the coordinates in use.
	 * @param string String.
	 */
	public void writeAString(String string) {
		string.chars().forEach(c -> {
			setCharInUse(new AsciiChar(c, charBackColorInUse, charForeColorInUse));
			writeCharOnPointer();
			setCoordinatesPointer(getCoordinateXPointer()+1, getCoordinateYPointer());
		});
		setCharInUse(new AsciiChar(1, charBackColorInUse, charForeColorInUse));
	}
	
	//delete 
	/**
	 * OBSERVER/OBSERVABLE DESING PATTERN
	 * MEthod that removes the ascii char in the position of the pointer in the matrix (replaced by the Ascii char of default).
	 * It notifies the observers of the coordinates where the char is been removed and the value 1 that is the remove value.
	 */
	public void removeCharOnPointer() {
		matrix[coordinateYPointer][coordinateXPointer] = new AsciiChar(0, Color.BLACK, Color.WHITE);
		updateCoordinates(1);
		//System.out.println("Ho rimosso il carattere che c'era nella matrice, notifico gli observer");
		//printMatrix();
		setChanged();
		notifyObservers(coordinates);
		if(getTool()==0)addItemToHistoryUndo(matrix.clone());
	}
	
	//fill
	/**
	 * OBSERVER/OBSERVABLE DESING PATTERN
	 * Method that fills the matrix or a closed portion of the matrix such as square/rectangle area with the current char in use.
	 * It notifies the observers of the coordinates where to start and the value 2 that is the fill value.
	 */
	public void fill() {
		AsciiChar charToChange = getCharOnPointer();
		fillMatrix(charToChange, getCoordinateXPointer(), getCoordinateYPointer());
		//printMatrix();
		updateCoordinates(2);
		setChanged();
		notifyObservers(coordinates);
		if(getTool()==2)addItemToHistoryUndo(matrix.clone());
	}
	
	//unfill
	/**
	 * OBSERVER/OBSERVABLE DESING PATTERN
	 * Method that unfill the area selected/or the whole matrix if is filled by putting the default char and colors.
	 * It notifies the observers of the coordinates where to start and the value 3 that is the unfill value.
	 */
	public void unfill() {
		
		AsciiChar ch = charInUse;
		setCharInUse(new AsciiChar(0, Color.BLACK, Color.WHITE));
		fillMatrix(ch, getCoordinateXPointer(), getCoordinateYPointer());
		setCharInUse(ch);
		updateCoordinates(3);
		setChanged();
		notifyObservers(coordinates);
		if(getTool()==2)addItemToHistoryUndo(matrix.clone());
	}
	
	//Char Selector
	/**
	 * OBSERVER/OBSERVABLE DESIGN PATTERN
	 * Method that set's the char in use to the one passed as a parameter.
	 * It notifies the observers of the char in use so containing the information about the id, foreground and background colours.
	 * @param asciiChar AsciiChar to set.
	 */
	public void setCharInUse(AsciiChar asciiChar) {
		//System.out.println("id del carattere in uso:" + charInUse.getId());
		this.charInUse = asciiChar;
		//System.out.println("id del NUOVO carattere in uso:" + charInUse + " notifico gli observers.");
		setChanged();
		notifyObservers(charInUse);	
	}
	
	//Pick
	/**
	 * Method that changes the char in use to the one on the pointer.
	 */
	public void pick() {
		setCharInUse(getCharOnPointer());
	}
	
	
	//Clone Stamp
	/**
	 * Method for the tool cloneStamp that sets the pointing start for the copy/draw.
	 */
	public void cloneStampPoint() {
		AsciiChar hold = this.charInUse;
		this.cloneStampHolder = matrix;
		this.xCloneStampCoordinatePointer = getCoordinateXPointer();
		this.yCloneStampCoordinatePointer = getCoordinateYPointer();
		this.pointerChar = getCharOnPointer();
		setCharInUse(new AsciiChar(pointerChar.getId(), Color.RED, pointerChar.getForegroundColor()));
		writeCharOnPointer();
		if(getTool()==6)addItemToHistoryUndo(matrix.clone());
		setTool(7);
		setCharInUse(hold);
		
	}
	
	/**
	 * Method for the tool cloneStamp that draws after the pointing part is been made.
	 */
	public void cloneStampDraw() {
		
		//System.out.println(pointerChar.getBackgroundColor());
		
		AsciiChar hold = this.charInUse;
		if(!pointerSetted) {
			this.xCloneStampCoordinateWriter = getCoordinateXPointer();
			this.yCloneStampCoordinateWriter = getCoordinateYPointer();
			setCharInUse(pointerChar);
			writeCharOnPointer();
			//setCharInUse(hold);
			this.pointerSetted = true;
		}else {
		
		int x = getCoordinateXPointer();
		int y = getCoordinateYPointer();
		
		if(x >= xCloneStampCoordinateWriter && y >= yCloneStampCoordinateWriter) {
			if((yCloneStampCoordinatePointer + (y - yCloneStampCoordinateWriter)) >= cloneStampHolder.length || (xCloneStampCoordinatePointer + (x - xCloneStampCoordinateWriter)) >= cloneStampHolder[0].length) {
				//System.out.println("wrong");
			}else {
			setCharInUse(cloneStampHolder[yCloneStampCoordinatePointer + (y - yCloneStampCoordinateWriter)][xCloneStampCoordinatePointer + (x - xCloneStampCoordinateWriter)]);
			writeCharOnPointer();
			}
		}else if(x <= xCloneStampCoordinateWriter && y <= yCloneStampCoordinateWriter) {
			if((yCloneStampCoordinatePointer - (yCloneStampCoordinateWriter - y)) < 0 || (xCloneStampCoordinatePointer - (xCloneStampCoordinateWriter - x)) < 0) {
				//System.out.println("wrong");
			}else {
			
			setCharInUse(cloneStampHolder[yCloneStampCoordinatePointer - (yCloneStampCoordinateWriter - y)][xCloneStampCoordinatePointer - (xCloneStampCoordinateWriter - x)]);
			writeCharOnPointer();
			}
			
		}else if(x >= xCloneStampCoordinateWriter && y <= yCloneStampCoordinateWriter) {
			
			if((yCloneStampCoordinatePointer - (yCloneStampCoordinateWriter - y)) < 0 || (xCloneStampCoordinatePointer + (x - xCloneStampCoordinateWriter)) >= cloneStampHolder[0].length) {
				//System.out.println("wrong");
			}else {
			setCharInUse(cloneStampHolder[yCloneStampCoordinatePointer - (yCloneStampCoordinateWriter - y)][xCloneStampCoordinatePointer + (x - xCloneStampCoordinateWriter)]);
			writeCharOnPointer();
			}
			
		}else if(x <= xCloneStampCoordinateWriter && y >= yCloneStampCoordinateWriter) {
			if((yCloneStampCoordinatePointer + (y - yCloneStampCoordinateWriter)) >= cloneStampHolder.length || (xCloneStampCoordinatePointer - (xCloneStampCoordinateWriter - x)) < 0) {
				//System.out.println("wrong");
			}else {
			setCharInUse(cloneStampHolder[yCloneStampCoordinatePointer + (y - yCloneStampCoordinateWriter)][xCloneStampCoordinatePointer - (xCloneStampCoordinateWriter - x)]);
			writeCharOnPointer();
		
			}
		}
		
		setCharInUse(hold);
	
		}
		if(getTool()==7)addItemToHistoryUndo(matrix.clone());
	}
	
	/**
	 * Method that resets the coordinates of the pointer after the use of the tool cloneStamp.
	 */
	public void setPointerCharBack() {
		AsciiChar hold = charInUse;
		setCharInUse(pointerChar);
		setCoordinatesPointer(xCloneStampCoordinatePointer, yCloneStampCoordinatePointer);
		writeCharOnPointer();
		setCharInUse(hold);
	}
	
	//Clear Method that clean the whole panel to the default colors
	/**
	 * OBSERVER OBSERVABLE DESIGN PATTERN
	 * Method that clears the whole matrix to the default values.
	 * It notifies the observers of the coordinates where to start and the value 4 that is the clear value.
	 */
	public void clearMatrix() {
		this.matrix = createMatrix();
		updateCoordinates(4);
		setChanged();
		notifyObservers(coordinates);
	}
	
	//load
	/**
	 * OBSERVER/OBSERVABLE DESIGN PATTERN
	 * Method that from an AsciiRaster passed as a parameter it retrieves the matrix of ascii chars, that needs to be applied to one in use.
	 * It notifies the observes of the raster passed as a parameter. 
	 * @param raster AsciiRaster.
	 * 	
	 */
	public void loadRaster(AsciiRaster raster) {
		createNewMatrix(raster.getSy(), raster.getSx());
			
		for(int y = 0; y < raster.getSy(); y++) {
				
			for(int x = 0; x < raster.getSx(); x++) {
					
					matrix[y][x] = new AsciiChar((int)raster.getChars()[x][y], raster.getBackcolors()[x][y], raster.getForecolors()[x][y]);
			}
		}
		//printMatrix();
		setChanged();
		notifyObservers(raster);
	}
	
	/**
	 * Method that set's the starting coordinates for the selection tool. 
	 */
	public void selectStartingPoint() {	
		this.x0SelectCoordinate = getCoordinateXPointer();
		this.y0SelectCoordinate = getCoordinateYPointer();
		
		//System.out.println(x0SelectCoordinate + " " + y0SelectCoordinate);
	
	}
	
	/**
	 * Method that set's the ending coordinates for the selection tool.
	 */
	public void selectFinishPoint() {
		this.x1SelectCoordinate = getCoordinateXPointer();
		this.y1SelectCoordinate = getCoordinateYPointer();
	
		//System.out.println(x1SelectCoordinate + " " + y1SelectCoordinate);
	}
	
	/**
	 * Method that depending on the int (operation) it will apply the actions on the select.
	 * 0 = select.
	 * 1 = deselect.
	 * 2 = cut.
	 * 3 = smooth.
	 * 4 = updateColors.
	 * @param operation int.
	 * @param back Color.
	 * @param fore Color.
	 */
	public void selectOperation(int operation, Color back, Color fore) {
		//x0,y0 <= x1,y1
		if(x0SelectCoordinate <= x1SelectCoordinate && y0SelectCoordinate <= y1SelectCoordinate) {
		
			switch(operation) {
				case 0 :
					selectArea(x0SelectCoordinate, y0SelectCoordinate, x1SelectCoordinate, y1SelectCoordinate);
					break;
				
				case 1 :
					deselectArea(x0SelectCoordinate, y0SelectCoordinate, x1SelectCoordinate, y1SelectCoordinate);
					break;
					
				case 2 :
					copy();
					cutArea(x0SelectCoordinate, y0SelectCoordinate, x1SelectCoordinate, y1SelectCoordinate);
					if(getTool()==3)addItemToHistoryUndo(matrix.clone());
					break;
					
				case 3 :
					smoothArea(x0SelectCoordinate, y0SelectCoordinate, x1SelectCoordinate, y1SelectCoordinate);
					if(getTool()==3)addItemToHistoryUndo(matrix.clone());
					break;
					
				case 4 : 
					updateColorsArea(x0SelectCoordinate, y0SelectCoordinate, x1SelectCoordinate, y1SelectCoordinate, back, fore);
					if(getTool()==3)addItemToHistoryUndo(matrix.clone());
					break;
					
					
					
			}
			
	    
		//x0,y0 >= x1,y1
	
		}else if(x0SelectCoordinate >= x1SelectCoordinate && y0SelectCoordinate >= y1SelectCoordinate) {
			
			switch(operation) {
			case 0 :
				selectArea(x1SelectCoordinate, y1SelectCoordinate, x0SelectCoordinate, y0SelectCoordinate);
				break;
			
			case 1 :
				deselectArea(x1SelectCoordinate, y1SelectCoordinate, x0SelectCoordinate, y0SelectCoordinate);
				break;
				
			case 2 :
				copy();
				cutArea(x1SelectCoordinate, y1SelectCoordinate, x0SelectCoordinate, y0SelectCoordinate);
				if(getTool()==3)addItemToHistoryUndo(matrix.clone());
				break;
				
			case 3 :
				smoothArea(x1SelectCoordinate, y1SelectCoordinate, x0SelectCoordinate, y0SelectCoordinate);
				if(getTool()==3)addItemToHistoryUndo(matrix.clone());
				break;
				
			case 4 : 
				updateColorsArea(x1SelectCoordinate, y1SelectCoordinate, x0SelectCoordinate, y0SelectCoordinate, back, fore);
				if(getTool()==3)addItemToHistoryUndo(matrix.clone());
				break;
				
				
				
			}
			
		
		//x0 <= x1 and y0 >= y1	
	    }else if(x0SelectCoordinate <= x1SelectCoordinate && y0SelectCoordinate >= y1SelectCoordinate) {
			
	    	
	    	switch(operation) {
			case 0 :
				selectArea(x0SelectCoordinate, y1SelectCoordinate, x1SelectCoordinate, y0SelectCoordinate);
				break;
			
			case 1 :
				deselectArea(x0SelectCoordinate, y1SelectCoordinate, x1SelectCoordinate, y0SelectCoordinate);
				break;
				
			case 2 :
				copy();
				cutArea(x0SelectCoordinate, y1SelectCoordinate, x1SelectCoordinate, y0SelectCoordinate);
				if(getTool()==3)addItemToHistoryUndo(matrix.clone());
				break;
				
			case 3 :
				smoothArea(x0SelectCoordinate, y1SelectCoordinate, x1SelectCoordinate, y0SelectCoordinate);
				if(getTool()==3)addItemToHistoryUndo(matrix.clone());
				break;
				
			case 4 : 
				updateColorsArea(x0SelectCoordinate, y1SelectCoordinate, x1SelectCoordinate, y0SelectCoordinate, back, fore);
				if(getTool()==3)addItemToHistoryUndo(matrix.clone());
				break;
				
				
				
	    	}
			
					
		// x0 >= x1 and y0 <= y1	
		} else if(x0SelectCoordinate >= x1SelectCoordinate && y0SelectCoordinate <= y1SelectCoordinate) {	
			
			switch(operation) {
			case 0 :
				selectArea(x1SelectCoordinate, y0SelectCoordinate, x0SelectCoordinate, y1SelectCoordinate);
				break;
			
			case 1 :
				deselectArea(x1SelectCoordinate, y0SelectCoordinate, x0SelectCoordinate, y1SelectCoordinate);
				break;
				
			case 2 :
				copy();
				cutArea(x1SelectCoordinate, y0SelectCoordinate, x0SelectCoordinate, y1SelectCoordinate);
				if(getTool()==3)addItemToHistoryUndo(matrix.clone());
				break;
				
			case 3 :
				smoothArea(x1SelectCoordinate, y0SelectCoordinate, x0SelectCoordinate, y1SelectCoordinate);
				if(getTool()==3)addItemToHistoryUndo(matrix.clone());
				break;
				
			case 4 : 
				updateColorsArea(x1SelectCoordinate, y0SelectCoordinate, x0SelectCoordinate, y1SelectCoordinate, back, fore);
				if(getTool()==3)addItemToHistoryUndo(matrix.clone());
				break;
				
		   }	
		}
	}
	
	
	/**
	 * Method that deselect the area choosen by the previous action select in the image Editor application.
	 * @param x0 x coordinate to start deselect.
	 * @param y0 y coordinate to start deselect.
	 * @param x1 x coordinate to finish deselect.
	 * @param y1 y coordinate to finish deselect.
	 */
	private void deselectArea(int x0, int y0, int x1, int y1) {
		AsciiChar hold = this.charInUse;
		int xCoordinateHold = getCoordinateXPointer();
		int yCoordinateHold = getCoordinateYPointer();
		int counterRows = 0;
		int counterCols = 0;
		for(int y=y0; y <= y1; y++) {
			for(int x=x0; x <= x1; x++) {
				setCoordinatesPointer(x, y);
				//System.out.println(selectHolder[counterRows][counterCols].getId());
				setCharInUse(selectHolder[counterRows][counterCols]);
				writeCharOnPointer();
				counterCols++;
			}
			counterCols = 0;
			counterRows++;
		}
		setSelectHolder(null);
		setCharInUse(hold);
		setCoordinatesPointer(xCoordinateHold, yCoordinateHold);
	}
	
	/**
	 * Method that select an area in the matrix, it changes the color of the background 
	 * @param x0 starting coordinate x int.
	 * @param y0 starting coordinate y int.
	 * @param x1 finish coordinate x int.
	 * @param y1 finish coordinate y int.
	 */
	private void selectArea(int x0, int y0, int x1, int y1) {
		AsciiChar hold = this.charInUse;
		setSelectHolder(new AsciiChar[y1-y0+1][x1-x0+1]);
		int counterRows = 0;
		int counterCols = 0;
		for(int y = y0; y <= y1; y++) {
			for(int x = x0; x <= x1; x++) {
				//System.out.println(matrix[y][x].getId());
				selectHolder[counterRows][counterCols] = matrix[y][x];
				setCharInUse(new AsciiChar(matrix[y][x].getId(), Color.GRAY, matrix[y][x].getForegroundColor()));
				setCoordinatesPointer(x, y);
				writeCharOnPointer();
				counterCols++;
			}
			counterRows++;
			counterCols = 0;
		}
		setCharInUse(hold);
		//printMatrix(selectHolder);
		//System.out.println(x0SelectCoordinate + ", " + y0SelectCoordinate + " -> " + x1SelectCoordinate + ", " + y1SelectCoordinate);
		//System.out.println(getCoordinateXPointer() + " , " + getCoordinateYPointer());
	}
	
	/**
	 * Method that copies the selected matrix into the copyHolder.
	 */
	public void copy() {
		if(selectHolder != null) {
			setCopyHolder(selectHolder);
		}
	}
	
	/**
	 * Method that paste the matrix contained in the copyHolder in the position of the pointers.
	 */
	public void paste() {
		if(copyHolder != null) {
			AsciiChar hold = this.charInUse;
			int x0 = getCoordinateXPointer();
			for(int y = 0; y < copyHolder.length; y++) {
				for(int x = 0; x < copyHolder[0].length; x++) {
					if(getCoordinateXPointer() == matrix[0].length) {
						break;
					}
					setCharInUse(copyHolder[y][x]);
					writeCharOnPointer();
				    setCoordinateXPointer(coordinateXPointer+1);
				}
				if(getCoordinateYPointer() == matrix.length) {break;}
				setCoordinateXPointer(x0);
				setCoordinateYPointer(coordinateYPointer+1);
			}
			setCharInUse(hold);
		}
		if(getTool()==5)addItemToHistoryUndo(matrix.clone());
	}
		
	/**
	 * Method that replace the selected area with the default colors.
	 * @param x0  initial x coordinate int.
	 * @param y0  initial y coordinate int.
	 * @param x1  final x coordinate int.
	 * @param y1  final y coordinate int.
	 */
	private void cutArea(int x0, int y0, int x1, int y1) {
		
		AsciiChar hold = this.charInUse;
		for(int y = y0; y <= y1; y++) {
			for(int x = x0; x <= x1; x++) {
				setCharInUse(new AsciiChar(0, defaultCharBackColor, defaultCharForeColor));
				setCoordinatesPointer(x, y);
				writeCharOnPointer();
			}
		}
		setCharInUse(hold);
		setSelectHolder(null);
	}
	
	/**
	 * Method that smooth the selected area.
	 * @param x0 starting x coordinate int.
	 * @param y0 starting y coordinate int.
	 * @param x1 finishing x coordinate int.
	 * @param y1 finishing y coordinate int.
	 */
	private void smoothArea(int x0, int y0, int x1, int y1) {
		
		AsciiChar hold = this.charInUse;
		int xSelectHolder = 0;
		int ySelectHolder = 0;
		for(int y = y0; y <= y1; y++) {
			for(int x = x0; x <= x1; x++) {
				
				if(x > x0 &&  y > y0 && x < x1-2 && y < y1-2) {
					
					
					Color p1 = new Color(getCharInPosition(x-1, y-1).getForegroundColor().getRGB());
		        	Color p2 = new Color(getCharInPosition(x-1, y).getForegroundColor().getRGB());
		        	Color p3 = new Color(getCharInPosition(x-1, y+1).getForegroundColor().getRGB());
		        	Color p4 = new Color(getCharInPosition(x, y-1).getForegroundColor().getRGB());
		        	Color p5 = new Color(getCharInPosition(x, y).getForegroundColor().getRGB());
		        	Color p6 = new Color(getCharInPosition(x, y+1).getForegroundColor().getRGB());
		        	Color p7 = new Color(getCharInPosition(x+1, y-1).getForegroundColor().getRGB());
		        	Color p8 = new Color(getCharInPosition(x+1, y).getForegroundColor().getRGB());
		        	Color p9 = new Color(getCharInPosition(x+1, y+1).getForegroundColor().getRGB());
		        	
		        	int r = (p1.getRed() + p2.getRed() + p3.getRed() + 
		        	         p4.getRed() + p5.getRed() + p6.getRed() + 
		        	         p7.getRed() + p8.getRed() + p9.getRed()) / 9;
		        	int g = (p1.getGreen() + p2.getGreen() + p3.getGreen() + 
		        	         p4.getGreen() + p5.getGreen() + p6.getGreen() + 
		        	         p7.getGreen() + p8.getGreen() + p9.getGreen()) / 9;
		        	int b = (p1.getBlue() + p2.getBlue() + p3.getBlue() + 
		        	         p4.getBlue() + p5.getBlue() + p6.getBlue() + 
		        	         p7.getBlue() + p8.getBlue() + p9.getBlue()) / 9;
		        	
		        	setCoordinatesPointer(x, y);
					setCharInUse(new AsciiChar(getCharOnPointer().getId(), getCharOnPointer().getBackgroundColor(), new Color(r,g,b)));
					selectHolder[ySelectHolder][xSelectHolder] = new AsciiChar(selectHolder[ySelectHolder][xSelectHolder].getId(), selectHolder[ySelectHolder][xSelectHolder].getBackgroundColor(), new Color(r,g,b));
					writeCharOnPointer();
					
				}
				xSelectHolder++;
			}
			xSelectHolder=0;
			ySelectHolder++;
		}
		setCharInUse(hold);
	}
	
	/**
	 * Method that updates the colors(foreground, background) of the Selected area.
	 * @param x0 starting x coordinate.
	 * @param y0 starting y coordinate.
	 * @param x1 finishing x coordinate.
	 * @param y1 finishing y coordinate.
	 * @param back Color.
	 * @param fore Color.
	 */
	private void updateColorsArea(int x0, int y0, int x1, int y1, Color back, Color fore) {
		AsciiChar hold = this.charInUse;
		int xSelectHolder = 0;
		int ySelectHolder = 0;
		
		for(int y = y0; y <= y1; y++) {
			for(int x = x0; x <= x1; x++) {
				setCoordinatesPointer(x, y);
				if(back.equals(null)) {back = selectHolder[ySelectHolder][xSelectHolder].getBackgroundColor();}
				if(fore.equals(null)) {fore = selectHolder[ySelectHolder][xSelectHolder].getForegroundColor();}
				
				setCharInUse(new AsciiChar(getCharOnPointer().getId(), back, fore));
				selectHolder[ySelectHolder][xSelectHolder] = new AsciiChar(selectHolder[ySelectHolder][xSelectHolder].getId(), back, fore);
				writeCharOnPointer();
				xSelectHolder++;
			}
			xSelectHolder = 0;
			ySelectHolder++;
		}
		setCharInUse(hold);
	}

	/**
	 * Method that paints a figure (square = 0, rectangle = 0, triangle =1) on the coordinates in use.
	 * @param figure int for the figure to paint.
	 * @param borderSize int for the size of the border.
	 * @param bordersFore Color for the foreground of the border.
	 * @param bordersBack Color for the background of the border.
	 * @param insideFore Color for the foreground of the inside.
	 * @param insideBack Color for the background of the inside.
	 */
	public void drawFigure(int figure, int borderSize, Color bordersFore, Color bordersBack, Color insideFore, Color insideBack) {
		switch(figure) {
				
		case 0:
			drawQuadrilateral(getCoordinateXPointer(), getCoordinateYPointer(), x1Figure, y1Figure, borderSize, bordersFore, bordersBack, insideFore, insideBack);
			if(getTool()==8)addItemToHistoryUndo(matrix.clone());
			if(getTool()==9)addItemToHistoryUndo(matrix.clone());
			break;
		
		case 1:
			drawTriangle(getCoordinateXPointer(), x1Figure, getCoordinateYPointer(), borderSize, bordersFore, bordersBack, insideFore, insideBack);
			if(getTool()==10)addItemToHistoryUndo(matrix.clone());
			break;
		}
	}
	
	/**
	 * Method that paints a quadrilateral (square/rectangle)
	 * @param x0 starting x coordinate.
	 * @param y0 starting y coordinate.
	 * @param x1 finishing x coordinate.
	 * @param y1 finishing y coordinate.
	 * @param border int for the size of the border.
	 * @param bordersFore Color for the foreground of the border.
	 * @param bordersBack Color for the background of the border.
	 * @param insideFore Color for the foreground of the border.
	 * @param insideBack Color for the background of the border.
	 */
	private void drawQuadrilateral(int x0, int y0, int x1, int y1, int border, Color bordersFore, Color bordersBack, Color insideFore, Color insideBack) {
		
		for(int y = y0; y <= y1; y++) {
			for(int x = x0; x <= x1; x++) {
				if(x >= 0 && y>= 0 && y < matrix.length && x < matrix[0].length) {
					setCoordinatesPointer(x, y);
					if(x< (x0+border) || x > (x1-border) || y < (y0+border) || y > (y1 - border)) {
						setCharInUse(new AsciiChar(charInUse.getId(), bordersBack, bordersFore));
						writeCharOnPointer();
					}else {
						setCharInUse(new AsciiChar(charInUse.getId(), insideBack, insideFore));
						writeCharOnPointer();
					}
				}
			}
		}
	}
	
	/**
	 * Method that paints a triangle
	 * @param x0 starting x coordinate.
	 * @param x1 finishing x coordinate.
	 * @param y1 starting y coordinate.
	 * @param border int for the size of the border.
	 * @param bordersFore Color for the foreground of the border.
	 * @param bordersBack Color for the background of the border.
	 * @param insideFore Color for the foreground of the border.
	 * @param insideBack Color for the background of the border.
	 */
	private void drawTriangle(int x0, int x1, int y1, int border, Color bordersFore, Color bordersBack, Color insideFore, Color insideBack) {
	
		int height;
		int spaces = 0;
		if(x1-x0 % 2 == 0) {
			height = (x1-x0)/2;
		}else {
			height = (x1-x0)/2 +1;
		}
		for(int y = y1; y > y1- height; y--) {
			for(int x = x0; x <= x1; x++) {
				if(x >= matrix[0].length || y >= matrix.length || y< 0) {
					break;
				}
				if(x-x0 < spaces || x > x1-spaces) {
					continue;
				}
				if(x1-x0 >= 4) {
					if(y <= y1-border && x-x0 >= spaces + border && x <= x1-spaces-border) {
						setCoordinatesPointer(x, y);
						setCharInUse(new AsciiChar(charInUse.getId(), insideBack, insideFore));
						writeCharOnPointer();
						
					}else {
						setCoordinatesPointer(x, y);
						setCharInUse(new AsciiChar(charInUse.getId(), bordersBack, bordersFore));
						writeCharOnPointer();
						
					}
				}else {
				setCoordinatesPointer(x, y);
				setCharInUse(new AsciiChar(charInUse.getId(), bordersBack, bordersFore));
				writeCharOnPointer();
				}
			}
			spaces++;
	
		}
	}
	
	
	
	
	/**
	 * Method that recursively fills the matrix starting from the position chosen until it find "borders" or completely fills the matrix. 
	 * @param charToChange The AsciiChar that it needs to be changed.
	 * @param xCoordinate the X int coordinate from where to start. 
	 * @param yCoordinate the Y int coordinate from where to start
	 */
	private void fillMatrix(AsciiChar charToChange, int xCoordinate, int yCoordinate) {
		
		if(getCharInUse().getId() == charToChange.getId()) {
			removeCharOnPointer();
	
		}else {
		
			//Condizione che mi stoppa la ricorsione
			if(getCharInPosition(xCoordinate, yCoordinate).getId() == charToChange.getId()) {
			
				writeCharInPosition(xCoordinate, yCoordinate);
			
				//Checking top 
				if(yCoordinate > 0) {fillMatrix(charToChange, xCoordinate, yCoordinate-1);}
			
				//Checking top left
				if(yCoordinate > 0 && xCoordinate > 0) {fillMatrix(charToChange, xCoordinate-1, yCoordinate-1);}
			
				//Checking top right
				if(yCoordinate > 0 && xCoordinate < matrix[0].length-1) {fillMatrix(charToChange, xCoordinate+1, yCoordinate-1);}
			
				//Checking left
				if(xCoordinate > 0) {fillMatrix(charToChange, xCoordinate-1, yCoordinate);}
	
				//Checking right
				if(xCoordinate < matrix[0].length-1) {fillMatrix(charToChange, xCoordinate+1, yCoordinate);}
			
				//Checking bottom
				if(yCoordinate < matrix.length-1) {fillMatrix(charToChange, xCoordinate, yCoordinate+1);}
			
				//Checking bottom left
				if(yCoordinate < matrix.length-1 && xCoordinate > 0) {fillMatrix(charToChange, xCoordinate-1, yCoordinate+1);}
			
				//Checking bottom right
				if(yCoordinate < matrix.length-1 && xCoordinate < matrix[0].length-1) {fillMatrix(charToChange, xCoordinate+1, yCoordinate+1);}	
			}
		}
	
	}
		
	
	//next char
	/**
	 * Method that set's the ascii char in use to the next one.
	 */
	public void setCharInUseToNext() {
		if(charInUse.getId()< 255) {
			setCharInUse(new AsciiChar(charInUse.getId()+1, charBackColorInUse, charForeColorInUse));
		}
	}
	
	//previous char
	/**
	 * Method that sets the ascii char in use to the previous one.
	 */
	public void setCharInUseToPrevious() {
		if(charInUse.getId() > 0) {
			setCharInUse(new AsciiChar(charInUse.getId()-1, charBackColorInUse, charForeColorInUse));
		}
		
	}
	
	//undo
	
	/**
	 * Method that set's the beginning of the history for the actions undo/redo.
	 */
	public void setHistory() {
		this.historyUndo = new ArrayDeque<AsciiChar[][]>();
		this.historyRedo = new ArrayDeque<AsciiChar[][]>();
		pushHistory(1, matrix);
	}
	
	/**
	 * Method that makes a deep copy of the matrix passed as a parameter and push it in the chosen history deque.
	 * @param op int for the operation 1 = undo, 2 = redo.
	 * @param mat matrix of ascii char to deep copy.
	 */
	private void pushHistory(int op, AsciiChar[][] mat) {
		
		switch(op) {
		
		case 1:
			historyUndo.push(Arrays.stream(mat).map(el -> el.clone()).toArray(el1 ->mat.clone()));
			break;
		
		case 2:
			historyRedo.push(Arrays.stream(mat).map(el -> el.clone()).toArray(el1 ->mat.clone()));
			break;
		}
	}
	
	/**
	 * Method that adds the matrix of asciiChar passed as a parameter to the undoHistory deque.
	 * @param mat AsciiChar[][].
	 */
	public void addItemToHistoryUndo(AsciiChar[][] mat) {
		
		if(historyUndo.size()==20) {
			historyUndo.removeLast();
		}
		pushHistory(1, mat);
		if(historyRedo.size() > 0)historyRedo = new ArrayDeque<AsciiChar[][]>();
	}
	
	/**
	 * Method that adds the matrix of asciiChar passed as a parameter to the undoHistoryDeque.
	 * @param mat AsciChar[][].
	 */
	private void addItemToHistoryRedo(AsciiChar[][] mat) {
		if(historyRedo.size()==20) {
			historyRedo.removeLast();
		}
		pushHistory(2, mat);
	}
	
	/**
	 * Method for the operation undo.
	 */
	public void undo() {
		if(historyUndo.size()>1) {
				
			AsciiChar hold = getCharInUse();
			int tool = getTool();
			setTool(-1);
			addItemToHistoryRedo(historyUndo.pop());
			AsciiChar[][] holder = historyUndo.getFirst();
			for(int y = 0; y<matrix.length; y++) {
				for(int x = 0; x < matrix[0].length ; x++) {
					setCoordinatesPointer(x, y);
					setCharInUse(holder[y][x]);
					writeCharOnPointer();
				}
			}
			setCharInUse(hold);
			setTool(tool);
		}
		
		//printMatrix();
		
	}
	
	/**
	 * Method for the operation redo.
	 */
	public void redo() {
		if(historyRedo.size()>0) {
			AsciiChar hold = getCharInUse();
			int tool = getTool();
			setTool(-1);
			AsciiChar[][] holder = historyRedo.pop();
			historyUndo.push(holder);
			for(int y = 0; y<matrix.length; y++) {
				for(int x = 0; x < matrix[0].length ; x++) {
					setCoordinatesPointer(x, y);
					setCharInUse(holder[y][x]);
					writeCharOnPointer();
				}
			}
			setCharInUse(hold);
			setTool(tool);
		}
	}
		
	
	
	/**
	 * Method that creates a matrix of AsciiChars with all the chars as a default.
	 * @return matris AsciiChar[][]. 
	 */
	private AsciiChar[][] createMatrix(){
		
		AsciiChar[][] m = new AsciiChar[matrixRows][matrixColumns];
		for(int y=0; y < matrixRows; y++) {
			for(int x=0; x< matrixColumns; x++) {
				m[y][x]= new AsciiChar(0, defaultCharBackColor, defaultCharForeColor);
			}
		}
		return m;
	}
	
	/**
	 * Method that retrieves the AsciiChar in the matrix where the pointer is.
	 * @return Asciichar. 
	 */
	public AsciiChar getCharOnPointer() {
		return matrix[coordinateYPointer][coordinateXPointer];
	}
	
	/**
	 * Method that retrieves the AsciiChar in a specific position (x,y) of the matrix.
	 * @param x Corrdinate int.
	 * @param y Coordinate int.
	 * @return AsciiChar.
	 */
	public AsciiChar getCharInPosition(int x, int y) {
		return matrix[y][x];
	}
	
	/**
	 * Method that update the array containing the coordinates and the action that needs to be commited.
	 * @param buttonPressed
	 */
	private void updateCoordinates(int buttonPressed) {
		this.coordinates[0] = getCoordinateXPointer();
		this.coordinates[1] = getCoordinateYPointer();
		this.coordinates[2] = buttonPressed;
	}
	
	
	/**
	 * Method that prints the AsciiChar matrix.
	 */
	public void printMatrix() {
		for(int y=0; y< matrix.length; y++) {
			for(int x=0; x< matrix[0].length; x++) {
				if(x== 0) {
					System.out.print("[" + getCharInPosition(x,y).getId() +", ");
					continue;
				}
				
				if(x== matrix[0].length-1) {
					System.out.print(getCharInPosition(x,y).getId() +"]");
					continue;
				}
				System.out.print(getCharInPosition(x,y).getId() +", ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Method that prints the Ascii Char matrix passed as a parameter.
	 * @param mat AciiChar[][]
	 */
	public void printMatrix(AsciiChar[][] mat) {
		for(int y=0; y< mat.length; y++) {
			for(int x=0; x< mat[0].length; x++) {
				if(mat[0].length ==1) {
					System.out.print("[" + mat[y][x].getId() +"]");
					continue;
				}
				
				if(x== 0) {
					System.out.print("[" + mat[y][x].getId() +", ");
					continue;
				}
				
				if(x== mat[0].length-1) {
					System.out.print(mat[y][x].getId() +"]");
					continue;
				}
				System.out.print(mat[y][x].getId() +", ");
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Method that writes in a specific location (x,y) of the matrix the ascii char in use.
	 * @param x coordinate int.
	 * @param y coordinate int.
	 */
	private void writeCharInPosition(int x, int y) {
		matrix[y][x] = charInUse;
	}
	
	//Foreground color panel
	/**
	 * Method that retrieves the foreground color for the char in use.
	 * @return charForeColorInUse Color.
	 */
	private Color getCharForeColorInUse() {
		return charForeColorInUse;
	}

	/**
	 * Method that set's the foreground color for the char in use.
	 * @param charForeColor Color.
	 */
	public void setCharForeColorInUse(Color charForeColor) {
		this.charForeColorInUse = charForeColor;
		setCharInUse(new AsciiChar(charInUse.getId(), charBackColorInUse, charForeColor));
	}

	//Background color panel
	/**
	 * Method that retrieves the background color for the char in use.	
	 * @return charBackColorInUse Color.
	 */
	private Color getCharBackColorInUse() {
		return charBackColorInUse;
	}

	/**
	 * Method that set's the background color for the char in use.
	 * @param charBackColor Color.
	 */
	public void setCharBackColorInUse(Color charBackColor) {
		this.charBackColorInUse = charBackColor;
		setCharInUse(new AsciiChar(charInUse.getId(), charBackColor, charForeColorInUse));
	}
	
	/**
	 * Method that set's the matrix in use.
	 * @param matrix AsciiChar[][].
	 */
	private void setMatrix(AsciiChar[][] matrix) {
		this.matrix = matrix;
	}

	/**
	 * Method that retrieves the matrix of AsciiChar.
	 * @return matrix AsciiChar[][].
	 */
	public AsciiChar[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * Method that retrieves the matrix width.
	 * @return matrixColumns int.
	 */
	public int getMatrixColumns() {
		return matrixColumns;
	}

	/**
	 * Method that set's the matrix width.
	 * @param matrixColumns int.
	 */
	public void setMatrixColumns(int matrixColumns) {
		this.matrixColumns = matrixColumns;
	}

	/**
	 * Method that retrieves the matrix height.
	 * @return matrixRows int.
	 */
	public int getMatrixRows() {
		return matrixRows;
	}

	/**
	 * Method that sets the matrix height.
	 * @param matrixRows int
	 */
	public void setMatrixRows(int matrixRows) {
		this.matrixRows = matrixRows;
	}

	/**
	 * Method that retrieves the AsciiChar in use.
	 * @return charInUse AsciiChar.
	 */
	public AsciiChar getCharInUse() {
		return charInUse;
	}
	
	/**
	 * Method that retrieves the x coordinate of the matrix pointer.
	 * @return coordinateXPointer int.
	 */
	public int getCoordinateXPointer() {
		return coordinateXPointer;
	}

	/**
	 * Method that set's the x coordinate of the matrix pointer.
	 * @param indexX int.
	 */
	public void setCoordinateXPointer(int indexX) {
		this.coordinateXPointer = indexX;
	}

	/**
	 * Method that retrieves the y coordinate of the matrix pointer.
	 * @return coordinateYPointer int.
	 */
	public int getCoordinateYPointer() {
		return coordinateYPointer;
	}

	/**
	 * Method that set's the y coordinate of the matrix pointer.
	 * @param indexY int.
	 */
	public void setCoordinateYPointer(int indexY) {
		this.coordinateYPointer = indexY;
	}
	
	/**
	 * Method that set's both coordinate of the matrix pointer (x,y).
	 * @param x coordinate int.
	 * @param y cooridnate int.
	 */
	public void setCoordinatesPointer(int x, int y) {
		setCoordinateXPointer(x);
		setCoordinateYPointer(y);
	}

	/**
	 * Method that retrieves the tool in use.
	 * @return tool integer.
	 */
	public Integer getTool() {
		return tool;
	}

	/**
	 * Method that set's the tool in use.
	 * @param tool int.
	 */
	public void setTool(Integer tool) {
		if(getSelectHolder() != null) {
			selectOperation(1, null, null);
		}
		this.tool = tool;
	}

	/**
	 * Method that retrieves the image imported (buffered image).
	 * @return importedImage BufferedImage.
	 */
	public BufferedImage getImportedImage() {
		return importedImage;
	}

	/**
	 * Method that sets the image imported.
	 * @param importedImage BufferedImage.
	 */
	public void setImportedImage(BufferedImage importedImage) {
		this.importedImage = importedImage;
	}

	/**
	 * Method that retrieves the default foreground color for the chars.
	 * @return defaultCharForColor Color
	 */
	public Color getDefaultCharForeColor() {
		return defaultCharForeColor;
	}
	
	/**
	 * Method that retrieves the default background color for the chars.
	 * @return defaultCharBackColor Color
	 */
	public Color getDefaultCharBackColor() {
		return defaultCharBackColor;
	}

	/**
	 * Method that retrieves the default starting string to be written in the matrix. 
	 * @return startingString String
	 */
	public String getStartingString() {
		return startingString;
	}
	
	/**
	 * Method that retrieves the selectHolder matrix of AsciiChars.
	 * @return AsciiChar[][] select holder.
	 */
	public AsciiChar[][] getSelectHolder() {
		return selectHolder;
	}

	/**
	 * Method that set's the selectHolder matrix of AsciiChars.
	 * @param selectHolder AsciiChar[][].
	 */
	private void setSelectHolder(AsciiChar[][] selectHolder) {
		this.selectHolder = selectHolder;
	}

	/**
	 * Method that retrieves a the matrix of asciiChar contained in the holder.
	 * @return copyHolder AsciiChar[][].
	 */
	private AsciiChar[][] getCopyHolder() {
		return copyHolder;
	}

	/**
	 * Method that sets the copyHolder.
	 * @param copyHolder AsciChar[][].
	 */
	private void setCopyHolder(AsciiChar[][] copyHolder) {
		this.copyHolder = copyHolder;
	}

	/**
	 * Method that sets the setter used for the clonestamp tool is true when is the pointer is settled, false when is not.
	 * @param pointerSetted boolean.
	 */
	public void setPointerSetted(boolean pointerSetted) {
		this.pointerSetted = pointerSetted;
	}

	/**
	 * Method that retrieves the final x coordinate of the figure that needs to be drawn.
	 * @return x1Figure int.
	 */
	public int getX1Figure() {
		return x1Figure;
	}

	/**
	 * Method that set's the x1 coordinate for the figure to draw.
	 * @param x1Figure int.
	 */
	public void setX1Figure(int x1Figure) {
		this.x1Figure = x1Figure;
	}

	/**
	 * Method that retrieves the final y coordinate of the figure that needs to be drawn.
	 * @return y1Figure int.
	 */
	public int getY1Figure() {
		return y1Figure;
	}

	/**
	 * Method that set's the y1 coordinate for the figure to draw.
	 * @param y1Figure int.
	 */
	public void setY1Figure(int y1Figure) {
		this.y1Figure = y1Figure;
	}
	
}