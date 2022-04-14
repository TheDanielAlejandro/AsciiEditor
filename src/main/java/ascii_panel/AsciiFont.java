package ascii_panel;

/**
 * This class holds provides all available Fonts for the AsciiPanel.
 * Some graphics are from the Dwarf Fortress Tileset Wiki Page
 * 
 * @author zn80
 *
 */
public class AsciiFont {

	/**
	 * Font cp347 8x8.
	 */
	public static final AsciiFont CP437_8x8 = new AsciiFont("cp437_8x8.png", 8, 8);
	
	/**
	 * Font cp347 10x10.
	 */
	public static final AsciiFont CP437_10x10 = new AsciiFont("cp437_10x10.png", 10, 10);
	
	/**
	 * Font cp347 12x12.
	 */
	public static final AsciiFont CP437_12x12 = new AsciiFont("cp437_12x12.png", 12, 12);
	
	/**
	 * Font cp347 16x16.
	 */
	public static final AsciiFont CP437_16x16 = new AsciiFont("cp437_16x16.png", 16, 16);
	
	/**
	 * Font cp347 9x16.
	 */
	public static final AsciiFont CP437_9x16 = new AsciiFont("cp437_9x16.png", 9, 16);
	
	/**
	 * Font Drake 10x10.
	 */
	public static final AsciiFont DRAKE_10x10 = new AsciiFont("drake_10x10.png", 10, 10);
	
	/**
	 * Font Taffer 10x10.
	 */
	public static final AsciiFont TAFFER_10x10 = new AsciiFont("taffer_10x10.png", 10, 10);
	
	/**
	 * Font Qbicfeet 10x10.
	 */
	public static final AsciiFont QBICFEET_10x10 = new AsciiFont("qbicfeet_10x10.png", 10, 10);
	
	/**
	 * Font Talryth 15x15.
	 */
	public static final AsciiFont TALRYTH_15_15 = new AsciiFont("talryth_square_15x15.png", 15, 15);
	
	/**
	 * String for the font file name.
	 */
	private String fontFilename;

	/**
	 * Method to retrieve the font file name
	 * @return String, Font file name.
	 */
	public String getFontFilename() {
		return fontFilename;
	}

	/**
	 * Integer field for the width of the font.
	 */
	private int width;

	/**
	 * Method to retrieve the width of the font.
	 * @return width, int.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Integer field for the height of the font.
	 */
	private int height;

	/**
	 * Method that retrieves the height of the font.
	 * @return height, int.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Constructor for the AsciiFont class.
	 * @param filename String, name of the font.
	 * @param width int, width of the font.
	 * @param height int, height of the font.
	 */
	public AsciiFont(String filename, int width, int height) {
		this.fontFilename = filename;
		this.width = width;
		this.height = height;
	}
}
