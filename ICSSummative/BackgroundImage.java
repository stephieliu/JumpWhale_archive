/**
 * Stephie Liu & Saivenkat Jilla
 * 2022/06/21
 * 
 * A 2D platformer game coded in Java! The goal of the game is to move the whale character over the
 * platforms using arrow keys to get the end of the map, where the whale's family is waiting for it!
 * 
 * Description:
 * This class manages the background images of the gamepanel
 */

package ICSSummative;

import javax.swing.*;
import java.awt.*;

public class BackgroundImage {
	Image Pic;//declares the image pic

    //allows for backgrounds to be changed easily using a string argument for the constructor class
    public BackgroundImage(String s){
    	Pic = new ImageIcon("Images/"+s).getImage();
    }

    public void draw(Graphics g){//draws the image and makes sure it stays within the frame
        g.setColor(Color.WHITE);
        g.drawImage(Pic, 0, 0, Pic.getWidth(null), GamePanel.GAME_HEIGHT, null);
    }
}