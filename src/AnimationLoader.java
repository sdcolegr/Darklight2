
public class AnimationLoader {

	
	public static SpriteAtlas potion;
	public static SpriteAtlas potionmana;
	public static SpriteAtlas golem;
	public static SpriteAtlas eye;
	public static SpriteAtlas ghoul;
	public static SpriteAtlas wick;
	
	//public static SpriteAtlas character2;
	
	

	public static void load() {
		
		wick = new SpriteAtlas("Animations/wick.png","Animations/wick.atlas");
		potion = new SpriteAtlas("Animations/healthpot.png","Animations/pot.atlas.txt");
		potionmana = new SpriteAtlas("Animations/manapot.png","Animations/pot.atlas.txt");
		golem = new SpriteAtlas("Animations/golem.png","Animations/golem.atlas");
		eye = new SpriteAtlas("Animations/eye.png","Animations/eye.atlas");
		ghoul = new SpriteAtlas("Animations/ghoul.png","Animations/ghoul.atlas.txt");
		
		//character2 = new SpriteAtlas("Resources/girl.png","Resources/animated.atlas");


	}
}
