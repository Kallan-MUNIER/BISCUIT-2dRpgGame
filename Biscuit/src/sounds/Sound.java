package sounds;

import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	ArrayList<URL> soundsUrl = new ArrayList<>();
	
	public Sound() {
		soundsUrl.add(getClass().getResource("/sounds/main.wav"));
		soundsUrl.add(getClass().getResource("/sounds/powerup.wav"));
		soundsUrl.add(getClass().getResource("/sounds/unlock.wav"));
	}
	
	public void setFile(int i, float volume) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundsUrl.get(i));
			clip = AudioSystem.getClip();
			clip.open(ais);
			setVolume(volume);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setVolume(float volume) {
	    if (volume < 0f || volume > 1f)
	        throw new IllegalArgumentException("Volume not valid: " + volume);
	    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
	    gainControl.setValue(20f * (float) Math.log10(volume));
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}