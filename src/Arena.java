import java.awt.Color;
import java.awt.Graphics2D;

public class Arena {
	
	protected int xBoundL = 0 - Darklight2.WIDTH;
	protected int xBoundR = Darklight2.WIDTH*2;
	protected int yBoundU = 0 - Darklight2.HEIGHT;
	protected int yBoundD = Darklight2.HEIGHT*2;
	protected int xOffsetBorder = Darklight2.WIDTH/4;
	protected int yOffsetBorder = Darklight2.HEIGHT/4;
	protected int xOffset = 0;
	protected int yOffset = 0;
	
	public void draw(Graphics2D g) {
		
		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Darklight2.WIDTH, Darklight2.HEIGHT);
		
		// floor grid
		g.setColor(new Color(10, 10, 10));
		for (int i = 0; i < (Darklight2.HEIGHT*3)/64; i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < (Darklight2.WIDTH*3)/64; j += 2) {
					g.fillRect(xBoundL + (64 * j) + xOffset, yBoundU + (64 * i) + yOffset, 64, 64);
				}
			} else {
				for (int j = 1; j < (Darklight2.WIDTH*3)/64; j += 2) {
					g.fillRect(xBoundL + (64 * j) + xOffset, yBoundU + (64 * i) + yOffset, 64, 64);
				}
			}
		}
		
		// static objects
		g.setColor(Color.PINK);
		g.fillRect(200 + xOffset, 200 + yOffset, 20, 20);
		g.fillRect(800 + xOffset, 50 + yOffset, 20, 20);
		g.fillRect(50 + xOffset, 300 + yOffset, 20, 20);
		g.fillRect(500 + xOffset, 450 + yOffset, 20, 20);
		g.fillRect(100 + xOffset, 250 + yOffset, 20, 20);
		g.fillRect(650 + xOffset, 100 + yOffset, 20, 20);
		
		// map boundaries
		g.setColor(Color.YELLOW);
		g.drawRect(xBoundL + xOffset, yBoundU + yOffset, Darklight2.WIDTH*3, Darklight2.HEIGHT*3);
	}
}
