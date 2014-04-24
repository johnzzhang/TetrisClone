package javagame;

import java.awt.Font;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import javagame.Piece.Tuple;

public class GameplayState extends BasicGameState {
	
	private int stateID = 0;
	
	PieceFactory pieceFactory = null;
	
	Pit pit = null;
	
	Tuple cursorPos;
	Piece currentPiece = null;
	Piece nextPiece = null;
	
	Image gameHUD = null;
	Image transparentGameHUD = null;
	
	TrueTypeFont trueTypeFont = null;
	
	Sound blockFX = null;
	
	int score = 0;
	
	int deltaCounter = 500;
	int inputDelta = 0;
	
	int pitWidth = 0;
	int pitDepth = 0;
	
	static int PIT_X = 52;
	static int PIT_Y = 18;
	
	int blockSize = 0;
	
	ArrayList<Image> blockImages = null;
	
	private STATES currentState = null;
	
	//states within game play state
	private enum STATES {
		START_GAME_STATE, NEW_PIECE_STATE, MOVING_PIECE_STATE, LINE_DESTRUCTION_STATE, 
		PAUSE_GAME_STATE, HIGHSCORE_STATE, GAME_OVER_STATE
	}
	
	//constructor
	public GameplayState(int stateID) {
		this.stateID = stateID;
	}
	
	public int getID() {
		return stateID;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Image allImages = new Image("res/gameplaytextures.png");
		
		gameHUD = new Image("res/hudblockgame.jpg");
		transparentGameHUD = allImages.getSubImage(29, 0 , 287, 573);
		
		blockImages = new ArrayList<Image>();
		
		blockSize = 28;
		
		for(int i = 0; i < 4; i++)
			blockImages.add(allImages.getSubImage(0, i * blockSize, blockSize, blockSize));
		
		pieceFactory = new PieceFactory();
		pitWidth = 10;
		pitDepth = 20;
		
		pit = new Pit(pitWidth, pitDepth);
		
		blockFX = new Sound("res/crash.wav");
		
		Font font = new Font("Verdana", Font.BOLD, 40);
		trueTypeFont = new TrueTypeFont(font, true);
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		super.enter(gc, sbg);
		
		pit.makeCleanPit();
		currentState = STATES.START_GAME_STATE;
		score = 0;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		gameHUD.draw(0, 0);
		
		//draw pit
		for(int lineIdx = 0; lineIdx < pit.getNumberOfLines(); lineIdx++) {
			for(int columnIdx = 0; columnIdx < pit.getNumberOfColumns(); columnIdx++) {
				int blockType = pit.getBlockAt(columnIdx, lineIdx);
				
				if(blockType != -1) {
					blockImages.get(blockType).draw((columnIdx * blockSize) + PIT_X,
					PIT_Y + (blockSize * (pit.getNumberOfLines() - lineIdx - 1)));
				}
			}
		}
		
		//draw current piece
		if(currentPiece != null) {
			for(int i = 0; i < 4; i++) {
				Tuple blockPos = currentPiece.getPosOfBlock(i);
				blockImages.get(i).draw(PIT_X + (blockPos.x + cursorPos.x) * blockSize,
				PIT_Y + (pit.getNumberOfLines() - 1 - (blockPos.y + cursorPos.y)) * blockSize);
			}
		}
		
		//draw next piece
		if(nextPiece != null) {
			for(int i = 0; i < 4; i++) {
				Tuple blockPos = nextPiece.getPosOfBlock(i);
				blockImages.get(i).draw(PIT_X + 350 + (blockPos.x) * -blockSize,
				PIT_Y + 56 + (blockPos.y) * blockSize);
			}
		}
		
		trueTypeFont.drawString(525, 75, String.valueOf(score), Color.orange);
		
		transparentGameHUD.draw(48,15);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		switch(currentState) {
			case START_GAME_STATE:
				currentState = STATES.NEW_PIECE_STATE;
				deltaCounter = 500;
				break;
			case NEW_PIECE_STATE:
				generateNewPiece();
				break;
			case MOVING_PIECE_STATE:
				updatePiece(gc, sbg, delta);
				break;
			case LINE_DESTRUCTION_STATE:
				checkForFullLines(gc, sbg, delta);
				break;
			case HIGHSCORE_STATE:
				break;
			case PAUSE_GAME_STATE:
				break;
			case GAME_OVER_STATE:
				Highscores.getInstance().addScore(score);
				Highscores.saveScores();
				sbg.enterState(SlickBlocksGame.MAINMENUSTATE);
				break;
		}
	}
	
	public void checkForFullLines(GameContainer gc, StateBasedGame sbg, int delta) {
		int linesDestroyed = 0;
		
		for(int lineIdx = 0; lineIdx < pit.getNumberOfLines(); ) {
			if(pit.isLineFull(lineIdx)) {
				pit.eraseLine(lineIdx);
				linesDestroyed++;
			}else{
				lineIdx++;
			}
		}
		
		switch(linesDestroyed) {
			case 0: score += 10;
				break;
			case 1: score += 100;
				break;
			case 2: score += 300;
				break;
			case 3: score += 600;
				break;
			case 4: score += 1000;
				break;
		}
		
		currentState = STATES.NEW_PIECE_STATE;
	}
	
	private void updatePiece(GameContainer gc, StateBasedGame sbg, int delta) {
		Tuple newCursorPos = new Tuple(cursorPos.x, cursorPos.y);
		
		deltaCounter -= delta;
		inputDelta -= delta;
		if(deltaCounter < 0) {
			newCursorPos.y -= 1;
			if(!pit.isPieceInsertableIn(currentPiece, newCursorPos)) {
				pit.insertPieceAt(currentPiece, (int)cursorPos.x, (int)cursorPos.y);
				blockFX.play();
				currentState = STATES.LINE_DESTRUCTION_STATE;
				return;
			}
			
			deltaCounter = 500;
		}
		
		Input input = gc.getInput();
		if(inputDelta < 0) {
			if(input.isKeyDown(Input.KEY_LEFT)) {
				newCursorPos.x -= 1;
				
				if(!pit.isPieceInsertableIn(currentPiece, newCursorPos))
					newCursorPos.x += 1;
				else
					inputDelta = 100;
			}
			if(input.isKeyDown(Input.KEY_RIGHT)) {
				newCursorPos.x += 1;
				
				if(!pit.isPieceInsertableIn(currentPiece, newCursorPos))
					newCursorPos.x -= 1;
				else
					inputDelta = 100;
			}
			if(input.isKeyDown(Input.KEY_UP)) {
				currentPiece.rotateRight();
				if(!pit.isPieceInsertableIn(currentPiece, newCursorPos))	
					currentPiece.rotateLeft();
				else
					inputDelta = 150;
			}
			if(input.isKeyDown(Input.KEY_DOWN)) {
				newCursorPos.y -= 1;
				if(!pit.isPieceInsertableIn(currentPiece, newCursorPos))
					newCursorPos.y += 1;
				else
					inputDelta = 10;
			}
		}
		
		cursorPos = new Tuple(newCursorPos.x, newCursorPos.y);
	}
	
	private void generateNewPiece() {
		if(currentPiece == null)
			nextPiece = pieceFactory.generateRandomPiece();
		
		currentPiece = nextPiece;
		cursorPos = new Tuple(5, 19);
		
		if(pit.isPieceInsertableIn(currentPiece, cursorPos)) {
			nextPiece = pieceFactory.generateRandomPiece();
			currentState = STATES.MOVING_PIECE_STATE;
		}else{
			currentState = STATES.GAME_OVER_STATE;
		}
	}
}
