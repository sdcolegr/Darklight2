import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class Darklight2 extends Game {
	
	Arena arena = new Arena();
	Player player = new Player(0, WIDTH/2, HEIGHT/2, 64, 10, 8);
	Wave wave = new Wave();
	
	int cooldown = 0;

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		
		arena.draw(g);
		
//		// offset borders
//		g.setColor(Color.RED);
//		g.drawRect(0, 0, arena.xOffsetBorder, HEIGHT);								// left
//		g.drawRect(WIDTH - arena.xOffsetBorder, 0, arena.xOffsetBorder, HEIGHT);	// right
//		g.drawRect(0, 0, WIDTH, arena.yOffsetBorder);								// top
//		g.drawRect(0, HEIGHT - arena.yOffsetBorder, WIDTH, arena.yOffsetBorder);	// bottom
			
		// player
		player.draw(g);
		player.movement(p1, arena);

		wave.newWave();
		wave.maintain(g, arena, player);

		// speed increasing
		if (p1.pressed(Button.C) && cooldown == 0) {
			player.speed *= 2;
			cooldown = 60;
		}
		if (cooldown > 0) {
			cooldown -= 1;
			
			if (cooldown == 0) {
				player.speed /= 2;
			}
		}
		
		g.dispose();
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

	@Override
	public Image banner() {
		// 512 x 128
		return null;
	}

	public static void main(String[] args) {
		Arcadia.display(new Arcadia(new Darklight2()));
	}
}
