package image_editor.model;

import java.awt.Color;

/**
 * Class that defines all the basic informations of the ascii chars.
 * @author danie
 *
 */
public class AsciiChar {
	
	/**
	 * Integer value of the ascii char.
	 */
	private int id;
	
	/**
	 * Background colour.
	 */
	private Color backgroundColor;
	
	/**
	 * Foreground colour.
	 */
	private Color foregroundColor;
	
	/**
	 * Constructor For the Ascii char.
	 * @param id int.
	 * @param back Color. 
	 * @param fore Color.
	 */
	public AsciiChar(int id, Color back, Color fore) {
		
		this.id = id;
		this.backgroundColor = back;
		this.foregroundColor = fore;
		
	}

	/**
	 * Method that retrieves the id of the ascii char.
	 * @return id int.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method that sets' the id of the ascii char (from 0 to 255).
	 * @param id int.
	 */
	public void setId(int id) {
		if(id >=0 && id < 256)this.id = id;
	}

	/**
	 * Method that retrieves the background color of the ascii char.
	 * @return backgroundColor Color.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Method that set's the background color of the ascii char.
	 * @param backgroundColor Color.
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Method that retrieves the foreground colour of the ascii char.
	 * @return foregroundColor Color.
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}
	
	/**
	 * Method that set's the foreground color for the ascii char.
	 * @param foregroundColor Color.
	 */
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}
	
}
