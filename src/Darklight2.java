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
	//Weapon weapon = new Weapon("Greatsword");
	//Weapon weapon = new Weapon("Spear");
	Weapon wep1 = new Weapon("Short Sword");
	boolean swordSpec = false;
	Weapon wep2 = new Weapon("Greatsword");
	Weapon wep3 = new Weapon("Spear");
	Weapon groundWeapon = new Weapon("Greatsword");
	Weapon[] inv = new Weapon[4];
	Sounds sound = new Sounds();
	Sounds weps = new Sounds();
	Sounds battle = new Sounds();
	Sounds wepspec = new Sounds();
	Sounds monster = new Sounds();
	int delay = 0;
	int spDelay = 0;
	String[] abcs = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z", };
			
	boolean active1 = true;
	boolean active2 = false;
	boolean active3 = false;
	boolean pressedBefore = false;
	boolean displayHighScores = false;
	StoreScores storeScores = new StoreScores();
	
	int wait = 0;
	int slot = 0;
	int count = 0;
	int xCoor = ((int)((Math.random() * WIDTH * 3) - WIDTH));
	int yCoor = ((int)((Math.random() * HEIGHT * 3) - HEIGHT));
	int i = 0;
	int j = 0;
	int k = 0;
	static int yourScore = 0;
	static int waveNum = 0;
	static int floorNum = 0;
	static String name = null;

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		
		// Menu
		if (gameState == 0) {
			// Song
			sound.loadSound("Resources/Sounds/Menu.wav");
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
			if (gameState == 0) {
			// Song
			sound.loadSound("Resources/Sounds/Menu.wav");
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
				if (justPressed(p1, Button.U)) {
					button = 2;
				}
				if (justPressed(p1, Button.D)) {
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
			} else if (button == 1){
				
				// LEADERBOARDS
				g.fillRect(WIDTH/2 - 160, 340, 320, 90);
				if (justPressed(p1, Button.U)) {
					button = 0;
				}
				if (justPressed(p1, Button.D)) {
					button = 2;
				}
				if (justPressed(p1, Button.A) ||
					justPressed(p1, Button.B) ||
					justPressed(p1, Button.C)) {
					gameState = 5;
				}
			} else {
				// EXIT
				g.fillRect(WIDTH/2 - 160, 440, 320, 90);
				if (justPressed(p1, Button.U) ) {
					button = 1;
				}
				if (justPressed(p1, Button.D)) {
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
			g.fillRect(WIDTH/2 - 150, 450, 300, 70);
			// option box text
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			centerText("PLAY", g, WIDTH/2, 295);
			centerText("LEADERBOARD", g, WIDTH/2, 395);
			centerText("EXIT", g, WIDTH/2, 495);
			
//			g.setColor(Color.YELLOW);
//			g.fillRect(WIDTH/2 - 352, 20, 704, 192);
		}
		}
		// Game
		if (gameState == 1) {
			arena.draw(g);
			
			// Sound
			sound.loadSound("Resources/Sounds/ambient.wav");
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
			wave.maintain(g, arena, player, swordSpec);
			
			//If enemy spots, play battle sound
			for (Enemy enemy : wave.enemies.values()) {
				if (enemy.spottedPlayer) {
					
					// Check to play noise
					int temp = (int) (Math.random() * 150);
					if (temp <= 1) {
						// Check for Ghoul or Golem
						if (enemy.type <= 60) {
							// Play Ghoul sound
							monster.loadSound("Resources/Sounds/ghoul.wav");
							monster.run();
						}
						
						if (enemy.type > 85) {
							// Play golem sound
							monster.loadSound("Resources/Sounds/golem.wav");
							monster.run();
						}
					}
					
					battle.loadSound("Resources/Sounds/Game Song.wav");
					battle.runLoop();
					break;
				}
			}
			
			// Stop battle sound, new wave
			if (wave.waveStart && wave.wave != 0) {
				battle.stop();
				battle.reset();
			}
			
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
				battle.stop();
				battle.reset();
			}

			if (justPressed(p1, Button.C)) {
				wave.enemies.clear();
			}

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, (int)(player.x - 256), HEIGHT);
			g.fillRect((int)(player.x + 256), 0, (int)(WIDTH - player.x - 255), HEIGHT);
			g.fillRect(0, 0, WIDTH, (int)(player.y - 256));
			g.fillRect(0, (int)(player.y + 256), WIDTH, (int)(HEIGHT - player.y - 255));
			g.drawImage(TextureLoader.light, (int)(player.x - 256), (int)(player.y - 256), null);
			
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
				wave.transition.reset();
				wave.waveStart = true;
				wave.difficulty *= 1.1;
				gameState = 1;
			}
		}
		
		// Game Over
		if (gameState == 3) {
			
			//Sound
			sound.loadSound("Resources/Sounds/Death.wav");
			sound.runOnce();
	 
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 150));
			centerText("GAME OVER", g, WIDTH/2, HEIGHT/2);
			wait++;
			yourScore = wave.score;
			waveNum = (wave.wave % 10 == 0) ? 10 : wave.wave % 10;
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
		// Enter Your initials
		if (gameState == 4) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.PLAIN, 45));
			centerText("Enter Your Initials", g, (Game.WIDTH / 2) ,(Game.HEIGHT / 4));
			centerText("Press C to enter", g, (Game.WIDTH / 2) ,(Game.HEIGHT / 2 + 100));
			g.drawString(abcs[i],(Game.WIDTH / 2) - 55,(Game.HEIGHT / 2));
			g.drawString(abcs[j],(Game.WIDTH / 2),(Game.HEIGHT / 2));
			g.drawString(abcs[k],(Game.WIDTH / 2) + 55,(Game.HEIGHT / 2));
			name = abcs[i] + abcs[j] + abcs[k];
			
			if (active1)
				g.fillRect((Game.WIDTH / 2) - 55, (Game.HEIGHT / 2) + 5, 35, 5);
			if (active2)
				g.fillRect((Game.WIDTH / 2) + 5, (Game.HEIGHT / 2) + 5, 35, 5);
			if (active3)
				g.fillRect((Game.WIDTH / 2) + 55, (Game.HEIGHT / 2) + 5, 35, 5);
			
			if (!pressedBefore) {
				if (p1.pressed(Button.U) || p1.pressed(Button.D)
						|| p1.pressed(Button.L) || p1.pressed(Button.R)
						|| p1.pressed(Button.A) || p1.pressed(Button.B)
						|| p1.pressed(Button.C)) {
					pressedBefore = true;
				} else {
					pressedBefore = false;
				}
			} else {
				if (!p1.pressed(Button.U) && !p1.pressed(Button.D)
						&& !p1.pressed(Button.L) && !p1.pressed(Button.R)
						&& !p1.pressed(Button.A) && !p1.pressed(Button.B)
						&& !p1.pressed(Button.C)) {
					pressedBefore = false;
				} else {
					return;
				}
			}
			if (p1.pressed(Button.C)) {
				storeScores.main();
				reset();
				gameState = 5;
			}
			if (active1) {

				if (p1.pressed(Button.U)) {
					i += 1;
					if (i > abcs.length - 1)
						i = 0;
				}
				if (p1.pressed(Button.D)) {
					i -= 1;
					if (i < 0)
						i = abcs.length - 1;
				}
				if (p1.pressed(Button.L)) {
					active1 = false;
					active3 = true;
				}
				if (p1.pressed(Button.R)) {
					active1 = false;
					active2 = true;
				}
				return;
			}
			if (active2) {

				if (p1.pressed(Button.U)) {
					j += 1;
					if (j > abcs.length - 1)
						j = 0;
				}
				if (p1.pressed(Button.D)) {
					j -= 1;
					if (j < 0)
						j = abcs.length - 1;
				}
				if (p1.pressed(Button.L)) {
					active2 = false;
					active1 = true;
				}
				if (p1.pressed(Button.R)) {
					active2 = false;
					active3 = true;
				}
				return;
			}
			if (active3) {

				if (p1.pressed(Button.U)) {
					k += 1;
					if (k > abcs.length - 1)
						k = 0;
				}
				if (p1.pressed(Button.D)) {
					k -= 1;
					if (k < 0)
						k = abcs.length - 1;
				}
				if (p1.pressed(Button.L)) {
					active3 = false;
					active2 = true;
				}
				if (p1.pressed(Button.R)) {
					active3 = false;
					active1 = true;
				}
				return;
			}
		}
		
		// high scores
		if (gameState == 5) {
			storeScores.Read();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.PLAIN, 35));
			centerText("High Scores", g, (Game.WIDTH / 2) ,(Game.HEIGHT / 16));
			g.drawLine(0, Game.HEIGHT/16 + 8, 1200, Game.HEIGHT / 16 + 8);
			g.drawString("Enemies killed", Game.WIDTH / 2 + 150, Game.HEIGHT / 16 + 60);
			g.drawString("Wave", Game.WIDTH / 2 , Game.HEIGHT / 16 + 60 );
			g.drawString("Floor", Game.WIDTH / 2 - 210, Game.HEIGHT / 16 + 60);
			int height = - 80;
			for (int l = 0; l < 10; l++) {
				g.drawString(storeScores.nameList.get(l), Game.WIDTH / 2 - 400, Game.HEIGHT / 8 - height);
				height = height - 40;
			}
			//g.drawString(storeScores, Game.WIDTH / 2, Game.HEIGHT / 2);
			wait++;
			if (wait == 1005) {
				sound.stop();
				sound.reset();
				wait = 0;
				yourScore = 0;
				waveNum = 0;
				i = 0;
				j = 0;
				k = 0;
				floorNum = 0;
				gameState = 0;
			}
		}
		
		updateKeyState(p1);
	}
	
	public void generateMana() {
		if (player.mana < 100) {
			player.mana += 5;
			if (player.mana > 100) {
				player.mana = 100;
			}
		}
	}
