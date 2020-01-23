package expression;

public class Div extends BinaryExpression {

	
	
	
	public Div(Expression left, Expression right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calculate() {
		return left.calculate() / right.calculate();
	}

}
