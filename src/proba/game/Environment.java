package proba.game;

public class Environment extends Place{

	public Environment(int _id, String _name, double _weight) {
		super(_name, _id, _weight);
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return this._name;
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
}
