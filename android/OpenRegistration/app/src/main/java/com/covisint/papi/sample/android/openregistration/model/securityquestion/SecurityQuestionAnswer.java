package com.covisint.papi.sample.android.openregistration.model.securityquestion;

import com.covisint.papi.sample.android.openregistration.model.common.RealmScopedResource;

/**
 * Created by Nitin.Khobragade on 2/12/2015.
 */
public class SecurityQuestionAnswer extends RealmScopedResource {
    SecurityQuestion[] questions;

    public SecurityQuestion[] getQuestions() {
        return questions;
    }

    public void setQuestions(SecurityQuestion[] questions) {
        this.questions = questions;
    }

    public class SecurityQuestion {
        Question question;
        String answer;

        public Question getQuestion() {
            return question;
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }

    public class Question {
        String id;
        String type;
        String realm;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRealm() {
            return realm;
        }

        public void setRealm(String realm) {
            this.realm = realm;
        }
    }
}
