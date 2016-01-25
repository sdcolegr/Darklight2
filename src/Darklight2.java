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
	
	int gameState = 0;
	private HashMap<Button, Boolean> keyState = new HashMap<Button, Boolean>();
	int button = 0;
	
	Arena arena = new Arena();
	Player player = new Player(0, WIDTH/2, HEIGHT/2, 64, 10, 8);
	Wave wave = new Wave();
	
	int cooldown = 0;

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		
		// Menu
		if (gameState == 0) {
			
			// background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			// title
			g.setColor(Color.GREEN);
			g.setFont(new Font("Arial", Font.BOLD, 100));
			centerText("DARKLIGHT 2", g, WIDTH/2, 180);
			
			// selection
			g.setColor(Color.RED);
			if (button == 0) {
				g.fillRect(WIDTH/2 - 160, 240, 320, 90);
				if (justPressed(p1, Button.U) || justPressed(p1, Button.D)) {
					button = 1;
				}
				if (justPressed(p1, Button.A) ||
					justPressed(p1, Button.B) ||
					justPressed(p1, Button.C)) {
					gameState = 1;
				}
			} else {
				g.fillRect(WIDTH/2 - 160, 340, 320, 90);
				if (justPressed(p1, Button.U) || justPressed(p1, Button.D)) {
					button = 0;
				}
				if (justPressed(p1, Button.A) ||
						justPressed(p1, Button.B) ||
						justPressed(p1, Button.C)) {
						System.exit(0);
					}
			}
			
			// option boxes
			g.setColor(Color.GREEN);
			g.fillRect(WIDTH/2 - 150, 250, 300, 70);
			g.fillRect(WIDTH/2 - 150, 350, 300, 70);
			
			// option box text
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			centerText("PLAY", g, WIDTH/2, 295);
			centerText("EXIT", g, WIDTH/2, 395);
		}
		
		// Game
		if (gameState == 1) {
			arena.draw(g);
			
//			// offset borders
//			g.setColor(Color.RED);
//			g.drawRect(0, 0, arena.xOffsetBorder, HEIGHT);								// left
//			g.drawRect(WIDTH - arena.xOffsetBorder, 0, arena.xOffsetBorder, HEIGHT);	// right
//			g.drawRect(0, 0, WIDTH, arena.yOffsetBorder);								// top
//			g.drawRect(0, HEIGHT - arena.yOffsetBorder, WIDTH, arena.yOffsetBorder);	// bottom
				
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
		
		updateKeyState(p1);
	}
	
	private void centerText(String text, Graphics2D g, int x, int y) {

		int width = g.getFontMetrics().stringWidth(text);
		g.drawString(text, x - (width / 2), y);
	}
	
	private void updateKeyState(Input p1) {

		Button[] buttons = { Button.A, Button.B, Button.C, Button.U, Button.D,
				Button.L, Button.R };

		for (Button b : buttons) {
			if (p1.pressed(b)) {
				keyState.put(b, true);
			} else {
				keyState.remove(b);
			}
		}
	}

	private boolean justPressed(Input p1, Button source) {
		return p1.pressed(source) && !keyState.containsKey(source);
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
		TextureLoader.load();
		Arcadia.display(new Arcadia(new Darklight2()));
	}
}
