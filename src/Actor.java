import java.awt.Graphics2D;

public class Actor {

	protected int id;
	protected int x;
	protected int y;
	protected int size;
	protected int health;
	protected int speed;
	
	public Actor(int id, int x, int y, int size, int health, int speed) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.size = size;
		this.health = health;
		this.speed = speed;
	}
	
	public boolean isColliding(Actor a, Arena arena) {
		
		if (id == 0) {
			if (x + (size/2) > a.x - (a.size/2) + arena.xOffset && 
					x - (size/2) < a.x + (a.size/2) + arena.xOffset) {

				if (y + (size/2) > a.y - (a.size/2) + arena.yOffset && 
						y - (size/2) < a.y + (a.size/2) + arena.yOffset) {
					return true;
				}
			}
		} else {
			if (x + arena.xOffset + (size/2) > a.x - (a.size/2) + arena.xOffset && 
					x + arena.xOffset - (size/2) < a.x + (a.size/2) + arena.xOffset) {

				if (y + arena.yOffsetBorder + (size/2) > a.y - (a.size/2) + arena.yOffset && 
						y + arena.yOffset - (size/2) < a.y + (a.size/2) + arena.yOffset) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String toString() {
		String string = "<id: " + id + ", coords(" + x + ", " + y
				+ "), size: " + size + ", health: " + health + ", speed: " + speed + ">";
		return string;
	}
}
