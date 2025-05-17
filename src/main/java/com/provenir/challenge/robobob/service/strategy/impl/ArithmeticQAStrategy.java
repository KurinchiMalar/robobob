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

/**
 * QAStrategy implementation that handles arithmetic questions.
 * This strategy can answer questions in the following formats
 *      -"What is [expression]?"
 *      -"Calculate [expression]"
 *      -"Compute [expression]"
 *      -"Solve [expression]"
 *      -"Evaluate [expression]"
 *      -"What is [expression]"
 *  Cases don't matter , the strategy is intelligent enough to follow a generic key irrespective of cases.
 *  This Strategy has the Second Priority among the strategies we have in this application.
 *
 * @author KurinchiMalar
 */
@Component
@Order(2) // Second Priority
public class ArithmeticQAStrategy implements QAStrategy {

    private static final Logger logger = LoggerFactory.getLogger(ArithmeticQAStrategy.class);

    /**
     * Pattern for matching valid arithmetic questions (eg. "What is 4+2?")
     */
    private static final Pattern VALID_ARITHMETIC_QUESTION_PATTERN = Pattern.compile(
                                                                    RegexPatterns.ARITHMETIC_QUESTION.getValue(),
                                                                    Pattern.CASE_INSENSITIVE);
    private final ArithmeticExpressionEvaluator evaluator;

    /**
     * Constructs an ArithmeticQAStrategy with the specified expression evaluator.
     *
     * @param evaluator The arithmetic expression evaluator to use to compute the expression.
     * @throws IllegalStateException if the evaluator dependency is not injected.
     */
    @Autowired
    public ArithmeticQAStrategy(ArithmeticExpressionEvaluator evaluator) {
        if (evaluator == null) {
            throw new IllegalStateException("ArithExpEvaluator dep not injected");
        }
        this.evaluator = evaluator;
        logger.info("Arithmetic QA strategy initialized with evaluator:{}",evaluator.getClass());
    }

    /**
     * Determines if the question is an arithmetic question with a valid expression.
     *
     * @param question The question to check if answerable.
     * @return boolean Returns true if question contains valid arithmetic expression, false otherwise.
     */
    @Override
    public boolean isAnswerable(String question) {

        String expression = extractArithmeticExpr(question);

        // validate expression
        boolean isValidExpression = (expression != null && evaluator.isValidExpression(expression));
        logger.debug("Question : {}, Expression : {}, ValidExpression: {}", question,expression,isValidExpression);
        return isValidExpression;
    }

    /**
     * Answers an arithmetic question by evaluating the expression present in the question.
     * @param question The question to be answered.
     * @return String The result of expression or an error message if the evaluation fails.
     * @throws ArithmeticException Division by zero and other arithmetic reasons.
     */

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
        }catch (ArithmeticException ex){
            logger.warn("Invalid Arithmetic expression :{} in question :{}",expression,question);
            return "Sorry! Invalid Arithmetic Expression: "+ex.getMessage();
        }catch(Exception ex){
            logger.warn("Error evaluating expression :{} in question :{}",expression,question);
            return "Sorry! I am not able to calculate that. Please check expression.";
        }
    }

    /**
     * Extracts the arithmetic expression from question string.
     *
     * @param question The question containing the arithmetic expression.
     * @return String The extracted arithmetic expression, or null if not found/invalid.
     */
    public String extractArithmeticExpr(String question){
        Matcher matcher = VALID_ARITHMETIC_QUESTION_PATTERN.matcher(question.trim());

        if(matcher.matches()){
            // matcher.group(0) = entire question
            // matcher.group(1) = first capturing group. In this case (.+?) which is the expression.
            String expression =  matcher.group(1).trim();
            if(expression.matches(RegexConstants.getArithMeticExpressionRegex())){
                return expression;
            }
        }
        // Invalid Question
        return null;
    }

}
