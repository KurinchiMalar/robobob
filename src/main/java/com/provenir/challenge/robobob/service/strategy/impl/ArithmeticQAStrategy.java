package com.provenir.challenge.robobob.service.strategy.impl;

import com.provenir.challenge.robobob.api.constants.RegexConstants;
import com.provenir.challenge.robobob.api.constants.RegexPatterns;
import com.provenir.challenge.robobob.service.impl.evaluator.ArithmeticExpressionEvaluator;
import com.provenir.challenge.robobob.service.strategy.QAStrategy;
import com.provenir.challenge.robobob.service.util.ArithMeticUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(2) // Second Priority
public class ArithmeticQAStrategy implements QAStrategy {

    private static final Logger logger = LoggerFactory.getLogger(ArithmeticQAStrategy.class);

    private static final Pattern VALID_ARITHMETIC_QUESTION_PATTERN = Pattern.compile(
                                                                    RegexPatterns.ARITHMETIC_QUESTION.getValue(),
                                                                    Pattern.CASE_INSENSITIVE);
    private final ArithmeticExpressionEvaluator evaluator;

    @Autowired
    public ArithmeticQAStrategy(ArithmeticExpressionEvaluator evaluator) {
        if (evaluator == null) {
            throw new IllegalStateException("ArithExpEvaluator dep not injected");
        }
        this.evaluator = evaluator;
        logger.info("Arithmetic QA strategy initialized with evaluator:{}",evaluator.getClass());
    }

    @Override
    public boolean isAnswerable(String question) {

        String expression = extractArithmeticExpr(question);

        // validate expression
        boolean isValidExpression = (expression != null && evaluator.isValidExpression(expression));
        logger.debug("Question : {}, Expression : {}, ValidExpression: {}", question,expression,isValidExpression);
        return isValidExpression;
    }

    @Override
    public String answer(String question) {

        String expression = extractArithmeticExpr(question);

        if(expression == null){
            return "Sorry! I am not able to parse the given expression.";
        }

        try{
            double result = evaluator.evaluate(expression);
            String formattedResult = ArithMeticUtils.formatArithmeticComputationResult(result);
            return " Answer is : "+ formattedResult;
        }catch (Exception ex){
            logger.warn("Error evaluating expression :{} in question :{}",expression,question);
            return "Sorry! I am not able to calculate that. Please check expression.";
        }
    }

    //Helper method to extract expression
    public String extractArithmeticExpr(String question){
        Matcher matcher = VALID_ARITHMETIC_QUESTION_PATTERN.matcher(question.trim());

        // Check if any substring of question matches with the valid question pattern.
        if(matcher.matches()){
            // matcher.group(0) = entire question
            // matcher.group(1) = first capturing group. In this case (.*?) , which is the expression.
            String expression =  matcher.group(1).trim();
            if(expression.matches(RegexConstants.getArithMeticExpressionRegex())){
                return expression;
            }
        }
        // Invalid Question
        return null;
    }

}
