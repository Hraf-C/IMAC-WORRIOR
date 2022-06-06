package proba.game.utils;

import proba.game.*;
import proba.game.character.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
public class Probabilities {
	
	public static ArrayList<Double> uniformDitributionList = new ArrayList<Double>();
	public static ArrayList<Integer> bernouilliDistributionList = new ArrayList<Integer>();
	public static ArrayList<Double> weightedDistributionList = new ArrayList<Double>();
	public static ArrayList<Double> poisson = new ArrayList<Double>();
	public static ArrayList<Double> triangulareDistributionList = new ArrayList<Double>();

	// ---- Distribution uniforme entre deux êxtremes l'une avec multiplicateur l'autre non
	public static double uniformeDistribution(double min, double max, double multiplier) {
		double result = Math.floor((Math.random() * (max - min) + min) * multiplier);
		return result;
	}
	public static double uniformeDistribution(double min, double max) {
		double result = (Math.random() * (max - min) + min);
		return result;
	}
	//-----------------------------------------------
	// --- weighted distribution ---
	//On prend en paramètre un tableau de room
	public static Room roomDistribution(Room ...room) {
			double totalWeight = 0.0;
			//on définit un poid total
			//on parcours notre tableau chambre dont on additionne les poids
			for(Room i : room) {
				totalWeight += i.getWeight();
			}
			//On définit un id qui prendra en fin de boucle la valeur de position de la chambre
			//dans le tableau 
			//par ex : room[3] soit la troisième chambre dans le tableau
			int idx = 0;
			//on génére un nombre aléatoire multiplié par le total des poids
			//Tant que idx < la taille du tableau alors idx += 1 
			for (double r = Math.random() * totalWeight; idx < room.length - 1; ++idx) {
					//Dans un même temps on soustrait a notre nombre aléatoire les poids des chambres
					//si r <= 0.0 on arrête la boucle et donc idx vaudra un nombre compris entre 0 et 3 (1 et 4 mathématiquement parlant)
			    r -= room[idx].getWeight();
			    if (r <= 0.0) break;
			}
			
			Room randomRoom = room[idx];
			weightedDistributionList.add((double)randomRoom.getId());
			
			return randomRoom;
		}
	
	public static Enemy encounter(Enemy ...enemies) {
		double totalWeight = 0.0;
		for(Enemy i : enemies) {
			totalWeight += i.get_weight();
		}
		int idx = 0;
		for (double r = Math.random() * totalWeight; idx < enemies.length - 1; ++idx) {
		    r -= enemies[idx].get_weight();
		    if (r <= 0.0) break;
		}
		
		Enemy randomEncounter = enemies[idx];
		weightedDistributionList.add((double)randomEncounter.get_id());
		
		return randomEncounter;
	}
	public static void envDistribution(Room room, Environment ...env) {
		double totalWeight = 0.0;
		for(Environment i : env) {
			totalWeight += i.getWeight();
		}
		int idx = 0;
		for (double r = Math.random() * totalWeight; idx < env.length - 1; ++idx) {
		    r -= env[idx].getWeight();
		    if (r <= 0.0) break;
		}
		
		Environment randomEnv = env[idx];
		room.setEnv(randomEnv);
	}
	//-----------------------------------------------
	// Bernouilli law to now if player is touch or if enemy touching player --- n : number of trials, k : number of success to pass the test succesfully, p : Probability
	public static boolean binomial(int n, double k, double p) {
		boolean success;
		double log_q = Math.log(1.0 - p); // proba d'échec lors du lancer
		double random = 0;
		int x = 0;
		p /= 100; // Division de p par 100 car c'est un score compris entre 1 et 100
		for (int i = 0; i < n; i++) {
			random += Math.log(Math.random()) / (n - x);
			if(random >= log_q) {
				x++;
			}	
		}

		if(x >= k) { // si le nombre de x soit le nombre de succés et >= k alors c'est un succés
			success = true;
		}
		else {
			success = false;
		}
		
		return success;
	}
	//-----------------------------------------------
	// ---- distribution triangulaire ----
	public static double triangularDistribution(double a, double b, double c, double rand) {
	    double F = (c - a) / (b - a);
	    double result;
	    if (rand < F) {
	    	result = a + Math.sqrt(rand * (b - a) * (c - a));
	    	triangulareDistributionList.add(result);
	        return result;
	    } else {
	    	result = b - Math.sqrt((1 - rand) * (b - a) * (b - c));
	    	triangulareDistributionList.add(result);
	        return result;
	    }
	}
	//prend un nombre lambda en paramètre, chez nous ce nombre vaux bonus * 100
	public static int poisson(double lambda) {
			  double L = Math.exp(-lambda);
			  double p = 1.0;
			  int k = 0;
			  do {
			    k++;
			    p *= Math.random();
			  } while (p > L);
				//tant que p < Math.exp(-lambda) on remultiplie p par un nombre aléatoire et on ajoute 1 a k;
			  return k - 1;
	}// on retourne k - 1;
	
