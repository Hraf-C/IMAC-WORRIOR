package proba.game.utils;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
public class Helpers {
	private static Scanner scanner = new Scanner(System.in);
	//Handle user input
	public static int readInt (String prompt, int userChoices) {
		int input;
		
		do {
			System.out.println(prompt);
			try {
				input = Integer.parseInt(scanner.next());
				if(input > userChoices) {
					System.out.println( input + " is not in your choice possibilities ! please choose between the propositions...");
				}
			} catch (Exception e) {
				input = -1;
				System.out.println("Please enter an integer !");
				// TODO: handle exception
			}
		}while(input < 1 || input > userChoices);
		return input;
	}
	//Clear console
	public static void clearConsole() {
		for (int i = 0; i < 100; i++) {
			System.out.println();
		}
	}
	//separator like ------------
	public static void printSeparator(int n) {
		String separator = "";
		for (int i = 0; i < n; i++) {
			separator += "-";
		}
		System.out.println(separator);
	}
	//print title
	public static void printTitle(String title) {
		printSeparator(30);
		System.out.println(title);
		printSeparator(30);
	}
	
	public static void anythingToContinue() {
		System.out.println("\nPress Enter to continue...");
		scanner.nextLine();
	}
	
	public static ArrayList<String> readTxt(Path path) {
    	ArrayList<String> list = new ArrayList<String>();
    	BufferedReader reader;
    	
    	try {
    		reader = new BufferedReader(new FileReader(""+path));
			
    		String line = reader.readLine();
    		
    		while(line != null) {
    			line.split("\n");
    			list.add(line);
    			line = reader.readLine();
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static void printStats(String sentence, double e) {
		if(e == 0) {
			System.out.println(sentence + " : La distribution n'a pas été utilisé dans le jeux");
		}
		else {
			System.out.println(sentence + " : " + e);
		}
	}
	public static void printStats(String sentence, String printList) {
		if(printList == null) {
			System.out.println(sentence + " : La distribution n'a pas été utilisé dans le jeux");
		}
		else {
			System.out.println(sentence + " : " + printList);
		}
	}
	public static String printList(ArrayList<?> list) {
		String str = null;
		if(list.size() > 0) {
			str = "[";
			for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				if(iterator.hasNext()) {
					str += object + ", ";
				}
			}
			str += "]";
		}
		else {
			str = "Pas de données pour cette variable";
		}
		return str;
	}
}
