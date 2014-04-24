package javagame;

public class Piece {
	
	//variables
	private String name;
	Tuple[][] rotationMatrice;
	int currentRotation;
	
	//constructor
	public Piece(String name, Tuple[][] rotationMatrice) {
		this.name = name;
		
		currentRotation = 0;
		
		this.rotationMatrice = rotationMatrice;
	}
	
	public Tuple getPosOfBlock(int blockNumber) {
		return rotationMatrice[currentRotation][blockNumber];
	}
	
	public void rotateRight() {
		currentRotation = ++currentRotation % rotationMatrice.length;
	}
	
	public void rotateLeft() {
		currentRotation = --currentRotation % rotationMatrice.length;
	}
	
	//tuple
	public static class Tuple {
		public int x;
		public int y;
		
		public Tuple(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}
