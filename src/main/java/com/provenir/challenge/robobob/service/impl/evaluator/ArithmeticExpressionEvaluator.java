package com.provenir.challenge.robobob.service.impl.evaluator;

import com.provenir.challenge.robobob.api.constants.RegexPatterns;
import com.provenir.challenge.robobob.service.core.ExpressionEvaluator;
import com.provenir.challenge.robobob.service.util.ArithMeticUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

@Component
public class ArithmeticExpressionEvaluator implements ExpressionEvaluator {

    private static final Logger logger = LoggerFactory.getLogger(ArithmeticExpressionEvaluator.class);

    private static final Pattern VALID_ARITHMETIC_EXP = Pattern.compile(RegexPatterns.ARITHMETIC_EXPRESSION.getValue());
    @Override
    public boolean isValidExpression(String expression) {

        if(expression == null || expression.trim().isEmpty()){
            return false;
        }
        return VALID_ARITHMETIC_EXP.matcher(expression).matches();
    }

    @Override
    public  double evaluate(String expression) {

        if(!isValidExpression(expression)){
            throw new IllegalArgumentException("Invalid arithmetic expression : "+expression);
        }

        // format expression to remove whitespaces and make it eligible for evaluation.
        expression = expression.replaceAll(RegexPatterns.ONE_OR_MORE_WHITESPACE.getValue(), "");

        try{
            return processArithmeticExpression(expression);
        } catch (Exception e) {
            logger.warn("Error evaluating expression: {}",expression,e);
            throw new IllegalArgumentException("Invalid arithmetic expression: "+expression,e);
        }
    }

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
