package com.provenir.challenge.robobob.service.util;

import com.provenir.challenge.robobob.api.constants.RegexPatterns;

public class StringTransformationUtils {

    public static String convertToLowerCaseAndRemoveAllWhiteSpacesAndQuestionMark(String str){
        String result = str.trim().toLowerCase().replaceAll(RegexPatterns.ZERO_OR_MORE_TRAILING_WHITESPACE.getValue(), "");
        return result.replaceAll(RegexPatterns.QUESTION_MARK.getValue(), "");
    }
}
