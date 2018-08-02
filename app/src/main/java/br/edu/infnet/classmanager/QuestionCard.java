package br.edu.infnet.classmanager;

import java.util.Calendar;
import java.util.Date;

public class QuestionCard {

    private String body;
    private Date moment;
    private String askerName;
    private boolean answered;
    private boolean anonymous;

    public QuestionCard(String text, String askerName, boolean anonymous) {
        this.body = text;
        this.askerName = askerName;
        this.anonymous = anonymous;

        this.answered = false;
        //pega a data e hora do momento de criação
        this.moment = Calendar.getInstance().getTime();
    }

    @Override
    public java.lang.String toString() {
        return body + "\n" + moment + "\n" + askerName + "\n";
    }

    public String getBody() {
        return body;
    }

    public Date getMoment() {
        return moment;
    }

    public String getAskerName() {
        return askerName;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
