package com.impiger.thirukkural.model;

/**
 * Created by anand on 17/11/15.
 */
public class Question {
    private int id;
    private String question;
    private String firstOption;

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getFirstOption() {
        return firstOption;
    }

    public String getSecondOption() {
        return secondOption;
    }

    public String getThirdOption() {
        return thirdOption;
    }

    public String getFourOption() {
        return fourOption;
    }

    public String getAnswer() {
        return answer;
    }

    private String secondOption;
    private String thirdOption;
    private String fourOption;
    private String answer;

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setFirstOption(String firstOption) {
        this.firstOption = firstOption;
    }

    public void setSecondOption(String secondOption) {
        this.secondOption = secondOption;
    }

    public void setThirdOption(String thirdOption) {
        this.thirdOption = thirdOption;
    }

    public void setFourOption(String fourOption) {
        this.fourOption = fourOption;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
