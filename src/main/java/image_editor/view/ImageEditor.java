package image_editor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import ascii_panel.AsciiFont;
import ascii_panel.AsciiPanel;
import ascii_panel.AsciiRaster;
import image_editor.model.AsciiChar;

/**
 * View for the application ImageEditor.
 * @author danie
 *
 */
public class ImageEditor extends JFrame implements Observer {

	/**
	 * ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Singleton Design Pattern - Instance of ImageEditor.
	 */
	private static ImageEditor instance;

	// Components
	
	/**
	 * AsciiPanel the main workspace of the application.
	 */
	public AsciiPanel mainPanel;
	
	//Aggiunta dimensioni pannello
	/**
	 * Width dimension of the main panel.
	 */
	private int mainPanelWidth = 80;
	
	/**
	 * Height dimension of the main panel.
	 */
	private int mainPanelHeight = 60;
		
	/**
	 * Color for the foreground of the ascii chars.
	 */
	private Color charForeColor = Color.WHITE;
	
	/**
	 * Color for the background of the ascii chars.
	 */
	private Color charBackColor = Color.BLACK;
	
	/**
	 * Integer indicating the ascii char in use.
	 */
	private Integer selectedChar = 1;
	
	/**
	 * JPanel that displays and allows to select the foreground colour.
	 * @see ImageEditor#charForeColor
	 */
	private JPanel foregroundPanel;
	
	/**
	 * JPanel that displays and allows to select the background colour.
	 * @see ImageEditor#charBackColor
	 */
	private JPanel backgroundPanel;
	
	/**
	 * JButton for the previous ascii char selector.  
	 */
	private JButton previousCharButton;
	
	/**
	 * JButton for the next ascii char selector.
	 */
	private JButton nextCharButton;
	
	/**
	 * JButton that contains the ascii char in use and let the user to select it.
	 */
	private JButton charSelectorButton;
	
	/**
	 * JButton for the pick tool.
	 */
	private JButton pickButton;
	
	/**
	 * JButton for the paint tool.
	 */
	private JButton paintButton;
	
	/**
	 * JButton for the fill tool.
	 */
	private JButton fillButton;
	
	/**
	 * JButton for the clone stamp tool.
	 */
	private JButton cloneStampButton;
		
	/**
	 * AsciiPanel used as a display of the actual char in use for the charSelectorButton. 
	 * @see ImageEditor#charSelectorButton
	 */
	private AsciiPanel selectedCharPreview;
	
	/**
	 * JButton for the undo action.
	 */
	private JButton undoButton;
	
	/**
	 * JButton for the red action.
	 */
	private JButton redoButton;

	/**
	 * JPanel for the controls such us "paint","fill", ecc.
	 */
	private JPanel controlsPanel;
	
	/**
	 * JMenuBar for the ImageEditor panel.
	 */
	private JMenuBar menuBar;
	
	/**
	 * JMenu for the "file" menu.
	 * It's going to be applied to the menu bar.
	 * @see ImageEditor#menuBar
	 */
	private JMenu menuBarFile;
	
	/**
	 * JMenuItem for the option "New" for the ImageEditor application.
	 */
	private JMenuItem mbFileNew;
	
	/**
	 * JMenuItem for the option "Load" for the ImageEditor application.
	 */
	private JMenuItem mbFileLoad;
	
	/**
	 * JMenuItem for the option "Save" for the ImageEditor application.
	 */
	private JMenuItem mbFileSave;
	
	/**
	 * JMenuItem for the option "Import" for the ImageEditor application.
	 */
	private JMenuItem mbFileImport;
	
	/**
	 * JMenu for the "edit" menu.
	 */
	private JMenu menuBarEdit;
	
	/**
	 * JMenuItem for the action "Select" from the "edit" menu.
	 */
	private JMenuItem mbEditSelect;
	
	/**
	 * JMenuItem for the action "Copy" from the "edit" menu.
	 */
	private JMenuItem mbEditCopy;
	
