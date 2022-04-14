package image_editor.view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * View for the "ASCII ImageEditor - CharacterSelector" window
 * @author danie
 *
 */
public class CharacterSelector extends JFrame{

	/**
	 * ID.
	 */
	private static final long serialVersionUID = -3046096625552352801L;
	
	/**
	 * List of Jbuttons that correspond to every single char available in the palette of ascii chars
	 */
	private List<JButton> asciiCharsPalette;
	
	/**
	 * SINGLETON DESIGNPATTERN - Instance of the CharacterSelector, so it can be only one window at the time.
	 */
	private static CharacterSelector instance;
	
	/**
	 * Method that creates an instance of the CharacterSelector if doesn't exist,
	 * if it does retrieves it back (singleton).
	 * @return Instance of CharacterSelector
	 */
	public static CharacterSelector getInstance() {
		if (instance == null)
			instance = new CharacterSelector();
		return instance;
	}

	/**
	 * Private Constructor for the class CharacterSelector so the class can be instantiated only once at a time (singleton design pattern), it set's all the content position, bounds of
	 * the window ("ASCII ImageEditor - CharacterSelector").  
	 */
	private CharacterSelector() {
		super("ASCII ImageEditor - CharacterSelector");
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.asciiCharsPalette = new ArrayList<JButton>();
		this.setBounds(0, 0, 16 * 17 , (int) (16 * 18.5));
		this.setResizable(false);
		this.setLayout(null);
	}
	
	/**
	 * Method that retrieves a List of JButtons containing all the possible ascii chars to use.
	 * @return asciiCharsPalette List of JButton. 
	 */
	public List<JButton> getAsciiCharsPalette(){
		return asciiCharsPalette;
	}
	
	/**
	 * Method that set's all the buttons with the ascii chars (buffered images) .
	 * @param glyphs BufferedImage[].
	 */
	public void setAsciiCharsPalette(BufferedImage[] glyphs) {
		for (int i = 0; i < glyphs.length; i++) {
			int x = i % 16;
			int y = i / 16;
			JButton g = new JButton(new ImageIcon(glyphs[i]));
			asciiCharsPalette.add(g);
			this.add(g);
			g.setBounds(x * 16, y * 16, 16, 16);
		}
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
