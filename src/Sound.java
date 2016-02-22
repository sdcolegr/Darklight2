import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.*;

public class Sound {

	private Clip clip;
	private boolean soundPlayed = false;
	private boolean runOnce = false;
	InputStream is = null;
	private AudioFormat af = null;
	private AudioInputStream audioInputStream = null;
	private int size = 0;
	byte[] audio = null;
	DataLine.Info info = null;

	public void loadSound(String fileName) {

		try {
			is= getClass().getResourceAsStream(fileName); 
			audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			af = audioInputStream.getFormat();
			size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
			audio = new byte[size];
			info = new DataLine.Info(Clip.class, af, size);
			audioInputStream.read(audio, 0, size);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {

		try {
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(af, audio, 0, size);
		}

		catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		clip.start();
	}

	public void runOnce() {
		if (!soundPlayed) {
			try {
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(af, audio, 0, size);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}

			clip.start();
			soundPlayed = true;
		}
	}

	public void runLoop() {
		if (!runOnce) {
			try {
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(af, audio, 0, size);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			runOnce = true;
		}
	}

	public void stop() {
		clip.stop();
	}
}