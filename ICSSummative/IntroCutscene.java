/**
 * Stephie Liu & Saivenkat Jilla
 * 2022/06/21
 * 
 * A 2D platformer game coded in Java! The goal of the game is to move the whale character over the
 * platforms using arrow keys to get the end of the map, where the whale's family is waiting for it!
 * 
 * Description:
 * This class manages the initial cutscene at the start of the game
 */
package ICSSummative;

//import statements
import java.awt.*;
import javax.swing.*;

public class IntroCutscene extends JFrame{
    public IntroCutscene(){
        this.setTitle("Intro!!");//title

        //set other properties for the window
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// makes it disappear when closed, removes resources it is using
        this.setSize(1200, 700);
        this.setBackground(Color.DARK_GRAY);
        this.setResizable(false);
        this.setLocationRelativeTo(null);//set window in middle of screen

        Icon icon = new ImageIcon("Images/IntroCutscene.gif");
        if (System.currentTimeMillis() - GamePanel.startTime < 21750) {// only starts the gif if not too much time has passed since the game was started
        	try {
        		this.setContentPane(new JLabel(icon));
        	}
        	catch (Exception e) {
        	}
        }
        this.setVisible(true);//makes it all visible to the user
    }
}