package com.provenir.challenge.robobob.api.constants;

import lombok.Getter;

/**
 * Utility class containing Regex constants for validating arithmetic expressions.
 * Contains various Regex patterns that will be used by application for validating arithmetic expressions.
 */
@Getter
public class RegexConstants {

    /**
     * Regex to check for consecutive operators
     * eg) Compute 5 + + 6
     */
    public static final String NO_CONSECUTIVE_OP_REGEX = "(?!.*[+\\-*/]{2,})";

    /**
     * Regex to check for empty parantheses
     * eg) ()
     */
    public static final String NO_EMPTY_BRACKETS_REGEX = "(?!.*\\(\\))";

    /**
     * Regex to check for operands without operators between
     * eg) Compute 5  7
     */
    public static final String OPERANDS_WITH_NO_OPERATOR_REGEX = "(?!.*\\d\\s+\\d)";

    /**
     * Regex that defined allowed elements in an arithmetic expression
     * Allows only numbers, operators, decimal point, spaces
     */
    public static final String ALLOWED_ARITHMETIC_ELEMENTS_REGEX = "[\\d+\\-*/().\\s]+";

    /**
     * This is a helper method to create a complete validation pattern for arithmetic expressions.
     * The combined pattern ensures
     *  - No consecutive operators
     *  - No empty brackets
     *  - No missing operators between operands
     *  - Only allowed elements/characters are present in the arithmetic expression
     * @return String containing complete regex pattern that validates arithmetic expressions
     */
    public static final String getArithMeticExpressionRegex(){
        return "^"+
                NO_CONSECUTIVE_OP_REGEX
                +NO_EMPTY_BRACKETS_REGEX
                +OPERANDS_WITH_NO_OPERATOR_REGEX
                +ALLOWED_ARITHMETIC_ELEMENTS_REGEX
                +"$";
    }
}
