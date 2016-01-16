import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class darklight2 extends Game {
	
	float playerX = WIDTH/2 - 25;
	float playerY = HEIGHT/2 - 25;
	int playerSpeed = 10;
	int cooldown = 0;

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.GREEN);
		g.fillRect((int)playerX, (int)playerY, 50, 50);
		
		// player movement
		if(p1.pressed(Button.U) && playerY >= 0) {
			playerY -= playerSpeed;
		}
		if (p1.pressed(Button.D) && playerY <= HEIGHT - 50) {
			playerY += playerSpeed;
		}
		if (p1.pressed(Button.R) && playerX <= WIDTH - 50) {
			playerX += playerSpeed;
		}
		if (p1.pressed(Button.L) && playerX >= 0) {
			playerX -= playerSpeed;
		}
		
		// random speed increasing bullshit
		if (p1.pressed(Button.C) && cooldown == 0) {
			playerSpeed *= 2;
			cooldown = 60;
		}
		if (cooldown > 0) {
			cooldown -= 1;
			
			if (cooldown == 0) {
				playerSpeed /= 2;
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
		Arcadia.display(new Arcadia(new darklight2()));
	}
}
