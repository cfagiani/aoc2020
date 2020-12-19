package aoc.days;

import aoc.DaySolution;
import aoc.util.StringUtil;

import java.util.List;
import java.util.Stack;

/**
 * Evaluates mathematical expressions from the input file. To do this, we first convert the infix-notation to postfix
 * using the Shunting-yard algorithm. Then we evaluate the postfix expressions. Part 1 ignores operator precedence and
 * part 2 enforces precedence (using the rule that addition is higher precedence than multiplication).
 */
public class Day18SolutionImpl implements DaySolution {

    private static final char PLUS = '+';
    private static final char TIMES = '*';
    private static final char OPEN_PAREN = '(';
    private static final char CLOSE_PAREN = ')';


    @Override
    public void part1(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        long sum = 0L;
        for (String line : lines) {
            sum += eval(line, false);
        }
        System.out.println("Sum of all expressions is " + sum);
    }

    @Override
    public void part2(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        long sum = 0L;
        for (String line : lines) {
            sum += eval(line, true);
        }
        System.out.println("Sum of all expressions is " + sum);
    }

    //RIGHT NOW THIS ONLY SUPPORTS SINGLE DIGITS IN THE EXPRESSION. NEED TO OPERATE ON TOKENS INSTEAD OF CHARACTERS
    private long eval(String exp, boolean enforceOpPrecedence) {
        //convert to postfix notation
        StringBuilder output = new StringBuilder();
        Stack<Character> opStack = new Stack<>();
        for (char c : exp.toCharArray()) {
            switch (c) {
                case ' ':
                    break;
                case OPEN_PAREN:
                    opStack.push(c);
                    break;
                case CLOSE_PAREN:
                    while (!opStack.isEmpty() && opStack.peek() != OPEN_PAREN) {
                        output.append(opStack.pop());
                    }
                    if (!opStack.isEmpty() && opStack.peek() == OPEN_PAREN) {
                        opStack.pop();
                    }
                    break;
                case PLUS:
                case TIMES:
                    while (!opStack.isEmpty() && opStack.peek() != OPEN_PAREN
                        && (opStack.peek() == TIMES || opStack.peek() == PLUS) && (!enforceOpPrecedence || isHigherPrecedence(c, opStack.peek()))) {
                        output.append(opStack.pop());
                    }
                    opStack.push(c);
                    break;
                default:
                    output.append(c);
            }
        }
        while (!opStack.isEmpty()) {
            output.append(opStack.pop());
        }
        return evalPostfix(output.toString());
    }

    /**
     * Returns true if op2 has higher precedence than op1.
     * For this problem, addition has higher precedence than multiplication.
     *
     * @param op1
     * @param op2
     * @return
     */
    private boolean isHigherPrecedence(char op1, char op2) {
        if (op1 == TIMES && op2 == PLUS) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Evaluates a string in postfix notation. NOTE: right now this only support single-digit terms.
     *
     * @param exp
     * @return
     */
    private long evalPostfix(String exp) {
        Stack<Long> stack = new Stack<>();
        for (int i = 0; i < exp.length(); i++) {
            switch (exp.charAt(i)) {
                case PLUS:
                    stack.push(stack.pop() + stack.pop());
                    break;
                case TIMES:
                    stack.push(stack.pop() * stack.pop());
                    break;
                default:
                    stack.push(Long.parseLong("" + exp.charAt(i)));
            }
        }
        return stack.pop();
    }
}
