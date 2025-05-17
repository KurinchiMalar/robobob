package com.provenir.challenge.robobob.service.util;

import com.provenir.challenge.robobob.api.constants.RegexPatterns;

/**
 * Utility class for various tranformations on string.
 * @author KurinchiMalar
 */
public class StringTransformationUtils {

    /**
     * Formats the string by:
     *  - First Converts to lowercase
     *  - Then removes all trailing white spaces
     *  - Then removes trailing ? if present.
     * @param str The input string to be formatted
     * @return String formatted output string.
     */
    public static String convertToLowerCaseAndRemoveAllWhiteSpacesAndQuestionMark(String str){
        String result = str.trim().toLowerCase().replaceAll(RegexPatterns.ZERO_OR_MORE_TRAILING_WHITESPACE.getValue(), "");
        return result.replaceAll(RegexPatterns.QUESTION_MARK.getValue(), "");
    }
}
