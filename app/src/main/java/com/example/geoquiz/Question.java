package com.example.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private String userAnswer = "";

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }
    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String answer) {
        userAnswer = answer;
    }


}
