package com.example.parkinson.features.questionnaire.single_question.models;

/** UI model for MultipleChoiceAnswer - used by QuestionBinderOpenAnswer Binder **/
public class OpenAnswer {
    String answer;

    public OpenAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
