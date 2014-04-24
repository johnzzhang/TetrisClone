package javagame;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SlickBlocksGame extends StateBasedGame {
	
	//states
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	
	//constructor
	public SlickBlocksGame() {
		super("SlickBlocks");
		
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE));
		this.enterState(MAINMENUSTATE);
	}
	
	//main
	public static void main(String[] args) throws SlickException {
		//create game container
		AppGameContainer app = new AppGameContainer(new SlickBlocksGame());
		
		//set resolution to 800 x 600
		app.setDisplayMode(800, 600, false);
		app.start();
	}
	
	//hlists game states
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		this.getState(MAINMENUSTATE).init(gameContainer, this);
		this.getState(GAMEPLAYSTATE).init(gameContainer, this);
	}
	
	
}