	//-----------------------------------------------
	// --- endgame stats ----
	public static double average(ArrayList<Double> list) {
		double sum = 0;
		double average = 0;
		//On parcours notre list et on fait la somme de ses membres
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i);
		}
		//on divise par la taille de la liste
		average = sum / list.size();
		//Si taille de liste vaut 0 alors moyenn = 0
		if (list.size() == 0) {
			average = 0;
		}
		return average;
	}
	public static ArrayList<Double> meanDeviationValues(double average, ArrayList<Double> list) {
		ArrayList<Double> meanDeviationList = new ArrayList<Double>();
		@SuppressWarnings("unused")
		double meanDeviation = 0;
		for (Iterator<Double> iterator = list.iterator(); iterator.hasNext();) {
			Double iter = (Double) iterator.next();
			meanDeviation = iter - average;
			meanDeviationList.add(iter);
		}
		return meanDeviationList;
	}
	
	public static double meanDeviation(ArrayList<Double> list) {
		double sum = 0;
		for (Iterator<Double> iterator = list.iterator(); iterator.hasNext();) {
			Double iter = (Double) iterator.next();
			sum += Math.abs(iter);
		}
		
		double divide = sum/list.size();
		return divide;
	}
	
	public static double variance(double average, ArrayList<Double> list) {
			double sum = 0;
	//On parcours le tableau de valeur
			for (Iterator<Double> iterator = list.iterator(); iterator.hasNext();) {
				Double iter = (Double) iterator.next();
				//On fait l'écart entre nos valeur et la moyenne au carré
				iter = Math.pow(iter - average, 2);
				//On fait la somme des écarts
				sum += iter;
			}
			
			//Enfin on divise par le nombre de valeur
			double divide = sum/list.size();
			return divide;
	}
	
	public static double standardDeviation(double e) {
		return Math.sqrt(e);
	}
	
	// ---- Notre Bernouilli est un cas special on cherche à calculer nos stats sur le nombre de 0 et de 1
	public static double bernouilliAverage (ArrayList<Integer> list) {
		Map<Integer, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		double average = 0;	
		double sum = 0;
		for (Entry<Integer, Long> entry : counts.entrySet()) {
			sum += entry.getValue();
		}
		average = sum/counts.size();
		return average;
	}
	public static ArrayList<Double> bernouilliMeanDeviationValues(double average, ArrayList<Integer> list) {
		ArrayList<Double> meanDeviationList = new ArrayList<Double>();
		Map<Integer, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		double meanDeviation = 0;
		for (Entry<Integer, Long> entry : counts.entrySet()) {
			meanDeviation = Math.abs(entry.getValue() - average);
			meanDeviationList.add(meanDeviation);
		}
		return meanDeviationList;
	}
	public static void bernouilliZeroAndOne(ArrayList<Integer> list) {
		Map<Integer, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		@SuppressWarnings("unused")
		double average = 0;	
		double sum = 0;
		System.out.println("Distribution de Bernouilli nombre de 0 et de 1 :");
		for (Entry<Integer, Long> entry : counts.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
			sum += entry.getValue();
		}
		average = sum/counts.size();
	}
	// Uniform Distribution
	
}

