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

public class SmoothConverterBehaviour implements ImageConvertStrategy{

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
					
					if(x > 0 &&  y > 0 && x < Math.min(bimage.getWidth(), M.getMatrixColumns())-2 &&  y < Math.min(bimage.getHeight(), M.getMatrixRows()) -2 ) {
						
						Color p1 = new Color(bimage.getRGB(x-1, y-1));
			        	Color p2 = new Color(bimage.getRGB(x - 1, y));
			        	Color p3 = new Color(bimage.getRGB(x - 1, y+1));
			        	Color p4 = new Color(bimage.getRGB(x, y-1));
			        	Color p5 = new Color(bimage.getRGB(x, y));
			        	Color p6 = new Color(bimage.getRGB(x, y + 1));
			        	Color p7 = new Color(bimage.getRGB(x + 1, y - 1));
			        	Color p8 = new Color(bimage.getRGB(x + 1, y));
			        	Color p9 = new Color(bimage.getRGB( x + 1, y + 1));
			        	
			        	int r = (p1.getRed() + p2.getRed() + p3.getRed() + 
			        	         p4.getRed() + p5.getRed() + p6.getRed() + 
			        	         p7.getRed() + p8.getRed() + p9.getRed()) / 9;
			        	int g = (p1.getGreen() + p2.getGreen() + p3.getGreen() + 
			        	         p4.getGreen() + p5.getGreen() + p6.getGreen() + 
			        	         p7.getGreen() + p8.getGreen() + p9.getGreen()) / 9;
			        	int b = (p1.getBlue() + p2.getBlue() + p3.getBlue() + 
			        	         p4.getBlue() + p5.getBlue() + p6.getBlue() + 
			        	         p7.getBlue() + p8.getBlue() + p9.getBlue()) / 9;
			        	
						M.setCharInUse(new AsciiChar(k, M.getDefaultCharBackColor(), new Color(r,g,b)));
						M.writeCharOnPointer();
					
					}else {
						M.setCharInUse(new AsciiChar(k, M.getDefaultCharBackColor(), new Color(bimage.getRGB(x, y))));
						M.writeCharOnPointer();
					}
					
					
				}
			IMAGE_IMPORTER_VIEW.setVisible(false);
		}	
		if(M.getTool()==11)M.addItemToHistoryUndo(M.getMatrix().clone());
		M.setCharInUse(hold);
		M.setTool(tool);
		//M.printMatrix();
	}
	
	

}
