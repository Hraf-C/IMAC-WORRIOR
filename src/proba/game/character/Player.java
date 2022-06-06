package proba.game.character;

public class Player extends Character {
	private int _maxHp;
	private int _luck;
	public Player(String _name, int _hp, int _xp, int _atk, int _def, int _hitScore, int _dodgeScore, int luck) {
		super(_name, _hp, _xp, _atk, _def, _hitScore, _dodgeScore);
		this._maxHp = _hp;
		this._luck = luck;
		// TODO Auto-generated constructor stub
	}
	
	public int get_maxHp() {
		return _maxHp;
	}

	public void set_maxHp(int _maxHp) {
		this._maxHp = _maxHp;
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

	public void set_hp(int _hp) {
		this._hp = _hp;
	}

	public int get_xp() {
		return _xp;
	}

	public void set_xp(int _xp) {
		this._xp = _xp;
	}

	public int get_atk() {
		return _atk;
	}

	public void set_atk(int _atk) {
		this._atk = _atk;
	}

	public int get_def() {
		return _def;
	}

	public void set_def(int _def) {
		this._def = _def;
	}

	public int get_hitScore() {
		return _hitScore;
	}

	public void set_hitScore(int _hitScore) {
		this._hitScore = _hitScore;
	}
	
	public int get_dodgeScore() {
		return _dodgeScore;
	}

	public void set_dodgeScore(int _dodgeScore) {
		this._dodgeScore = _dodgeScore;
	}
	public int get_luck() {
		return _luck;
	}

	public void set_luck(int _luck) {
		this._luck = _luck;
	}
	
	public void set_stats(int[] stats) {
		this._hp = _hp + stats[0];
		this._maxHp = _maxHp + stats[0];
		this._atk = _atk + stats[1];
		this._def = _def + stats[2];
		this._hitScore = _hitScore + stats[3];
		this._dodgeScore = _dodgeScore + stats[4];
		this._luck = _luck + stats[5];
	}

	@Override // only to debug player class
	public String toString() {
		// TODO Auto-generated method stub
		return "Player stats : hp = "+ _hp
				+ ", maxHp = " + _maxHp 
				+ ", atk = " + _atk 
				+ ", def = "+ _def 
				+ ", hitScore = " + _hitScore
				+ ", dodgeScore = " +_dodgeScore
				+ ", luck = " +_luck
				+ ", xp = " +_xp
				+ ", name = " + _name;
	}
	@Override
	public int atk() {
		int atk = (int)Math.floor(Math.random() * _atk);
		return atk;
	}
}
