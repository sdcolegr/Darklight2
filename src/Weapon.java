
public class Weapon {
	
	String name;
	int damage;
	int speed;
	
	//type of attack:
	//slash, stab, etc
	// 0 = slash
	// 1 = stab
	// 2 = arc
	int atType;
	
	int length;
	int width;
	
	boolean held;
	
	
	public Weapon(String name){
		this.name = name;
		
		if(name.equals("Short Sword")){
			//damage = ;
			//speed = ;
			atType = 0;
			length = 64;
			width = 96;
		}
		else if(name.equals("Spear")){
			//damage = ;
			//speed = ;
			atType = 1;
			length = 128;
			width = 16;
		}
		else if(name.equals("Greatsword")){
			//damage = ;
			//speed = ;
			atType = 2;
			length = 96;
			width = 192;
		}
	}
	
	
	
	
	
}
