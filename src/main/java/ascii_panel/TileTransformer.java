package ascii_panel;

/**
 * Interface for the implementation of conversion of tiles in an image.
 * 
 * @author danie
 *
 */
public interface TileTransformer {
	public void transformTile(int x, int y, AsciiCharacterData data);
}