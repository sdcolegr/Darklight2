public class Actor {

	protected int id;
	protected int x;
	protected int y;
	protected int size;
	protected int health;
	protected int speed;
	
	public Actor(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public boolean isColliding(Actor a, Arena arena) { return false; }
	
	public String toString() {
		String string = "<id: " + id + ", coords(" + x + ", " + y
				+ "), size: " + size + ", health: " + health + ", speed: " + speed + ">";
		return string;
	}
}
