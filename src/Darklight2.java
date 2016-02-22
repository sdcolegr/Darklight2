import java.awt.Color;
import java.awt.Font;
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
	Player player = new Player(0, WIDTH/2, HEIGHT/2);
	Wave wave = new Wave();
	
	Weapon sSword = new Weapon("Short Sword");
	Weapon gSword = new Weapon("Greatsword");
	Weapon spear = new Weapon("Spear");
	
	int wait = 0;

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
				
				// PLAY
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
				
				// EXIT
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
			player.weapon = sSword;
			attack(g, p1);
			if (player.health <= 0) {
				gameState = 2;
			}
	
			// wave
			wave.newWave(player, arena);
			wave.maintain(g, arena, player);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			centerText("Wave " + wave.wave, g, WIDTH/2, 50);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			centerText("Remaining enemies: " + wave.enemies.size(), g, WIDTH/2, 65);

			g.dispose();
		}
		
		// Game Over
		if (gameState == 2) {
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 150));
			centerText("GAME OVER", g, WIDTH/2, HEIGHT/2);
			wait++;
			if (wait == 60) {
				gameState = 0;
				reset();
			}
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
	
public void attack(Graphics2D g, Input p1) {

		// up attack
		if (p1.pressed(Button.A) && player.direction == 0 && justPressed(p1, Button.A)) {
			
			g.setColor(Color.cyan);
			
			if (player.weapon.name.equals("Greatsword")) {
				
				for (Enemy enemy : wave.enemies.values()) {
					if (enemy.x + arena.xOffset + enemy.size/2 >= player.x - 32
							&& enemy.x + arena.xOffset - enemy.size/2 <= player.x + 32
							&& enemy.y + arena.yOffset + enemy.size/2 >= player.y - 96
							&& enemy.y + arena.yOffset - enemy.size/2 <= player.y - 33) {
						
						//hitting goes here

						g.setColor(Color.pink);
						g.drawOval(enemy.x + arena.xOffset, enemy.y
								+ arena.yOffset, 10, 10);
						System.out.println("gSword is badass");
						enemy.health -= player.weapon.damage;

					}

				}
				
				g.drawRect(player.x - 96, player.y - 96, gSword.width,
						gSword.length);
			}
			
			if (player.weapon.name.equals("Short Sword")) {

				for (Enemy enemy : wave.enemies.values()) {
					if (enemy.x + arena.xOffset - enemy.size/2 <= player.x + 48
							&& enemy.x + arena.xOffset + enemy.size/2 >= player.x - 48
							&& enemy.y + arena.yOffset - enemy.size/2 <= player.y - 33
							&& enemy.y + arena.yOffset + enemy.size/2 >= player.y - 96) {

						g.setColor(Color.pink);
						g.drawOval(enemy.x + arena.xOffset, enemy.y
								+ arena.yOffset, 10, 10);
						System.out.println("YOU ALSO HIT " + enemy.id);
						enemy.health -= player.weapon.damage;
					}
				}

				g.drawRect(player.x - 48, player.y - 96, sSword.width,
						sSword.length);
			}
			
			if (player.weapon.name.equals("Spear")) {
				g.drawRect(player.x, player.y - 160, spear.width, spear.length);
			}
		}

		// right attack
		if (p1.pressed(Button.A) && player.direction == 1 && justPressed(p1, Button.A)) {
			
			g.setColor(Color.cyan);
			
			if (player.weapon.name.equals("Greatsword")) {

				for (Enemy enemy : wave.enemies.values()) {
					if (enemy.x + arena.xOffset + enemy.size/2 >= player.x + 32
							&& enemy.x + arena.xOffset - enemy.size/2 <= player.x + 96
							&& enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32
							&& enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32) {

						g.setColor(Color.GREEN);
						g.drawOval(enemy.x + arena.xOffset, enemy.y
								+ arena.yOffset, 10, 10);
						System.out.println("gSword is badass 2");
						enemy.health -= player.weapon.damage;
					}
				}

				g.drawRect(player.x, player.y - 96, gSword.length, gSword.width);
			}
			
			if (player.weapon.name.equals("Short Sword")) {

				for (Enemy enemy : wave.enemies.values()) {
					if (enemy.x + arena.xOffset - enemy.size/2 <= player.x + 96
							&& enemy.x + arena.xOffset + enemy.size/2 >= player.x + 36
							&& enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32
							&& enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32) {

						g.setColor(Color.BLUE);
						g.drawOval(enemy.x + arena.xOffset, enemy.y
								+ arena.yOffset, 10, 10);
						System.out.println("YOU HIT " + enemy.id);
						enemy.health -= player.weapon.damage;
					}
				}

				g.drawRect(player.x + 32, player.y - 48, sSword.length,
						sSword.width);
			}
			
			if (player.weapon.name.equals("Spear")) {
				g.drawRect(player.x + 32, player.y, spear.length, spear.width);
			}
		}

		// down attack
		if (p1.pressed(Button.A) && player.direction == 2 && justPressed(p1, Button.A)) {
			
			g.setColor(Color.cyan);
			
			if (player.weapon.name.equals("Greatsword")) {
				for (Enemy enemy : wave.enemies.values()) {
					if (enemy.x + arena.xOffset - enemy.size/2 <= player.x + 32
							&& enemy.x + arena.xOffset + enemy.size/2 >= player.x - 32
							&& enemy.y + arena.yOffset - enemy.size/2 <= player.y + 96
							&& enemy.y + arena.yOffset + enemy.size/2 >= player.y + 33) {

						g.setColor(Color.YELLOW);
						g.drawOval(enemy.x + arena.xOffset, enemy.y
								+ arena.yOffset, 10, 10);
						System.out.println("gSword is badass");
						enemy.health -= player.weapon.damage;
					}

				}
				
				g.drawRect(player.x - 96, player.y, gSword.width, gSword.length);
			}
			
			if (player.weapon.name.equals("Short Sword")) {

				for (Enemy enemy : wave.enemies.values()) {
					if (enemy.x + arena.xOffset + enemy.size/2 >= player.x - 48
							&& enemy.x + arena.xOffset - enemy.size/2 <= player.x + 48
							&& enemy.y + arena.yOffset + enemy.size/2 >= player.y + 36
							&& enemy.y + arena.yOffset - enemy.size/2 <= player.y + 96) {

						g.setColor(Color.YELLOW);
						g.drawOval(enemy.x + arena.xOffset, enemy.y
								+ arena.yOffset, 10, 10);
						System.out
								.println("YOU ALSO HIT THE ONE OTHER DIRECTION BRO "
										+ enemy.id);
						enemy.health -= player.weapon.damage;
					}
				}

				g.drawRect(player.x - 48, player.y + 32, sSword.width,
						sSword.length);
			}
			
			if (player.weapon.name.equals("Spear")) {
				g.drawRect(player.x, player.y + 32, spear.width, spear.length);
			}
			
		}

		// left attack
		if (p1.pressed(Button.A) && player.direction == 3 && justPressed(p1, Button.A)) {
			
			g.setColor(Color.cyan);
			
			if (player.weapon.name.equals("Greatsword")) {

				for (Enemy enemy : wave.enemies.values()) {
					if (enemy.x + arena.xOffset - enemy.size/2 <= player.x - 32
							&& enemy.x + arena.xOffset + enemy.size/2 >= player.x - 96
							&& enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32
							&& enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32) {

						g.setColor(Color.MAGENTA);
						g.drawOval(enemy.x + arena.xOffset, enemy.y
								+ arena.yOffset, 10, 10);
						System.out.println("gSword is badass 2");
						enemy.health -= player.weapon.damage;
					}

				}
				
				g.drawRect(player.x - 96, player.y - 96, gSword.length,
						gSword.width);
			}
			
			if (player.weapon.name.equals("Short Sword")) {

				for (Enemy enemy : wave.enemies.values()) {
					if (enemy.x + arena.xOffset + enemy.size/2 >= player.x - 96
							&& enemy.x + arena.xOffset - enemy.size/2 <= player.x - 36
							&& enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32
							&& enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32) {

						g.setColor(Color.GREEN);
						g.drawOval(enemy.x + arena.xOffset, enemy.y
								+ arena.yOffset, 10, 10);
						System.out.println("YOU HIT THE OTHER GUY " + enemy.id);
						enemy.health -= player.weapon.damage;
					}
				}

				g.drawRect(player.x - 96, player.y - 48, sSword.length,
						sSword.width);
			}
			
			if (player.weapon.name.equals("Spear")) {
				g.drawRect(player.x - 160, player.y, spear.length, spear.width);
			}
		}

	}
	
	@Override
	public void reset() {
		arena = new Arena();
		player = new Player(0, WIDTH/2, HEIGHT/2);
		wave = new Wave();
		wait = 0;
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
