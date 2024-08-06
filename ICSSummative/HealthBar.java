package ICSSummative;

import javafx.scene.shape.Rectangle;
import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class HealthBar extends Rectangle{
    public Image bar;

    public HealthBar() {
		super(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
		bar = new ImageIcon("Images/healthbar.png").getImage();
	}

    public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(bar, 0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
	}
}
