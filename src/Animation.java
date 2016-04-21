import java.awt.Image;
import java.util.ArrayList;

public class Animation {
	private ArrayList<Image> frames = new ArrayList<>();
	
	public Image getFrame(int id) {
		return frames.get(id);
	}
	
	public int getFrames() {
		return frames.size();
	}
	
	public void addFrame(Image i) {
		frames.add(i);
	}
}
