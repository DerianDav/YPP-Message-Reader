package model;

import java.io.File;
import java.io.FileNotFoundException;

public class InputMain {
	
	public static void main(String[] args) {
		InputMain im = new InputMain();
		im.start();
	}

	private  void start() {
		File file = new File("C:\\Users\\black\\Documents\\Arcticwolf_obsidian_Chat logs");
		File file2 = new File("C:\\Users\\black\\Documents\\WolfSpotter_obsidian_Chat logs");
		File file3 = new File("C:\\Users\\black\\Documents\\Arcticwolf_obsidian_Chat logs2");
		try {
			Input_Sorter is = new Input_Sorter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
