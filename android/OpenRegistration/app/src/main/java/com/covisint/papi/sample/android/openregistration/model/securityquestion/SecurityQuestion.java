package com.covisint.papi.sample.android.openregistration.model.securityquestion;

import com.covisint.papi.sample.android.openregistration.model.common.RealmScopedResource;

/**
 * Created by Nitin.Khobragade on 2/12/2015.
 */
public class SecurityQuestion extends RealmScopedResource {
    SecurityQuestionOwner owner;
    Question [] question;

    public SecurityQuestionOwner getOwner() {
        return owner;
    }

    public Question[] getQuestions() {
        return question;
    }

    public void setQuestion(Question[] question) {
        this.question = question;
    }

    public class Question {
        String lang;
        String text;

        public Question() {
        }

        public String getLang() {
            return lang;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Override
    public String toString() {
        String returnVal = null;
        if (question != null && question.length > 0)
            if (question[0] != null)
                returnVal = question[0].getText();
        return returnVal;
    }
}
