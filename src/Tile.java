import java.awt.Color;
import java.awt.Graphics2D;


public class Tile {
	
	int x = 0;
	int y = 0;
	Color color = Color.BLACK;
	
	public Tile(int x, int y) {
		
		this.x = x;
		this.y = y;
		
		double rand;
		if ((rand = Math.random()*5) < 1) {
			color = new Color(25, 25, 25);
		} else if (rand < 2) {
			color = new Color(20, 20, 20);
		} else if (rand < 3) {
			color = new Color(15, 15, 15);
		} else if (rand < 4) {
			color = new Color(10, 10, 10);
		} else {
			color = new Color(5, 5, 5);
		}
	}
	
	public void draw(Graphics2D g, int xOffset, int yOffset) {
		g.setColor(color);
		g.fillRect(x + xOffset, y + yOffset, 64, 64);
	}
}
