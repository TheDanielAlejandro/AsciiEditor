package image_editor.view;

import java.awt.Color;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * View for the "ASCII ImageEditor - Draw Rectangle" window.
 * @author danie
 *
 */
public class DrawRectangle extends JFrame{

	/**
	 * ID.
	 */
	private static final long serialVersionUID = -3046096625552352801L;
	
	/**
	 * JButton for the "draw" operation.
	 */
	private JButton drawButton;
	
	/**
	 * JPanel for the foreground Color of the border of the rectangle.
	 */
	private JPanel foregroundBorderPanel;
	
	/**
	 * JPanel for the background Color of the border of the rectangle.
	 */
	private JPanel backgroundBorderPanel;
	
	/**
	 * JPanel for the foreground Color of the inside of the rectangle.
	 */
	private JPanel foregroundInsidePanel;
	
	/**
	 * JPanel for the background Color of the inside of the rectangle.
	 */
	private JPanel backgroundInsidePanel;
	
	/**
	 * JTextField for the height of the rectangle.
	 */
	private JTextField heightTextField;
	
	/**
	 * JTextField for the width of the rectangle.
	 */
	private JTextField widthTextField;
	
	/**
	 * JTextField for the size of the borders of the rectangle.
	 */
	private JTextField sizeBorderTextField;
	
	/**
	 * SINGLETON DESIGNPATTERN - Instance of the DrawRectangle, so it can be only one window at the time.
	 */
	private static DrawRectangle instance;
	
	/**
	 * Method that creates an instance of the DrawRectangle if doesn't exist,
	 * if it does retrieves it back (singleton).
	 * @return Instance of DrawRectangle.
	 */
	public static DrawRectangle getInstance() {
		if (instance == null)
			instance = new DrawRectangle();
		return instance;
	}
	
	/**
	 * Private Constructor for the class DrawSquare so the class can be instantiated only once at a time (singleton design pattern), it set's all the content position, bounds of
	 * the window ("Draw Rectangle").  
	 */
	private DrawRectangle() {
		super("Draw Rectangle");
		setBounds(100, 100, 300, 250);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		drawButton = new JButton("Draw on pointer");
		drawButton.setBounds(6, 177, 272, 23);
		getContentPane().add(drawButton);
		
		JLabel foregroundLabel = new JLabel("Foreground:");
		foregroundLabel.setBounds(16, 100, 75, 14);
		getContentPane().add(foregroundLabel);
		
		JLabel backgroundLabel = new JLabel("Background:");
		backgroundLabel.setBounds(16, 148, 75, 14);
		getContentPane().add(backgroundLabel);
		
		JLabel colorBorderLabel = new JLabel("Color Border:");
		colorBorderLabel.setBounds(121, 75, 75, 14);
		getContentPane().add(colorBorderLabel);
		
		foregroundBorderPanel = new JPanel();
		foregroundBorderPanel.setBounds(141, 100, 44, 23);
		foregroundBorderPanel.setBackground(Color.WHITE);
		getContentPane().add(foregroundBorderPanel);
		
		backgroundBorderPanel = new JPanel();
		backgroundBorderPanel.setBounds(141, 148, 44, 23);
		backgroundBorderPanel.setBackground(Color.BLACK);
		getContentPane().add(backgroundBorderPanel);
		
		heightTextField = new JTextField("10");
		heightTextField.setBounds(72, 11, 65, 20);
		getContentPane().add(heightTextField);
		heightTextField.setColumns(10);
		heightTextField.setHorizontalAlignment(JTextField.CENTER);
		
		JLabel widthLabel = new JLabel("Width:");
		widthLabel.setBounds(150, 14, 44, 14);
		getContentPane().add(widthLabel);
		
		JLabel heightLabel = new JLabel("Height:");
		heightLabel.setBounds(16, 14, 46, 14);
		getContentPane().add(heightLabel);
		
		foregroundInsidePanel = new JPanel();
		foregroundInsidePanel.setBounds(222, 100, 44, 23);
		foregroundInsidePanel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(foregroundInsidePanel);
		
		backgroundInsidePanel = new JPanel();
		backgroundInsidePanel.setBounds(222, 148, 44, 23);
		backgroundInsidePanel.setBackground(Color.BLACK);
		getContentPane().add(backgroundInsidePanel);
		
		JLabel insideColorLabel = new JLabel("Color Inside:");
		insideColorLabel.setBounds(206, 75, 72, 14);
		getContentPane().add(insideColorLabel);
		
		JLabel sizeBorderLabel = new JLabel("Size Border:");
		sizeBorderLabel.setBounds(16, 51, 75, 14);
		getContentPane().add(sizeBorderLabel);
		
		sizeBorderTextField = new JTextField("1");
		sizeBorderTextField.setBounds(101, 48, 35, 20);
		sizeBorderTextField.setHorizontalAlignment(JTextField.CENTER);
		getContentPane().add(sizeBorderTextField);
		sizeBorderTextField.setColumns(10);
		
		widthTextField = new JTextField("20");
		widthTextField.setColumns(10);
		widthTextField.setBounds(201, 11, 65, 20);
		widthTextField.setHorizontalAlignment(JTextField.CENTER);
		getContentPane().add(widthTextField);
	}

	/**
	 * Method that retrieves the JButton for the action "draw on pointer".
	 * @return drawButton JButton.
	 */
	public JButton getDrawButton() {
		return drawButton;
	}

	/**
	 * Method that retrieves the JPanel for the foreground color selection of the border.
	 * @return foregroundBorderPanel JPanel.
	 */
	public JPanel getForegroundBorderPanel() {
		return foregroundBorderPanel;
	}

	/**
	 * Method that retrieves the JPanel for the background color selection of the border.
	 * @return backgroundBorderPanel JPanel.
	 */
	public JPanel getBackgroundBorderPanel() {
		return backgroundBorderPanel;
	}

	/**
	 * Method that retrieves the JPanel for the foreground color selection of the inside.
	 * @return foregroundInsidePanel JPanel.
	 */
	public JPanel getForegroundInsidePanel() {
		return foregroundInsidePanel;
	}

	/**
	 * Method that retrieves the JPanel for the background color selection of the inside.
	 * @return backgroundInsidePanel JPanel.
	 */
	public JPanel getBackgroundInsidePanel() {
		return backgroundInsidePanel;
	}

	/**
	 * Method that retrieves the JTextField for the height of the rectangle.
	 * @return hightTextField JTextField.
	 */
	public JTextField getHeightTextField() {
		return heightTextField;
	}

	/**
	 * Method that retrieves the JTextField for the width of the rectangle.
	 * @return widthTextField JTextField.
	 */
	public JTextField getWidthTextField() {
		return widthTextField;
	}

	/**
	 * Method that retrieves the JTextField for the size of the borders of the rectangle.
	 * @return sizeBorderTextField JTextField.
	 */
	public JTextField getSizeBorderTextField() {
		return sizeBorderTextField;
	}
	
}
