import java.awt.Graphics2D;
import java.util.HashSet;

public class Wave {
	
	int wave = 0;
	int enemyCount = 20;
	boolean waveStart = true;
	HashSet<Enemy> enemies = new HashSet<Enemy>();
	
	public void newWave() {
		
		if (waveStart) {
			
			wave += 1;
			
			for (int i = 1; i <= enemyCount; i++) {

				Enemy enemy = new Enemy(i, (int)((Math.random() * Darklight2.WIDTH * 3) - Darklight2.WIDTH),
						(int)((Math.random() * Darklight2.HEIGHT * 3) - Darklight2.HEIGHT), 64, 10, 4);
				enemies.add(enemy);
			}
			waveStart = false;
		}
	}
	
	public void maintain(Graphics2D g, Arena arena, Player player) {
		
		for (Enemy enemy : enemies) {
			enemy.draw(g, arena);
			enemy.trackPlayer(arena, player, enemies);
			if (player.isColliding(enemy, arena)) {
				System.out.println("hit " + enemy.id);
			}
			if (enemy.health <= 0) {
				enemies.remove(enemy);
			}
		}
		if (enemies.size() == 0) {
			waveStart = true;
		}
	}
}
