package test;
//
public class Variable {

	public double value;
	public String name;
	public int bindFlag;
	public String bindMem;

	public Variable(double value, String varName) {
		this.value = value;
		this.name = varName;
		bindFlag = 0;
		bindMem = null;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;

	}

	public void changeFlag() {
		bindFlag = 1;

	}
}