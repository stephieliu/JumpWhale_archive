/**
 * Stephie Liu & Saivenkat Jilla
 * 2022/06/21
 * 
 * A 2D platformer game coded in Java! The goal of the game is to move the whale character over the
 * platforms using arrow keys to get the end of the map, where the whale's family is waiting for it!
 * 
 * Description:
 * Manages and displays the contents of the gameframe, checks for collisions, and updates movement
 */

package ICSSummative;

//import statements
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.text.DecimalFormat;
import javax.sound.sampled.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable, KeyListener{
    //width and height of the panel
    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGHT = 650;
    
    static Clip clip; // music
    
    public boolean showingTitle = true;// keeps track of whether or not the user is on the title screen
    
    // these booleans are used to make sure that the music either plays only once or does not play when it is already playing
    public boolean startPlayed = false;// keeps track if startup music was played
    public boolean lossPlayed = false;// keeps track if loss music was played
    public boolean victoryPlayed = false;// keeps track if victory music was played
    public boolean backgroundPlayed = false;// keeps track if background music was played
    
    private boolean endgame = false;// checks if the user has in some way completed the game
    private boolean loss = false;// checks if the user lost
    private boolean victory = false;// checks if the user won
    private boolean cutscene = false;// checks if the cutscene is showing

    public int attempts = 1;//tracks how many times you have restarted the game

    //declaring all sound files
    public Sound lose = new Sound("Loss.wav");
    public Sound start = new Sound("Start.wav");
    public Sound crash = new Sound("Crash.wav");
    public Sound Victory = new Sound("WallHit.wav");
    public Sound elevator = new Sound("Elevator.wav");//music filename
    
    private Image intro; // cutscene

    public Thread gameThread;
    public Graphics graphics;
    public Image image;

    //declaring background
    public BackgroundImage background;
    private int display = 1;//tracks which image should be shown
    
    public static long startTime = System.currentTimeMillis(); // get the start Time
    public long endTime = 86400; // arbitrary large number   

    //declaring character
    public Player player;

    //declaring plastic
    public Plastic plastic;
    
    //declaring health bar
    public Health health;
    public HealthBar healthtext;
    private int checkpoint = 0; // provides a safe place to go after injury

    //declaring platforms
    public static ArrayList<Platforms> platformList = new ArrayList<Platforms>();

    //gamepanel constructor
    public GamePanel(){
        //calculates system time to display the introduction cutscene
    	if (System.currentTimeMillis()- startTime < 19) {
    		intro = new ImageIcon("Images/IntroCutscene.gif").getImage();
    	}
    	intro = new ImageIcon("StartScreen.png").getImage();

        //initializing variables
    	plastic = new Plastic();//the plastic border at the bottom of screen
        healthtext = new HealthBar();//the text that says "health bar"
    	background = new BackgroundImage("StartScreen.png"); // background
    	player = new Player(120, 0); //create the player, set start location to left of the bottom of the screen
    	
        platformList.clear();// clears the array so that no platforms are on the screen when display is not 1-11
    	
        //Display 1, fills up the platform array so that it is not empty
    	if (display == 1) {
    		platformList.clear();
    		platformList.add(new Platforms(125, 497, 104, 18,'y'));
    		platformList.add(new Platforms(189, 335, 236, 20,'y'));
    		platformList.add(new Platforms(409, 441, 230, 209,'y'));
    		platformList.add(new Platforms(435, 115, 206, 26,'y'));
    		platformList.add(new Platforms(723, 203, 250, 90,'y'));
    		platformList.add(new Platforms(917, 487, 186, 163,'y'));
    		platformList.add(new Platforms(1048, 199, 144, 32,'y'));
    	}
        
        health = new Health(1000, 30, 100); // health bar
    	
        this.setFocusable(true); //make everything in this class appear on the screen
        this.addKeyListener(this); //start listening for keyboard input
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        
        //make this class run at the same time as other classes
        gameThread = new Thread(this);
        gameThread.start();
    }

    //paint is a method in java.awt library that we are overriding
    public void paint(Graphics g){
        //we are using "double buffering" here - if we draw images directly onto the screen, it takes time and the human eye can actually notice flashes of lag as each pixel on the screen is drawn one at a time. Instead, we are going to draw images OFF the screen (outside dimensions of the frame), then simply move the image on screen as needed. 
        image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
        graphics = image.getGraphics();
        draw(graphics); //update the positions of everything on the screen 
        g.drawImage(image, 0, 0, this); //redraw everything on the screen
    }

    //call the draw methods in each class to update positions as things move and actions take place
    public void draw(Graphics g){
        //calculates system time to draw the animation for the intro cutscene
    	if (System.currentTimeMillis()- startTime < 19) {
    		g.drawImage(intro, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
    	}

    	background.draw(g);//draws background
    	if (showingTitle) {
    		play(g);//calls the play method
    	}
    	else if (loss && endgame) {
    		loss(g);//calls the loss method
    	}
    	else if (victory && endgame) {
    		victory(g);//calls the victory method
    	}
    	else { // only displays if not on title screen/fail screen/end screen
    		if (!backgroundPlayed) {
    	    	backgroundPlayed = true;// sets this equal to true so that it is not played multiple times at the same time
    		}
            //draw other elements in order
    		player.draw(g);
            plastic.draw(g);
    		health.draw(g);
            healthtext.draw(g);

            // adds the platforms for level 1
            if (display == 1) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(125, 497, 104, 18,'l'));
                platformList.add(new Platforms(189, 335, 236, 20,'l'));
                platformList.add(new Platforms(409, 441, 230, 209,'l'));
                platformList.add(new Platforms(435, 115, 206, 26,'l'));
                platformList.add(new Platforms(723, 203, 250, 90,'l'));
                platformList.add(new Platforms(917, 487, 186, 163,'l'));
                platformList.add(new Platforms(1048, 199, 144, 32,'l'));
            }
    		if (display == 1) {
                for(int i = 0; i<7; i++){
                    platformList.get(i).draw(g);// draws the platforms for level 1
                }
                checkpoint = 125;// creates a checkpoint, if the user ends up falling, they will start with this position
                // so that it is more user friendly and easier to get to a platform
	    	}

            // adds the platforms for level 2
    		if (display == 2) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(84, 365, 130, 285,'l'));
                platformList.add(new Platforms(166, 169, 172, 26,'l'));
                platformList.add(new Platforms(364, 415, 194, 26,'l'));
                platformList.add(new Platforms(534, 93, 130, 24,'l'));
                platformList.add(new Platforms(770, 491, 154, 28,'l'));
                platformList.add(new Platforms(850, 243, 338, 66,'l'));
                for(int i = 0; i<6; i++){
                    platformList.get(i).draw(g);// draws all platforms for level 2
                }
                checkpoint = 84;// updates position of checkpoint
			}

            //adds the platforms for level 3
    		if (display == 3) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(142, 413, 220, 54,'l'));
                platformList.add(new Platforms(452, 305, 90, 30,'l'));
                platformList.add(new Platforms(612, 217, 92, 433,'l'));
                platformList.add(new Platforms(773, 113, 146, 28,'l'));
                platformList.add(new Platforms(877, 367, 316, 30,'l'));
                platformList.add(new Platforms(850, 550, 120, 20, 'l'));
                for(int i = 0; i<6; i++){
                    platformList.get(i).draw(g);//draws all platforms for level 3
                }
                checkpoint = 142;// updates position of checkpoint
			}

            //adds the platforms for level 4
			if (display == 4) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(69, 231, 208, 28,'d'));
                platformList.add(new Platforms(349, 305, 98, 22,'d'));
                platformList.add(new Platforms(536, 509, 98, 30,'d'));
                platformList.add(new Platforms(790, 187, 98, 28,'d'));
                platformList.add(new Platforms(962, 337, 136, 22,'d'));
                platformList.add(new Platforms(1055, 470, 343, 19, 'd'));
                for(int i = 0; i<6; i++){
                    platformList.get(i).draw(g);//draws all platforms for level 4
                }
                checkpoint = 69;//updates position of checkpoint
			}

            //adds the platforms for level 5
			if (display == 5) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(148, 449, 60, 201,'g'));
                platformList.add(new Platforms(382, 293, 164, 357,'g'));
                platformList.add(new Platforms(680, 485, 44, 165,'g'));
                platformList.add(new Platforms(826, 291, 114, 28,'g'));
                platformList.add(new Platforms(956, 203, 126, 28,'g'));
                platformList.add(new Platforms(1109, 125, 90, 30,'g'));
                for(int i = 0; i<6; i++){
                    platformList.get(i).draw(g);//draws all platforms for level 5
                }
                checkpoint = 148;//updates position of checkpoint
			}

            //adds the platforms for level 6
			if (display == 6) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(127, 164, 144, 40,'d'));
                platformList.add(new Platforms(225, 340, 248, 26,'d'));
                platformList.add(new Platforms(543, 673, 130, 26,'d'));
                platformList.add(new Platforms(785, 430, 166, 220,'d'));
                platformList.add(new Platforms(989, 140, 180, 52,'d'));
                platformList.add(new Platforms(1082, 314, 80, 60,'d')); 
				for (int i = 0; i<6; i++) {
                    platformList.get(i).draw(g);//draws all platforms for level 6
				}
                checkpoint = 127;//updates position of checkpoint
			}

            //adds the platforms for level 7
			if (display == 7) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(88, 234, 224, 34,'l'));
                platformList.add(new Platforms(296, 538, 354, 112,'l'));
                platformList.add(new Platforms(496, 358, 184, 28,'l'));
                platformList.add (new Platforms(747, 132, 212, 58,'d'));
                platformList.add(new Platforms(939, 466, 241, 184,'l'));  
				for (int i = 0; i<5; i++) {
                    platformList.get(i).draw(g);//draws all platforms for level 7
				}
                checkpoint = 88;//updates position of checkpoint
			}

            //adds the platforms for level 8
			if (display == 8) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(23, 150, 124, 22,'l'));
                platformList.add(new Platforms(187, 123, 134, 24,'l'));
                platformList.add(new Platforms(201, 502, 182, 22, 'l'));
                platformList.add(new Platforms(411, 235, 142, 26,'l'));
                platformList.add(new Platforms(531, 373, 146, 24,'l'));
                platformList.add(new Platforms(763, 261, 184, 30,'l'));
                platformList.add(new Platforms(939, 525, 250, 26,'l'));
                
				for (int i = 0; i<7; i++) {
                    platformList.get(i).draw(g);//draws all platforms for level 8
				}
                checkpoint = 23;//updates position of checkpoint
			}

            //adds the platforms for level 9
			if (display == 9) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(24, 582, 188, 30,'l'));
                platformList.add(new Platforms(240, 258, 146, 38,'l'));
                platformList.add(new Platforms(380, 492, 214, 26,'k'));
                platformList.add(new Platforms(610, 406, 114, 20,'w'));
                platformList.add(new Platforms(766, 306, 152, 26,'k'));
                platformList.add(new Platforms(948, 120, 204, 30,'l'));
				for (int i = 0; i<6; i++) {
                    platformList.get(i).draw(g);//draws all platforms for level 9
				}
                checkpoint = 24;//updates position of checkpoint
			}

            //adds the platforms for level 10
			if (display == 10) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(38, 228, 148, 28,'d'));
                platformList.add(new Platforms(176, 486, 288, 26,'d'));
                platformList.add(new Platforms(352, 362, 166, 18,'d'));
                platformList.add(new Platforms(564, 132, 216, 33,'d'));
                platformList.add(new Platforms(741, 427, 90, 30,'d'));
                platformList.add(new Platforms(849, 225, 120, 30,'d'));
                platformList.add(new Platforms(947, 394, 240, 256,'d'));
				for (int i = 0; i<7; i++) {
                    platformList.get(i).draw(g);//draws all platforms for level 10
				}
                checkpoint = 38;//updates position of checkpoint
			}

            //adds the platforms for level 11
			if (display == 11) {
                platformList.clear();// clears any previous platforms so that collision detection works properly
                platformList.add(new Platforms(61, 468, 130, 180,'c'));
                platformList.add(new Platforms(315, 562, 132, 28,'c'));
                platformList.add(new Platforms(323, 294, 136, 38,'c'));
                platformList.add(new Platforms(579, 516, 158, 28,'c'));
                platformList.add(new Platforms(611, 170, 172, 30,'c'));
                platformList.add(new Platforms(815, 408, 166, 46,'c'));
				for (int i = 0; i<6; i++) {
                    platformList.get(i).draw(g);//draws all platforms for level 11
				}
                checkpoint = 61;//updates position of checkpoint
			}            
    	}
    }
    
	//press any key to begin/restart/continue
	public void play(Graphics g) {
        background = new BackgroundImage("StartScreen.png");//beginning screen
		if (!startPlayed) {//starts and stops relevant music
            start.play();
            Victory.stop();
            lose.stop();
	    	startPlayed = true;//sets it equal to true so it doesn't play multiple times
		}
	}
	public void loss(Graphics g) {
        background = new BackgroundImage("FailScreen.png");//end screen if you lose
		if (!lossPlayed) {//starts and stops relevant music
            elevator.stop();
            crash.stop();
            lose.play();
	    	lossPlayed = true;//sets it equal to true so it doesn't play multiple times
		}
	}
	public void victory(Graphics g) {
        background = new BackgroundImage("EndScreen.png");//end screen if you win
		if (!victoryPlayed) {//starts and stops relevant music
            elevator.stop();
            Victory.play();
	    	victoryPlayed = true;//sets it equal to true so it doesn't play multiple times
		}
	}
	
	//call the move methods in other classes to update positions
    public void move(){
    	if (!showingTitle || !endgame) { // doesn't allow player to move if the game is on the title screen or is over
    		player.move();
    	}
    }
    // handles all collisions and responds accordingly
    public void checkCollision(){
        //force players to remain on screen
        if (player.y <= 0) {//top of screen
            player.y = 0;
        }
        if (player.y > GAME_HEIGHT-Player.CHARACTER_HEIGHT) {//bottom of screen
            crash.play();//plays crash music
        	Health.HEALTH_WIDTH-=20;//you only get 4 total falls before you die (5 falls including your last)
        	if (Health.HEALTH_WIDTH <= 0) {//if the health bar is depleted
        		loss = true;//the player has lost
        		endgame = true;//the game is over
        		player.gravity = 0;//gravity is 0 so that the player is not in motion if they choose to replay
        	}
            player.y = 0; // player steers themselves back to a platform
            player.x = checkpoint;// safe point for user friendliness
            player.isJump = false;// they aren't jumping
            player.KeyPressedDuration = 0;// resets
        }

        //make it semi wrap-around
        if(player.x<=0){//left of screen
            player.x = 0;// doesn't let the player leave the screen
        }
        if(player.x>=GAME_WIDTH - Player.CHARACTER_WIDTH){//right of screen
            player.x = 0;// player is transported to the left of the screen
            display++;  // new level is generated          
            if(display==12) {// the player has reached their objective, the whale family
                endgame = true;// the game is over
                victory = true;// the player has won
                player.xVelocity = 0;// the player stops moving
                player.x = 950;// the player is nestled between their parents
            }
            else background = new BackgroundImage("bkg"+display+".png");// displays new background as it is a new level
        }
    }

    // intentionally slows down the computer
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000/amountOfTicks;
        double delta =0;
        long now;

        while(true){
            now = System.nanoTime();
            delta = delta + (now-lastTime)/ns;
            lastTime = now;

            if(delta>=1){
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }
    public void keyPressed(KeyEvent e){
  	  //starts game after any key is pressed
  		if(showingTitle && attempts == 1) {//if it's the first time that the player is using the game, then show the cutscene
  			showingTitle = false;
            IntroCutscene intro = new IntroCutscene();//shows the intro cutscene
            background = new BackgroundImage("bkg"+display+".png");
            elevator.loop();// keeps elevator music looping
            Victory.stop();// stops any other music playing
            lose.stop();// stops any other music playing
            start.stop();// stops any other music playing
            attempts++;//updates number of attempts
  		}
        else if(showingTitle && attempts>1){//not the first time the user has restarted
            //do everything but don't show the cutscene at the start
            showingTitle = false;
            background = new BackgroundImage("bkg"+display+".png");
            elevator.loop();
            Victory.stop();
            lose.stop();
            start.stop();
            attempts++;
        }
  		if (e.getKeyCode() == 27 && !endgame && !victory && !showingTitle){//this is the code for the escape key
  			showingTitle = true;// goes back to start screen in case the user needs to pause
  		}
  		if (e.getKeyCode() == 82 && endgame) {// if the player presses r to restart when the game is over
            elevator.stop();// stops elevator music

            // resets many values to what they were at the beginning
  			showingTitle = true;
  		    startPlayed = false;
  		    lossPlayed = false;
  		    victoryPlayed = false;
  		    backgroundPlayed = false;
  		    endgame = false;
  		    loss = false;
  		    victory = false;
  		    display = 1;
  		    health.HEALTH_WIDTH = 100;
  		    checkpoint = 0;
  		    showingTitle = true;
  		    player.x = 120;
  		    player.y = 0;
            player.gravity = 0.3;
            repaint();
            // new GameFrame();
  		}
  		
  		player.keyPressed(e); // if none of the above is true, the key pressed is sent over to the Player class for processing

    }
    
    public void keyReleased(KeyEvent e){
    	player.keyReleased(e);// the key released is sent over to the Player class for processing
    }
    
    // left empty because it is not needed; must be here because it is required to be overridden by the KeyListener interface
    public void keyTyped(KeyEvent e){

    }
}