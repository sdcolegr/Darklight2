import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends Actor {

	public Enemy(int id, int x, int y, int size, int health, int speed) {
		super(id, x, y, size, health, speed);
	}
	
	public void draw(Graphics2D g, Arena arena) {
		g.setColor(Color.RED);
		g.fillRect(x + arena.xOffset - (size/2), y + arena.yOffset - (size/2), size, size);
	}
	
	public void trackPlayer(Arena arena, Player player) {
		
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
	}
}