	/**
	 * JMenuItem for the action "Cut" from the "edit" menu.
	 */
	private JMenuItem mbEditCut;
	
	/**
	 * JMenuItem for the action "Paste" from the "edit" menu.
	 */
	private JMenuItem mbEditPaste;
	
	/**
	 * JMenuItem for the action "Smooth" from the "edit" menu.
	 */
	private JMenuItem mbEditSmooth;
	
	/**
	 * JMenuItem for the action "Change Colors" from the "edit" menu.
	 */
	private JMenuItem mbEditChangeColors;
	
	/**
	 * JMenu for the "draw" menu.
	 */
	private JMenu menuBarDraw;
	
	/**
	 * JMenuItem for the action "Square" from the "Draw" menu.
	 */
	private JMenuItem mbDrawSquare;
	
	/**
	 * JMenuItem for the action "Rectangle" from the "Draw" menu.
	 */
	private JMenuItem mbDrawRectangle;
	
	/**
	 * JMenuItem for the action "Triangle" from the "Draw" menu.
	 */
	private JMenuItem mbDrawTriangle;
	
	/**
	 * SINGLETON DESIGN PATTERN
	 * Method that retrieves the only instance of ImageEditor in case it already exists or creates a new one in case not. 
	 * @return instance of the ImageEditor
	 */
	public static ImageEditor getInstance() {

		if (instance == null)
			instance = new ImageEditor();
		return instance;
	}
	
	/**
	 * Private constructor for the imageEditor view Singleton Design Pattern.
	 */
	private ImageEditor() {
		
		// Setting the Frame
		
		super("ASCII ImageEditor");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 80 * 16, 60 * 16);
		this.setLayout(new BorderLayout());
		
		// Setting main Ascii Panel
		
		mainPanel = new AsciiPanel(mainPanelWidth, mainPanelHeight, AsciiFont.CP437_16x16);
		mainPanel.setCursorX(0);
		mainPanel.setCursorY(0);

		this.add(mainPanel, BorderLayout.CENTER);
		
		
		// Setting the controls Panel
		
		controlsPanel = new JPanel();
		controlsPanel.setLayout(new GridLayout(1,3,10, 10));
		
		foregroundPanel = new JPanel();
		backgroundPanel = new JPanel();
		selectedCharPreview = new AsciiPanel(1, 1, AsciiFont.CP437_16x16);
		previousCharButton = new JButton("<<");
		nextCharButton = new JButton(">>");
		charSelectorButton = new JButton();
		
		Image pickIcon =  new ImageIcon(this.getClass().getResource("/eye_dropper.png")).getImage();
		pickButton = new JButton(new ImageIcon(pickIcon));
		
		Image paintIcon =  new ImageIcon(this.getClass().getResource("/paint_brush.png")).getImage();
		//Image newPaintIcon = paintIcon.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		paintButton = new JButton(new ImageIcon(paintIcon));
	
		Image fillIcon =  new ImageIcon(this.getClass().getResource("/fill_drip.png")).getImage();
		fillButton = new JButton(new ImageIcon(fillIcon));
		
		Image cloneStampIcon = new ImageIcon(this.getClass().getResource("/clone_stamp.png")).getImage();
		cloneStampButton = new JButton(new ImageIcon(cloneStampIcon));
		
		Image undoIcon = new ImageIcon(this.getClass().getResource("/undo.png")).getImage();
		undoButton = new JButton(new ImageIcon(undoIcon));
		
		Image redoIcon = new ImageIcon(this.getClass().getResource("/redo.png")).getImage();
		redoButton = new JButton(new ImageIcon(redoIcon));
		
