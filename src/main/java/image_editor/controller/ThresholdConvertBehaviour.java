package image_editor.controller;
import java.awt.Color;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.util.HashMap;
import java.util.Map;

import image_editor.interfaces.ImageConvertStrategy;
import image_editor.model.AsciiChar;
import image_editor.model.Model;
import image_editor.view.ImageEditor;
import image_editor.view.ImageImporter;

public class ThresholdConvertBehaviour implements ImageConvertStrategy{
	
	@Override
	public void convertImage(ImageEditor IMAGE_EDITOR_VIEW, ImageImporter IMAGE_IMPORTER_VIEW, Model M) {
		AsciiChar hold = M.getCharInUse();
		int tool = M.getTool();
		M.setTool(11);
		BufferedImage bimage = M.getImportedImage();

		System.out.println("Convert Pressed..");
		if (bimage != null) {
			M.clearMatrix();
			int thi = Integer.parseInt(IMAGE_IMPORTER_VIEW.getThresholdTextVal().getText());
			BufferedImage[] glyphs = IMAGE_EDITOR_VIEW.mainPanel.getGlyphs();

			Map<Integer, Integer> index2numpixels = new HashMap<Integer, Integer>();
			for (int i = 0; i < glyphs.length; i++) {
				BufferedImage bi = glyphs[i];
				if (bi == null)
					continue;
				int tot = 0;
				for (int x = 0; x < bi.getWidth(); x++)
					for (int y = 0; y < bi.getHeight(); y++) {
						int c = bi.getRGB(x, y);
						Color cc = new Color(c, false);
						if (cc.getRed() > 0 || cc.getGreen() > 0 || cc.getBlue() > 0)
							tot++;
					}
				int key = (int) ((float) (tot) / (float) (bi.getWidth() * bi.getHeight()) * 255.f);
				if (!index2numpixels.containsKey(key))
					index2numpixels.put(key, i);

			}

			int[][] buffer = new int[bimage.getWidth()][bimage.getHeight()];
			// BufferedImage bi=ImageIO.read(new
			// File("resources/bitmaps/80x50Stefano.png"));
			for (int x = 0; x < bimage.getWidth(); x++)
				for (int y = 0; y < bimage.getHeight(); y++) {
					int c = bimage.getRGB(x, y);
					Color cc = new Color(c);
					int ri = Math.max(Math.max(cc.getRed(), cc.getGreen()), cc.getBlue());
					// if (cc.getRed()>0||cc.getGreen()>0||cc.getBlue()>0)
					buffer[x][y] = Math.min(ri, thi);
				}

			if (!IMAGE_IMPORTER_VIEW.getAllColorsBox().isSelected()) {
				BufferedImage ci = convert4(bimage);
				bimage = ci;
			}
			for (int x = 0; x < Math.min(bimage.getWidth(), M.getMatrixColumns()); x++)
				for (int y = 0; y < Math.min(bimage.getHeight(), M.getMatrixRows()); y++) {
					int k = 255 - buffer[x][y];
					// System.out.println("**"+k);
					while (!index2numpixels.containsKey(k) && k > 0) {
						k--;
					}
					IMAGE_EDITOR_VIEW.mainPanel.setCursorX(x);
					IMAGE_EDITOR_VIEW.mainPanel.setCursorY(y);
					M.setCoordinatesPointer(x, y);
					M.setCharInUse(new AsciiChar(k, M.getDefaultCharBackColor(), new Color(bimage.getRGB(x, y))));
					M.writeCharOnPointer();
				}
			IMAGE_IMPORTER_VIEW.setVisible(false);
		}
		if(M.getTool()==11)M.addItemToHistoryUndo(M.getMatrix().clone());
		M.setCharInUse(hold);
		M.setTool(tool);
		//M.printMatrix();
	}
	
	
	/**
	 * Converts the source image to 4-bit colour using the default 16-colour
	 * palette:
	 * <ul>
	 * <li>black</li>
	 * <li>dark red</li>
	 * <li>dark green</li>
	 * <li>dark yellow</li>
	 * <li>dark blue</li>
	 * <li>dark magenta</li>
	 * <li>dark cyan</li>
	 * <li>dark grey</li>
	 * <li>light grey</li>
	 * <li>red</li>
	 * <li>green</li>
	 * <li>yellow</li>
	 * <li>blue</li>
	 * <li>magenta</li>
	 * <li>cyan</li>
	 * <li>white</li>
	 * </ul>
	 * No transparency.
	 * 
	 * @param src the source image to convert
	 * @return a copy of the source image with a 4-bit colour depth, with the
	 *         default colour pallette
	 */
	public static BufferedImage convert4(BufferedImage src) {
		int[] cmap = new int[] { 0x000000, 0x800000, 0x008000, 0x808000, 0x000080, 0x800080, 0x008080, 0x808080,
				0xC0C0C0, 0xFF0000, 0x00FF00, 0xFFFF00, 0x0000FF, 0xFF00FF, 0x00FFFF, 0xFFFFFF };
		return convert4(src, cmap);
	}

	/**
	 * Converts the source image to 4-bit colour using the given colour map. No
	 * transparency.
	 * 
	 * @param src  the source image to convert
	 * @param cmap the colour map, which should contain no more than 16 entries The
	 *             entries are in the form RRGGBB (hex).
	 * @return a copy of the source image with a 4-bit colour depth, with the custom
	 *         colour pallette
	 */
	public static BufferedImage convert4(BufferedImage src, int[] cmap) {
		IndexColorModel icm = new IndexColorModel(4, cmap.length, cmap, 0, false, Transparency.OPAQUE,
				DataBuffer.TYPE_BYTE);
		BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_BINARY, icm);
		ColorConvertOp cco = new ColorConvertOp(src.getColorModel().getColorSpace(),
				dest.getColorModel().getColorSpace(), null);
		cco.filter(src, dest);

		return dest;
	}

}
