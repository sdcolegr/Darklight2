public class Weapon {
	
	String name;
	int damage;
	int speed;
	boolean pickedUp = false;
	
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
		
		if (name.equals("Short Sword")){
			damage = 2;
			//speed = ;
			atType = 0;
			length = 64;
			width = 96;
		} else if (name.equals("Spear")){
			damage = 5;
			//speed = ;
			atType = 1;
			length = 128;
			width = 16;
		} else if (name.equals("Greatsword")){
			damage = 50;
			//speed = ;
			atType = 2;
			length = 96;
			width = 192;
		}
	}	
	
	public boolean weaponPickedUp() {
		return pickedUp;
	}
	
	public void setPickedUp() {
		pickedUp = true;
	}
	
	public void setWeapon(String weaponName) {
		name = weaponName;
		if (name.equals("Short Sword")){
			damage = 2;
			//speed = ;
			atType = 0;
			length = 64;
			width = 96;
		} else if (name.equals("Spear")){
			damage = 5;
			//speed = ;
			atType = 1;
			length = 128;
			width = 16;
		} else if (name.equals("Greatsword")){
			damage = 50;
			//speed = ;
			atType = 2;
			length = 96;
			width = 192;
		}
	}
	
	public String getWeapon() {
		return name;
	}
	
	
}
