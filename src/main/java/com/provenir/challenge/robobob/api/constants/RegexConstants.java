package com.provenir.challenge.robobob.api.constants;

import lombok.Getter;

@Getter
public class RegexConstants {

    // numbers, operators, decimal point, spaces
    //public static final String ARITHMETIC_EXPRESSION_VALID_ELEMENTS_REGEX = "^[\\d+\\-*/().\\s]+$";

    public static final String NO_CONSECUTIVE_OP_REGEX = "(?!.*[+\\-*/]{2,})";
    public static final String NO_EMPTY_BRACKETS_REGEX = "(?!.*\\(\\))";
    public static final String OPERANDS_WITH_NO_OPERATOR_REGEX = "(?!.*\\d\\s+\\d)";
    public static final String ALLOWED_ARITHMETIC_ELEMENTS_REGEX = "[\\d+\\-*/().\\s]+";

    public static final String getArithMeticExpressionRegex(){
        return "^"+
                NO_CONSECUTIVE_OP_REGEX
                +NO_EMPTY_BRACKETS_REGEX
                +OPERANDS_WITH_NO_OPERATOR_REGEX
                +ALLOWED_ARITHMETIC_ELEMENTS_REGEX
                +"$";
    }
}
