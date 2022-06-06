package proba.game.character;

public class Items extends Character {
	private double _weight;
	private int _luck;
	public Items(String _name, int _hp, int _xp, int _atk, int _def, int _hitScore, int _dodgeScore, int luck, double weight) {
		super(_name, _hp, _xp, _atk, _def, _hitScore, _dodgeScore);
		this._luck = luck;
		this._weight = weight;
		// TODO Auto-generated constructor stub
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public int get_hp() {
		return _hp;
	}
	public int get_atk() {
		return _atk;
	}
	public int get_def() {
		return _def;
	}
	public int get_hitScore() {
		return _hitScore;
	}
	public int get_dodgeScore() {
		return _dodgeScore;
	}
	public int get_luck() {
		return _luck;
	}
	public double get_weight() {
		return _weight;
	}
	public int[] get_stats() {
		int[] stats = {_hp, _atk, _def, _hitScore, _dodgeScore, _luck}; 
		return stats;
	}
	@Override // only to debug player class
	public String toString() {
		// TODO Auto-generated method stub
		return "Item stats : hp = "+ _hp 
				+ ", atk = " + _atk 
				+ ", def = "+ _def 
				+ ", hitScore = " + _hitScore
				+ ", dodgeScore = " +_dodgeScore
				+ ", luck = " +_luck
				+ ", name = " +_name;
	}
	@Override
	public int atk() {
		// TODO Auto-generated method stub
		return 0;
	}

}
