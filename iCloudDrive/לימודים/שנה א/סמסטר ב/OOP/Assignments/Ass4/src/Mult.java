import java.util.List;
import java.util.Map;

/**
 * @author Roey Shefi & Oded Thaller
 * @version 1.0
 * @since 10/04/2016
 */
public class Mult extends BinaryExpression implements Expression {

    /**
     * Instantiates a binary expression.
     * <p>
     * @param exp1 the expression from the superClass BaseExpression.
     * @param exp2 the provided second expression.
     */
    public Mult(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    /**
     * Instantiates a binary expression.
     * <p>
     * @param exp the expression from the superClass BaseExpression.
     * @param var the provided second expression.
     */
    public Mult(Expression exp, String var) {
        super(exp, var);
    }

    /**
     * Instantiates a binary expression.
     * <p>
     * @param var the expression from the superClass BaseExpression.
     * @param exp the provided second expression.
     */
    public Mult(String var, Expression exp) {
        super(var, exp);
    }

    /**
     * Instantiates a binary expression.
     * <p>
     * @param exp the expression from the superClass BaseExpression.
     * @param num the provided second expression.
     */
    public Mult(Expression exp, double num) {
        super(exp, num);
    }

    /**
     * Instantiates a binary expression.
     * <p>
     * @param num the expression from the superClass BaseExpression.
     * @param exp the provided second expression.
     */
    public Mult(double num, Expression exp) {
        super(num, exp);
    }

    /**
     * Instantiates a binary expression.
     * <p>
     * @param num the expression from the superClass BaseExpression.
     * @param var the provided second expression.
     */
    public Mult(double num, String var) {
        super(num, var);
    }

    /**
     * Instantiates a binary expression.
     * <p>
     * @param var the expression from the superClass BaseExpression.
     * @param num the provided second expression.
     */
    public Mult(String var, double num) {
        super(var, num);
    }

    /**
     * Instantiates a binary expression.
     * <p>
     * @param var1 the expression from the superClass BaseExpression.
     * @param var2 the provided second expression.
     */
    public Mult(String var1, String var2) {
        super(var1, var2);
    }

    /**
     * Instantiates a binary expression.
     * <p>
     * @param num1 the expression from the superClass BaseExpression.
     * @param num2 the provided second expression.
     */
    public Mult(double num1, double num2) {
        super(num1, num2);
    }

    /**
     * Assigns values to variables and evaluates the result of the expression.
     * <p>
     * @param assignment a map of the variables and their assigned values.
     * @return the result of the expression.
     * @throws Exception when there are unassigned variables.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        Mult e = new Mult(this.exp1, this.exp2);
        for (String key: assignment.keySet()) {
            e = (Mult) e.assign(key, new Num(assignment.get(key)));
        }
        if (!e.getVariables().isEmpty()) {
            throw new Exception("There are unassigned variables");
        }
        return e.exp1.evaluate() * e.exp2.evaluate();
    }

    /**
     * evaluates the result of the expression without variable assignment.
     * <p>
     * @return the result of the expression.
     * @throws Exception when there are unassigned variables.
     */
    @Override
    public double evaluate() throws Exception {
        return super.evaluate();
    }

    /**
     * Returns a list of unique variables in the expression.
     * <p>
     * @return list of variables.
     */
    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    /**
     * Prints the expression in a specific format.
     * <p>
     * @return a string representation of the expression.
     */
    @Override
    public String toString() {
        String s1 = this.exp1.toString();
        String s2 = this.exp2.toString();
        String s3 = s1 + " * " + s2;
        return "(" + s3 + ")";
    }

    /**
     * assigns the given values to the variables.
     * <p>
     * @param var the variables to be assigned.
     * @param expression the expression to assigned.
     * @return a new expression with the assigned variables.
     */
    @Override
    public Expression assign(String var, Expression expression) {
        Expression e1 = this.exp1.assign(var, expression);
        Expression e2 = this.exp2.assign(var, expression);

        return new Mult(e1, e2);
    }

    /**
     * returns the derivative of the given expression according to the given variable.
     * <p>
     * @param var the variable for the derivative.
     * @return the differentiated expression.
     */
    @Override
    public Expression differentiate(String var) {
        if (this.getVariables().contains(var)) {
            return new Plus(new Mult(this.exp1.differentiate(var), this.exp2),
                    new Mult(this.exp1, this.exp2.differentiate(var)));
        }
        return new Num(0);
    }

    /**
     * Simplifies the expression to make it easier to read.
     * <p>
     * Implemented simplifications:
     * <ul>
     *     <li> x * 1 = x (also 1 * x = x)
     *     <li> x * 0 = 0 (also 0 * x = 0)
     *     <li> an expression without variables evaluates to its result.
     * </ul>
     * @return the simplified expression.
     */
    @Override
    public Expression simplify() {
        if (this.getVariables().isEmpty()) {
            try {
                return new Num(this.evaluate());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.exp1.toString().equals("0.0") || this.exp2.toString().equals("0.0")) {
            return new Num(0);
        } else if (this.exp1.toString().equals("1.0")) {
            return this.exp2.simplify();
        } else if (this.exp2.toString().equals("1.0")) {
            return this.exp1.simplify();
        }

        Expression e1 = this.exp1.simplify();
        Expression e2 = this.exp2.simplify();

        return new Mult(e1, e2);
    }

}