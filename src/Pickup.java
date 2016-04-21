import java.awt.Color;
import java.awt.Graphics2D;

public class Pickup {

	double id;
	float x;
	float y;
	int type;
	
	public Pickup(double id, float x, float y) {
		this.id = id;
		this.x = x;
		this.y = y;
		type = (int)(Math.random()*100);
	}
	
	public void draw(Graphics2D g, Arena arena) {
		if (type < 50) {
			g.setColor(Color.PINK);
		} else {
			g.setColor(Color.BLUE);
		}
		g.fillRect((int)(x - 8 + arena.xOffset), (int)(y - 8 + arena.yOffset), 16, 16);
	}
	
	public boolean playerPickup(Player player, Arena arena) {
		
		if (x + 8 + arena.xOffset > player.x - (player.size/2) && 
			x - 8 + arena.xOffset < player.x + (player.size/2) &&
			y + 8 + arena.yOffset > player.y - (player.size/2) &&
			y - 8 + arena.yOffset < player.y + (player.size/2)) {
			
			if (type < 50) {
				player.health += 10;
				if (player.health > 150) {
					player.health = 150;
				}
			} else {
				player.mana += 30;
				if (player.mana > 100) {
					player.mana = 100;
				}
			}
			
			return true;
		}
		return false;
	}
}
