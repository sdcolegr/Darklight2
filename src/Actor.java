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
	
	// getters
	public int getID() { return id; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getSize() { return size; }
	public int getHealth() { return health; }
	public int getSpeed() { return speed; }
	
	// setters
	public void setID(int id) { this.id = id; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setSize(int size) { this.size = size; }
	public void setHealth(int health) { this.health = health; }
	public void setSpeed(int speed) { this.speed = speed; }
	
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
