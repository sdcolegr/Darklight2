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
	public static Image topLeftWall;
	public static Image topWall;
	public static Image topRightWall;
	public static Image leftWall;
	public static Image rightWall;
	public static Image bottomLeftWall;
	public static Image bottomWall;
	public static Image bottomRightWall;
	public static Image light;

	public static void load() {

		try {
			stone = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/stone.png"));
			moss = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/moss.png"));
			mossRocks = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/mossRocks.png"));
			mossRocks2 = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/mossRocks2.png"));
			mossSkull = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/mossSkull.png"));
			mossHelm = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/mossHelm.png"));
			guy = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Tiles/guy.png"));
			topLeftWall = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Walls/topLeftWall.png"));
			topWall = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Walls/topWall.png"));
			topRightWall = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Walls/topRightWall.png"));
			leftWall = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Walls/leftWall.png"));
			rightWall = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Walls/rightWall.png"));
			bottomLeftWall = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Walls/bottomLeftWall.png"));
			bottomWall = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Walls/bottomWall.png"));
			bottomRightWall = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Walls/bottomRightWall.png"));
			light = ImageIO.read(TextureLoader.class.getResource("Resources/Textures/Misc/light.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
