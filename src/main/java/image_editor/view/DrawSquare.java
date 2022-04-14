package image_editor.view;

import java.awt.Color;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * View for the "ASCII ImageEditor - Draw Square" window.
 * @author danie
 *
 */
public class DrawSquare extends JFrame{
	
	/**
	 * ID.
	 */
	private static final long serialVersionUID = -3046096625552352801L;
	
	/**
	 * JButton for the "draw on pointer" operation.
	 */
	private JButton drawButton;
	
	/**
	 * JPanel for the foreground Color of the border of the square.
	 */
	private JPanel foregroundBorderPanel;
	
	/**
	 * JPanel for the background Color of the border of the square.
	 */
	private JPanel backgroundBorderPanel;
	
	/**
	 * JPanel for the foreground Color of the inside of the square.
	 */
	private JPanel foregroundInsidePanel;
	
	/**
	 * JPanel for the background Color of the inside of the square.
	 */
	private JPanel backgroundInsidePanel;
	
	/**
	 * JTextField for the size of the side of the square.
	 */
	private JTextField sideSizeTextField;
		
	/**
	 * JTextField for the size of the borders of the square.
	 */
	private JTextField sizeBorderTextField;
	
	/**
	 * SINGLETON DESIGNPATTERN - Instance of the DrawSquare, so it can be only one window at the time.
	 */
	private static DrawSquare instance;
	
	/**
	 * Method that creates an instance of the DrawSquare if doesn't exist,
	 * if it does retrieves it back (singleton).
	 * @return Instance of DrawSquare.
	 */
	public static DrawSquare getInstance() {
		if (instance == null)
			instance = new DrawSquare();
		return instance;
	}

	/**
	 * Private Constructor for the class DrawSquare so the class can be instantiated only once at a time (singleton design pattern), it set's all the content position, bounds of
	 * the window ("Draw Square").  
	 */
	private DrawSquare() {
		super("Draw Square");
		setBounds(100, 100, 300, 219);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		drawButton = new JButton("Draw on pointer");
		drawButton.setBounds(6, 151, 272, 23);
		getContentPane().add(drawButton);
		
		JLabel foregroundLabel = new JLabel("Foreground:");
		foregroundLabel.setBounds(16, 77, 75, 14);
		getContentPane().add(foregroundLabel);
		
		JLabel backgroundLabel = new JLabel("Background:");
		backgroundLabel.setBounds(16, 115, 75, 14);
		getContentPane().add(backgroundLabel);
		
		foregroundBorderPanel = new JPanel();
		foregroundBorderPanel.setBounds(141, 77, 44, 23);
		foregroundBorderPanel.setBackground(Color.WHITE);
		getContentPane().add(foregroundBorderPanel);
		
		backgroundBorderPanel = new JPanel();
		backgroundBorderPanel.setBounds(141, 115, 44, 23);
		backgroundBorderPanel.setBackground(Color.BLACK);
		getContentPane().add(backgroundBorderPanel);
		
		JLabel colorBorderLabel = new JLabel("Color Border:");
		colorBorderLabel.setBounds(121, 57, 75, 14);
		getContentPane().add(colorBorderLabel);
		
		foregroundInsidePanel = new JPanel();
		foregroundInsidePanel.setBounds(222, 77, 44, 23);
		foregroundInsidePanel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(foregroundInsidePanel);
		
		backgroundInsidePanel = new JPanel();
		backgroundInsidePanel.setBounds(222, 115, 44, 23);
		backgroundInsidePanel.setBackground(Color.BLACK);
		getContentPane().add(backgroundInsidePanel);
		
		JLabel colorInsideLabel = new JLabel("Color Inside:");
		colorInsideLabel.setBounds(206, 57, 72, 14);
		getContentPane().add(colorInsideLabel);
		
		JLabel sizeBorderLabel = new JLabel("Size Border:");
		sizeBorderLabel.setBounds(147, 24, 75, 14);
		getContentPane().add(sizeBorderLabel);
		
		sizeBorderTextField = new JTextField("1");
		sizeBorderTextField.setBounds(222, 21, 35, 20);
		sizeBorderTextField.setHorizontalAlignment(JTextField.CENTER);
		getContentPane().add(sizeBorderTextField);
		sizeBorderTextField.setColumns(10);
		
		sideSizeTextField = new JTextField("10");
		sideSizeTextField.setColumns(10);
		sideSizeTextField.setBounds(92, 21, 35, 20);
		sideSizeTextField.setHorizontalAlignment(JTextField.CENTER);
		getContentPane().add(sideSizeTextField);
		
		JLabel sizeSideLabel = new JLabel("Size Side:");
		sizeSideLabel.setBounds(16, 24, 75, 14);
		getContentPane().add(sizeSideLabel);
		
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
	 * Method that retrieves the JTextField for the side size of the square.
	 * @return sideSizeTextField JTextField.
	 */
	public JTextField getSideSizeTextField() {
		return sideSizeTextField;
	}

	/**
	 * Method that retrieves the JTextField for the size of the borders of the square.
	 * @return sizeBorderTextField JTextField.
	 */
	public JTextField getSizeBorderTextField() {
		return sizeBorderTextField;
	}
}
