package br.edu.infnet.classmanager;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class QuestionCard implements Serializable {

    private String body;
    private Date moment;
    private String askerName;
    private boolean answered;
    private boolean anonymous;

    public QuestionCard() {
    }

    public QuestionCard(String text, String askerName, boolean anonymous) {
        this.body = text;
        this.askerName = askerName;
        this.anonymous = anonymous;

        this.answered = false;
        //pega a data e hora do momento de criação
        this.moment = Calendar.getInstance().getTime();
    }

    @Override
    public String toString() {
        return body + "\n" + moment + "\n" + askerName + "\n" + anonymous + "\n";
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

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    public void setAskerName(String askerName) {
        this.askerName = askerName;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}
