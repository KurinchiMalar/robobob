package com.provenir.challenge.robobob.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This Enum contains the various Regular Expression patterns used throughout the application.
 */
@AllArgsConstructor
@Getter
public enum RegexPatterns {

    /**
     * Pattern to match a literal question mark character
     * eg)  1 + 2 ? ==> 1 + 2
     */
    QUESTION_MARK("\\?"),

    /**
     * Pattern to match trailing decimal points followed by 0 or more 0s at the end of a number
     * eg) 5.00 ==> 5
     *     3.50 ==> 3.5
     *     6.0 ==>  6
     */
    ZERO_OR_MORE_TRAILING_ZEROES("\\.?0*$"),

    /**
     * Pattern to match trailing whitespace(0 or more whitespaces) at the end of a string.
     * eg) "What is 2+2?"
     *      "What is 2+2?   "
     */
    ZERO_OR_MORE_TRAILING_WHITESPACE("\\s*$"),

    /**
     * Pattern for validating arithmetic expression.
     * Rules from RegexConstants taken to classify a valid arithmetic expression:
     *     - No consecutive operators
     *     - No empty brackets
     *     - No missing operators between operands
     *     - Only allowed elements/characters are present in the arithmetic expression
     */
    ARITHMETIC_EXPRESSION("^\\s*"+RegexConstants.getArithMeticExpressionRegex()+"\\s*$"),

    /**
     * Pattern for identifying valid arithmetic questions.
     * Matches the questions like below:
     *  - "What is 2+2?"
     *  - "Calculate (5*3)"
     *  - "Solve 10/2"
     * Case-Insensitive
     */
    ARITHMETIC_QUESTION("^(?:what is|calculate|compute|solve|evaluate)\\s+(.+?)\\s*\\?*$");

    /**
     * The regex string value of the pattern.
     */
    public final String value;
}