		foregroundPanel.setBackground(charForeColor);
		backgroundPanel.setBackground(charBackColor);

		
		JPanel charPanel = new JPanel();
		charPanel.setBorder(BorderFactory.createTitledBorder("Character"));
		charPanel.add(previousCharButton);
		charPanel.add(charSelectorButton);
		charPanel.add(nextCharButton);
		charPanel.add(foregroundPanel);
		charPanel.add(backgroundPanel);

		
		JPanel colorsCharPanel = new JPanel();
		colorsCharPanel.setMaximumSize(new Dimension(50,50));
		colorsCharPanel.setPreferredSize(new Dimension(80,60));
		colorsCharPanel.setBorder(BorderFactory.createTitledBorder("Colors"));
		colorsCharPanel.setLayout(new GridLayout(1,1,30,50));
		colorsCharPanel.add(foregroundPanel);
		colorsCharPanel.add(backgroundPanel);
		
		JPanel actionsPanel = new JPanel();
		actionsPanel.setBorder(BorderFactory.createTitledBorder("Tools"));
		actionsPanel.add(paintButton);
		actionsPanel.add(fillButton);
		actionsPanel.add(pickButton);
		actionsPanel.add(cloneStampButton);
		
		JPanel undoRedoPanel = new JPanel();
		undoRedoPanel.setBorder(BorderFactory.createTitledBorder("Undo/Redo"));
		undoRedoPanel.add(undoButton);
		undoRedoPanel.add(redoButton);
		
		
		controlsPanel.add(actionsPanel);
		controlsPanel.add(undoRedoPanel);
		controlsPanel.add(charPanel);
		controlsPanel.add(colorsCharPanel);
		
		this.add(controlsPanel, BorderLayout.NORTH);
		
		// Menu bar and elements
		
		menuBar = new JMenuBar();
		
		menuBarFile = new JMenu("File");
		mbFileNew = new JMenuItem("New...");
		mbFileLoad = new JMenuItem("Load...");
		mbFileSave = new JMenuItem("Save...");
		mbFileImport = new JMenuItem("Import...");
		
		menuBarEdit = new JMenu("Edit");
		mbEditSelect = new JMenuItem("Select");
		mbEditCopy = new JMenuItem("Copy");
		mbEditCut = new JMenuItem("Cut");
		mbEditPaste = new JMenuItem("Paste");
		mbEditSmooth = new JMenuItem("Smooth");
		mbEditChangeColors = new JMenuItem("Change Colors");
		
		menuBarDraw = new JMenu("Draw");
		mbDrawSquare = new JMenuItem("Square");
		mbDrawRectangle = new JMenuItem("Rectangle");
		mbDrawTriangle = new JMenuItem("Triangle");
		
		menuBar.add(menuBarFile);
		menuBar.add(menuBarEdit);
		menuBar.add(menuBarDraw);
		
		
		menuBarFile.add(mbFileNew);
		menuBarFile.add(mbFileLoad);
		menuBarFile.add(mbFileSave);
		menuBarFile.add(mbFileImport);
		
		menuBarEdit.add(mbEditSelect);
		menuBarEdit.add(mbEditCopy);
		menuBarEdit.add(mbEditCut);
		menuBarEdit.add(mbEditPaste);
		menuBarEdit.add(mbEditSmooth);
		menuBarEdit.add(mbEditChangeColors);
		
		menuBarDraw.add(mbDrawSquare);
		menuBarDraw.add(mbDrawRectangle);
		menuBarDraw.add(mbDrawTriangle);
	
		this.setJMenuBar(menuBar);
		menuBar.setVisible(true);
		
		updateSelectedCharPreview();
	}

