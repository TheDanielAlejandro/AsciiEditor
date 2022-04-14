package image_editor.interfaces;

import java.awt.image.BufferedImage;

import ascii_panel.AsciiPanel;
import image_editor.model.Model;
import image_editor.view.ImageEditor;
import image_editor.view.ImageImporter;

public interface ImageConvertStrategy {
	
	public void convertImage(ImageEditor IMAGE_EDITOR_VIEW, ImageImporter IMAGE_IMPORTER_VIEW, Model M);

}
