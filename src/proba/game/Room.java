package proba.game;

import java.util.ArrayList;
import proba.game.character.*;
import proba.game.utils.*;
import proba.game.utils.Probabilities;

public class Room extends Place{
	private Environment _env;
	
	public Room(int _id, String _name,  double _weight) {
		super(_name, _id, _weight);
		// TODO Auto-generated constructor stub
	}
	public Room(int _id, String _name, Environment _env, double _weight) {
		super(_name, _id, _weight);
		this._env = _env;
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return this._name;
	}
	
	public String getEnv() {
		return this._env.getName();
	}
	public void setEnv(Environment _env) {
		this._env = _env;
	}
	public int getId() {
		return _id;
	}
	
	public double getWeight() {
		return _weight;
	}
	
	public void setWeight(double weight) {
		this._weight = weight;
	}
	//comparing id number instead of name to make compute faster (number comparison faster than String comparison)
	public void roomAction(Player player, Difficulty diff, ArrayList<String> lootName) {
		
		if(_id == 1) {
			fight(player, diff);
		}
		if(_id == 2) {
			trap(player, diff);
		}
		if(_id == 3) {
			rest(player, diff);
		}
		if(_id == 4) {
			lootRoom(player, diff, lootName);
		}
	}
	//n number of iteration
	public void trap(Player player, Difficulty diff) {
		System.out.println("You enter in a room where you detect a trap, you try to deactivate it...");
		boolean hit = Probabilities.binomial(100, diff.get_malus()*100, (double)player.get_dodgeScore());
		double damage = (Math.random() * (diff.get_malus() - 0.1) + 0.1) * 100;
		if(!hit) {
			Probabilities.bernouilliDistributionList.add(0);
			System.out.println("Sadly you fail ! because of clumsiness... \nyou lose " + (int) damage +" hp !");
			player.set_hp(player.get_hp() - (int)damage);
		}
		else {
			Probabilities.bernouilliDistributionList.add(1);
			int xp = (int)(Math.random() * Math.floor(diff.get_bonus()*100)/2);
			System.out.println("You deactivate the trap with success ! How lucky you are... \nYou earned " + xp + " xp by deactivating the trap !");
			player.set_xp(player.get_xp() + xp);
		}	
	}
	public void rest(Player player, Difficulty diff) {
		double rest = 0;
		if(_env._id == 4) {
			rest = (Math.random() * (diff.get_malus() - 0.1) + 0.3) * 100;
			System.out.println("You enter in a Town with a small inn, you decide to take a rest for a night \nYou recover " + (int)rest + "hp !");
		}
		else {
			rest = Math.random() * diff.get_malus() * 100;
			System.out.println("After a long trip you decide to take a rest in this room that appears to be a "+ _env._name + "\nYou recover " + (int)rest + " hp !");
		}
		
		if(player.get_hp() + (int)rest < player.get_maxHp()) {
			player.set_hp(player.get_hp() + (int)rest);
		}
		else {
			player.set_hp(player.get_maxHp());
		}
	}
	
