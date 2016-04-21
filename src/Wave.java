import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.ConcurrentHashMap;

public class Wave {
	
	int wave = 0;
	float difficulty = 1;
	int enemyCount = 0;
	boolean waveStart = true;
	boolean hole = false;
	boolean falling = false;
	ConcurrentHashMap<Integer, Enemy> enemies = new ConcurrentHashMap<Integer, Enemy>();
	ConcurrentHashMap<Double, Pickup> pickups = new ConcurrentHashMap<Double, Pickup>();
	int random;
	
	public void newWave(Graphics2D g, Arena arena, Player player) {
		
		if (waveStart) {
			
			wave++;
			enemyCount = (wave % 10 == 0) ? 100 : (wave % 10) * 10;
			
			for (int i = 1; i <= enemyCount; i++) {
				
				Enemy enemy = new Enemy(i, (int)(Math.random() * Darklight2.WIDTH * 3) - Darklight2.WIDTH, 
						(int)(Math.random() * Darklight2.HEIGHT * 3) - Darklight2.HEIGHT, player, arena, difficulty);
				enemies.put(enemy.id, enemy);
			}
			waveStart = false;
		}
		if (hole) {
			g.setColor(Color.BLACK);
			g.fillRect((int)((Darklight2.WIDTH/2) - 160 + arena.xOffset), (int)((Darklight2.HEIGHT/2) - 96 + arena.yOffset), 320, 192);
			
			if (player.x - player.size/2 >= (Darklight2.WIDTH/2) - 160 + arena.xOffset &&
				player.x + player.size/2 <= (Darklight2.WIDTH/2) + 160 + arena.xOffset &&
				player.y - player.size/2 >= (Darklight2.HEIGHT/2) - 96 + arena.yOffset &&
				player.y + player.size/2 <= (Darklight2.HEIGHT/2) + 96 + arena.yOffset) {
				falling = true;
			}
		}
	}
	
	public void maintain(Graphics2D g, Arena arena, Player player, boolean swordSpec) {
		
		for (Pickup pickup : pickups.values()) {
			pickup.draw(g, arena);
			if (pickup.playerPickup(player, arena)) {
				pickups.remove(pickup.id);
			}
		}
		for (Enemy enemy : enemies.values()) {
			enemy.draw(g, arena);
			enemy.movement(arena, player, enemies);
			enemy.attack(player, arena, swordSpec);
			// enemy death
			if (enemy.health <= 0) {
				random = (int)(Math.random()*100);
				if (random < 30) {
					Pickup p = new Pickup(Math.random(), enemy.x, enemy.y);
					pickups.put(p.id, p);
				}
				enemies.remove(enemy.id);
			}
		}
		
		// new wave
		if (enemies.size() == 0) {
			if (wave != 0 && wave % 10 == 0) {
				hole = true;
			} else {
				waveStart = true;
			}
		}
	}
}
