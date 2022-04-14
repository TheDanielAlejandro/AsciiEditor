package image_editor.view;

import java.awt.Color;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * View for the "ASCII ImageEditor - Select Color Changer" window.
 * @author danie
 *
 */
public class SelectColorChanger extends JFrame{
	
	/**
	 * ID.
	 */
	private static final long serialVersionUID = -3046096625552352801L;
	
	/**
	 * JButton for the action "apply" in the "select color changer" window.
	 */
	private JButton applyButton;
	
	/**
	 * JPanel for the foreground color.
	 */
	private JPanel foregroundPanel;
	
	/**
	 * JPanel for the background color.
	 */
	private JPanel backgroundPanel;
	
	/**
	 * SINGLETON DESIGNPATTERN - Instance of the SelectColorChanger, so it can be only one window at the time.
	 */
	private static SelectColorChanger instance;
	
	/**
	 * Method that creates an instance of the SelectColorChanger if doesn't exist,
	 * if it does retrieves it back (singleton).
	 * @return Instance of SelectColorChanger.
	 */
	public static SelectColorChanger getInstance() {
		if (instance == null)
			instance = new SelectColorChanger();
		return instance;
	}
	
	/**
	 * Private Constructor for the class SelectColorChanger so the class can be instantiated only once at a time (singleton design pattern), it set's all the content position, bounds of
	 * the window ("Select Color Changer").  
	 */
	private SelectColorChanger() {
		super("Select Color Changer");
		setResizable(false);
		setBounds(100, 100, 200, 150);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		applyButton = new JButton("Apply");
		applyButton.setBounds(10, 77, 164, 23);
		getContentPane().add(applyButton);
		
		JLabel foregorundLabel = new JLabel("Foreground:");
		foregorundLabel.setBounds(10, 11, 75, 14);
		getContentPane().add(foregorundLabel);
		
		JLabel BackgroundLabel = new JLabel("Background:");
		BackgroundLabel.setBounds(10, 48, 75, 14);
		getContentPane().add(BackgroundLabel);
		
		foregroundPanel = new JPanel();
		foregroundPanel.setBounds(109, 11, 44, 23);
		foregroundPanel.setBackground(Color.WHITE);
		getContentPane().add(foregroundPanel);
		
		backgroundPanel = new JPanel();
		backgroundPanel.setBounds(109, 48, 44, 23);
		backgroundPanel.setBackground(Color.BLACK);
		getContentPane().add(backgroundPanel);
	}

	/**
	 * Method that retrieves the applyButton from the SelectColorChanger view.
	 * @return applyButton JButton.
	 */
	public JButton getApplyButton() {
		return applyButton;
	}

	/**
	 * Method that retrieves the foregroundPanel from the SelectColorChanger view.
	 * @return foregroundPanel JPanel.
	 */
	public JPanel getForegroundPanel() {
		return foregroundPanel;
	}

	/**
	 * Method that retrieves the backgroundPanel from the SelectColorChanger view.
	 * @return backgroundPanel JPanel.
	 */
	public JPanel getBackgroundPanel() {
		return backgroundPanel;
	}

}