	public void fight(Player player, Difficulty diff) {
		//var declarations
		System.out.println("You enter in a fight room !");
		boolean playerTurn = true, runaway = false, hit = false, fight = true;
		int input;
		Enemy elf = Generate.enemyStats(diff.get_malus(), 100, 1, "elf");
		Enemy skeleton = Generate.enemyStats(diff.get_malus(), 100, 3, "skeleton");
		Enemy bandit = Generate.enemyStats(diff.get_malus(), 100, 4, "bandit");
		Enemy dryad = Generate.enemyStats(diff.get_malus(), 100, 2, "dryad");
		
		changeStatsByEnv(diff, elf, skeleton, dryad, bandit);
		Enemy encounter = Probabilities.encounter(elf, skeleton, bandit, dryad);
		
//		--- for debuging random func
//		System.out.println(elf + "\n" + skeleton + "\n" + bandit + "\n" + dryad + "\n" + _env._name + "\n");
//		System.out.println(encounter + "\n" + _env._name);
		if(encounter.get_id() == _env._id) {
			System.out.println("The enemy seems to get benefits from the environment, be carefful he's stronger than others... You'd better run away");
		}
		
		while(fight) {
			Helpers.printSeparator(30);
			int dmg = 0;
			if(playerTurn) {
				System.out.println("That's your turn what's your action ?\n1. Attack\n2. Enemy analysis\n3. Runaway");
				input = Helpers.readInt("->", 3);
				if(input == 1) {
					hit = Probabilities.binomial(100, 
							Math.floor((double) encounter.get_dodgeScore()), 
							Math.floor((double) player.get_hitScore() + (diff.get_bonus() * 100)));
					
					if(hit) {
						dmg = player.atk() - encounter.get_def() / (int)(diff.get_malus()*10);
						Probabilities.bernouilliDistributionList.add(1);
						if(dmg > 0) {
							encounter.set_hp(encounter.get_hp() - dmg);
							System.out.println("You dealt " + dmg + " to the " + encounter.get_name());
						}
						else {
							System.out.println("The enemy is indifferent to your attack !");
							Probabilities.bernouilliDistributionList.add(0);
						}
						hit = false;
					}
					else {
						System.out.println("you have unfortunately failed your attack...");
						Probabilities.bernouilliDistributionList.add(0);
					}
				}
				if(input == 2) {
					System.out.println("You launch an analysis of the enemy\n" + encounter);
				}
				if (input == 3) {
					runaway = Probabilities.binomial(100,
							Math.floor(diff.get_malus() * 100) + encounter.get_hitScore()/(int) (diff.get_bonus() * 10), 
							Math.floor((double)(player.get_luck() + player.get_dodgeScore()) + diff.get_bonus() * 100));
					
					//runaway condition
					if(runaway) {
						System.out.println("you succeed to runaway from the enemy !");
						Probabilities.bernouilliDistributionList.add(1);
						fight = false;
					}
					else {
						System.out.println("You fail to runaway from the enemy !");
						Probabilities.bernouilliDistributionList.add(0);
					}
				}
				playerTurn = false;
			}
			else {
				hit = Probabilities.binomial(100, 
						Math.floor((double) player.get_dodgeScore()), 
						Math.floor((double) encounter.get_hitScore() + (diff.get_malus() * 100)));
				if(hit) {
					dmg = encounter.atk() - player.get_def();
					Probabilities.bernouilliDistributionList.add(1);
					if(dmg > 0) {
						player.set_hp(player.get_hp() - dmg);
						System.out.println("you took " + dmg + " from the " + encounter.get_name());
					}
					else {
						System.out.println("You are indifferent to the enemy attack !");
						Probabilities.bernouilliDistributionList.add(0);
					}
				}
				else {
					System.out.println("You dodge the Enemy Attack !");
					Probabilities.bernouilliDistributionList.add(0);
				}
				playerTurn = true;
			}
			//end fight if hp are 0
			if(player.get_hp() <= 0 || encounter.get_hp() <= 0) {
				if(encounter.get_hp() <= 0) {
					System.out.println("You defeated a " + encounter.get_name());
					player.set_xp(player.get_xp() + encounter.get_xp());
				}
				if(player.get_hp() <= 0) {
					System.out.println("You are defeated by a " + encounter.get_name());
				}
				fight = false;
				Helpers.printSeparator(30);
			}
		}
		Helpers.printSeparator(30);
	}
	
	public void changeStatsByEnv(Difficulty diff, Enemy ...enemies) {
		for(Enemy i : enemies) {
			if(_env._id == i.get_id()) {
				i.set_weight(i.get_weight() + diff.get_malus() - 0.1);
				i.set_stats(2);
			}
		}
	}
	
	public void lootRoom(Player player, Difficulty diff, ArrayList<String> lootName) {
		System.out.println("By exploring this " + _env._name + " You find a chest and open it...");
		ArrayList<Items> loot = Generate.randomLootStats(lootName, diff.get_bonus(), diff.get_malus());
		Items item = Generate.randomLoot(loot);
		if(item.get_name().equals("Nothing")) {
			System.out.println("But Nothing... The Chest is empty :(");
		}
		else {
			System.out.println("You find the " + item.get_name() + "\nBy analysing the item you see his stats...\n" + item);
			Helpers.printSeparator(30);
			boolean decision = true;
			while (decision) {
				System.out.println("Will you equip it or destroy it to try to get some hp ? \n1. Equip the Item \n2.Destroy\n3. Watch your stats");
				int input = Helpers.readInt("->", 3);
				if(input == 1) {
					Helpers.printSeparator(30);
					System.out.println("You decide to equip the item your stats have change !");
					player.set_stats(item.get_stats());
					decision = false;
				}
				if(input == 2) {
					Helpers.printSeparator(30);
					double hp = item.get_hp() + Math.random()*10;
					if(hp < 0) {
						System.out.println("By destroying the item you receive " + (int)hp +" damage !");
					}
					else {
						System.out.println("By destroying this item you are heal of " + (int)hp +" hp !");
					}
					player.set_hp(player.get_hp() + (int)hp);
					decision = false;
				}
				if(input == 3) {
					Helpers.printSeparator(30);
					System.out.println("Here are the stats of the item and yours");
					System.out.println(item);
					System.out.println(player);
				}
			}
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Type : " + _name + ", Environment : " + _env.getName() + ", id : " + _id;
	}
}
