package ascii_panel;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class that allows to create an Ascii raster, a grid of ascii chars.
 * @author danie
 *
 */
public class AsciiRaster 
{
	/**
	 * Matrix of chars.
	 */
	private char[][] chars;

	/**
	 * Matrix of colours for the foreground.
	 */
	private Color[][] forecolors;
	
	/**
	 * Matrix of colours for the background.
	 */
	private Color[][] backcolors;
	
	/**
	 * Width dimension.
	 */
	private int sx;
	
	/**
	 * Height dimension.
	 */
	private int sy;
	
	/**
	 * Constructor for the raster.
	 * @param sx int, x coordinate.
	 * @param sy int, y coordinate.
	 */
	public AsciiRaster(int sx,int sy)
	{
		setSx(sx);
		setSy(sy);
		setChars(new char[sx][sy]);
		setForecolors(new Color[sx][sy]);
		setBackcolors(new Color[sx][sy]);
	}

	/**
	 * Method that retrieves the dimension for the width of the AsciiRaster.
	 * @return sx width of the AsciiRaster.
	 */
	public int getSx() {
		return sx;
	}

	/**
	 * Method that sets the dimension for the width of the asciiRaster.
	 * @param sx width of the AsciiRaster.
	 */
	public void setSx(int sx) {
		this.sx = sx;
	}
	
	/**
	 * Method that retrieves the dimension for the height of the AsciiRaster.
	 * @return sy height of the AsciRaster.
	 */
	public int getSy() {
		return sy;
	}
	
	/**
	 * Method that sets the dimension for the height of the asciiRaster.
	 * @param sy height of the AsciiRaster.
	 */
	public void setSy(int sy) {
		this.sy = sy;
	}

	/**
	 * Method that retrieves the matrix of chars.
	 * @return chars char[][].
	 */
	public char[][] getChars() {
		/*if (chars==null) 
		{
		
			System.out.println("raster chars null!");
			System.exit(1);
		}*/
		
		return chars;
	}
	
	/**
	 * Method that set's the matrix of chars.
	 * @param chars, chars[][].
	 */
	private void setChars(char[][] chars) {
		this.chars = chars;
	}
	
	/**
	 * Method that retrieves the matrix of foreground colours.
	 * @return forecolors, Color[][].
	 */
	public Color[][] getForecolors() {
		return forecolors;
	}
	
	/**
	 * Method that sets the matrix of foreground colours.
	 * @param forecolors, Color[][]
	 */
	private void setForecolors(Color[][] forecolors) {
		this.forecolors = forecolors;
	}
	
	/**
	 * Method that retrieves the matrix of background colours.
	 * @return backcolors, Color[][].
	 */
	public Color[][] getBackcolors() {
		return backcolors;
	}
	
	/**
	 * Method that sets the matrix of background colours.
	 * @param backcolors, Color[][]
	 */
	private void setBackcolors(Color[][] backcolors) {
		this.backcolors = backcolors;
	}

	/**
	 * Method that retrieves the AsciiRaster from a specific file.
	 * @param filename Path of the file
	 * @return AsciiRaster from the file selected
	 */
	public static AsciiRaster fromFile(String filename) {
		BufferedReader br;
		AsciiRaster res=null;
		try {
			br = new BufferedReader(new FileReader(filename));
			
			int sx=Integer.parseInt(br.readLine());
			int sy=Integer.parseInt(br.readLine());
			res=new AsciiRaster(sx,sy);
			int x=0;
			int y=0;
			while (br.ready())
			{
				//System.out.println("Loading x:"+x+"/sx:"+sx+" y:"+y+"/ sy:"+sy);
				String line=br.readLine();
				String[] lines=line.split("\t");
				Color fg=new Color(Integer.parseInt(lines[1]));
				Color bg=new Color(Integer.parseInt(lines[2]));
				char ch=(char)Integer.parseInt(lines[0]);
				res.getChars()[y][x]=ch;
				res.getForecolors()[y][x]=fg;
				res.getBackcolors()[y][x]=bg;
				x++;
				if (x==sy)
				{
					x=0;
					y++;
					if (y==sx) break;
				}
			}
			br.close();	
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return res;
	}
}
