import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextureLoader {

	public static Image character;

	public static void load() {

		try {
			character = ImageIO.read(new File("Resources/Wick.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
