/**
 * Stephie Liu & Saivenkat Jilla
 * 2022/06/21
 * 
 * A 2D platformer game coded in Java! The goal of the game is to move the whale character over the
 * platforms using arrow keys to get the end of the map, where the whale's family is waiting for it!
 * 
 * Description:
 * This class manages the health bar of the character in the upper right corner
 */

package ICSSummative;

//import statements
import java.awt.*;
import java.awt.event.*;

public class Health extends Rectangle{
    public static int HEALTH_WIDTH = 100;// the initial value of the healthbar's width, this will be modified later
    public static final int HEALTH_HEIGHT = 20;// the height of the health bar
    public static char colour = 'c';// colour of the health bar

    public Health(int x, int y, int w){
        super(x, y, w, HEALTH_HEIGHT);
        HEALTH_WIDTH = w; // health bar updates      
    }

    public void draw (Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(x, y-1, 101, 22); // placed under so that health bar is visible
        g.setColor(Color.green);
        g.fillRect(x, y, HEALTH_WIDTH, HEALTH_HEIGHT);// the actual health bar
    }
}