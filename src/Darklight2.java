import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class Darklight2 extends Game {
	
	int xBoundL = 0 - WIDTH;
	int xBoundR = WIDTH*2;
	int yBoundU = 0 - HEIGHT;
	int yBoundD = HEIGHT*2;
	int xOffsetBorder = WIDTH/4;
	int yOffsetBorder = HEIGHT/4;
	int xOffset = 0;
	int yOffset = 0;
	int playerX = WIDTH/2;
	int playerY = HEIGHT/2;
	int playerSize = 64;
	int playerSpeed = 8;
	int cooldown = 0;

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		
		// background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// floor grid
		g.setColor(new Color(10, 10, 10));
		for (int i = 0; i < (HEIGHT*3)/64; i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < (WIDTH*3)/64; j += 2) {
					g.fillRect(xBoundL + (64 * j) + xOffset, yBoundU + (64 * i) + yOffset, 64, 64);
				}
			} else {
				for (int j = 1; j < (WIDTH*3)/64; j += 2) {
					g.fillRect(xBoundL + (64 * j) + xOffset, yBoundU + (64 * i) + yOffset, 64, 64);
				}
			}
		}
		
		// offset borders
		g.setColor(Color.RED);
		g.drawRect(0, 0, xOffsetBorder, HEIGHT);						// left
		g.drawRect(WIDTH - xOffsetBorder, 0, xOffsetBorder, HEIGHT);	// right
		g.drawRect(0, 0, WIDTH, yOffsetBorder);							// top
		g.drawRect(0, HEIGHT - yOffsetBorder, WIDTH, yOffsetBorder);	// bottom
		
		// map boundaries
		g.setColor(Color.YELLOW);
		g.drawRect(xBoundL + xOffset, yBoundU + yOffset, WIDTH*3, HEIGHT*3);
		
		// static objects
		g.setColor(Color.PINK);
		g.fillRect(200 + xOffset, 200 + yOffset, 20, 20);
		g.fillRect(800 + xOffset, 50 + yOffset, 20, 20);
		g.fillRect(50 + xOffset, 300 + yOffset, 20, 20);
		g.fillRect(500 + xOffset, 450 + yOffset, 20, 20);
		g.fillRect(100 + xOffset, 250 + yOffset, 20, 20);
		g.fillRect(650 + xOffset, 100 + yOffset, 20, 20);
		
		// player
		g.setColor(Color.GREEN);
		g.fillRect(playerX - (playerSize/2), playerY - (playerSize/2), playerSize, playerSize);
		
		// player movement
		// left
		if (p1.pressed(Button.L) && playerX > xBoundL + xOffset + (playerSize/2)) {
			if (playerX > xOffsetBorder + 32) {
				playerX -= playerSpeed;
			} else {
				xOffset += playerSpeed;
			}
		}
		// right
		if (p1.pressed(Button.R) && playerX < xBoundR + xOffset - (playerSize/2)) {
			if (playerX < WIDTH - xOffsetBorder - 32) {
				playerX += playerSpeed;
			} else {
				xOffset -= playerSpeed;
			}
		}
		// up
		if (p1.pressed(Button.U) && playerY > yBoundU + yOffset + (playerSize/2)) {
			if(playerY > yOffsetBorder + 32) {
				playerY -= playerSpeed;
			} else {
				yOffset += playerSpeed;
			}
		}
		// down
		if (p1.pressed(Button.D) && playerY < yBoundD + yOffset - (playerSize/2)) {
			if (playerY < HEIGHT - yOffsetBorder - 32) {
				playerY += playerSpeed;
			} else {
				yOffset -= playerSpeed;
			}
		}
		
		// speed increasing
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
		Arcadia.display(new Arcadia(new Darklight2()));
	}
}
