package image_editor.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * View for the windonw "Image New" in the Image Editor application.
 * @author danie
 *
 */
public class ImageNew extends JFrame{
	
	/**
	 * Id.
	 */
	private static final long serialVersionUID = -1844498949903148619L;
	
	/**
	 * Button for the "create new" action.
	 */
	private JButton proceedButton;
	
	/**
	 * Label with the word : "Width", width dimension for the new panel.
	 */
	private JLabel widthLabel;
	
	/**
	 * Text field that the user can insert for the width parameter for the new panel.
	 */
	private JTextField widthText;
	
	/**
	 * Label with the word : "Height", height dimension for the new panel.
	 */
	private JLabel heightLabel;
	
	/**
	 * Text field that the user can insert for the height parameter for the new panel.
	 */
	private JTextField heightText;

	/**
	 * SINGLETON DESIGN PATTERN - Instance of the ImageNew, so it can be only one window at the time.
	 */
	private static ImageNew instance;

	/**
	 * Method that creates an instance of the imageNew if doesn't exist,
	 * if it does retrieves it back (singleton).
	 * @return Instance of ImageNew
	 */
	public static ImageNew getInstance() {
		if (instance == null)
			instance = new ImageNew();
		return instance;
	}
	
	/**
	 * Private Constructor for the class ImageNew so the class can be instantiated only once at a time (singleton design pattern), it set's all the content position, bounds that needs to be inside
	 * the window ("ASCII ImageEditor - New").  
	 */
	private ImageNew() {
		super("ASCII ImageEditor - New");
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setBounds(0, 0, 320, 230);
		this.setResizable(false);
		this.setLayout(null);

		widthLabel = new JLabel("Width:");
		widthText = new JTextField("80");
		heightLabel = new JLabel("Height:");
		heightText = new JTextField("60");
		proceedButton = new JButton("Create New");
		this.add(widthLabel);
		this.add(widthText);
		this.add(heightLabel);
		this.add(heightText);
		this.add(proceedButton);
		widthLabel.setBounds(0, 0, 320, 40);
		widthText.setBounds(0, 40, 320, 40);
		heightLabel.setBounds(0, 80, 320, 40);
		heightText.setBounds(0, 120, 320, 40);
		proceedButton.setBounds(0, 160, 320, 40);
	
	}
	
	/**
	 * Method that retrieves the button for the action "Create New"
	 * @return JButton proceedButton.
	 */
	public JButton getProceedButton() {
		return proceedButton;
	}

	
	
	/**
	 * Method that retrieves the JTextField for the width of the new main panel that is going to be inserted by the user.
	 * @return widthText JtextField.
	 */
	public JTextField getWidthText() {
		return widthText;
	}

	/**
	 * Method that set's the JTextField for the width of the new main panel.
	 * @param widthText JTextField
	 */
	public void setWidthText(JTextField widthText) {
		this.widthText = widthText;
	}

	/**
	 * Method that retrieves the JTextField for the height of the new main panel that is going to be inserted by the user.
	 * @return heightText JtextField.
	 */
	public JTextField getHeightText() {
		return heightText;
	}

	/**
	 * Method that set's the JTextField for the height of the new main panel.
	 * @param heightText JTextField
	 */
	public void setHeightText(JTextField heightText) {
		this.heightText = heightText;
	}

	/**
	 * Method that closes the window of the importer releasing all the memory, it also set's the instance of the previous one created to null.
	 */
	public void close() {
		instance.setVisible(false);
		instance.dispose();
		instance = null;

	}

}
