import java.awt.Color;
import java.awt.Graphics2D;

import arcadia.Button;
import arcadia.Input;

public class Player extends Actor {
	
	Weapon weapon;
	
	// 0 up
	// 1 right
	// 2 down
	// 3 left
	int direction;
	float magnitude;

	public Player(int id, int x, int y) {
		super(id, x, y);
		size = 64;
		health = 20;
		speed = 8;
	}
	
	public void draw(Graphics2D g) {
		// square
		g.setColor(new Color(0, 180, 0));
		g.fillRect((int)(x - size/2), (int)(y - size/2), size, size);
		
		// hitbox
		g.setColor(Color.GREEN);
		g.drawRect((int)(x - size/2), (int)(y - size/2), size, size);
		
		//show character, will definitely have to be changed.
		//g.drawImage(TextureLoader.character, x - (size/2), y - (size/2), null);
	}
	
	public void movement(Input p1, Arena arena) {
		
		magnitude = 0;
		if ((p1.pressed(Button.L) || p1.pressed(Button.R)) &&
			(p1.pressed(Button.U) || p1.pressed(Button.D))) {
			magnitude += (float)Math.sqrt(speed*speed + speed*speed);
		} else {
			magnitude += (float)Math.sqrt(speed*speed);
		}
		magnitude = speed / magnitude;
		
		// left
		if (p1.pressed(Button.L) && x - (size/2) > arena.xBoundL + arena.xOffset) {
			direction = 3;
			if (x > arena.xOffsetBorder + (size/2)) {
				x -= speed;
			} else {
				arena.xOffset += speed;
			}
		}
		// right
		if (p1.pressed(Button.R) && x + (size/2) < arena.xBoundR + arena.xOffset) {
			direction = 1;
			if (x < Darklight2.WIDTH - arena.xOffsetBorder - (size/2)) {
				x += speed;
			} else {
				arena.xOffset -= speed;
			}
		}
		// up
		if (p1.pressed(Button.U) && y  - (size/2) > arena.yBoundU + arena.yOffset) {
			direction = 0;
			if(y > arena.yOffsetBorder + (size/2)) {
				y -= speed;
			} else {
				arena.yOffset += speed;
			}
		}
		// down
		if (p1.pressed(Button.D) && y  + (size/2) < arena.yBoundD + arena.yOffset) {
			direction = 2;
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
			x - (size/2) < a.x + arena.xOffset + (a.size/2) &&
			y + (size/2) > a.y + arena.yOffset - (a.size/2) &&
			y - (size/2) < a.y + arena.yOffset + (a.size/2)) {

			return true;
		}
		return false;
	}
}
