import java.awt.Graphics2D;
import java.awt.Image;

import arcadia.Arcadia;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class darkLight2 extends Game {

	@Override
	public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image banner() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static void main (String[] args) {
		
		Arcadia.display(new Arcadia(new darkLight2()));
	}
	
}

