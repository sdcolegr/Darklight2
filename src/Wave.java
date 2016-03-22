import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class Wave {
	
	int wave = 0;
	int enemyCount = 0;
	boolean waveStart = true;
	ConcurrentHashMap<Integer, Enemy> enemies = new ConcurrentHashMap<Integer, Enemy>();
	HashSet<Pickup> pickups = new HashSet<Pickup>();
	int random;
	
	public void newWave(Player player, Arena arena) {
		
		if (waveStart) {
			
			wave += 1;
			enemyCount = wave * 10;
			
			for (int i = 1; i <= enemyCount; i++) {
				
				Enemy enemy = new Enemy(i, (int)(Math.random() * Darklight2.WIDTH * 3) - Darklight2.WIDTH, 
						(int)(Math.random() * Darklight2.HEIGHT * 3) - Darklight2.HEIGHT, player, arena);
				enemies.put(enemy.id, enemy);
			}
			waveStart = false;
		}
	}
	
	public void maintain(Graphics2D g, Arena arena, Player player) {
		
		for (Pickup pickup : pickups) {
			pickup.draw(g, arena);
			if (pickup.playerPickup(player, arena)) {
				pickups.remove(pickup);
			}
		}
		for (Enemy enemy : enemies.values()) {
			enemy.draw(g, arena);
			enemy.movement(arena, player, enemies);
			enemy.attack(player, arena);
			// enemy death
			if (enemy.health <= 0) {
				random = (int)(Math.random()*100);
				if (random < 30) {
					Pickup p = new Pickup(enemy.x, enemy.y);
					pickups.add(p);
				}
				enemies.remove(enemy.id);
			}
		}
		
		// new wave
		if (enemies.size() == 0) {
			waveStart = true;
		}
	}
}
