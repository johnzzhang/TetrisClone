package javagame;

import java.awt.Font;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
	
	//GUI images
	Image background = null;
	Image startGameOption = null;
	Image exitOption = null;
	
	//state
	int stateID = 0;
	
	//object of highscore class
	Highscores highscores = null;
	
	//position of menu
	private static int menuX = 410;
	private static int menuY = 160;
	
	//scale of buttons
	float startGameScale = 1;
	float exitScale = 1;
	float scaleStep = 0.0001f;
	
	//sound
	Sound fx = null;
	Music music = null;
	
	//font
	TrueTypeFont trueTypeFont = null;
	
	//constructor
	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}
	
	public int getID() {
		return stateID;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		//load menu images
		background = new Image("res/menu.jpg");
		Image menuOptions = new Image("res/menuoptions.png");
		
		//subdivide image into portions
		startGameOption = menuOptions.getSubImage(0, 0, 377, 71);
		exitOption = menuOptions.getSubImage(0, 71, 377, 71);
		
		//load sound
		fx = new Sound("res/click.wav");
		music = new Music("res/tetris.wav");
		music.loop();
		//load fonts
		Font font = new Font("Verdana", Font.BOLD, 20);
		trueTypeFont = new TrueTypeFont(font, true);
		
		//get highscores
		highscores = Highscores.getInstance();
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//render background
		background.draw(0, 0);
		
		//render menu
		startGameOption.draw(menuX, menuY, startGameScale);
		exitOption.draw(menuX, menuY + 80, exitScale);
		
		//draw highscores
		int index = 1;
		int posY = 300;
		
		ArrayList<Integer> highScoreList = highscores.getScores();
		
		for(Integer score : highScoreList) {
			trueTypeFont.drawString(20, posY, " " + (index < highScoreList.size() ? "0" + index : "" + index) + ".  " + score, Color.orange);
			index++;
			posY += 20;
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//create input
		Input input = gc.getInput();
		
		//get mouse position
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		//self explanatory
		boolean insideStartGame = false;
		boolean insideExit = false;
		
		//check to see if booleans are true
		if((mouseX >= menuX && mouseX <= menuX + startGameOption.getWidth()) && (mouseY >= menuY && mouseY <= menuY + startGameOption.getHeight())) {
			insideStartGame = true;
		}else if((mouseX >= menuX && mouseX <= menuX + exitOption.getWidth()) && (mouseY >= menuY + 80 && mouseY <= menuY + 80 + exitOption.getHeight())) {
			insideExit = true;
		}
		
		//if boolean is true then increase scale of button
		if(insideStartGame) {
			if(startGameScale < 1.05f)
				startGameScale += scaleStep * delta;
			
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				fx.play();
				sbg.enterState(SlickBlocksGame.GAMEPLAYSTATE);
			}
		}else {
			if(startGameScale > 1.0f)
				startGameScale -= scaleStep * delta;
		}
		
		if(insideExit) {
			if(exitScale < 1.05f)
				exitScale += scaleStep * delta;
			
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				gc.exit();
			}
		}else {
			if(exitScale > 1.0f)
				exitScale -= scaleStep * delta;
		}
		
	}
}
