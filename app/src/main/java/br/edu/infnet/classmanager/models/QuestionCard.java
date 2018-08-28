package br.edu.infnet.classmanager.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class QuestionCard implements Serializable {

    private String body;
    //private Date moment;
    private long timestamp;
    private String askerName;
    private String askerId;
    private boolean answered;
    private boolean anonymous;

    private Map<String, String> createdAt;


    public QuestionCard() {
    }

    public QuestionCard(String text, String askerName,
                        String askerId, boolean anonymous) {
        this.body = text;
        this.askerName = askerName;
        this.anonymous = anonymous;
        this.askerId = askerId;
        this.answered = false;
        //pega a data e hora do momento de criação
        //this.moment = Calendar.getInstance().getTime();
        // marca esse atributo com valor especial a ser substituído
        // pelo TIMESTAMP no servidor
        createdAt = ServerValue.TIMESTAMP;
    }

    public Map<String, String> getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        //this.createdAt = createdAt;
        //moment = new Date(createdAt);
        this.createdAt = ServerValue.TIMESTAMP;
        timestamp = createdAt;
    }

    @Override
    public String toString() {
        return body + "\n"  + "\n" + askerName + "\n" + anonymous + "\n";
    }

    public String getBody() {
        return body;
    }

    @Exclude
    public Date getMoment() {
        //return moment;
        return new Date(timestamp);
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

    //public void setMoment(Date moment) {
     //   this.moment = moment;
    //}

    public void setAskerName(String askerName) {
        this.askerName = askerName;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getAskerId() {
        return askerId;
    }

    public void setAskerId(String askerId) {
        this.askerId = askerId;
    }
}
