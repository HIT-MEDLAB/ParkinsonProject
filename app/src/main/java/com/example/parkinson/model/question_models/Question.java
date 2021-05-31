package com.example.parkinson.model.question_models;

import com.example.parkinson.model.enums.EQuestionType;

public class Question {
    private String title;//הכותרת של השאלה , השאלה עצמה
    private EQuestionType type;// סוג השאלה , פתוחה מרובת תשובות וכדומה

    public Question() {
    }

    public Question(String title, EQuestionType type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EQuestionType getType()
    {
        return type;
    }

    public void setType(EQuestionType type) {

        this.type = type;
    }
}
