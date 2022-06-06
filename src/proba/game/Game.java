package proba.game;

import proba.game.utils.*;
import proba.game.character.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;



public class Game {
	private static Scanner scanner = new Scanner(System.in);
	
	public static Difficulty chooseDifficulty(){
		boolean diffSet = false;
		Difficulty diff = null;
		System.out.println("First you must choose a difficulty !");
		System.out.println("1. Easy");
		System.out.println("2. Normal");
		System.out.println("3. Hard");
		
		do {
			int input = Helpers.readInt("->",3);
			
			if(input == 1) {
				System.out.println("You choose to play in Easy Mod !");
				diff = new Difficulty(0.6, 0.4);
				diffSet = true;
			}
			if(input == 2) {
				System.out.println("You choose to play in Normal Mod !");
				diff = new Difficulty(0.5, 0.5);
				diffSet = true;
			}
			if(input == 3) {
				System.out.println("You choose to play in Hard Mod !");
				diff = new Difficulty(0.4, 0.6);
				diffSet = true;
			}
		}while(!diffSet);
		return diff;
	}
	public static void chooseName(Player player) {
		String name;
		boolean nameSet = false;
		do {
			System.out.println("Enter your name !");
			name = scanner.next();
			System.out.println("So Your name is " + name + "...\nAre you sure ?\n1. Yes\n2. No");
			int input = Helpers.readInt("-> ", 2);
			if(input == 1) {
				player.set_name(name);
				nameSet = true;
			}
			else {
				System.out.println("So you don't know your name, this game is not going to be easy for you !");
			}
		}while(!nameSet);
	}
	public static void startGame() {
		//to get story and rules
		Path path[] = {Paths.get("data/intro.txt"), Paths.get("data/items.txt")};
		ArrayList<String> intro = Helpers.readTxt(path[0].toAbsolutePath());
		for (Iterator<String> iterator = intro.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
		ArrayList<String> lootName = Helpers.readTxt(path[1].toAbsolutePath());
		
		Helpers.printSeparator(30);
		Helpers.anythingToContinue();
		Difficulty diff = chooseDifficulty();
		Player player = Generate.playerStats(diff.get_bonus(), 100);
		Helpers.clearConsole();
		chooseName(player);
		System.out.println(player);
		Helpers.printSeparator(30);
		
		//Malus Room --- Room have the same chance to being choose but difficulty modify it 
		Room fight = new Room(1, "Fight", diff.get_malus());
		Room trap = new Room(4, "Trap", diff.get_malus());
		//Bonus Room
		Room rest = new Room(2, "Rest", diff.get_bonus());
		Room loot = new Room(3, "Loot", diff.get_bonus());
		
		//Environment 
		Environment forest = new Environment(1, "forest", 0.25);
		Environment town = new Environment(4, "town", 0.25);
		Environment river = new Environment(2, "river", 0.25);
		Environment swamp = new Environment(3, "swamp", 0.25);
		
		//game loop
		boolean game = true;
		
		while(game) {
			if(player.get_hp() <=0) {
				game = false;
				System.out.println("Game Over ! \nYou Are defeated by the Darklord's forces");
			}
			else {
				System.out.println("Would you continue to explore the dungeon \n1. Continue... \n2. Watch my stats \n3. No I'm done !");
				int input = Helpers.readInt("-> ", 3);
				//pick a room and an enviromnment according to their weight
				//--- loi binomiale -- car les poids sont influencé par la difficulté choisie par le joueur.
				Room room = Probabilities.roomDistribution(fight, trap, rest, loot);
				//--- loi uniforme discrète car tous les poids sont à 0.25 donc on a autant de chance de tirer une forest qu'une town ou un swamp ou une river
				Probabilities.envDistribution(room, forest, town, river, swamp);
				if(input == 1) {
					room.roomAction(player, diff, lootName);
					Helpers.printSeparator(30);
				}
				if(input == 2) {
					System.out.println(player);
					Helpers.printSeparator(30);
				}
				if(input == 3) {
					Helpers.clearConsole();
					player.set_hp(0);
					System.out.println("See you next time !");
					game = false;
				}
			}
			if(player.get_xp() >= (int)(diff.get_bonus() * 1000)) {
				Helpers.clearConsole();
				System.out.println("You defeat the dark forces of this dungeon !\n The Darklord runaway face to your power !\n Will you try another run to try to capture him ?");
				game = false;
			}
		}
		double uniformAverage = Probabilities.average(Probabilities.uniformDitributionList);
		double bernouilliAverage = Probabilities.bernouilliAverage(Probabilities.bernouilliDistributionList);
		double irwinHallAverage =  Probabilities.average(Probabilities.poisson);
		double triangulareAverage = Probabilities.average(Probabilities.triangulareDistributionList);
		double weightedAverage = Probabilities.average(Probabilities.weightedDistributionList);
		
		ArrayList<Double> uniformDeviationValues = Probabilities.meanDeviationValues(uniformAverage, Probabilities.uniformDitributionList);
		ArrayList<Double> bernouilliDeviationValues = Probabilities.bernouilliMeanDeviationValues(bernouilliAverage, Probabilities.bernouilliDistributionList);
		ArrayList<Double> IH2MeanDeviationValues = Probabilities.meanDeviationValues(uniformAverage, Probabilities.poisson);
		ArrayList<Double> triangularMeanDeviationValues = Probabilities.meanDeviationValues(uniformAverage, Probabilities.triangulareDistributionList);
		ArrayList<Double> weightedDeviationValues = Probabilities.meanDeviationValues(uniformAverage, Probabilities.weightedDistributionList);
		
//		double uniformMeanDeviation = Probabilities.meanDeviation(uniformDeviationValues);
//		double bernouilliMeanDeviation = Probabilities.meanDeviation(bernouilliDeviationValues);  
//		double IH2MeanDeviation = Probabilities.meanDeviation(IH2MeanDeviationValues);  
//		double triangularMeanDeviation = Probabilities.meanDeviation(triangularMeanDeviationValues);  
//		double weightedMeanDeviation = Probabilities.meanDeviation(weightedDeviationValues);  
		
		double uniformVariance = Probabilities.variance(uniformAverage, uniformDeviationValues);
		double bernouilliVariance = Probabilities.variance(bernouilliAverage, bernouilliDeviationValues);
		double IH2Variance = Probabilities.variance(irwinHallAverage, IH2MeanDeviationValues);
		double triangularVariance = Probabilities.variance(triangulareAverage, triangularMeanDeviationValues);
		double weightedVariance = Probabilities.variance(weightedAverage, weightedDeviationValues);
		
		double uniformStandardDeviation = Probabilities.standardDeviation(uniformVariance);
		double bernouilliStandardDeviation = Probabilities.standardDeviation(bernouilliVariance);
		double IH2StandardDeviation = Probabilities.standardDeviation(IH2Variance);
		double triangularStandardDeviation = Probabilities.standardDeviation(triangularVariance);
		double weightedStandardDeviation = Probabilities.standardDeviation(weightedVariance);
		
		Helpers.printSeparator(100);
		System.out.println("---- Voici les stats du jeu ----\n\n");
		System.out.println(player);
		System.out.println("---- nombre de 0 et de 1 pour les tirage de Bernouilli & Moyenne ----");
		Probabilities.bernouilliZeroAndOne(Probabilities.bernouilliDistributionList);
		Helpers.printSeparator(100);
		System.out.println("---- Moyenne ----");
		Helpers.printStats("Moyenne pour Distribution Uniforme", uniformAverage);
		Helpers.printStats("Moyenne pour Distribution Bernouilli", bernouilliAverage);
		Helpers.printStats("Moyenne pour Distribution Poisson",irwinHallAverage);
		Helpers.printStats("Moyenne pour Distribution Triangulaire", triangulareAverage);
		Helpers.printStats("Moyenne pour Distribution par Poids", weightedAverage);
		Helpers.printSeparator(100);
//		System.out.println("---- Ecart à la Moyenne ----");
//		Helpers.printStats("Ecart à la moyenne pour Distribution Uniforme", uniformMeanDeviation);
//		Helpers.printStats("Ecart à la moyenne pour Distribution Bernouilli", bernouilliMeanDeviation);
//		Helpers.printStats("Ecart à la moyenne pour Distribution Poisson",IH2MeanDeviation);
//		Helpers.printStats("Ecart à la moyenne pour Distribution Triangulaire", triangularMeanDeviation);
//		Helpers.printStats("Ecart à la moyenne pour Distribution par Poids", weightedMeanDeviation);
//		Helpers.printSeparator(100);
		System.out.println("---- Variance ----");
		Helpers.printStats("Variance pour Distribution Uniforme", uniformVariance);
		Helpers.printStats("Variance pour Distribution Bernouilli", bernouilliVariance);
		Helpers.printStats("Variance pour Distribution Poisson", IH2Variance);
		Helpers.printStats("Variance pour Distribution Triangulaire", triangularVariance);
		Helpers.printStats("Variance pour Distribution par Poids", weightedVariance);
		Helpers.printSeparator(100);
		System.out.println("---- Ecart Type ----");
		Helpers.printStats("Ecart Type pour Distribution Uniforme", uniformStandardDeviation);
		Helpers.printStats("Ecart Type pour Distribution Bernouilli", bernouilliStandardDeviation);
		Helpers.printStats("Ecart Type pour Distribution Poisson", IH2StandardDeviation);
		Helpers.printStats("Ecart Type pour Distribution Triangulaire", triangularStandardDeviation);
		Helpers.printStats("Ecart Type pour Distribution par Poids", weightedStandardDeviation);
		Helpers.printSeparator(100);
		
	}
}
