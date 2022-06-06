package proba.game.utils;

public class Difficulty {
	private final double _bonus, _malus;

	public Difficulty(double _bonus, double _malus) {
		super();
		this._bonus = _bonus;
		this._malus = _malus;
	}
	
	public double get_bonus() {
		return _bonus;
	}

	public double get_malus() {
		return _malus;
	}


	
	
	
}
