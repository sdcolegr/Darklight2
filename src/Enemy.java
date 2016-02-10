import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

public class Enemy extends Actor {

	int cooldown = 0;
	int randomX = 0;
	int randomY = 0;
	boolean hasSpotted = false;
	boolean left = true;
	boolean right = true;
	boolean above = true;
	boolean below = true;

	public Enemy(int id, int x, int y, int size, int health, int speed) {
		super(id, x, y, size, health, speed);
	}

	public void draw(Graphics2D g, Arena arena) {
		g.setColor(Color.RED);
		g.fillRect(x + arena.xOffset - (size/2), y + arena.yOffset - (size/2), size, size);
	}

	public void trackPlayer(Arena arena, Player player, HashMap<Integer, Enemy> enemies) {

		// Enemy Collision
		for(int id2 : enemies.keySet()) {
			if (id != id2) {
				// Check for left collision
				if (((y - size/2 + arena.yOffset < enemies.get(id2).y + enemies.get(id2).size/2 + arena.yOffset &&
					y + size/2 + arena.yOffset > enemies.get(id2).y - enemies.get(id2).size/2 + arena.yOffset) ||
					y + arena.yOffset == enemies.get(id2).y + arena.yOffset) &&
					x - size/2 + arena.xOffset == enemies.get(id2).x + enemies.get(id2).size/2 + arena.xOffset) {
					left = false;
				}

				// Check for right collision
				if (((y - size/2 + arena.yOffset < enemies.get(id2).y + enemies.get(id2).size/2 + arena.yOffset &&
					y + size/2 + arena.yOffset > enemies.get(id2).y - enemies.get(id2).size/2 + arena.yOffset) ||
					y + arena.yOffset == enemies.get(id2).y + arena.yOffset) &&
					x + size/2 + arena.xOffset == enemies.get(id2).x - enemies.get(id2).size/2 + arena.xOffset) {
					right = false;
				}

				// Check for above collision
				if (((x - size/2 + arena.xOffset < enemies.get(id2).x + enemies.get(id2).size/2 + arena.xOffset &&
					x + size/2 + arena.xOffset > enemies.get(id2).x - enemies.get(id2).size/2 + arena.xOffset) ||
					x + arena.xOffset == enemies.get(id2).x + arena.xOffset) &&
					y + arena.yOffset - size/2 == enemies.get(id2).y + enemies.get(id2).size/2 + arena.yOffset) {
					above = false;
				}


				// Check for below collision
				if (((x - size/2 + arena.xOffset < enemies.get(id2).x + enemies.get(id2).size/2 + arena.xOffset &&
					x + size/2 + arena.xOffset > enemies.get(id2).x - enemies.get(id2).size/2 + arena.xOffset) ||
					x + arena.xOffset == enemies.get(id2).x + arena.xOffset) &&
					y + size/2 + arena.yOffset == enemies.get(id2).y - enemies.get(id2).size/2 + arena.yOffset) {
					below = false;
				}
			}
		}

		if ((x + arena.xOffset + (size / 2) + 256 > player.x &&
				x + arena.xOffset - (size / 2) - 256 < player.x &&
				y + arena.yOffset + (size / 2) + 256 > player.y &&
				y + arena.yOffset - (size / 2) - 256 < player.y) || hasSpotted) {

			hasSpotted = true;

			if (x + arena.xOffset > player.x && left) {
				x -= speed;
			}
			if (x + arena.xOffset < player.x && right) {
				x += speed;
			}
			if (y + arena.yOffset > player.y && above) {
				y -= speed;
			}
			if (y + arena.yOffset < player.y && below) {
				y += speed;
			}
		} else if (cooldown < 30) {

			if (cooldown == 0) {
				randomX = (int)(Math.random() * 3);
				randomY = (int)(Math.random() * 3);
			}

			if (randomX == 1 && x + (size/2) < arena.xBoundR && right) {
				x += speed;
			} 
			if (randomX == 2 && x - (size/2) > arena.xBoundL && left) {
				x -= speed;
			}
			if (randomY == 1 && y + (size/2) < arena.yBoundD && below) {
				y += speed;
			} 
			if (randomY == 2 && y - (size/2) > arena.yBoundU && above) {
				y -= speed;
			}
			cooldown ++;
		} else {
			cooldown ++;
			if (cooldown == 90) {
				cooldown = 0;
			}
		}

		left = true;
		right = true;
		above = true;
		below = true;
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

