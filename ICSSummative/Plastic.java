/**
 * Stephie Liu & Saivenkat Jilla
 * 2022/06/21
 * 
 * A 2D platformer game coded in Java! The goal of the game is to move the whale character over the
 * platforms using arrow keys to get the end of the map, where the whale's family is waiting for it!
 * 
 * Description:
 * This class manages the plastic waste border around the bottom of the screen
 */

 package ICSSummative;

 //import statements
import javafx.scene.shape.Rectangle;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class Plastic extends Rectangle{
    public Image plastic;// creates plastic, an image

    public Plastic() {
		super(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
		plastic = new ImageIcon("Images/Plastic.png").getImage();// retrieves file
	}

    public void draw(Graphics g) {// this draw method is called before the platforms are, thus allowing them to cover the plastic visually
		g.setColor(Color.BLACK);
		g.drawImage(plastic, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);// draws the plastic
	}
}