public void basicAttack(Graphics2D g, Input p1) {

		if (justPressed(p1, Button.A) && delay == 0) {
			delay = player.weapon.delay;
			if (player.weapon.name.equals("Short Sword")) {
				
				weps.loadSound("Resources/Sounds/short.wav");
				weps.run();
				
				// UP
				if (player.direction == 0) {
					AnimationLoader.wick.setAnimation(14);
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset - enemy.size/2 <= player.x + 48 &&
							enemy.x + arena.xOffset + enemy.size/2 >= player.x - 48 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y - 33 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 96) {
							if (swordSpec) {
								enemy.health -= (1.5 * player.weapon.damage); 
							} else {
								enemy.health -= player.weapon.damage;
							}
							generateMana();
						}
					}
					g.drawRect((int)(player.x - 48), (int)(player.y - 96), weapon.width, weapon.length);
				}
				// DOWN
				if (player.direction == 1) {
				AnimationLoader.wick.setAnimation(4);
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x - 48 &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x + 48 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y + 36 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 96) {
							if (swordSpec) {
								enemy.health -= (1.5 * player.weapon.damage); 
							} else {
								enemy.health -= player.weapon.damage;
							}
							generateMana();
						}
					}
					g.drawRect((int)(player.x - 48), (int)(player.y + 32), weapon.width, weapon.length);
				}
				// LEFT
				if (player.direction == 2) {
					AnimationLoader.wick.setAnimation(6);
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x - 96 &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x - 36 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32) {
							if (swordSpec) {
								enemy.health -= (1.5 * player.weapon.damage); 
							} else {
								enemy.health -= player.weapon.damage;
							}
							generateMana();
						}
					}
					g.drawRect((int)(player.x - 96), (int)(player.y - 48), weapon.length, weapon.width);
				}
				// RIGHT
				if (player.direction == 3) {
					AnimationLoader.wick.setAnimation(5);
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset - enemy.size/2 <= player.x + 96 &&
							enemy.x + arena.xOffset + enemy.size/2 >= player.x + 36 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32) {
							if (swordSpec) {
								enemy.health -= (1.5 * player.weapon.damage); 
							} else {
								enemy.health -= player.weapon.damage;
							}
							generateMana();
						}
					}
					g.drawRect((int)(player.x + 32), (int)(player.y - 48), weapon.length, weapon.width);
				}
			}
			if (player.weapon.name.equals("Greatsword")) {
				
				weps.loadSound("Resources/Sounds/great.wav");
				weps.run();
				
				// UP
				if (player.direction == 0) {
					
					AnimationLoader.wick.setAnimation(15);

					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 > player.x - 32 &&
							enemy.x + arena.xOffset - enemy.size/2 < player.x + 32 &&
							enemy.y + arena.yOffset + enemy.size/2 > player.y - 96 &&
							enemy.y + arena.yOffset - enemy.size/2 < player.y - 33) {
							enemy.health -= player.weapon.damage;
							enemy.y -= 32;
							generateMana();
						}
					}
					g.drawRect((int)(player.x - 96), (int)(player.y - 96), weapon.width, weapon.length);
				}
				// DOWN
				if (player.direction == 1) {
					AnimationLoader.wick.setAnimation(7);

					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset - enemy.size/2 < player.x + 32 &&
							enemy.x + arena.xOffset + enemy.size/2 > player.x - 32 &&
							enemy.y + arena.yOffset - enemy.size/2 < player.y + 96 &&
							enemy.y + arena.yOffset + enemy.size/2 > player.y + 33) {
							enemy.health -= player.weapon.damage;
							enemy.y += 32;
							generateMana();
						}
					}
					g.drawRect((int)(player.x - 96), (int)player.y, weapon.width, weapon.length);
				}
				// LEFT
				if (player.direction == 2) {
					AnimationLoader.wick.setAnimation(9);

					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset - enemy.size/2 < player.x - 32 &&
							enemy.x + arena.xOffset + enemy.size/2 > player.x - 96 &&
							enemy.y + arena.yOffset - enemy.size/2 < player.y + 32 &&
							enemy.y + arena.yOffset + enemy.size/2 > player.y - 32) {
							enemy.health -= player.weapon.damage;
							enemy.x -= 32;
							generateMana();
						}
					}
					g.drawRect((int)(player.x - 96), (int)(player.y - 96), weapon.length, weapon.width);
				}
				// RIGHT
				if (player.direction == 3) {
					AnimationLoader.wick.setAnimation(8);

					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 > player.x + 32 &&
							enemy.x + arena.xOffset - enemy.size/2 < player.x + 96 &&
							enemy.y + arena.yOffset + enemy.size/2 > player.y - 32 &&
							enemy.y + arena.yOffset - enemy.size/2 < player.y + 32) {
							enemy.health -= player.weapon.damage;
							enemy.x += 32;
							generateMana();
						}
					}
					g.drawRect((int)player.x, (int)(player.y - 96), weapon.length, weapon.width);
				}
			}
			if (player.weapon.name.equals("Spear")) {
				
				weps.loadSound("Resources/Sounds/spear.wav");
				weps.run();
				
				// UP
					if (player.direction == 0) {
						AnimationLoader.wick.setAnimation(12);

					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x + weapon.width &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x + weapon.width &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - weapon.length -32 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y -32) {
							enemy.health -= player.weapon.damage;
							generateMana();

						}
					}
					g.drawRect((int)player.x, (int)(player.y - 160), weapon.width, weapon.length);
				}
				// DOWN
				if (player.direction == 1) {
					AnimationLoader.wick.setAnimation(13);

					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x + weapon.width &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x + weapon.width &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y  +32 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + weapon.length +32) {
							enemy.health -= player.weapon.damage;
							generateMana();
							
						}
					}
					g.drawRect((int)player.x, (int)(player.y + 32), weapon.width, weapon.length);
				}
				// LEFT
				if (player.direction == 2) {
					AnimationLoader.wick.setAnimation(11);

					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x - weapon.length -32 &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x - 32 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y  + weapon.width &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + weapon.width) {
							enemy.health -= player.weapon.damage;
							generateMana();

						}
					}
					g.drawRect((int)(player.x - 160), (int)player.y, weapon.length, weapon.width);
				}
				// RIGHT
				if (player.direction == 3) {
					AnimationLoader.wick.setAnimation(10);

					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x  + 32  &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x + 32 +  weapon.length &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y  +weapon.width &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + weapon.width) {
							enemy.health -= player.weapon.damage;
							generateMana();

						}
					}
					g.drawRect((int)(player.x + 32), (int)player.y, weapon.length, weapon.width);
				}
			}
		}
		if (delay > 0) {
			delay--;
		}
	}
	
	
	void swordSpecHelper(Graphics2D g) {
		
		if (swordSpec) {
			player.mana -= 0.5;
			g.setColor(Color.ORANGE);
			g.drawRect((int)(player.x - player.size/2 - 2), (int)(player.y - player.size/2 - 2), 68, 68);
			
			if (player.mana <= 0) {
				player.mana = 0;
				swordSpec = false;
				// Stop special noise
				wepspec.stop();
				wepspec.reset();
			}
		}
	}

	public void specialAttack(Graphics2D g, Input p1) {
	
		swordSpecHelper(g);
		
		if (justPressed(p1, Button.B)) {
			if (player.weapon.name.equals("Short Sword")) {
				if (swordSpec) {
					swordSpec = false;
					
					// Stop special noise
					wepspec.stop();
					wepspec.reset();
					
				} else if (!swordSpec && player.mana > 0) {
					// Start special noise
					weps.loadSound("Resources/Sounds/special.wav");
					weps.run();
					
					wepspec.loadSound("Resources/Sounds/fire.wav");
					wepspec.runLoop();
					swordSpec = true;
				}
			}
			if (player.weapon.name.equals("Greatsword") && player.mana >= 40 && spDelay == 0) {
				
				//Greatsword spec sound
				weps.loadSound("Resources/Sounds/special.wav");
				weps.run();
				
				player.mana -= 40;
				spDelay = player.weapon.delay;
				for (Enemy enemy : wave.enemies.values()) {
					if (enemy.x + enemy.size/2 + arena.xOffset > player.x - 128 &&
						enemy.x - enemy.size/2 + arena.xOffset < player.x + 128 &&
						enemy.y + enemy.size/2 + arena.yOffset > player.y - 128 &&
						enemy.y - enemy.size/2 + arena.yOffset < player.y + 128) {
						enemy.health -= player.weapon.spDamage;
						//UP
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x - 32 &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x + 32 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 96 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y - 33) {
							enemy.y -= 32;
						}
						//DOWN
						if (enemy.x + arena.xOffset - enemy.size/2 <= player.x + 32 &&
							enemy.x + arena.xOffset + enemy.size/2 >= player.x - 32 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 96 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y + 33) {
							enemy.y += 32;
						}			
						//LEFT
						if (enemy.x + arena.xOffset - enemy.size/2 <= player.x - 32 &&
							enemy.x + arena.xOffset + enemy.size/2 >= player.x - 96 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32) {
							enemy.x -= 32;
						}
						//RIGHT
						if (enemy.x + arena.xOffset + enemy.size/2 >= player.x + 32 &&
							enemy.x + arena.xOffset - enemy.size/2 <= player.x + 96 &&
							enemy.y + arena.yOffset + enemy.size/2 >= player.y - 32 &&
							enemy.y + arena.yOffset - enemy.size/2 <= player.y + 32) {
							enemy.x += 32;
						}
					}
				}
				g.drawRect((int)(player.x - 128), (int)(player.y - 128), 256, 256);
			}
			if (player.weapon.name.equals("Spear") && player.mana >= 25 && spDelay == 0) {
				
				wepspec.loadSound("Resources/Sounds/special.wav");
				wepspec.run();
				
				player.mana -= 25;
				spDelay = player.weapon.delay;
				// UP
				if (player.direction == 0) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + enemy.size/2 + arena.xOffset > player.x - player.size/2 - 16 &&
							enemy.x - enemy.size/2 + arena.xOffset < player.x + player.size/2 + 16 &&
							enemy.y + enemy.size/2 + arena.yOffset > player.y - player.size/2 - 192 &&
							enemy.y - enemy.size/2 + arena.yOffset < player.y - player.size/2) {
							enemy.health -= player.weapon.spDamage;
							enemy.y -= 32;
						}
					}
					g.drawRect((int)(player.x - player.size/2 - 16), (int)(player.y - player.size/2 - 192), 96, 192);
				}
				// DOWN
				if (player.direction == 1) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + enemy.size/2 + arena.xOffset > player.x - player.size/2 - 16 &&
							enemy.x - enemy.size/2 + arena.xOffset < player.x + player.size/2 + 16 &&
							enemy.y + enemy.size/2 + arena.yOffset > player.y + player.size/2 &&
							enemy.y - enemy.size/2 + arena.yOffset < player.y + player.size/2 + 192) {
							enemy.health -= player.weapon.spDamage;
							enemy.y += 32;
						}
					}
					g.drawRect((int)(player.x - player.size/2 - 16), (int)(player.y + player.size/2), 96, 192);
				}
				// LEFT
				if (player.direction == 2) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + enemy.size/2 + arena.xOffset > player.x - player.size/2 - 192 &&
							enemy.x - enemy.size/2 + arena.xOffset < player.x - player.size/2 &&
							enemy.y + enemy.size/2 + arena.yOffset > player.y - player.size/2 - 16 &&
							enemy.y - enemy.size/2 + arena.yOffset < player.y + player.size/2 + 16) {
							enemy.health -= player.weapon.spDamage;
							enemy.x -= 32;
						}
					}
					g.drawRect((int)(player.x - player.size/2 - 192), (int)(player.y - player.size/2 - 16), 192, 96);
				}
				// RIGHT
				if (player.direction == 3) {
					for (Enemy enemy : wave.enemies.values()) {
						if (enemy.x + enemy.size/2 + arena.xOffset > player.x + player.size/2 &&
							enemy.x - enemy.size/2 + arena.xOffset < player.x + player.size/2 + 192 &&
							enemy.y + enemy.size/2 + arena.yOffset > player.y - player.size/2 - 16 &&
							enemy.y - enemy.size/2 + arena.yOffset < player.y + player.size/2 + 16) {
							enemy.health -= player.weapon.spDamage;
							enemy.x += 32;
						}
					}
					g.drawRect((int)(player.x + player.size/2), (int)(player.y - player.size/2 - 16), 192, 96);
				}
			}
		}
		if (spDelay > 0) {
			spDelay--;
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
				if(!wep2.weaponPickedUp() && wave.wave == 3) {
					wep2.setPickedUp();
					inv[1] = wep2;
					xCoor = ((int)((Math.random() * WIDTH * 3) - WIDTH));
					yCoor = ((int)((Math.random() * HEIGHT * 3) - HEIGHT));
					System.out.println("Picked up weapon!");
				}
				else if (!wep3.weaponPickedUp()){
					wep3.setPickedUp();
					if(!wep2.weaponPickedUp()) {
						inv[1] = wep3;
					}
					else {
						inv[2] = wep3;
					}
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
				player.weapon.setWeapon(inv[0].getName());
				slot = 0;
			}
			else {
				if (swordSpec) {
					wepspec.stop();
					wepspec.reset();
					swordSpec = false;
				}
				player.weapon.setWeapon(inv[slot + 1].getName());
				slot++;
				delay = 0;
				spDelay = 0;
			}
			System.out.println(slot + " " + inv[slot].getName());
		}
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
		delay = 0;
		spDelay = 0;
		swordSpec = false;
	}

	@Override
	public Image banner() {
		// 512 x 128
		return null;
	}

	public static void main(String[] args) {
		TextureLoader.load();
		AnimationLoader.load();
		Arcadia.display(new Arcadia(new Darklight2()));
	}
	public static String Name() {
		return name;
	}
}
