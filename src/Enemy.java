import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.ConcurrentHashMap;

public class Enemy extends Actor {

	int moveCooldown = 0;
	int attackCooldown = 0;
	int randomX = 0;
	int randomY = 0;
	int strength = 0;
	boolean hasSpotted = false;
	boolean left = true;
	boolean right = true;
	boolean up = true;
	boolean down = true;

	public Enemy(int id, int x, int y, int size, int health, int speed) {
		super(id, x, y, size, health, speed);
		strength = 1;
	}

	public void draw(Graphics2D g, Arena arena) {
		// square
		g.setColor(new Color(180, 0, 0));
		g.fillRect(x + arena.xOffset - (size/2), y + arena.yOffset - (size/2), size, size);
		
		// hitbox
		g.setColor(Color.RED);
		g.drawRect(x - size/2 + arena.xOffset, y - size/2 + arena.yOffset, size, size);
	}

	public void trackPlayer(Arena arena, Player player, ConcurrentHashMap<Integer, Enemy> enemies) {

		// Enemy Collision
		for(Enemy enemy : enemies.values()) {
			if (id != enemy.id) {
				// Check for left collision
				if (((y - size/2 + arena.yOffset < enemy.y + enemy.size/2 + arena.yOffset &&
					y + size/2 + arena.yOffset > enemy.y - enemy.size/2 + arena.yOffset) ||
					y + arena.yOffset == enemy.y + arena.yOffset) &&
					(x - size/2 + arena.xOffset <= enemy.x + size/2 + arena.xOffset &&
					x - size/2 + arena.xOffset > enemy.x + arena.xOffset)) {
					left = false;
				}

				// Check for right collision
				if (((y - size/2 + arena.yOffset < enemy.y + enemy.size/2 + arena.yOffset &&
					y + size/2 + arena.yOffset > enemy.y - enemy.size/2 + arena.yOffset) ||
					y + arena.yOffset == enemy.y + arena.yOffset) &&
					(x + size/2 + arena.xOffset >= enemy.x - size/2 + arena.xOffset &&
					x + size/2 + arena.xOffset < enemy.x + arena.xOffset)) {
					right = false;
				}

				// Check for above collision
				if (((x - size/2 + arena.xOffset < enemy.x + enemy.size/2 + arena.xOffset &&
					x + size/2 + arena.xOffset > enemy.x - enemy.size/2 + arena.xOffset) ||
					x + arena.xOffset == enemy.x + arena.xOffset) &&
					(y - size/2 + arena.yOffset <= enemy.y + size/2 + arena.yOffset &&
					y - size/2 + arena.yOffset > enemy.y + arena.yOffset)) {
					up = false;
				}


				// Check for below collision
				if (((x - size/2 + arena.xOffset < enemy.x + enemy.size/2 + arena.xOffset &&
					x + size/2 + arena.xOffset > enemy.x - enemy.size/2 + arena.xOffset) ||
					x + arena.xOffset == enemy.x + arena.xOffset) &&
					(y + size/2 + arena.yOffset >= enemy.y - size/2 + arena.yOffset &&
					y + size/2 + arena.yOffset < enemy.y + arena.yOffset)) {
					down = false;
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
			if (y + arena.yOffset > player.y && up) {
				y -= speed;
			}
			if (y + arena.yOffset < player.y && down) {
				y += speed;
			}
		} else if (moveCooldown < 30) {

			if (moveCooldown == 0) {
				randomX = (int)(Math.random() * 3);
				randomY = (int)(Math.random() * 3);
			}

			if (randomX == 2 && x - (size/2) > arena.xBoundL && left) {
				x -= speed;
			}
			if (randomX == 1 && x + (size/2) < arena.xBoundR && right) {
				x += speed;
			} 
			if (randomY == 2 && y - (size/2) > arena.yBoundU && up) {
				y -= speed;
			}
			if (randomY == 1 && y + (size/2) < arena.yBoundD && down) {
				y += speed;
			} 
			moveCooldown ++;
		} else {
			moveCooldown ++;
			if (moveCooldown == 90) {
				moveCooldown = 0;
			}
		}

		left = true;
		right = true;
		up = true;
		down = true;
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

