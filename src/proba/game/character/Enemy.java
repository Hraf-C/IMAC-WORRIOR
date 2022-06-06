package proba.game.character;

public class Enemy extends Character {
	private final int _id;
	private double _weight;
	public Enemy(int _id, String _name, int _hp, int _xp, int _atk, int _def, int _hitScore, int dodgeScore, double _weight) {
		super(_name, _hp, _xp, _atk, _def, _hitScore, dodgeScore);
		this._id=_id;
		this._weight = _weight;
	}
	
	public String get_name() {
		return _name;
	}

	public int get_hp() {
		return _hp;
	}
	
	public void set_hp(int hp) {
		this._hp = hp;
	}
	public int get_xp() {
		return _xp;
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
	
	public void set_stats(int multiplier) {
		this._hp = _hp * (multiplier);
		this._xp = _xp * (multiplier);
		this._atk = _atk * (multiplier);
		this._def = _def * (multiplier);
	}
	
	
	public int get_id() {
		return _id;
	}

	public double get_weight() {
		return _weight;
	}

	public void set_weight(double _weight) {
		this._weight = _weight;
	}

	@Override // only to debug player class
	public String toString() {
		// TODO Auto-generated method stub
		return "Enemy stats : hp = "+ _hp 
				+ ", atk = " + _atk 
				+ ", def = "+ _def 
				+ ", hitScore = " + _hitScore
				+ ", dodgeScore = " +_dodgeScore
				+ ", xp = " +_xp
				+ ", name = " + _name;
	}
	@Override
	public int atk() {
		int atk = (int)Math.floor(Math.random() * _atk);
		return atk;
	}

}
