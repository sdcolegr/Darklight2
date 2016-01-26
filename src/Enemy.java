import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends Actor {

	int cooldown = 0;
	int randomX = 0;
	int randomY = 0;
	
	public Enemy(int id, int x, int y, int size, int health, int speed) {
		super(id, x, y, size, health, speed);
	}
	
	public void draw(Graphics2D g, Arena arena) {
		g.setColor(Color.RED);
		g.fillRect(x + arena.xOffset - (size/2), y + arena.yOffset - (size/2), size, size);
	}
	
	public void trackPlayer(Arena arena, Player player) {

		if (x + arena.xOffset + (size / 2) + 256 > player.x &&
			x + arena.xOffset - (size / 2) - 256 < player.x &&
			y + arena.yOffset + (size / 2) + 256 > player.y &&
			y + arena.yOffset - (size / 2) - 256 < player.y) {

			if (x + arena.xOffset > player.x) {
				x -= speed;
			}
			if (x + arena.xOffset < player.x) {
				x += speed;
			}
			if (y + arena.yOffset > player.y) {
				y -= speed;
			}
			if (y + arena.yOffset < player.y) {
				y += speed;
			}
		} else if (cooldown < 30) {

			
			if (cooldown == 0) {
				randomX = (int)(Math.random() * 3);
				randomY = (int)(Math.random() * 3);
			}
			
			if (randomX == 1) {
				x += speed;
			}
			
			else if (randomX == 2) {
				x -= speed;
			}

			if (randomY == 1) {
				y += speed;
			}

			else if (randomY == 2) {
				y -= speed;
			}
			
			cooldown ++;
		}
		else {
			cooldown ++;
			if (cooldown == 90) {
				cooldown = 0;
			}
		}
	}
	
	@Override
	public boolean isColliding(Actor a, Arena arena) {
		
		if (x + arena.xOffset + (size/2) > a.x + arena.xOffset - (a.size/2) && 
			x + arena.xOffset - (size/2) < a.x + arena.xOffset + (a.size/2)	&& 
			y + arena.yOffset + (size/2) > a.y + arena.yOffset - (a.size/2) &&
			y + arena.yOffset - (size/2) < a.y + arena.yOffset + (a.size/2)) {
			
			return true;
		}
		return false;
	}
}
