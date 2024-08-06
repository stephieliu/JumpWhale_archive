/**
 * Stephie Liu & Saivenkat Jilla
 * 2022/06/21
 * 
 * A 2D platformer game coded in Java! The goal of the game is to move the whale character over the
 * platforms using arrow keys to get the end of the map, where the whale's family is waiting for it!
 * 
 * Description:
 * This class manages the music in the game
 */

package ICSSummative;

//import statements
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.io.File;

//This class was built to play sound effects and music.
public class Music {
    private Clip clip;

    // Constructor for Music, called in GamePanel
    // Initializes clip
    public Music() {
        try {
            clip = AudioSystem.getClip(); // Initialize clip
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // plays music/sound effects based on file
    public void playMusic(String fileName) {
        try {
            File file = new File(fileName); // Create a file object from the file path
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());

            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream); // opens the clip
	        clip.setLoopPoints(0, -1);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);//loops the music

        } catch (Exception e) {
            e.printStackTrace();
        }
        clip.start(); // starts the clip
    }
}