package br.edu.infnet.classmanager;

import java.util.Calendar;
import java.util.Date;

public class Question {

    private String body;
    private Date moment;
    private User asker;
    private boolean answered;
    private boolean anonymous;

    public Question(String text, User asker, boolean anonymous) {
        this.body = text;
        this.asker = asker;
        this.anonymous = anonymous;

        this.answered = false;
        //pega a data e hora do momento de criação
        this.moment = Calendar.getInstance().getTime();
    }

    @Override
    public String toString() {
        return body + "\n" + moment + "\n" + asker.getName() + "\n";
    }

    public String getBody() {
        return body;
    }

    public Date getMoment() {
        return moment;
    }

    public User getAsker() {
        return asker;
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
