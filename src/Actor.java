public class Actor {

	protected int id;
	protected float x;
	protected float y;
	protected int size;
	protected double health;
	protected int speed;
	
	public Actor(int id, float x, float y) {
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
