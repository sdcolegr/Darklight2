public class Weapon {
	
	String name;
	double damage;
	int spDamage;
	int delay;
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
			damage = 5;
			delay = 0;
			atType = 0;
			length = 64;
			width = 96;
		} else if (name.equals("Spear")){
			damage = 8;
			spDamage = 12;
			delay = 15;
			atType = 1;
			length = 128;
			width = 16;
		} else if (name.equals("Greatsword")){
			damage = 10;
			spDamage = 15;
			delay = 30;
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
		this.name = weaponName;
		if (name.equals("Short Sword")){
			damage = 5;
			delay = 0;
			atType = 0;
			length = 64;
			width = 96;
		} else if (name.equals("Spear")){
			damage = 8;
			spDamage = 12;
			delay = 15;
			atType = 1;
			length = 128;
			width = 16;
		} else if (name.equals("Greatsword")){
			damage = 10;
			spDamage = 15;
			delay = 30;
			atType = 2;
			length = 96;
			width = 192;
		}
	}
	
	public String getWeapon() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
