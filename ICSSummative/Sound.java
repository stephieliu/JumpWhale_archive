/**
 * Stephie Liu & Saivenkat Jilla
 * 2022/06/21
 * 
 * A 2D platformer game coded in Java! The goal of the game is to move the whale character over the
 * platforms using arrow keys to get the end of the map, where the whale's family is waiting for it!
 * 
 * Description:
 * This class manages the sounds played during the game
 */

package ICSSummative;

//import statements
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.*;
import java.io.IOException;

public class Sound {
    private URL url;//stores the path
    private Clip clip;//for the sound clip
    public Sound(String requestedSound) {//the constructor receives a string, which is the path to the sound file
        url = this.getClass().getResource(requestedSound);//stores the file path
        if (url != null) {
            try {
                // open an audio input stream
                // get a sound clip resource
                // open audio clip and load samples from the audio input stream
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                clip.open(audioInput);

            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    //plays the sound clip
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    //loops the sound clip
    public void loop(){
        clip.setFramePosition(0);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);//loops the clip continuously
    }

    //stops playing the sound
    public void stop(){
        clip.stop();
    }
}