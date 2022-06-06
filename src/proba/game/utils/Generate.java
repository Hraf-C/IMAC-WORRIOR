package proba.game.utils;

import java.util.ArrayList;
import java.util.Iterator;

import proba.game.character.Enemy;
import proba.game.character.Items;
import proba.game.character.Player;

public class Generate {
	//distribution de Poisson
	public static Player playerStats(double bonus, int n) {
		
		double hp = 0, atk = 0, def = 0, hitScore = 0, dodgeScore = 0, luck = 0;
		
			hp 			+= Probabilities.poisson(bonus * 100); 
			atk 		+= Probabilities.poisson(bonus * 100); 
			def 		+= Probabilities.poisson(bonus * 100); 
			hitScore 	+= Probabilities.poisson(bonus * 100); 
			dodgeScore 	+= Probabilities.poisson(bonus * 100); 
			luck 		+= Probabilities.poisson(bonus * 100); 
			
			Probabilities.poisson.add(hp);
			Probabilities.poisson.add(atk);
			Probabilities.poisson.add(def);
			Probabilities.poisson.add(hitScore);
			Probabilities.poisson.add(dodgeScore);
			Probabilities.poisson.add(luck);
		
		
		Player player = new Player("",(int)hp , 0, (int)atk, (int)def, (int)hitScore, (int)dodgeScore, (int)luck );
		return player;
	}
	//distribution uniforme
	public static Enemy enemyStats(double malus, int n, int id, String name) {
		
		double hp = 0, atk = 0, def = 0, hitScore = 0, dodgeScore = 0, xp = 0;

		for (int i = 0; i < n; i++) {
			// --- 
			hp 			= Probabilities.uniformeDistribution(malus, 1, 100);
			atk 		= Probabilities.uniformeDistribution(malus, 1, 100);
			def 		= Probabilities.uniformeDistribution(malus, 1, 100);
			hitScore 	= Probabilities.uniformeDistribution(malus, 1, 100);
			dodgeScore 	= Probabilities.uniformeDistribution(malus, 1, 100);
			xp 			= Probabilities.uniformeDistribution(malus, 1, 100);
			
			Probabilities.uniformDitributionList.add(hp);
			Probabilities.uniformDitributionList.add(atk);
			Probabilities.uniformDitributionList.add(def);
			Probabilities.uniformDitributionList.add(hitScore);
			Probabilities.uniformDitributionList.add(dodgeScore);
			Probabilities.uniformDitributionList.add(xp);
		}
		
		Enemy enemy = new Enemy(id, name, (int)hp, (int)xp, (int)atk, (int)def, (int)hitScore, (int)dodgeScore, 0.25);
		return enemy;
	}
	
	public static ArrayList<Items> randomLootStats(ArrayList<String> lootName, double bonus, double malus) {
		ArrayList<Items> loots = new ArrayList<Items>();
		double hp = 0, atk = 0, def = 0, hitScore = 0, dodgeScore = 0, luck = 0;
		//make a number of trials for lentgh of lootName size --- generate number between bonus and -bonus (between 0.6 * 20 and -0.6*20)
 		for (int i = 0; i < lootName.size(); i++) {
			hp 			= Probabilities.triangularDistribution(-malus*50, bonus*100, 0, Math.random());
			atk 		= Probabilities.triangularDistribution(-malus*50, bonus*100, 0, Math.random());
			def 		= Probabilities.triangularDistribution(-malus*50, bonus*100, 0, Math.random());
			hitScore 	= Probabilities.triangularDistribution(-malus*50, bonus*100, 0, Math.random());
			dodgeScore 	= Probabilities.triangularDistribution(-malus*50, bonus*100, 0, Math.random());
			luck 		= Probabilities.triangularDistribution(-malus*50, bonus*100, 0, Math.random());
			
			Probabilities.triangulareDistributionList.add(hp);
			Probabilities.triangulareDistributionList.add(atk);
			Probabilities.triangulareDistributionList.add(def);
			Probabilities.triangulareDistributionList.add(hitScore);
			Probabilities.triangulareDistributionList.add(dodgeScore);
			Probabilities.triangulareDistributionList.add(luck);
			
			Items item = new Items(lootName.get(i), (int)hp, 0, (int)atk, (int)def, (int)hitScore, (int)dodgeScore, (int)luck, bonus);
			loots.add(item);
		}
			Items nothing = new Items("Nothing",0,0,0,0,0,0,0,malus*loots.size());
			loots.add(nothing);
			
		return loots;
	}
	public static Items randomLoot(ArrayList<Items> loots) {
		double totalWeight = 0.0;
		for (Iterator<Items> iterator = loots.iterator(); iterator.hasNext();) {
			Items items = (Items) iterator.next();
			totalWeight += items.get_weight();
		}
		int idx = 0;
		for (double r = Math.random() * totalWeight; idx < loots.size() - 1; ++idx) {
		    r -= loots.get(idx).get_weight();
		    if (r <= 0.0) break;
		}
		
		Items random = loots.get(idx);
		return random;
	}
}
