package ascii_panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Class that creates a Panel for the ascii chars in the ImageEditor application.
 * @author danie
 *
 */
public class AsciiPanel extends JPanel {
	
	/**
	 * ID.
	 */
	private static final long serialVersionUID = -4167851861147593092L;

    /**
     * The color black (pure black).
     */
    public static Color black = new Color(0, 0, 0);

    /**
     * The color red.
     */
    public static Color red = new Color(128, 0, 0);

    /**
     * The color green.
     */
    public static Color green = new Color(0, 128, 0);

    /**
     * The color yellow.
     */
    public static Color yellow = new Color(128, 128, 0);

    /**
     * The color blue.
     */
    public static Color blue = new Color(0, 0, 128);

    /**
     * The color magenta.
     */
    public static Color magenta = new Color(128, 0, 128);

    /**
     * The color cyan.
     */
    public static Color cyan = new Color(0, 128, 128);

    /**
     * The color white (light gray).
     */
    public static Color white = new Color(192, 192, 192);

    /**
     * A brighter black (dark gray).
     */
    public static Color brightBlack = new Color(128, 128, 128);

    /**
     * A brighter red.
     */
    public static Color brightRed = new Color(255, 0, 0);

    /**
     * A brighter green.
     */
    public static Color brightGreen = new Color(0, 255, 0);

    /**
     * A brighter yellow.
     */
    public static Color brightYellow = new Color(255, 255, 0);

    /**
     * A brighter blue.
     */
    public static Color brightBlue = new Color(0, 0, 255);

    /**
     * A brighter magenta.
     */
    public static Color brightMagenta = new Color(255, 0, 255);

    /**
     * A brighter cyan.
     */
    public static Color brightCyan = new Color(0, 255, 255);
    
    /**
     * A brighter white (pure white).
     */
    public static Color brightWhite = new Color(255, 255, 255);

    private Image offscreenBuffer;
    private Graphics offscreenGraphics;
    
    /**
     * Width dimension in characters for the ascii characters.
     */
    private int widthInCharacters;
    
    /**
     * Height dimension in characters for the ascii characters.
     */
    private int heightInCharacters;
    
    /**
     * Width dimension in pixels for the ascii characters.
     */
    private int charWidth = 9;
    
    /**
     * Height dimension in pixels for the ascii characters.
     */
    private int charHeight = 16;
    
    /**
     * Default font file.
     */
    private String terminalFontFile = "cp437_9x16.png";
    
    /**
     * Characters default background colour.
     */
    private Color defaultBackgroundColor;
    
    /**
     * Characters default foreground colour.
     */
    private Color defaultForegroundColor;
    
    /**
     * X coordinate for the next text position.
     */
    private int cursorX;
    
    /**
     * Y coordinate for the next text position.
     */
    private int cursorY;
    
    /**
     * Buffered Image of the font file.
     */
    private BufferedImage glyphSprite;
    
    /**
     * Array of buffered Images containing each ascii char extracted from the font file.
     */
    private BufferedImage[] glyphs;
    
    /**
     * Matrix of chars. 
     */
    private char[][] chars;
    
    /**
     * Matrix of background colours.
     */
    private Color[][] backgroundColors;
    
    /**
     * Matrix of foreground colours.
     */
    private Color[][] foregroundColors;
    
    /**
     * Matrix of chars.
     */
    private char[][] oldChars;
    
    /**
     * Matrix of background colours.
     */
    private Color[][] oldBackgroundColors;
    
    /**
     * Matrix of foreground colours.
     */
    private Color[][] oldForegroundColors;
    
    /**
     * Instance of AsciiFont for the font to use.
     */
    private AsciiFont asciiFont;
    
    /**
     * Coordinates x,y of the cursor.
     */
    private int mcursorx,mcursory;
    
    /**
     * Instance never used in the project.
     */
    private int cursorID=4;
    
    
    /**
     * Method that returns an array of buffered images that are the "glyphs" or ascii chars that can be used into the ascii panel.  
     * 
     * @return array of buffered images. 
     */
    public BufferedImage[] getGlyphs()
    {
    	return glyphs;
    }
    
    
    /**
     * Gets the height, in pixels, of a character.
     * @return charHeight in pixels, int.
     */
    public int getCharHeight() {
        return charHeight;
    }

