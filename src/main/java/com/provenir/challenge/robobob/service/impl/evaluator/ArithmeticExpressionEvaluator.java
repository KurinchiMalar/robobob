package com.provenir.challenge.robobob.service.impl.evaluator;

import com.provenir.challenge.robobob.api.constants.RegexPatterns;
import com.provenir.challenge.robobob.service.core.ExpressionEvaluator;
import com.provenir.challenge.robobob.service.util.ArithMeticUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Component for evaluating arithmetic expressions.
 * Validates and solves the arithmetic expression using stack-based algorithm.
 * @author KurinchiMalar
 */
@Component
public class ArithmeticExpressionEvaluator implements ExpressionEvaluator {

    private static final Logger logger = LoggerFactory.getLogger(ArithmeticExpressionEvaluator.class);

    private static final Pattern VALID_ARITHMETIC_EXP = Pattern.compile(RegexPatterns.ARITHMETIC_EXPRESSION.getValue());

    /**
     * Checks if the given expression is a properly formatted valid arithmetic expression
     * @param expression The expression to validate
     * @return true if the expression is valid, false otherwise
     */
    @Override
    public boolean isValidExpression(String expression) {

        if(expression == null || expression.trim().isEmpty()){
            return false;
        }
        return VALID_ARITHMETIC_EXP.matcher(expression).matches();
    }

    /**
     * Evaluates a valid arithmetic expression and returns the result.
     * @param expression The arithmetic expression to evaluate.
     * @return double The computed result of the expression
     * @throws IllegalArgumentException if the expression is invalid
     */
    @Override
    public  double evaluate(String expression) {

        if(!isValidExpression(expression)){
            throw new IllegalArgumentException("Invalid arithmetic expression : "+expression);
        }

        // format expression to remove whitespaces and make it eligible for evaluation.
        expression = expression.replaceAll(RegexPatterns.ZERO_OR_MORE_TRAILING_WHITESPACE.getValue(), "");

        try{
            return processArithmeticExpression(expression);
        } catch (Exception e) {
            logger.warn("Error evaluating expression: {}",expression,e);
            throw new IllegalArgumentException("Invalid arithmetic expression: "+expression,e);
        }
    }

    /**
     * This method processes the arithmetic expression using stacks for values and operators.
     * Constructs the reverse polish notation (RPN) for a given expression and evaluates as per precedence.
     *
     * @param expression The formatted arithmetic expression to evaluate.
     * @return double The computed result of the expression.
     */
    private double processArithmeticExpression(String expression){

        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        for(int i = 0 ; i < expression.length(); i++){

            char c = expression.charAt(i);

            if(Character.isWhitespace(c))continue; // doNothing

            // A valid digit or decimal point
            if(Character.isDigit(c) || c == '.'){
                StringBuilder digitSb = new StringBuilder();

                // Take the complete number
                while( i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')){
                    digitSb.append(expression.charAt(i));
                    i++;
                }

                i--; // Minor adjustment as overall iteration handled by for loop increment.
                values.push(Double.parseDouble(digitSb.toString()));
            }else if(c == '('){
                operators.push(c);
            }else if(c == ')'){
                // evaluate and push the result back until ( is encountered.
                while(!operators.isEmpty() && operators.peek() != '('){
                    ArithMeticUtils.applyOperator(values,operators);
                }
                //')' has to be popped.
                if(!operators.isEmpty()) operators.pop();

            }else if(ArithMeticUtils.isOperator(c)){
                // check if precedence of operator on top of stack is greater
                while(!operators.isEmpty()  && ArithMeticUtils.getPrecedence(operators.peek() ) >= ArithMeticUtils.getPrecedence(c)){
                    // operator at the top of stack has greater precedence than c.
                    ArithMeticUtils.applyOperator(values,operators);
                }
                operators.push(c);
            }
        }

        while(!operators.isEmpty()){
            ArithMeticUtils.applyOperator(values,operators);
        }
        return values.pop();
    }

}
