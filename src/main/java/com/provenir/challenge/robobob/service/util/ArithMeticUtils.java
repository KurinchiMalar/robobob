package com.provenir.challenge.robobob.service.util;

import com.provenir.challenge.robobob.api.constants.RegexPatterns;

import java.util.Stack;

/**
 * Utility class for Arithmetic operations and number formatting.
 *
 * @author KurinchiMalar
 */
public class ArithMeticUtils {

    /**
     * Formats a result of double type into String and also removes the trailing Zeroes.
     * eg) 5.0   ==> 5
     *     23.00 ==> 23
     *     23.50 ==> 23.5
     *
     * @param result The computed result
     * @return String Formatted string (integer if whole number, else 2 decimal places)
     */
    public static String formatArithmeticComputationResult(double result){
        String formattedResult = null;
        if(result == (long) result){
            formattedResult = String.format("%d",(long)result);
        }else{
            formattedResult = String.format("%.2f",result);

            formattedResult = formattedResult.replaceAll(RegexPatterns.ZERO_OR_MORE_TRAILING_ZEROES.getValue(), "");
        }
        return formattedResult;
    }

    /**
     * Checks if a character is a supported operator (+, -, *, /).
     * @param c The character to be checked if it is a valid operator.
     * @return boolean true if valid operator , false otherwise.
     */
    public static boolean isOperator(char c){
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    /**
     * Returns the precedence of an operator.
     *  '*' , '/' has higher precedence than '+' , '-'
     *
     * @param op The operator whose precedence value is to be calculated.
     * @return int The precedence value of the operator is returned.
     */
    public static int getPrecedence(char op){
        return switch(op){
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    /**
     * Computes the operation defined by the operator on the two operands provided.
     * @param operator Given operator
     * @param a Given operand1
     * @param b Given operand2
     * @return double The result of the computed operation between a and b is returned.
     */
    private static double computeOperation(char operator, double a, double b){
        return switch(operator){
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if(b == 0) throw new ArithmeticException("Division by zero is not allowed");
                yield a/b;
            }
            default -> 0;
        };
    }

    /**
     * Applies the top operator from operators stack to the top 2 values from values stack.
     * After computation, pushes back the value to values stack.
     *
     * @param values Stack of double values (must contain atleast 2 elements)
     * @param operators Stack of operators (must not be empty)
     * @throws ArithmeticException when DivisionByZero occurs
     * @throws IllegalArgumentException when the operator is invalid
     * @throws java.util.EmptyStackException when the stack doesn't have sufficient elements to process.
     */
    public static void applyOperator(Stack<Double> values, Stack<Character> operators){
        char op = operators.pop();
        double b = values.pop();
        double a = values.pop();
        values.push(computeOperation(op,a,b));
    }
}
