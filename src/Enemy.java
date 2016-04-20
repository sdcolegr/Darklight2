import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.ConcurrentHashMap;

public class Enemy extends Actor {

	int type = 0;
	double strength = 0;
	int moveCooldown = 0;
	int attackCooldown = 0;
	int randomX = 0;
	int randomY = 0;
	float magnitude;
	boolean spottedPlayer = false;
	boolean left = true;
	boolean right = true;
	boolean up = true;
	boolean down = true;

	public Enemy(int id, int x, int y, Player player, Arena arena, float difficulty) {
		super(id, x, y);
		
		type = (int)(Math.random()*100);
		if (type < 60) {
			// regular
			size = 64;
			health = 10 * difficulty;
			speed = 4;
			strength = 15 * difficulty;
		} else if (type < 85) {
			// small
			size = 32;
			health = 5 * difficulty;
			speed = 6;
			strength = 8 * difficulty;
		} else {
			// large
			size = 128;
			health = 20 * difficulty;
			speed = 2;
			strength = 25 * difficulty;
		}
		
		// player safety radius correction
		while (this.x > player.x - 256 - arena.xOffset && this.x < player.x + 256 - arena.xOffset &&
				this.y > player.y - 256 - arena.yOffset && this.y < player.y + 256 - arena.yOffset) {
			this.x = (float)(Math.random() * Darklight2.WIDTH * 3) - Darklight2.WIDTH;
			this.y = (float)(Math.random() * Darklight2.HEIGHT * 3) - Darklight2.HEIGHT;
		}
		
		// bounding
		if (this.x - size/2 < arena.xBoundL) {
			this.x += size/2;
		}
		if (this.x + size/2 > arena.xBoundR) {
			this.x -= size/2;
		}
		if (this.y - size/2 < arena.yBoundU) {
			this.y += size/2;
		}
		if (this.y + size/2 > arena.yBoundD) {
			this.y -= size/2;
		}
	}

	public void draw(Graphics2D g, Arena arena) {
		
		if (type < 60) {
			// regular
			// square
			g.setColor(new Color(230, 149, 0));
			g.fillRect((int)(x - size/2 + arena.xOffset), (int)(y - size/2 + arena.yOffset), size, size);
			
			// hitbox
			g.setColor(Color.ORANGE);
			g.drawRect((int)(x - size/2 + arena.xOffset), (int)(y - size/2 + arena.yOffset), size, size);
		} else if (type < 85) {
			// small
			// square
			g.setColor(new Color(227, 227, 0));
			g.fillRect((int)(x - size/2 + arena.xOffset), (int)(y - size/2 + arena.yOffset), size, size);
			
			// hitbox
			g.setColor(Color.YELLOW);
			g.drawRect((int)(x - size/2 + arena.xOffset), (int)(y - size/2 + arena.yOffset), size, size);
		} else {
			// large
			// square
			g.setColor(new Color(180, 0, 0));
			g.fillRect((int)(x - size/2 + arena.xOffset), (int)(y - size/2 + arena.yOffset), size, size);
			
			// hitbox
			g.setColor(Color.RED);
			g.drawRect((int)(x - size/2 + arena.xOffset), (int)(y - size/2 + arena.yOffset), size, size);
		}
	}

	public void movement(Arena arena, Player player, ConcurrentHashMap<Integer, Enemy> enemies) {

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
		
		magnitude = 0;
		if ((left || right) && (up || down)) {
			magnitude += (float)Math.sqrt(speed*speed + speed*speed);
		} else {
			magnitude += (float)Math.sqrt(speed*speed);
		}
		magnitude = speed / magnitude;

		if ((x + arena.xOffset + (size / 2) + 256 > player.x &&
			x + arena.xOffset - (size / 2) - 256 < player.x &&
			y + arena.yOffset + (size / 2) + 256 > player.y &&
			y + arena.yOffset - (size / 2) - 256 < player.y) || spottedPlayer) {

			spottedPlayer = true;

			if (x + arena.xOffset > player.x && left) {
				x -= speed * magnitude;
			}
			if (x + arena.xOffset < player.x && right) {
				x += speed * magnitude;
			}
			if (y + arena.yOffset > player.y && up) {
				y -= speed * magnitude;
			}
			if (y + arena.yOffset < player.y && down) {
				y += speed * magnitude;
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
	
	public void attack(Player player, Arena arena, boolean swordSpec) {
		// enemy attacking
		if (player.isColliding(this, arena) && attackCooldown == 0) {
			if (swordSpec) {
				player.health -= (strength/2);
				health -= 2;
			} else {
				player.health -= strength;
			}
			System.out.println("Enemy " + id + " hit the player. Player health is " + player.health);
			if  (type < 60) {
				attackCooldown = 30;
			} else if (type < 85) {
				attackCooldown = 45;
			} else {
				attackCooldown = 60;
			}
		} else if (attackCooldown > 0) {
			attackCooldown--;
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

