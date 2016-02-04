import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;

public class Arena {
	
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
	
	protected int xBoundL = 0 - Darklight2.WIDTH;
	protected int xBoundR = Darklight2.WIDTH*2;
	protected int yBoundU = 0 - Darklight2.HEIGHT;
	protected int yBoundD = Darklight2.HEIGHT*2;
	protected int xOffsetBorder = Darklight2.WIDTH/4;
	protected int yOffsetBorder = Darklight2.HEIGHT/4;
	protected int xOffset = 0;
	protected int yOffset = 0;
	protected HashSet<Tile> tiles = new HashSet<Tile>();
	
	public void draw(Graphics2D g) {
		
		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Darklight2.WIDTH, Darklight2.HEIGHT);
		
		// floor grid
		if (tiles.size() == 0) {
			for (int i = 0; i < (Darklight2.HEIGHT*3)/64; i++) {
				for (int j = 0; j < (Darklight2.WIDTH*3)/64; j++) {
					Tile tile = new Tile(xBoundL + j * 64, yBoundU + i * 64);
					tile.draw(g, xOffset, yOffset);
					tiles.add(tile);
				}
			}
		} else {
			for (Tile tile : tiles) {
				tile.draw(g, xOffset, yOffset);
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
