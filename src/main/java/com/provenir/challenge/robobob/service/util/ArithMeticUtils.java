package com.provenir.challenge.robobob.service.util;

import com.provenir.challenge.robobob.api.constants.RegexPatterns;

import java.util.Stack;

public class ArithMeticUtils {

    public static String formatArithmeticComputationResult(double result){
        String formattedResult = null;
        if(result == (long) result){
            // whole number can be rendered as int
            formattedResult = String.format("%d",(long)result);
        }else{
            formattedResult = String.format("%.2f",result);

            // trailing zeros can be removed 23.00 ==> 23 , 23.50 ==> 23.5
            formattedResult = formattedResult.replaceAll(RegexPatterns.ZERO_OR_MORE_TRAILING_ZEROES.getValue(), "");
        }
        return formattedResult;
    }

    public static boolean isOperator(char c){
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static int getPrecedence(char op){
        return switch(op){
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

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

    public static void applyOperator(Stack<Double> values, Stack<Character> operators){
        char op = operators.pop();
        double b = values.pop();
        double a = values.pop();
        values.push(computeOperation(op,a,b));
    }
}
