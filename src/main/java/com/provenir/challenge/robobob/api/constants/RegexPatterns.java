package com.provenir.challenge.robobob.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegexPatterns {
    QUESTION_MARK("\\?"),
    ZERO_OR_MORE_TRAILING_ZEROES("\\.?0*$"),
    ZERO_OR_MORE_TRAILING_WHITESPACE("\\s*$"),
    ARITHMETIC_EXPRESSION("^\\s*"+RegexConstants.getArithMeticExpressionRegex()+"\\s*$"),
    // Valid arithmetic questions: What is (expr) ? (or) Calculate (expr) (or) Evaluate (expr) (or) Solve (expr)
    ARITHMETIC_QUESTION("^(?:what is|calculate|compute|solve|evaluate)\\s+(.+?)\\s*\\?*$");
    // ARITHMETIC_QUESTION("(?:what is|calculate|compute|solve|evaluate)\\s+("+RegexConstants.ARITHMETIC_EXPRESSION_VALID_ELEMENTS_REGEX+")\\s*\\?*$");
    public final String value;
}