    /**
     * Gets the width, in pixels, of a character.
     * @return charWidt in pixels, int.
     */
    public int getCharWidth() {
        return charWidth;
    }

    /**
     * Gets the height in characters.
     * A standard terminal is 24 characters high.
     * @return int, height in characters.
     */
    public int getHeightInCharacters() {
        return heightInCharacters;
    }

    /**
     * Gets the width in characters.
     * A standard terminal is 80 characters wide.
     * @return int, width in characters.
     */
    public int getWidthInCharacters() {
        return widthInCharacters;
    }

    /**
     * Gets the distance from the left new text will be written to.
     * @return int, X coordinate.
     */
    public int getCursorX() {
        return cursorX;
    }

    /**
     * Sets the distance from the left new text will be written to.
     * This should be equal to or greater than 0 and less than the the width in characters.
     * @param cursorX the distance from the left new text should be written to
     */
    public void setCursorX(int cursorX) {
        if (cursorX >= 0 && cursorX < widthInCharacters)
      //      throw new IllegalArgumentException("cursorX " + cursorX + " must be within range [0," + widthInCharacters + ")." );

        this.cursorX = cursorX;
    }

    /**
     * Gets the distance from the top new text will be written to.
     * @return int, Y coordinate.
     */
    public int getCursorY() {
        return cursorY;
    }

    /**
     * Sets the distance from the top new text will be written to.
     * This should be equal to or greater than 0 and less than the the height in characters.
     * @param cursorY the distance from the top new text should be written to
     */
    public void setCursorY(int cursorY) {
        if (cursorY >= 0 && cursorY < heightInCharacters)
           
        this.cursorY = cursorY;
    }

    /**
     * Sets the x and y position of where new text will be written to. The origin (0,0) is the upper left corner.
     * The x should be equal to or greater than 0 and less than the the width in characters.
     * The y should be equal to or greater than 0 and less than the the height in characters.
     * @param x the distance from the left new text should be written to
     * @param y the distance from the top new text should be written to
     */
    public void setCursorPosition(int x, int y) {
        setCursorX(x);
        setCursorY(y);
    }

