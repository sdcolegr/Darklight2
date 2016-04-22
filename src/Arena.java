import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;

public class Arena {
	
	public class Tile {
	
		int x = 0;
		int y = 0;
		double type;
	
		public Tile(int x, int y) {
		
			this.x = x;
			this.y = y;
			type = Math.random()*100;
		}
	
		public void draw(Graphics2D g, int xOffset, int yOffset) {
			
			if (type < 3) {
				g.drawImage(TextureLoader.moss, x + xOffset, y + yOffset, null);
			} else if (type < 5) {
				g.drawImage(TextureLoader.mossRocks, x + xOffset, y + yOffset, null);
			} else if (type < 5.5) {
				g.drawImage(TextureLoader.mossRocks2, x + xOffset, y + yOffset, null);
			} else if (type < 6) {
				g.drawImage(TextureLoader.mossSkull, x + xOffset, y + yOffset, null);
			} else if (type < 7) {
				g.drawImage(TextureLoader.mossHelm, x + xOffset, y + yOffset, null);
			} else if (type < 7.01){
				g.drawImage(TextureLoader.guy, x + xOffset, y + yOffset, null);
			}
		}
	}
	
	protected int xBoundL = 0 - Darklight2.WIDTH;
	protected int xBoundR = Darklight2.WIDTH*2;
	protected int yBoundU = 0 - Darklight2.HEIGHT;
	protected int yBoundD = Darklight2.HEIGHT*2;
	protected int xOffsetBorder = Darklight2.WIDTH/4;
	protected int yOffsetBorder = Darklight2.HEIGHT/4;
	protected float xOffset = 0;
	protected float yOffset = 0;
	protected HashSet<Tile> tiles = new HashSet<Tile>();
	
	public void draw(Graphics2D g) {
		
		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Darklight2.WIDTH, Darklight2.HEIGHT);
		
		g.drawImage(TextureLoader.topLeftWall, (int)(xBoundL - 64 + xOffset), (int)(yBoundU - 64 + yOffset), null);
		g.drawImage(TextureLoader.topRightWall, (int)(xBoundR + xOffset), (int)(yBoundU - 64 + yOffset), null);
		g.drawImage(TextureLoader.bottomLeftWall, (int)(xBoundL - 64 + xOffset), (int)(yBoundD + yOffset), null);
		g.drawImage(TextureLoader.bottomRightWall, (int)(xBoundR + xOffset), (int)(yBoundD + yOffset), null);
		for (int i = 0; i < (Darklight2.WIDTH*3)/64; i++) {
			g.drawImage(TextureLoader.topWall, (int)(xBoundL + i * 64 + xOffset), (int)(yBoundU - 64 + yOffset), null);
		}
		for (int i = 0; i < (Darklight2.WIDTH*3)/64; i++) {
			g.drawImage(TextureLoader.bottomWall, (int)(xBoundL + i * 64 + xOffset), (int)(yBoundD + yOffset), null);
		}
		for (int i = 0; i < (Darklight2.HEIGHT*3)/64; i++) {
			g.drawImage(TextureLoader.leftWall, (int)(xBoundL - 64 + xOffset), (int)(yBoundU + i * 64 + yOffset), null);
		}
		for (int i = 0; i < (Darklight2.HEIGHT*3)/64; i++) {
			g.drawImage(TextureLoader.rightWall, (int)(xBoundR + xOffset), (int)(yBoundU + i * 64 + yOffset), null);
		}
		
		for (int i = 0; i < (Darklight2.HEIGHT*3)/64; i++) {
			for (int j = 0; j < (Darklight2.WIDTH*3)/64; j++) {
				g.drawImage(TextureLoader.stone, (int)(xBoundL + j * 64 + xOffset), (int)(yBoundU + i * 64 + yOffset), null);
			}
		}
		
		for (int i = 0; i < (Darklight2.HEIGHT*3)/64; i++) {
			for (int j = 0; j < (Darklight2.WIDTH*3)/64; j++) {
				g.drawImage(TextureLoader.stone, (int)(xBoundL + j * 64 + xOffset), (int)(yBoundU + i * 64 + yOffset), null);
			}
		}
		
		// floor grid
		if (tiles.size() == 0) {
			for (int i = 0; i < (Darklight2.HEIGHT*3)/64; i++) {
				for (int j = 0; j < (Darklight2.WIDTH*3)/64; j++) {
					Tile tile = new Tile(xBoundL + j * 64, yBoundU + i * 64);
					tile.draw(g, (int)xOffset, (int)yOffset);
					tiles.add(tile);
				}
			}
		} else {
			for (Tile tile : tiles) {
				tile.draw(g, (int)xOffset, (int)yOffset);
			}
		}
		
//		// map boundaries
//		g.setColor(Color.YELLOW);
//		g.drawRect((int)(xBoundL + xOffset), (int)(yBoundU + yOffset), Darklight2.WIDTH*3, Darklight2.HEIGHT*3);
	}
}
