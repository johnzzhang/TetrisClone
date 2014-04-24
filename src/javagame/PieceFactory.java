package javagame;

import javagame.Piece.Tuple;

public class PieceFactory {
	
	private int counter = 0;
	
	//constructor creates new instance of class
	public PieceFactory() {
		
	}
	
	public Piece generateRandomPiece() {
		java.util.Random random = new java.util.Random();
		
		//generates 1 of 7 random pieces
		switch(random.nextInt(7)) {
			/*
			case 0: return generatePiece('i');
			case 1: return generatePiece('i');
			case 2: return generatePiece('i');
			case 3: return generatePiece('i');
			case 4: return generatePiece('i');
			case 5: return generatePiece('i');
			case 6: return generatePiece('i');
			*/
			
			case 0: return generatePiece('t');
			case 1: return generatePiece('s');
			case 2: return generatePiece('z');
			case 3: return generatePiece('o');
			case 4: return generatePiece('i');
			case 5: return generatePiece('l');
			case 6: return generatePiece('j');
			
		}
		
		return null;
	}
	
	//rotation matrices
	static Tuple[][] TRotationMatrice = {
		{new Tuple(0, 0), new Tuple(-1, 0), new Tuple(1, 0), new Tuple(0, -1)},
		{new Tuple(0, 0), new Tuple(0, 1), new Tuple(0, -1), new Tuple(-1, 0)},
		{new Tuple(0, 0), new Tuple(1, 0), new Tuple(-1, 0), new Tuple(0, 1)},
		{new Tuple(0, 0), new Tuple(0, -1), new Tuple(0, 1), new Tuple(1, 0)}
	};
	
	static Tuple[][] SRotationMatrice = {
		{new Tuple(0, 0), new Tuple(1, 0), new Tuple(0, -1), new Tuple(-1, -1)},
		{new Tuple(0, 0), new Tuple(0, 1), new Tuple(1, 0), new Tuple(1, -1)},
		{new Tuple(0, 0), new Tuple(1, 0), new Tuple(0, -1), new Tuple(-1, -1)},
		{new Tuple(0, 0), new Tuple(0, 1), new Tuple(1, 0), new Tuple(1, -1)}
	};
	
	static Tuple[][] ZRotationMatrice = {
		{new Tuple(0, 0), new Tuple(-1, 0), new Tuple(0, -1), new Tuple(1, -1)},
		{new Tuple(0, 0), new Tuple(0, 1), new Tuple(-1, 0), new Tuple(-1, -1)},
		{new Tuple(0, 0), new Tuple(-1, 0), new Tuple(0, -1), new Tuple(1, -1)},
		{new Tuple(0, 0), new Tuple(0, 1), new Tuple(-1, 0), new Tuple(-1, -1)}
	};
	
	static Tuple[][] ORotationMatrice = {
		{new Tuple(0, 0), new Tuple(1, 0), new Tuple(0, -1), new Tuple(1, -1)},
		{new Tuple(0, 0), new Tuple(1, 0), new Tuple(0, -1), new Tuple(1, -1)},
		{new Tuple(0, 0), new Tuple(1, 0), new Tuple(0, -1), new Tuple(1, -1)},
		{new Tuple(0, 0), new Tuple(1, 0), new Tuple(0, -1), new Tuple(1, -1)}
	};
	
	static Tuple[][] IRotationMatrice = {
		{new Tuple(0, 0), new Tuple(-1, 0), new Tuple(1, 0), new Tuple(2, 0)},
		{new Tuple(0, 0), new Tuple(0, -1), new Tuple(0, 1), new Tuple(0, 2)},
		{new Tuple(0, 0), new Tuple(-1, 0), new Tuple(1, 0), new Tuple(2, 0)},
		{new Tuple(0, 0), new Tuple(0, -1), new Tuple(0, 1), new Tuple(0, 2)}
	};
	
	static Tuple[][] LRotationMatrice = {
		{new Tuple(0, 0), new Tuple(1, 0), new Tuple(-1, 0), new Tuple(-1, -1)},
		{new Tuple(0, 0), new Tuple(0, -1), new Tuple(0, 1), new Tuple(-1, 1)},
		{new Tuple(0, 0), new Tuple(-1, 0), new Tuple(1, 0), new Tuple(1, 1)},
		{new Tuple(0, 0), new Tuple(0, 1), new Tuple(0, -1), new Tuple(1, -1)}
	};
	
	static Tuple[][] JRotationMatrice = {
		{new Tuple(0, 0), new Tuple(-1, 0), new Tuple(1, 0), new Tuple(1, -1)},
		{new Tuple(0, 0), new Tuple(0, 1), new Tuple(0, -1), new Tuple(-1, -1)},
		{new Tuple(0, 0), new Tuple(1, 0), new Tuple(-1, 0), new Tuple(-1, 1)},
		{new Tuple(0, 0), new Tuple(0, -1), new Tuple(0, 1), new Tuple(1, 1)}
	};
	
	public Piece generatePiece(char pieceType) {
		String name = pieceType + "Shape" + counter++;
		
		switch(pieceType) {
			case 't': return new Piece(name, TRotationMatrice);
			case 's': return new Piece(name, SRotationMatrice);
			case 'z': return new Piece(name, ZRotationMatrice);
			case 'o': return new Piece(name, ORotationMatrice);
			case 'i': return new Piece(name, IRotationMatrice);
			case 'l': return new Piece(name, LRotationMatrice);
			case 'j': return new Piece(name, ZRotationMatrice);
		}
		
		return null;
	}
}
