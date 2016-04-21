import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextureLoader {

	public static Image stone;
	public static Image moss;
	public static Image mossRocks;
	public static Image mossRocks2;
	public static Image mossSkull;
	public static Image mossHelm;
	public static Image guy;

	public static void load() {

		try {
			stone = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/stone.png"));
			moss = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/moss.png"));
			mossRocks = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/mossRocks.png"));
			mossRocks2 = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/mossRocks2.png"));
			mossSkull = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/mossSkull.png"));
			mossHelm = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/mossHelm.png"));
			guy = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/guy.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
