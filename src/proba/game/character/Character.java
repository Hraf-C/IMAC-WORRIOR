package proba.game.character;

public abstract class Character {
	protected String _name;
	protected int _hp, _xp, _atk, _def, _hitScore, _dodgeScore;
	
	
	public Character(String _name, int _hp, int _xp, int _atk, int _def, int _hitScore, int dodgeScore) {
		super();
		this._name = _name;
		this._hp = _hp;
		this._xp = _xp;
		this._atk = _atk;
		this._def = _def;
		this._hitScore = _hitScore;
		this._dodgeScore = dodgeScore;
	}
	

	public abstract int atk();
}
