package image_editor.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * View for the window "ASCII ImageEditor - Import".
 * @author danie
 *
 */
public class ImageImporter extends JFrame{
	
	/**
	 * ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Button for the "import" action. 
	 */
	private JButton importButton;
	
	/**
	 * Button for the "convert" action.
	 */
	private JButton convertButton;
	
	/**
	 * Label with the word : "Threshold:", Indicating the segmentation of the image from grayscale or full-colour image. 
	 * This is typically done in order to separate "object" or foreground pixels from background pixels to aid in image processing.
	 */
	private JLabel thresholdLabel;
	
	/**
	 * Text field that the user can insert to choose the threshold for the image to import.
	 */
	private JTextField thresholdTextVal;
	
	/**
	 * Check box that the user can tick or not to choose if the image imported when is converted is going to be able to display all the colours available.
	 */
	private JCheckBox allColorsBox;

	/**
	 * Label with the word : "Filters:", Indicating the filter to apply to the image imported.
	 */
	private JLabel filtersLabel;
	
	/**
	 * JRadioButton for the filter selection : "none".
	 */
	private JRadioButton noneButton;
	
	/**
	 * JRadioButton for the filter selection : "B and W".
	 */
	private JRadioButton bwButton;
	
	/**
	 * JRadioButton for the filter selection : "Negative".
	 */
	private JRadioButton negativeButton;
	
	/**
	 * JRadioButton for the filter selection : "Smooth".
	 */
	private JRadioButton smoothButton;
	
	/**
	 * ButtonGroup of JradioButtons for the filter selection.
	 */
	private ButtonGroup filtersButtons;
	
	
	/**
	 * SINGLETON DESIGN PATTERN - Instance of the ImageImporter, so it can be only one window at the time.
	 */
	private static ImageImporter instance;
	
	/**
	 * Method that creates an instance of the imageImporter if doesn't exist,
	 * if it does retrieves it back (singleton).
	 * @return Instance of ImageImporter
	 */
	public static ImageImporter getInstance() {
		if (instance == null)
			instance = new ImageImporter();
		return instance;
	}

	/**
	 * Private Constructor for the class ImageImporter so the class can be instantiated only once at a time (singleton design pattern).
	 */
	private ImageImporter() {
		new JFrame("ASCII ImageEditor - Importer");
		setBounds(100, 100, 300, 219);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		convertButton = new JButton("Convert");
		convertButton.setBounds(6, 151, 272, 23);
		getContentPane().add(convertButton);
		
		importButton = new JButton("Import");
		importButton.setBounds(6, 11, 272, 23);
		getContentPane().add(importButton);
		
		thresholdLabel = new JLabel("Treshold:");
		thresholdLabel.setBounds(6, 58, 54, 14);
		getContentPane().add(thresholdLabel);
		
		thresholdTextVal = new JTextField();
		thresholdTextVal.setText("100");
		thresholdTextVal.setHorizontalAlignment(SwingConstants.CENTER);
		thresholdTextVal.setBounds(70, 55, 86, 20);
		getContentPane().add(thresholdTextVal);
		thresholdTextVal.setColumns(10);
		
		 allColorsBox = new JCheckBox("All colors");
		allColorsBox.setSelected(true);
		allColorsBox.setBounds(181, 54, 97, 23);
		getContentPane().add(allColorsBox);
		
		noneButton = new JRadioButton("None");
		noneButton.setSelected(true);
		noneButton.setBounds(6, 110, 54, 23);
		getContentPane().add(noneButton);
		
		filtersLabel = new JLabel("Filters:");
		filtersLabel.setBounds(6, 89, 46, 14);
		getContentPane().add(filtersLabel);
		
		bwButton = new JRadioButton("B&W");
		bwButton.setBounds(62, 110, 54, 23);
		getContentPane().add(bwButton);
		
		negativeButton = new JRadioButton("Negative");
		negativeButton.setBounds(118, 110, 77, 23);
		getContentPane().add(negativeButton);
		
		smoothButton = new JRadioButton("Smooth");
		smoothButton.setBounds(197, 110, 69, 23);
		getContentPane().add(smoothButton);
		
		filtersButtons = new ButtonGroup();
		filtersButtons.add(noneButton);
		filtersButtons.add(bwButton);
		filtersButtons.add(negativeButton);
		filtersButtons.add(smoothButton);
	}

	/**
	 * Method that retrieves the JTextField for the threshold (inserted by the user) that is going to be applied at the image imported into the new main panel.
	 * @return thresholdTextVal JTextField.
	 */
	public JTextField getThresholdTextVal() {
		return thresholdTextVal;
	}
	
	/**
	 * Method that retrieves the JCheckBox for the all colors option (inserted by the user) that is going to be applied to the image imported into the new main panel.
	 * @return allColorsBox JCheckBox
	 */
	public JCheckBox getAllColorsBox() {
		return allColorsBox;
	}
	
	/**
	 * Method that retrieves the JButton for the "import" action. 
	 * @return importButton JButton 
	 */
	public JButton getImportButton() {
		return importButton;
	}

	/**
	 * Method that retrieves the JButton for the "convert" action.
	 * @return convertButton.
	 */
	public JButton getConvertButton() {
		return convertButton;
	}
	
	/**
	 * Method that retrieves the none JRadioButton.
	 * @return noneButton JRadioButton.
	 */
	public JRadioButton getNoneButton() {
		return noneButton;
	}

	/**
	 * Method that retrieves the Bw JRadioButton.
	 * @return BwButton JRadioButton.
	 */
	public JRadioButton getBwButton() {
		return bwButton;
	}

	/**
	 * Method that retrieves the negative JRadioButton.
	 * @return negativeButton JRadioButton.
	 */
	public JRadioButton getNegativeButton() {
		return negativeButton;
	}

	/**
	 * Method that retrieves the smooth JRadioButton.
	 * @return smoothButton JRadioButton.
	 */
	public JRadioButton getSmoothButton() {
		return smoothButton;
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