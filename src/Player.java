import java.awt.Color;
import java.awt.Graphics2D;

import arcadia.Button;
import arcadia.Input;

public class Player extends Actor {
	
	String weapon;
	
	// 0 up
	// 1 right
	// 2 down
	// 3 left
	int direction;

	public Player(int id, int x, int y, int size, int health, int speed) {
		super(id, x, y, size, health, speed);
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x - (size/2), y - (size/2), size, size);
		//show character, will definitely have to be changed.
		//g.drawImage(TextureLoader.character, x - (size/2), y - (size/2), null);
	}
	
	public void movement(Input p1, Arena arena) {
		
		// left
		if (p1.pressed(Button.L) && x > arena.xBoundL + arena.xOffset + (size/2)) {
			if (x > arena.xOffsetBorder + (size/2)) {
				x -= speed;
			} else {
				arena.xOffset += speed;
			}
		}
		// right
		if (p1.pressed(Button.R) && x < arena.xBoundR + arena.xOffset - (size/2)) {
			if (x < Darklight2.WIDTH - arena.xOffsetBorder - (size/2)) {
				x += speed;
			} else {
				arena.xOffset -= speed;
			}
		}
		// up
		if (p1.pressed(Button.U) && y > arena.yBoundU + arena.yOffset + (size/2)) {
			if(y > arena.yOffsetBorder + (size/2)) {
				y -= speed;
			} else {
				arena.yOffset += speed;
			}
		}
		// down
		if (p1.pressed(Button.D) && y < arena.yBoundD + arena.yOffset - (size/2)) {
			if (y < Darklight2.HEIGHT - arena.yOffsetBorder - (size/2)) {
				y += speed;
			} else {
				arena.yOffset -= speed;
			}
		}
	}
	
	@Override
	public boolean isColliding(Actor a, Arena arena) {
		
		if (x + (size/2) > a.x + arena.xOffset - (a.size/2) && 
			x - (size/2) < a.x + arena.xOffset + (a.size/2)) {

			if (y + (size/2) > a.y + arena.yOffset - (a.size/2) && 
				y - (size/2) < a.y + arena.yOffset + (a.size/2)) {
				return true;
			}
		}
		return false;
	}
}
