/**
 * Stephie Liu & Saivenkat Jilla
 * 2022/06/21
 * 
 * A 2D platformer game coded in Java! The goal of the game is to move the whale character over the
 * platforms using arrow keys to get the end of the map, where the whale's family is waiting for it!
 * 
 * Description:
 * This class manages the platforms (size, colour, and position)
 */

package ICSSummative;

//import statements
import java.awt.*;
import java.awt.event.*;

public class Platforms extends Rectangle{

    public int PLATFORM_WIDTH = 10;//placeholder values, updated later
    public int PLATFORM_HEIGHT = 20;
    public static char colour = 'c';//to change colour of the platform

    public Platforms(int x, int y, int w, int h, char c){// each platform is different from each other - no two platforms are identical!
        super(x, y, w, h);// the location of the platform as well as how long and tall it is varies from platform to platform
        colour = c;
        PLATFORM_WIDTH = w;
        PLATFORM_HEIGHT = h;
    }

    public void draw (Graphics g){
        if(colour == 'w'){ //white platform
            g.setColor(Color.WHITE);
            g.fillRect(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        	}
        if(colour == 'g'){ //green platform
            g.setColor(Color.green);
            g.fillRect(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        	}
        if (colour == 'd') { //dark grey platform
            g.setColor(Color.gray);
            g.fillRect(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
            }
        if(colour == 'l'){//light gray platform
            g.setColor(Color.lightGray);
            g.fillRect(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
            }
        if(colour == 'p'){ //pink platform
            g.setColor(Color.magenta);
            g.fillRect(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        	}
        if (colour == 'y') { //yellow platform
        	g.setColor(Color.yellow);
            g.fillRect(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        	}
        if (colour == 'c') { //cyan platform
        	g.setColor(Color.cyan);
            g.fillRect(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        	}
        if (colour == 'b') { //blue platform
        	g.setColor(Color.blue);
            g.fillRect(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        	}
        if (colour == 'k') { //black platform
        	g.setColor(Color.black);
            g.fillRect(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        	}
        }
    }

