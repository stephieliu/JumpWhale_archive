/**
 * Stephie Liu & Saivenkat Jilla
 * 2022/06/21
 * 
 * A 2D platformer game coded in Java! The goal of the game is to move the whale character over the
 * platforms using arrow keys to get the end of the map, where the whale's family is waiting for it!
 * 
 * Description:
 * This class manages the properties of the player (collision checking, movement, and appearance)
 */

package ICSSummative;

//import statements
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class Player extends Rectangle {
	// movement speed and direction
	public double yVelocity;
	public double xVelocity;
	public final double SPEED = 1;
	public static final double maxSpeed = 8;//maximum speed cap for the character movement

	//character dimensions
	public static final int CHARACTER_WIDTH = 60;
	public static final int CHARACTER_HEIGHT = 65;

	//variables to track various properties and events
	public long KeyPressedDuration;// checks how long the key was pressed to calculate the jump
	public boolean right = false;// if the jump is to the right
	public boolean left = false;// if the jump is to the left
	public int height = 0;// jump height calculated
	public boolean isJump = false;// whether you jump
	public boolean isLanded = false;// whether you've already landed on a surface
	public boolean maxJump = false;// whether you pressed the space bar for over the time limit
	public double gravity = 0.3;// speed of gravity for the jump
	
	public Image whale;//the whale image

	//booleans checking if the particular key was pressed
	public boolean rightKeyDown = false;
	public boolean leftKeyDown = false;

	public Player(int x, int y) {
		super(x, y, CHARACTER_WIDTH, CHARACTER_HEIGHT);//creates the player
		whale = new ImageIcon("Images/whale.png").getImage();//retrieves image file
	}

	// in the event of a key being pressed, the player moves if their movement is valid
	// handles booleans for other methods
	public void keyPressed(KeyEvent e) {
		right = false;
		left = false;
		if ((e.getKeyCode() == KeyEvent.VK_SPACE || isJump) && isLanded) {
			// jump
			if (!isJump) {// start the timer
				KeyPressedDuration = System.currentTimeMillis();// record the time when you first press space
			}
			isJump = true;// you are jumping
			isLanded = false;

			if (System.currentTimeMillis() - KeyPressedDuration > 3000) {// jumps at the max if pressed for longer than 3 seconds
				maxJump = true;// set it to true to track whether you limit the jump ability to 3000 ms
				isJump = true;
			}

			// setting the direction of the jump
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				// //moves right
				xVelocity += 5;
				right = true;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				left = true;
			}
		}
		 if(e.getKeyCode() == KeyEvent.VK_LEFT){
			//moves left
			leftKeyDown = true;
			left = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// moves right
			rightKeyDown = true;
			right = true;
		}
	}

	// in the event that a key is released, the player stops moving or starts jumping
	// takes care of some booleans for other classes, updates yVelocity and the xVelocity (through xDirection)
	// based on how long the key was held
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (!updatePossible(0, 1)) {// only happens if the jump is possible
				yVelocity = -10;
			}
			// move the character when released
			if (!maxJump) {// not a max jump
				KeyPressedDuration = System.currentTimeMillis() - KeyPressedDuration;// time passed
			}
			else {
				KeyPressedDuration = 3000;// sets it to 3000 ms
			}

			if (right && isJump && !left) {// jump to the right
				setXDirection((int) (SPEED + Math.ceil(KeyPressedDuration / 300)));// speed depends on how long the key is pressed
			}
			else if (left && isJump && !right) {// jump to the left
				setXDirection((int) (SPEED + Math.ceil(KeyPressedDuration / 300)) * -1);// speed depends on how long the key is pressed
			}
			// reset variables
			isJump = false;
			KeyPressedDuration = 0;
			maxJump = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// stop moving
			right = false;
			rightKeyDown = false;
			setXDirection(0);
			move();
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			// stop moving
			left = false;
			leftKeyDown = false;
			setXDirection(0);
			move();
		}
	}

	// sets the y velocity equal to the y direction
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}

	// sets the x velocity equal to the x direction
	public void setXDirection(int xDirection) {
		xVelocity = xDirection;
	}

	// boolean checking if there is a collision
	public boolean collisionEvent(Platforms plat, double d, double e) {
		if (d+CHARACTER_WIDTH>plat.x && d<plat.x+plat.PLATFORM_WIDTH && e-plat.y<plat.PLATFORM_HEIGHT && plat.y-e<CHARACTER_HEIGHT) {// hard coded checking if character is touching a platform
			return true;// is true if there is a collision
		}
		return false;// false if there is no collision
	}

	// boolean checking if the player can move in a certain direction
	public boolean updatePossible(double xVelocity2, double yVelocity2) {
		for (Platforms plat : GamePanel.platformList) {// checks collsions for all platforms
			if (collisionEvent(plat, this.x + xVelocity2, this.y + yVelocity2)) {// checks if the player will collide in the future
				return false;// if the player is colliding in the future, they cannot move through the platform and thus an update is not possible
			}
		}
		return true;// can update the position as the new position would be valid
	}
	
	// makes sure that the speed cannot exceed the maximum, sets it to the maximum if this is the case
	public void capSpeed() {
		if(xVelocity>maxSpeed) {
			xVelocity = maxSpeed;
		}
		else if (xVelocity<-maxSpeed) {// checks in both directions
			xVelocity = -maxSpeed;
		}
	}

	// allows for movement, calls other methods to check if movement is valid
	public void move() {
		if(leftKeyDown) {
			xVelocity -= SPEED;// allows for acceleration in the x direction
		}
		else if(rightKeyDown) {
			xVelocity += SPEED;
		}
		xVelocity*=0.96;// smoother movement
		capSpeed();// calls capSpeed to check
		yVelocity = Math.min(10, yVelocity);// the yVelocity cannot exceed 10
		if (!updatePossible(0, yVelocity)) {// checks if the y-movement is valid by checking the boolean
			if (yVelocity > 0) {
				while (updatePossible(0, 1)) {// while this is true, update position
					y+=1;// allows for the player to move down
				}
			}
			else if (yVelocity < 0) {
				while (updatePossible(0, -1)) {// while this is true, update position
					y-=1;// allows for the player to move up
				}
			}
			yVelocity = 0;// sets y velocity equal to 0 otherwise
		}
		if (!updatePossible(xVelocity, 0)) {// checks if x-movement is valid by checking the boolean
			int increment = 0;
			if (xVelocity > 0) {
				while (updatePossible(increment, 0)) {
					increment++;// continues to increase until this this is not valid - a platform may have been struck
				}
				x+=increment - 1;// updates the position
			}
			else if (xVelocity < 0) {
				while (updatePossible(increment, 0)) {
					increment--;// continues to decrease until this this is not valid - a platform may have been struck
				}
				x+=increment + 1;// updates the position
			}
		}
		if (updatePossible(xVelocity, yVelocity)) {// if both x and y can be updated
			y = (int) (y + yVelocity);
			x = (int) (x + xVelocity);
		}
		else if (updatePossible(0, yVelocity)) {// if only y can be updated
			y += yVelocity;
		}
		else if (updatePossible(xVelocity, 0)) {// if only x can be updated
			x += xVelocity;
		}
		yVelocity = yVelocity + gravity;// adds gravity to the yVelocity
	}

	// draws the player, a young whale on its journey to reunite with its family!
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(whale, x, y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
	}
}