//	Setters & Getters
	
	/**
	 * Method that retrieves the main panel.
	 * @return mainPanel AsciiPanel.
	 */
	public AsciiPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * Method that set's the main panel.
	 * @param mainPanel AsciiPanel.
	 */
	public void setMainPanel(AsciiPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	/**
	 * Method that retrieves the width of the main panel.
	 * @return mainPanelWidth int.
	 */
	public int getMainPanelWidth() {
		return mainPanelWidth;
	}

	/**
	 * Method that set's the width of the main panel.
	 * @param mainPanelWidth int
	 */
	public void setMainPanelWidth(int mainPanelWidth) {
		this.mainPanelWidth = mainPanelWidth;
	}

	/**
	 * Method that retrieves the height of the main panel.
	 * @return mainPanelHeight int.
	 */
	public int getMainPanelHeight() {
		return mainPanelHeight;
	}

	/**
	 * Method that set's the height of the main panel. 
	 * @param mainPanelHeight int
	 */
	public void setMainPanelHeight(int mainPanelHeight) {
		this.mainPanelHeight = mainPanelHeight;
	}

	/**
	 * Method that retrieves the foreground colour for the chars in use.
	 * @return charForeColor Color.
	 */
	public Color getCharForeColor() {
		return charForeColor;
	}

	/**
	 * Method that set's the foreground colour for the chars in use.
	 * @param charForeColor Color.
	 */
	public void setCharForeColor(Color charForeColor) {
		this.charForeColor = charForeColor;
	}

	/**
	 * Method that retrieves the background colour for the chars in use.
	 * @return charBackColor Color.
	 */
	public Color getCharBackColor() {
		return charBackColor;
	}

	/**
	 * Method that set's the background colour for the chars in use.
	 * @param charBackColor Color.
	 */
	public void setCharBackColor(Color charBackColor) {
		this.charBackColor = charBackColor;
	}

	/**
	 * Method that retrieves the selected char in use
	 * @return selectedChar int.
	 */
	public Integer getSelectedChar() {
		return selectedChar;
	}

	/**
	 * Method that set's the char in use.
	 * @param selectedChar int.
	 */
	public void setSelectedChar(Integer selectedChar) {
		this.selectedChar = selectedChar;
	}

	/**
	 * Method that retrieves the foreground panel.
	 * @return foregroundPanel JPanel.
	 */
	public JPanel getForegroundPanel() {
		return foregroundPanel;
	}

	/**
	 * Method that retrieves the background panel.
	 * @return backgroundPanel JPanel.
	 */
	public JPanel getBackgroundPanel() {
		return backgroundPanel;
	}

	/**
	 * Method that retrieves the button that sets the char in use to the previous ascii char.
	 * @return previousCharButton JButton,
	 */
	public JButton getPreviousCharButton() {
		return previousCharButton;
	}

	/**
	 * Method that retrieves the button that sets the char in use to the next ascii char.
	 * @return nextCharButton JButton.
	 */
	public JButton getNextCharButton() {
		return nextCharButton;
	}

	/**
	 * Method that retrieves the button that sets the char in use to the one picked by the user.
	 * @return charSelectorButton JButton.
	 */
	public JButton getCharSelectorButton() {
		return charSelectorButton;
	}

	/**
	 * Method that retrieves the button for the action "pick".
	 * @return pickButton JButton.
	 */
	public JButton getPickButton() {
		return pickButton;
	}

	/**
	 * Method that retrieves the button for the action "paint".
	 * @return paintButton JButton.
	 */
	public JButton getPaintButton() {
		return paintButton;
	}

	/**
	 * Method that retrieves the button for the action "fill".
	 * @return fillButton JButton.
	 */
	public JButton getFillButton() {
		return fillButton;
	}

	/**
	 * Method that retrieves the button for the action "Clone Stamp".
	 * @return cloneStampButton JButton.
	 */
	public JButton getCloneStampButton() {
		return cloneStampButton;
	}

	/**
	 * Method that retrieves the Ascii panel that displays the char in use.
	 * @return selectedCharPreview AsciiPanel.
	 */
	public AsciiPanel getSelectedCharPreview() {
		return selectedCharPreview;
	}

	/**
	 * Method that retrieves the undo button.
	 * @return undoButton JButton.
	 */
	public JButton getUndoButton() {
		return undoButton;
	}

	/**
	 * Method that retrieves the redo button.
	 * @return redoButton JButton.
	 */
	public JButton getRedoButton() {
		return redoButton;
	}

	/**
	 * Method that retrieves the JPanel that contains all the "controls" or "action" that can be done on the main panel.
	 * @return controlsPanel JPanel.
	 */
	public JPanel getControlsPanel() {
		return controlsPanel;
	}

	/**
	 * Method that retrieves the JMenuITem for the option "New" from the menu "File".
	 * @return mbFileNew JMenuItem.
	 */
	public JMenuItem getMbFileNew() {
		return mbFileNew;
	}

	/**
	 * Method that retrieves the JMenuItem for the option "Load" from the menu "File".
	 * @return mbFileLoad JMenuItem.
	 */
	public JMenuItem getMbFileLoad() {
		return mbFileLoad;
	}

	/**
	 * Method that retrieves the JMenuItem for the option "Save" from the menu "File".
	 * @return mbFileSave JMenuItem.
	 */
	public JMenuItem getMbFileSave() {
		return mbFileSave;
	}

	/**
	 * Method that retrieves the JMenuITem for the option "Import" from the menu "File".
	 * @return mbFileImport JMenuItem.
	 */
	public JMenuItem getMbFileImport() {
		return mbFileImport;
	}

	/**
	 * Method that retrieves the JMenuITem for the action "Select" from the menu "Edit".
	 * @return mbEditSelect JMenuItem.
	 */
	public JMenuItem getMbEditSelect() {
		return mbEditSelect;
	}
	
	/**
	 * Method that retrieves the JMenuITem for the action "Copy" from the menu "Edit".
	 * @return mbEditCopy JMenuItem.
	 */
	public JMenuItem getMbEditCopy() {
		return mbEditCopy;
	}
	
	/**
	 * Method that retrieves the JMenuITem for the action "Cut" from the menu "Edit".
	 * @return mbEditCut JMenuItem.
	 */
	public JMenuItem getMbEditCut() {
		return mbEditCut;
	}
	
	/**
	 * Method that retrieves the JMenuITem for the action "Paste" from the menu "Edit".
	 * @return mbEditPaste JMenuItem.
	 */
	public JMenuItem getMbEditPaste() {
		return mbEditPaste;
	}	
	
	/**
	 * Method that retrieves the JMenuItem for the action "Smooth" from the menu "Edit".
	 * @return mbEditSmooth JMenuItem.
	 */
	public JMenuItem getMbEditSmooth() {
		return mbEditSmooth;
	}
	
	/**
	 * Method that retrieves the JMenuItem for the action "Change Colors" from the menu "Edit".
	 * @return mbEditChangeColors JMenuItem.
	 */
	public JMenuItem getMbEditChangeColors() {
		return mbEditChangeColors;
	}
	

	/**
	 * Method that retrieves the JMenuItem for the action "Square" from the menu "Draw".
	 * @return mbDrawSquare JMenuItem.
	 */
	public JMenuItem getMbDrawSquare() {
		return mbDrawSquare;
	}

	/**
	 * Method that retrieves the JMenuItem for the action "Rectangle" from the menu "Draw".
	 * @return mbDrawSquare JMenuItem.
	 */
	public JMenuItem getMbDrawRectangle() {
		return mbDrawRectangle;
	}
	
	/**
	 * Method that retrieves the JMenuItem for the action "Triangle" from the menu "Draw".
	 * @return mbDrawSquare JMenuItem.
	 */
	public JMenuItem getMbDrawTriangle() {
		return mbDrawTriangle;
	}


//-------------------------------------------------		




	/**
	 * Method that moves the coordinates (X,Y) in the matrix ready to commit action, according to the mouse cursor. 
	 * @param button it's an integer telling which button from the mouse has been pressed.
	 * @param x coordinate, Integer.
	 * @param y coordinate, Integer.
	 */
	public void onCursorMove(int button, int x, int y) {
		mainPanel.setMouseCursorX(x / 16);
		mainPanel.setMouseCursorY(y / 16);
		//mainPanel.repaint();
	}
	
	/**
	 * Method that updates the "display" (ascii panel) for the ascii char selector button.
	 * @see 
	 */
	private void updateSelectedCharPreview() {
		selectedCharPreview.clear();
		selectedCharPreview.write((char) (selectedChar + 0), 0, 0, charForeColor, charBackColor);
		//selectedCharPreview.repaint();
		charSelectorButton.setIcon(new ImageIcon(selectedCharPreview.getGlyphs()[selectedChar]));
	}
	
	/**
	 * Method that resets the Ascii panel in the ImageEditor application specifying the dimensions (width, height).
	 * @param sx width for the new ascii panel 
	 * @param sy height for the new ascii panel
	 */
	public void reset(int sx, int sy) {
		this.remove(mainPanel);
		mainPanel = new AsciiPanel(sx, sy, AsciiFont.CP437_16x16);
		this.add(mainPanel);
		mainPanel.clear();
		mainPanel.setCursorX(0);
		mainPanel.setCursorY(0);
		mainPanel.write("Empty");
		mainPanel.setBounds(80, 0, sx * 16, sy * 16);
		
	}
	
	/**
	 * OBSERVER/OBSERVABLE DESIGN PATTERN
	 * Method that is called when the observable has changed it status.
	 * In base of parameter passed by the observable this observer will update a specific part of the ImageEditor.
	 */
	@Override
	public void update(Observable model, Object values) {
		//System.out.println("observer riceve");
		
		//Set a new char, back and fore
		if(values.getClass().equals(AsciiChar.class)) {
			AsciiChar ch= (AsciiChar)values;
			//System.out.println("Ho ricevuto il carattere da cambiare dal model, cambio id fore e back color.");
			//System.out.println("il carattere precedente era : " + selectedChar);
			setSelectedChar(ch.getId());
			//System.out.println("il carattere attuale adesso è: " + selectedChar);
			charSelectorButton.setLabel(selectedChar + "");
			
			
			//System.out.println("Cambio il foreground color in uso, prima era:" + charForeColor);
			setCharForeColor(ch.getForegroundColor());
			//System.out.println("adesso è : " + charForeColor);
			foregroundPanel.setBackground(charForeColor);
			
			
			//System.out.println("Cambio il foreground color in uso, prima era:" + charBackColor);
			setCharBackColor(ch.getBackgroundColor());
			//System.out.println("adesso è : " + charBackColor);
			backgroundPanel.setBackground(charBackColor);
			
			backgroundPanel.repaint();
			foregroundPanel.repaint();
			updateSelectedCharPreview();
		}
		
		//write = 0/erase= 1/fill= 2/unfill= 3/clear = 4
		if(values.getClass().equals(int[].class)) {
			int[] coordinates = (int[])values;
			mainPanel.setCursorX(coordinates[0]);
			mainPanel.setCursorY(coordinates[1]);
			switch(coordinates[2]) {
				case 0:
					//System.out.println("prese le coordinate scrivo su ascii panel");
					mainPanel.write((char) (selectedChar + 0), charForeColor, charBackColor);
					break;
				case 1:
					//System.out.println("cancello su ascii panel");
					mainPanel.write((char) 0);
					break;
				case 2:
					//System.out.println("riempio il pannello con l'ascii char");
					mainPanel.fill((char) (selectedChar + 0), mainPanel.getCursorX(), mainPanel.getCursorY(), charForeColor, charBackColor);
					break;
				case 3:
					//System.out.println("resetta l'area selezionata con l'ascii char di default");
					mainPanel.fill((char) (0), mainPanel.getCursorX(), mainPanel.getCursorY(), Color.black, Color.black);
					break;
				case 4:
					//System.out.println("Svuoto il pannello principale completamente");
					mainPanel.clear();
					break;
			}
		}
		
		//Operation New panel
		if(values.getClass().equals(AsciiChar[][].class)) {
			AsciiChar[][] ch = (AsciiChar[][]) values;
			int x = ch[0].length;
			int y = ch.length;
			//System.out.println("le nuove dimensioni di x:" +x + " y:" +y);
			reset(x, y);
		}
		
		//Operation Load
		if(values.getClass().equals(AsciiRaster.class)) {
			AsciiRaster raster = (AsciiRaster) values;
			mainPanel.paintRaster(raster, 0, 0, false);
		}
		//It repaint the main panel
		repaint();
		
	}
	
}


