package image_editor.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import image_editor.interfaces.ImageConvertStrategy;
import image_editor.model.AsciiChar;
import image_editor.model.Model;
import image_editor.view.ImageEditor;
import image_editor.view.ImageImporter;

public class NegativeConvertBehaviour implements ImageConvertStrategy {

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
					Color color = new Color(bimage.getRGB(x, y));
					int rc = color.getRed();
					int gc = color.getGreen();
					int bc = color.getBlue();
					M.setCharInUse(new AsciiChar(k, M.getDefaultCharBackColor(), new Color(255-rc, 255-gc, 255-bc)));
					M.writeCharOnPointer();
				}
			IMAGE_IMPORTER_VIEW.setVisible(false);
		}
		if(M.getTool()==11)M.addItemToHistoryUndo(M.getMatrix().clone());
		M.setCharInUse(hold);
		M.setTool(tool);
		//M.printMatrix();
	}

}

