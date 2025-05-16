package com.provenir.challenge.robobob.service.util;

import com.provenir.challenge.robobob.api.constants.RegexPatterns;

public class StringTransformationUtils {

    public static String convertToLowerCaseAndRemoveAllWhiteSpaces(String str){
        return str.trim().toLowerCase().replaceAll(RegexPatterns.ONE_OR_MORE_WHITESPACE.getValue(), "");
    }
}
