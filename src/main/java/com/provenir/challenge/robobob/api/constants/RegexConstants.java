package com.provenir.challenge.robobob.api.constants;

import lombok.Getter;

@Getter
public class RegexConstants {

    // numbers, operators, decimal point, spaces
    public static final String ARITHMETIC_EXPRESSION_VALID_ELEMENTS_REGEX = "^[\\d+\\-*/().\\s]+$";

}
