package com.example.parkinson.model.question_models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Questionnaire {
    private List<Question> questionList = new ArrayList<>();//רשימה של כל השאלות הקיימות בשאלון
    private Date answeredAt;//התאריך שבו נענה השאלון

    public Questionnaire() {
    }

    public Questionnaire(List<Question> questionList, Date answeredAt) {
        this.questionList = questionList;
        this.answeredAt = answeredAt;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public Date getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(Date answeredAt) {
        this.answeredAt = answeredAt;
    }
}