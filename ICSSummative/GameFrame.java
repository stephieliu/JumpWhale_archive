/**
 * Stephie Liu & Saivenkat Jilla
 * 2022/06/21
 * 
 * A 2D platformer game coded in Java! The goal of the game is to move the whale character over the
 * platforms using arrow keys to get the end of the map, where the whale's family is waiting for it!
 * 
 * Description:
 * This class creates the window for the game
 */

package ICSSummative;

import java.awt.*;

import javax.swing.*;

public class GameFrame extends JFrame{

    GamePanel panel;//creates a GamePanel called panel

    public GameFrame(){
        panel = new GamePanel(); //run GamePanel constructor
        this.add(panel);
        this.setTitle("JumpWhale!"); //set title for frame
        this.setResizable(false); //frame can't change size
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
        this.pack();//makes components fit in window - don't need to set JFrame size, as it will adjust accordingly
        this.setVisible(true); //makes window visible to user
        this.setLocationRelativeTo(null);//set window in middle of screen
    }
}