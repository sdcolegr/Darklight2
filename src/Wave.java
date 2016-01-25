import java.awt.Graphics2D;
import java.util.HashMap;

public class Wave {
	
	int wave = 0;
	int enemyCount = 20;
	boolean waveStart = true;
	HashMap<Integer, Enemy> enemies = new HashMap<Integer, Enemy>();
	
	public void newWave() {
		
		if (waveStart) {
			
			wave += 1;
			
			for (int i = 1; i <= enemyCount; i++) {

				Enemy enemy = new Enemy(i, (int)((Math.random() * Darklight2.WIDTH * 3) - Darklight2.WIDTH),
						(int)((Math.random() * Darklight2.HEIGHT * 3) - Darklight2.HEIGHT), 64, 10, 4);
				enemies.put(i, enemy);
			}
			waveStart = false;
		}
	}
	
	public void maintain(Graphics2D g, Arena arena, Player player) {
		
		for (int id : enemies.keySet()) {
			enemies.get(id).draw(g, arena);
			enemies.get(id).trackPlayer(arena, player);
			if(player.isColliding(enemies.get(id), arena)) {
				System.out.println("hit" + id);
			}
		}
		}
		if (enemies.size() == 0) {
			waveStart = true;
		}
	}
}
