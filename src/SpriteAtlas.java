import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class SpriteAtlas {
	private int animationID;
	private long referencePoint;
	private ArrayList<Animation> animations = new ArrayList<Animation>();

	/**Create a collection of animations for a spritesheet
	 * 
	 * @param spritesheet
	 * @param filepath The file containing an outline of all animation frames
	 */
	public SpriteAtlas(String spritesheetpath, String filepath) {
		//Load up the animations from the file
		try {
			Image spritesheet = ImageIO.read(new File(spritesheetpath));

			//setAnimation(0);
			
			

			Scanner scanner = new Scanner(new File(filepath));
			while (scanner.hasNextInt()) {
				//File format: AnimationID X1 Y1 X2 Y2
				int id = scanner.nextInt();
				int x1 = scanner.nextInt();
				int y1 = scanner.nextInt();
				int x2 = scanner.nextInt();
				int y2 = scanner.nextInt();

				Image image = new BufferedImage(x2-x1,y2-y1,BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				g.drawImage(spritesheet, 0, 0, x2-x1, y2-y1, x1, y1, x2, y2, null);

				while (animations.size() <= id) {
					animations.add(new Animation());
				}
				animations.get(id).addFrame(image);
			}
			scanner.close();
		}
		catch (Exception e) {e.printStackTrace();}
	}

	public void setAnimation(int id) {
		if (animationID != id) {
			referencePoint = System.currentTimeMillis();
		}
		animationID = id;
	}

	public Image getFrame() {
		long time = System.currentTimeMillis() - referencePoint;
		//framerate
		int frame = Math.abs((int)((time / 200))%animations.get(animationID).getFrames());
		return animations.get(animationID)
				.getFrame(frame);
	}


}
