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
	
	Weapon weapon = new Weapon("Short Sword");
	Weapon wep1 = new Weapon("Short Sword");
	Weapon wep2 = new Weapon("Greatsword");
	Weapon wep3 = new Weapon("Spear");
	Weapon groundWeapon = new Weapon("Greatsword");
	Weapon[] inv = new Weapon[4];
	Sounds sound = new Sounds();
	
	int wait = 0;
	int slot = 0;
	int count = 0;
	int xCoor = ((int)((Math.random() * WIDTH * 3) - WIDTH));
	int yCoor = ((int)((Math.random() * HEIGHT * 3) - HEIGHT));

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		
		// Menu
		if (gameState == 0) {
			// Song
			sound.loadSound("Resources/Menu.wav");
			sound.runLoop();
			
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
					inv[0] = wep1;
					gameState = 1;
					sound.reset();
					sound.stop();
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
			
//			g.setColor(Color.YELLOW);
//			g.fillRect(WIDTH/2 - 352, 20, 704, 192);
		}
		
		// Game
		if (gameState == 1) {
			arena.draw(g);
			
			// Sound
			sound.loadSound("Resources/Game Song.wav");
			sound.runLoop();
			
//			// offset borders
//			g.setColor(Color.RED);
//			g.drawRect(0, 0, arena.xOffsetBorder, HEIGHT);								// left
//			g.drawRect(WIDTH - arena.xOffsetBorder, 0, arena.xOffsetBorder, HEIGHT);	// right
//			g.drawRect(0, 0, WIDTH, arena.yOffsetBorder);								// top
//			g.drawRect(0, HEIGHT - arena.yOffsetBorder, WIDTH, arena.yOffsetBorder);	// bottom
			
			if (wave.falling) {
				count = 0;
				gameState = 2;
			}
			
			// wave
			wave.newWave(g, arena, player);
			wave.maintain(g, arena, player);
			
			// player
			player.draw(g);
			player.movement(p1, arena);
			player.weapon = weapon;
			basicAttack(g, p1);
			specialAttack(g, p1);
			weaponPickup(g, p1, arena);
			weaponSwap(p1);
			
			if (player.health <= 0) {
				gameState = 3;
				sound.stop();
				sound.reset();
			}

			if (justPressed(p1, Button.C)) {
				wave.enemies.clear();
			}

			g.setColor(Color.BLUE);
			g.drawOval((int)(player.x - 256), (int)(player.y - 256), 512, 512);
			
			// HUD
			int floor = (wave.wave % 10 == 0) ? (wave.wave + 9)/10 : (wave.wave + 10)/10; 
			int currentWave = (wave.wave % 10 == 0) ? 10 : wave.wave % 10;
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("Floor " + floor + " | Wave " + currentWave, 5, 20);
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			g.drawString("Remaining enemies: " + wave.enemies.size(), 5, 30);
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(347, 531, 330, 10);
			g.fillRect(347, 541, 10, 20);
			g.fillRect(507, 541, 10, 20);
			g.fillRect(667, 541, 10, 20);
			g.fillRect(347, 561, 330, 15);
			g.setColor(Color.GREEN);
			if (player.health <= 100) {
				g.fillRect(357, 541, (int)(150 * (player.health/100)), 20);
			} else {
				g.fillRect(357, 541, 150, 20);
				g.setColor(Color.ORANGE);
				g.fillRect(357, 541, (int)(150 * ((player.health - 100)/100)), 20);
			}
			g.setColor(Color.BLUE);
			g.fillRect(517 + (int)(150 - (150 * (player.mana/100))), 541, (int)(150 * (player.mana/100)), 20);
			
			g.dispose();
		}
		
		// Floor switching
		if (gameState == 2) {
			g.setColor(new Color (0, 0, 0, 4 * count));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			count++;
			if (count == 60) {
				arena = new Arena();
				player.x = WIDTH/2;
				player.y = HEIGHT/2;
				wave.falling = false;
				wave.hole = false;
				wave.waveStart = true;
				wave.difficulty *= 1.1;
				gameState = 1;
			}
		}
		
		// Game Over
		if (gameState == 3) {
			
			//Sound
			sound.loadSound("Resources/Death.wav");
			sound.runOnce();
	 
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 150));
			centerText("GAME OVER", g, WIDTH/2, HEIGHT/2);
			wait++;
			
			if(justPressed(p1, Button.A) || 
			   justPressed(p1, Button.B) || 
			   justPressed(p1, Button.C)){
				
				sound.stop();
				sound.reset();
				gameState=0;
				reset();
			}
			
			if (wait == 3000) {
				sound.stop();
				sound.reset();
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

	public boolean justPressed(Input p1, Button source) {
		return p1.pressed(source) && !keyState.containsKey(source);
	}
	
	public void basicAttack(Graphics2D g, Input p1) {

		if (justPressed(p1, Button.A)) {
			if (player.mana < 100) {
				player.mana += 8;
				if (player.mana > 100) {
					player.mana = 100;
				}
			}
			if (player.weapon.name.equals("Short Sword")) {
				// UP
				if (player.direction == 0) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset - enemy.size/2 <= player.x + 48 &&
							enemy.x + arena.xOffset + enemy.size/2 >= player.x - 48 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y - 33 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 96) {
							enemy.health -= player.weapon.damage;
						}
					}
					g.drawRect((int)(player.x - 48), (int)(player.y - 96), weapon.width, weapon.length);
				}
				// DOWN
				if (player.direction == 1) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x - 48 &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x + 48 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y + 36 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 96) {
							enemy.health -= player.weapon.damage;
						}
					}
					g.drawRect((int)(player.x - 48), (int)(player.y + 32), weapon.width, weapon.length);
				}
				// LEFT
				if (player.direction == 2) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x - 96 &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x - 36 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32) {
							enemy.health -= player.weapon.damage;
						}
					}
					g.drawRect((int)(player.x - 96), (int)(player.y - 48), weapon.length, weapon.width);
				}
				// RIGHT
				if (player.direction == 3) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset - enemy.size/2 <= player.x + 96 &&
							enemy.x + arena.xOffset + enemy.size/2 >= player.x + 36 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32) {
							enemy.health -= player.weapon.damage;
						}
					}
					g.drawRect((int)(player.x + 32), (int)(player.y - 48), weapon.length, weapon.width);
				}
			}
			if (player.weapon.name.equals("Greatsword")) {
				// UP
				if (player.direction == 0) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x - 32 &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x + 32 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 96 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y - 33) {
							enemy.health -= player.weapon.damage;
						}
					}
					g.drawRect((int)(player.x - 96), (int)(player.y - 96), weapon.width, weapon.length);
				}
				// DOWN
				if (player.direction == 1) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset - enemy.size/2 <= player.x + 32 &&
							enemy.x + arena.xOffset + enemy.size/2 >= player.x - 32 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 96 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y + 33) {
							enemy.health -= player.weapon.damage;
						}
					}
					g.drawRect((int)(player.x - 96), (int)player.y, weapon.width, weapon.length);
				}
				// LEFT
				if (player.direction == 2) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset - enemy.size/2 <= player.x - 32 &&
							enemy.x + arena.xOffset + enemy.size/2 >= player.x - 96 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32) {
							enemy.health -= player.weapon.damage;
						}
					}
					g.drawRect((int)(player.x - 96), (int)(player.y - 96), weapon.length, weapon.width);
				}
				// RIGHT
				if (player.direction == 3) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x + 32 &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x + 96 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32) {
							enemy.health -= player.weapon.damage;
						}
					}
					g.drawRect((int)player.x, (int)(player.y - 96), weapon.length, weapon.width);
				}
			}
			if (player.weapon.name.equals("Spear")) {
				// UP
				if (player.direction == 0) {
					g.drawRect((int)player.x, (int)(player.y - 160), weapon.width, weapon.length);
				}
				// DOWN
				if (player.direction == 1) {
					g.drawRect((int)player.x, (int)(player.y + 32), weapon.width, weapon.length);
				}
				// LEFT
				if (player.direction == 2) {
					g.drawRect((int)(player.x - 160), (int)player.y, weapon.length, weapon.width);
				}
				// RIGHT
				if (player.direction == 3) {
					g.drawRect((int)(player.x + 32), (int)player.y, weapon.length, weapon.width);
				}
			}
		}
	}

	public void specialAttack(Graphics2D g, Input p1) {
	
		if (justPressed(p1, Button.B) && player.weapon.name.equals("Greatsword") && player.mana >= 40) {
			player.mana -= 40;
			for (Enemy enemy : wave.enemies.values()) {
				if (enemy.x + enemy.size/2 + arena.xOffset >= player.x - 128 &&
					enemy.x - enemy.size/2 + arena.xOffset <= player.x + 128 &&
					enemy.y + enemy.size/2 + arena.yOffset >= player.y - 128 &&
					enemy.y - enemy.size/2 + arena.yOffset <= player.y + 128) {
					
					g.setColor(Color.pink);
					g.drawOval((int)(enemy.x + arena.xOffset), (int)(enemy.y + arena.yOffset), 10, 10);
					enemy.health -= player.weapon.spDamage;
				}
			}
			g.drawRect((int)(player.x - 128), (int)(player.y - 128), 256, 256);
		}
	}
	
	public void weaponPickup(Graphics2D g, Input p1, Arena arena) {
	
		g.setColor(Color.MAGENTA);
	
		if(!wep2.weaponPickedUp() && wave.wave == 3) {
			g.fillRect((int)(xCoor - wep2.width/2 + arena.xOffset), 
					(int)(yCoor - wep2.length/2 + arena.yOffset), wep2.width, wep2.length);
			System.out.println(xCoor + " " + yCoor);
		}
		
		if(!wep3.weaponPickedUp() && wave.wave == 5) {
			g.fillRect((int)(xCoor - wep3.width/2 + arena.xOffset), 
					(int)(yCoor - wep3.length/2 + arena.yOffset), wep3.width, wep3.length);
			System.out.println(xCoor + " " + yCoor);
		}
		
		if (wave.wave == 3 || wave.wave == 5) {
			if ((player.x + player.size/2 >= xCoor - groundWeapon.width/2 + arena.xOffset) &&
					(player.x - player.size/2 <= xCoor + groundWeapon.width/2 + arena.xOffset) &&
					(player.y + player.size/2 >= yCoor - groundWeapon.length/2 + arena.yOffset) &&			
				
					(player.y - player.size/2 <= yCoor + groundWeapon.length/2 + arena.yOffset)) {
				if(!wep2.weaponPickedUp()) {
					wep2.setPickedUp();
					inv[1] = wep2;
					xCoor = ((int)((Math.random() * WIDTH * 3) - WIDTH));
					yCoor = ((int)((Math.random() * HEIGHT * 3) - HEIGHT));
					System.out.println("Picked up weapon!");
				}
				else if (!wep3.weaponPickedUp()){
					wep3.setPickedUp();
					inv[2] = wep3;
					xCoor = ((int)((Math.random() * WIDTH * 3) - WIDTH));
					yCoor = ((int)((Math.random() * HEIGHT * 3) - HEIGHT));
					System.out.println("Picked up weapon!");
				}
			}
		}
	}
	
	public void weaponSwap(Input p1) {
		if (justPressed(p1, Button.C)) {
			if (inv[slot+1] == null) {
				weapon.setWeapon(inv[0].getName());
				slot = 0;
			}
			else {
				weapon.setWeapon(inv[slot + 1].getName());
				slot++;
			}
			System.out.println(slot + " " + inv[slot].getName());
		}
	}
	
	@Override
	public void reset() {
		arena = new Arena();
		player = new Player(0, WIDTH/2, HEIGHT/2);
		wave = new Wave();
		wait = 0;
		slot = 0;
		weapon.setWeapon(inv[0].getName());
		inv[1] = null;
		inv[2] = null;
		wep2.pickedUp = false;
		wep3.pickedUp = false;
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