    /**
     * Gets the default background color that is used when writing new text.
     * @return Color for the background of chars.
     */
    public Color getDefaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    /**
     * Sets the default background color that is used when writing new text.
     * @param defaultBackgroundColor Color.
     */
    public void setDefaultBackgroundColor(Color defaultBackgroundColor) {
        if (defaultBackgroundColor == null)
            throw new NullPointerException("defaultBackgroundColor must not be null.");

        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    /**
     * Gets the default foreground color that is used when writing new text.
     * @return Color for the foreground of chars.
     */
    public Color getDefaultForegroundColor() {
        return defaultForegroundColor;
    }

    /**
     * Sets the default foreground color that is used when writing new text.
     * @param defaultForegroundColor Color foreground.
     */
    public void setDefaultForegroundColor(Color defaultForegroundColor) {
        if (defaultForegroundColor == null)
            throw new NullPointerException("defaultForegroundColor must not be null.");

        this.defaultForegroundColor = defaultForegroundColor;
    }

    /**
     * Gets the currently selected font.
     * @return asciiFont in use.
     */
    public AsciiFont getAsciiFont() {
        return asciiFont;
    }

    /**
     * Sets the used font. It is advisable to make sure the parent component is properly sized after setting the font
     * as the panel dimensions will most likely change
     * @param font to use.
     */
    public void setAsciiFont(AsciiFont font)
    {
        if(this.asciiFont == font)
        {
            return;
        }
        this.asciiFont = font;

        this.charHeight = font.getHeight();
        this.charWidth = font.getWidth();
        this.terminalFontFile = font.getFontFilename();

        Dimension panelSize = new Dimension(charWidth * widthInCharacters, charHeight * heightInCharacters);
        setPreferredSize(panelSize);

        glyphs = new BufferedImage[256];

        offscreenBuffer = new BufferedImage(panelSize.width, panelSize.height, BufferedImage.TYPE_INT_RGB);
        offscreenGraphics = offscreenBuffer.getGraphics();

        loadGlyphs();

        oldChars = new char[widthInCharacters][heightInCharacters];
    }

    /**
     * Class constructor.
     * Default size is 80x24.
     */
    public AsciiPanel() {
        this(80, 24);
    }

    /**
     * Class constructor specifying the width and height in characters.
     * @param width in characters.
     * @param height in characters.
     */
    public AsciiPanel(int width, int height) {
    	this(width, height, null);
    }
    
    /**
     * Class constructor specifying the width and height in characters and the AsciiFont
     * @param width in characters.
     * @param height in characters.
     * @param font if passing null, standard font CP437_9x16 will be used
     */
    public AsciiPanel(int width, int height, AsciiFont font) {
        super();

        if (width < 1) {
            throw new IllegalArgumentException("width " + width + " must be greater than 0." );
        }

        if (height < 1) {
            throw new IllegalArgumentException("height " + height + " must be greater than 0." );
        }

        widthInCharacters = width;
        heightInCharacters = height;

        defaultBackgroundColor = black;
        defaultForegroundColor = white;

        setChars(new char[widthInCharacters][heightInCharacters]);
        backgroundColors = new Color[widthInCharacters][heightInCharacters];
        foregroundColors = new Color[widthInCharacters][heightInCharacters];

        oldBackgroundColors = new Color[widthInCharacters][heightInCharacters];
        oldForegroundColors = new Color[widthInCharacters][heightInCharacters];

        if(font == null) {
        	font = AsciiFont.CP437_9x16;
        }
        setAsciiFont(font);
    }
    
    /**
     * Method that calls the method paint
     * @see AsciiPanel#paint(Graphics)
     */
    @Override
    public void update(Graphics g) {
         paint(g); 
    } 

    /**
     * Method that paints the whole asciiPanel with the two matrixes: 
     * backgroundColors and foregroundColors comparing them to the one already existent.
     * 
     */
    @Override
    public void paint(Graphics g) {
        if (g == null)
            throw new NullPointerException();

        for (int x = 0; x < widthInCharacters; x++) {
            for (int y = 0; y < heightInCharacters; y++) {
            	if (oldBackgroundColors[x][y] == backgroundColors[x][y]
            	 && oldForegroundColors[x][y] == foregroundColors[x][y]
            	 && oldChars[x][y] == getChars()[x][y])
            		continue;
            	
                Color bg = backgroundColors[x][y];
                Color fg = foregroundColors[x][y];
                if (bg==null) bg=Color.BLACK;
                if (fg==null) fg=Color.BLACK;
                
                LookupOp op = setColors(bg, fg);
                BufferedImage img = op.filter(glyphs[getChars()[x][y]], null);
                offscreenGraphics.drawImage(img, x * charWidth, y * charHeight, null);
                
                oldBackgroundColors[x][y] = backgroundColors[x][y];
        	    oldForegroundColors[x][y] = foregroundColors[x][y];
        	    oldChars[x][y] = getChars()[x][y];
            }
        }
        g.drawImage(offscreenBuffer,0,0,this);
    }
    
    /**
     * Method that allows to save the current AsciiPanel into the file system.
     * @param filename path of the file AsciiPanel. 
     */
    public void save(String filename)
    {
    	BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(filename));
			bw.write(widthInCharacters+"\n");
			bw.write(heightInCharacters+"\n");
			
			for (int x = 0; x < widthInCharacters; x++) {
		            for (int y = 0; y < heightInCharacters; y++) {
		            	Color fg=foregroundColors[x][y];
		            	Color bg=backgroundColors[x][y];
		            	
		            	if (fg==null) fg=Color.black;
		            	if (bg==null) bg=Color.black;
		            	
		            	
		            	bw.write((int)getChars()[x][y]
		            			+"\t"+fg.getRGB()
		            			+"\t"+bg.getRGB()+"\n");
		            	}
		            }
			bw.close();		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error saving:");
			System.exit(1);
		}
    	
    }
    
    /**
     * Method that allows to load an existent compatible file an asciiPanel. 
     * @param filename Path of the file.
     */
	public void load(String filename) {
		AsciiRaster img=AsciiRaster.fromFile(filename);
		this.paintRaster(img, 0, 0, false);
		repaint();
	}
    
    /**
     * Method that extracts all the single chars from the palette.
     */
    private void loadGlyphs() {
        try {
            glyphSprite = ImageIO.read(AsciiPanel.class.getClassLoader().getResource(terminalFontFile));
        } catch (IOException e) {
            System.err.println("loadGlyphs(): " + e.getMessage());
        }

        for (int i = 0; i < 256; i++) {
            int sx = (i % 16) * charWidth;
            int sy = (i / 16) * charHeight;

            glyphs[i] = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
            glyphs[i].getGraphics().drawImage(glyphSprite, 0, 0, charWidth, charHeight, sx, sy, sx + charWidth, sy + charHeight, null);
        }
    }
        
    /**
     * Create a <code>LookupOp</code> object (lookup table) mapping the original 
     * pixels to the background and foreground colors, respectively. 
     * @param bgColor the background color
     * @param fgColor the foreground color
     * @return the <code>LookupOp</code> object (lookup table)
     */
    private LookupOp setColors(Color bgColor, Color fgColor) {
        short[] a = new short[256];
        short[] r = new short[256];
        short[] g = new short[256];
        short[] b = new short[256];

        byte bga = (byte) (bgColor.getAlpha());
        byte bgr = (byte) (bgColor.getRed());
        byte bgg = (byte) (bgColor.getGreen());
        byte bgb = (byte) (bgColor.getBlue());

        byte fga = (byte) (fgColor.getAlpha());
        byte fgr = (byte) (fgColor.getRed());
        byte fgg = (byte) (fgColor.getGreen());
        byte fgb = (byte) (fgColor.getBlue());

        for (int i = 0; i < 256; i++) {
            if (i == 0) {
                a[i] = bga;
                r[i] = bgr;
                g[i] = bgg;
                b[i] = bgb;
            } else {
                a[i] = fga;
                r[i] = fgr;
                g[i] = fgg;
                b[i] = fgb;
            }
        }

        short[][] table = {r, g, b, a};
        return new LookupOp(new ShortLookupTable(0, table), null);
    }

    /**
     * Clear the entire screen to whatever the default background color is.
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel clear() {
        return clear(' ', 0, 0, widthInCharacters, heightInCharacters, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Clear the entire screen with the specified character and whatever the default foreground and background colors are.
     * @param character  the character to write
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel clear(char character) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

        return clear(character, 0, 0, widthInCharacters, heightInCharacters, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Clear the entire screen with the specified character and whatever the specified foreground and background colors are.
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel clear(char character, Color foreground, Color background) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

        return clear(character, 0, 0, widthInCharacters, heightInCharacters, foreground, background);
    }

    /**
     * Clear the section of the screen with the specified character and whatever the default foreground and background colors are.
     * The cursor position will not be modified.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param width      the height of the section to clear
     * @param height     the width of the section to clear
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel clear(char character, int x, int y, int width, int height) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")." );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")." );

        if (width < 1)
            throw new IllegalArgumentException("width " + width + " must be greater than 0." );

        if (height < 1)
            throw new IllegalArgumentException("height " + height + " must be greater than 0." );

        if (x + width > widthInCharacters)
            throw new IllegalArgumentException("x + width " + (x + width) + " must be less than " + (widthInCharacters + 1) + "." );

        if (y + height > heightInCharacters)
            throw new IllegalArgumentException("y + height " + (y + height) + " must be less than " + (heightInCharacters + 1) + "." );


        return clear(character, x, y, width, height, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Clear the section of the screen with the specified character and whatever the specified foreground and background colors are.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param width      the height of the section to clear
     * @param height     the width of the section to clear
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel clear(char character, int x, int y, int width, int height, Color foreground, Color background) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        if (width < 1)
            throw new IllegalArgumentException("width " + width + " must be greater than 0." );

        if (height < 1)
            throw new IllegalArgumentException("height " + height + " must be greater than 0." );

        if (x + width > widthInCharacters)
            throw new IllegalArgumentException("x + width " + (x + width) + " must be less than " + (widthInCharacters + 1) + "." );

        if (y + height > heightInCharacters)
            throw new IllegalArgumentException("y + height " + (y + height) + " must be less than " + (heightInCharacters + 1) + "." );

        int originalCursorX = cursorX;
        int originalCursorY = cursorY;
        for (int xo = x; xo < x + width; xo++) {
            for (int yo = y; yo < y + height; yo++) {
                write(character, xo, yo, foreground, background);
            }
        }
        cursorX = originalCursorX;
        cursorY = originalCursorY;

        return this;
    }

    /**
     * Write a character to the cursor's position.
     * This updates the cursor's position.
     * @param character  the character to write
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character) {
        if (character < 0 || character > glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

        return write(character, cursorX, cursorY, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a character to the cursor's position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character, Color foreground) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

        return write(character, cursorX, cursorY, foreground, defaultBackgroundColor);
    }

    /**
     * Write a character to the cursor's position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character, Color foreground, Color background) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

        return write(character, cursorX, cursorY, foreground, background);
    }

    /**
     * Write a character to the specified position.
     * This updates the cursor's position.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character, int x, int y) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        return write(character, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a character to the specified position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character, int x, int y, Color foreground) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        return write(character, x, y, foreground, defaultBackgroundColor);
    }

    /**
     * Write a character to the specified position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character, int x, int y, Color foreground, Color background) {
        
    	if (foreground == null) foreground = defaultForegroundColor;
        if (background == null) background = defaultBackgroundColor;

    	if (character >= 0 && character < glyphs.length)
      //      throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

        if (x >= 0 && x < widthInCharacters)
        //    throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

        if (y >= 0 && y < heightInCharacters)
          //  throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );
        {
        
        chars[x][y] = character;
        foregroundColors[x][y] = foreground;
        backgroundColors[x][y] = background;
        cursorX = x + 1;
        cursorY = y;
        }
        return this;
    }
    
    
    /**
     * Write a character in everysingle space in the whole panel.
     * This doesn't updates the default foreground or background colors.
     * @param character char, ascii char.
     * @param x int, x coordinate.
     * @param y int, y coordinate.
     * @param fc Color, foreground.
     * @param bc Color, background
     * @return AsciiPanel updated.
     */
    public AsciiPanel fill(char character,int x,int y,Color fc,Color bc) {
        
    
    	char oldchar=getChars()[x][y];
    	if (oldchar!=character)
    	{
        getChars()[x][y] = character;
        foregroundColors[x][y] = fc;
        backgroundColors[x][y] = bc;
        int diff=0;
        if (x<widthInCharacters-1)
        {
        diff=Math.abs(getChars()[x+1][y]-oldchar);
        if (diff>=0&&diff<=3)
        	fill(character,x+1,y,fc,bc);
        }
        if (x>0)
        {
        diff=Math.abs(getChars()[x-1][y]-oldchar);
        if (diff>=0&&diff<=3)
        	fill(character,x-1,y,fc,bc);
        }
        if (y>0)
        {
        diff=Math.abs(getChars()[x][y-1]-oldchar);
        if (diff>=0&&diff<=3)
        	fill(character,x,y-1,fc,bc);
        }
        if (y<heightInCharacters-1)
        {
        diff=Math.abs(getChars()[x][y+1]-oldchar);
        if (diff>=0&&diff<=3)
        	fill(character,x,y+1,fc,bc);
        }
    	}
        return this;
    }
    
    /**
     * Write a string to the cursor's position.
     * This updates the cursor's position.
     * @param string     the string to write
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (cursorX + string.length() > widthInCharacters)
            throw new IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length()) + " must be less than " + widthInCharacters + ".");

        return write(string, cursorX, cursorY, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a string to the cursor's position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param string     the string to write
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string, Color foreground) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (cursorX + string.length() > widthInCharacters)
            throw new IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length()) + " must be less than " + widthInCharacters + "." );

        return write(string, cursorX, cursorY, foreground, defaultBackgroundColor);
    }

    /**
     * Write a string to the cursor's position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param string     the string to write
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string, Color foreground, Color background) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (cursorX + string.length() > widthInCharacters)
            throw new IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length()) + " must be less than " + widthInCharacters + "." );

        return write(string, cursorX, cursorY, foreground, background);
    }

    /**
     * Write a string to the specified position.
     * This updates the cursor's position.
     * @param string     the string to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string, int x, int y) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (x + string.length() > widthInCharacters)
            throw new IllegalArgumentException("x + string.length() " + (x + string.length()) + " must be less than " + widthInCharacters + "." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        return write(string, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a string to the specified position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param string     the string to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string, int x, int y, Color foreground) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (x + string.length() > widthInCharacters)
            throw new IllegalArgumentException("x + string.length() " + (x + string.length()) + " must be less than " + widthInCharacters + "." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        return write(string, x, y, foreground, defaultBackgroundColor);
    }

    /**
     * Write a string to the specified position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param string     the string to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string, int x, int y, Color foreground, Color background) {
        if (string == null)
            throw new NullPointerException("string must not be null." );
        
        if (x + string.length() > widthInCharacters)
            throw new IllegalArgumentException("x + string.length() " + (x + string.length()) + " must be less than " + widthInCharacters + "." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")." );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")." );

        if (foreground == null)
            foreground = defaultForegroundColor;

        if (background == null)
            background = defaultBackgroundColor;

        for (int i = 0; i < string.length(); i++) {
            write(string.charAt(i), x + i, y, foreground, background);
        }
        return this;
    }

    /**
     * Write a string to the center of the panel at the specified y position.
     * This updates the cursor's position.
     * @param string     the string to write
     * @param y          the distance from the top to begin writing from
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel writeCenter(String string, int y) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (string.length() > widthInCharacters)
            throw new IllegalArgumentException("string.length() " + string.length() + " must be less than " + widthInCharacters + "." );

        int x = (widthInCharacters - string.length()) / 2;

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        return write(string, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a string to the center of the panel at the specified y position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     * @param string     the string to write
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel writeCenter(String string, int y, Color foreground) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (string.length() > widthInCharacters)
            throw new IllegalArgumentException("string.length() " + string.length() + " must be less than " + widthInCharacters + "." );

        int x = (widthInCharacters - string.length()) / 2;

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        return write(string, x, y, foreground, defaultBackgroundColor);
    }

    /**
     * Write a string to the center of the panel at the specified y position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param string     the string to write
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel writeCenter(String string, int y, Color foreground, Color background) {
        if (string == null)
            throw new NullPointerException("string must not be null." );

        if (string.length() > widthInCharacters)
            throw new IllegalArgumentException("string.length() " + string.length() + " must be less than " + widthInCharacters + "." );

        int x = (widthInCharacters - string.length()) / 2;
        
        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")." );

        if (foreground == null)
            foreground = defaultForegroundColor;

        if (background == null)
            background = defaultBackgroundColor;

        for (int i = 0; i < string.length(); i++) {
            write(string.charAt(i), x + i, y, foreground, background);
        }
        return this;
    }
    /**
     *  Method that transform the tile selected with a new one passing the TileTransformer.
     * @param transformer transformer.
     */
    public void withEachTile(TileTransformer transformer){
		withEachTile(0, 0, widthInCharacters, heightInCharacters, transformer);
    }
    
    /**
     * Method that transform the tile selected with a new one passing the coordinates, dimensions and the TileTransformer.
     * @param left coordinate for the x
     * @param top coordinate for the y
     * @param width value for the width
     * @param height value for the height
     * @param transformer transformer.
     */
    public void withEachTile(int left, int top, int width, int height, TileTransformer transformer){
		AsciiCharacterData data = new AsciiCharacterData();
		
    	for (int x0 = 0; x0 < width; x0++)
    	for (int y0 = 0; y0 < height; y0++){
    		int x = left + x0;
    		int y = top + y0;
    		
    		if (x < 0 || y < 0 || x >= widthInCharacters || y >= heightInCharacters)
    			continue;
    		
    		data.character = getChars()[x][y];
    		data.foregroundColor = foregroundColors[x][y];
    		data.backgroundColor = backgroundColors[x][y];
    		
    		transformer.transformTile(x, y, data);
    		
    		getChars()[x][y] = data.character;
    		foregroundColors[x][y] = data.foregroundColor;
    		backgroundColors[x][y] = data.backgroundColor;
    	}
    }
    
    /**
     * Method that retrieves the x coordinate of the mouse cursor
     * @return int, x coordinate.
     */
	public int getMouseCursorX() {
		return mcursorx;
	}
	
	/**
	 * Method that retrieves the y coordinate of the mouse cursor
	 * @return int, y coordinate.
	 */
	public int getMouseCursorY() {
		return mcursory;
	}
	
	/**
	 * Method that sets the x coordinate of the mouse.
	 * @param x coordinate, int.
	 */
	public void setMouseCursorX(int x) {
		mcursorx=x;
	}
	
	/**
	 * Method that sets the y coordinate of the mouse.
	 * @param y coordinate, int.
	 */
	public void setMouseCursorY(int y) {
		mcursory=y;
	}
	
	/**
	 * Method that retrieves the char selected by the action pick from the position cursor.
	 * @param px x coordinate, int.
	 * @param py y coordinate, int.
	 * @return Integer char number.
	 */
	public Integer pickChar(int px, int py) {
		return new Integer(getChars()[px][py]);
	}
	
	/**
	 * Method that retrieves the foreground colour from the position cursor.
	 * @param px x coordinate, int.
	 * @param py y coordinate, int.
	 * @return Color foreground colour.
	 */
	public Color pickFC(int px, int py) {
		return foregroundColors[px][py];
	}
	
	/**
	 * Method that retrieves the background colour from the position cursor.
	 * @param px x coordinate, int.
	 * @param py y coordinate, int.
	 * @return Color background colour.
	 */
	public Color pickBC(int px, int py) {
		return backgroundColors[px][py];
	}
	
	/**
	 * Method that paints the ascii panel with a Raster.
	 * @param raster Raster for the ascii panel.
	 * @param x x coordinate to set the raster.
	 * @param y y coordinate to set the raster.
	 * @param transparent boolean for the colors.
	 * @return AsciiPanel
	 */
	public AsciiPanel paintRaster(AsciiRaster raster,int x,int y,boolean transparent)
	{
		/*if (raster==null)
		{
			System.out.println("Raster null!");
			System.exit(1);
			
		}*/
		int dx=0;
		int dy=0;
		if (x<0) dx=-x;
		if (y<0) dy=-y;
		int sx=dx;
		int sy=dy;
		for (int xi=x+dx;xi<widthInCharacters;xi++)
		{
			sy=dy;
			for (int yi=y+dy;yi<heightInCharacters;yi++)
			{
				
				char nc=raster.getChars()[sx][sy];
					
				if (nc!=0||!transparent)
				{
					getChars()[xi][yi]=nc;
					foregroundColors[xi][yi]=raster.getForecolors()[sx][sy];
					backgroundColors[xi][yi]=raster.getBackcolors()[sx][sy];
				}
				sy++;
				if (sy==raster.getSy()) break;
			}
		sx++;
		if (sx==raster.getSx()) break;
			
		}
		return this;
	}
	
	/**
	 * Method that retrieves the matrix of chars.
	 * @return chars[][]
	 */
	public char[][] getChars() {
		return chars;
	}
	
	/**
	 * Method that sets the matrix of chars
	 * @param chars matrix of chars.
	 */
	public void setChars(char[][] chars) {
		this.chars = chars;
	}
}
