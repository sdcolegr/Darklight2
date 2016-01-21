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
	
	public void draw(Graphics2D g) {}
	
	public boolean isColliding(Actor a) {
		// TODO collision code
		return false;
	}
	
	public String toString() {
		String string = "<id: " + id + ", (x: " + x + ", y: " + y
				+ "), size: " + size + ", health: " + health + ", speed: " + speed + ">";
		return string;
	}
}
