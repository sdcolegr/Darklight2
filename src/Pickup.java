import java.awt.Color;
import java.awt.Graphics2D;

public class Pickup {

	float x;
	float y;
	int type;
	
	public Pickup(float x, float y) {
		this.x = x;
		this.y = y;
		type = (int)(Math.random()*100);
	}
	
	public void draw(Graphics2D g, Arena arena) {
		g.setColor(Color.PINK);
		g.fillRect((int)(x - 8 + arena.xOffset), (int)(y - 8 + arena.yOffset), 16, 16);
	}
	
	public boolean playerPickup(Player player, Arena arena) {
		
		if (x + 8 + arena.xOffset > player.x - (player.size/2) && 
			x - 8 + arena.xOffset < player.x + (player.size/2) &&
			y + 8 + arena.yOffset > player.y - (player.size/2) &&
			y - 8 + arena.yOffset < player.y + (player.size/2)) {
			
			if (type < 50) {
				player.health += 10;
				if (player.health > 120) {
					player.health = 120;
				}
			}
			
			return true;
		}
		return false;
	}
}
