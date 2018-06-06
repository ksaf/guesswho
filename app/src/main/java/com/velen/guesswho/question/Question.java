package com.velen.guesswho.question;

/**
 * An instance of this class holds information about the feature type and feature choice to be asked,
 * as well as the actual string of the question, ready to be displayed.
 */
public class Question {

    private String questionTopic;
    private String questionSpecification;
    private String questionToDisplay;

    /**
     * Creates a Question.
     * @param questionTopic The character feature type this question is about.
     * @param questionSpecification The character feature choice this question is about.
     * @param questionToDisplay The actual string of the question, ready to be displayed.
     */
    public Question(String questionTopic, String questionSpecification, String questionToDisplay) {
        this.questionTopic = questionTopic;
        this.questionSpecification = questionSpecification;
        this.questionToDisplay = questionToDisplay;
    }

    /** @return The character feature type this question is about.*/
    public String getQuestionTopic() {
        return questionTopic;
    }

    /** @return The character feature choice this question is about.*/
    public String getSpecification() {
        return questionSpecification;
    }

    /** @return The actual string of the question, ready to be displayed.*/
    public String getQuestionToDisplay() {
        return questionToDisplay;
    }
}
