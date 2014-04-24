package javagame;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Highscores {
	
	//instance of class
	private static Highscores instance = null;
	
	static File file = new File("scores.txt");
	
	//array of scores
	static ArrayList<Integer> scores = null;
	
	//constructor
	public Highscores(int size) {
		scores = new ArrayList<Integer>();
		
		loadScores(size);
		
		//set scores to 0
		//for(int i = 0; i < size; i++)
			//scores.add(new Integer(0));
	}
	
	public static Highscores getInstance() {
		//if there are no scores create new scores
		if(instance == null)
			instance = new Highscores(10);
		
		return instance;
	}
	
	public boolean addScore(int score) {
		//check to see if new highscore
		for(int idx = 0; idx < scores.size(); idx++) {
			if(score > scores.get(idx)) {
				scores.add(idx, new Integer(score));
				scores.remove(scores.size() - 1);
				
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<Integer> getScores() {
		return scores;
	}
	
	public boolean checkIsFile() {
		return file.isFile();
	}
	
	public void loadScores(int size) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			for(int i = 0; i < size; i++) {
				scores.add(i, Integer.parseInt(reader.readLine()));
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void saveScores() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < scores.size(); i++) {
				writer.write(scores.get(i).toString());
				writer.newLine();
			}
			writer.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
