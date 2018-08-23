package br.edu.infnet.classmanager.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.Date;
import java.util.Map;

public class Answer {

    private String text;
    private String userName;
    private Map<String, String> lastModifiedMap = ServerValue.TIMESTAMP;


    private long timeStamp;

    public Answer(){}

    public Answer(String text, String userName) {
        this.text = text;
        this.userName = userName;
    }


    @Exclude
    public Date getLastModified(){
        return new Date(timeStamp);
    }

    public String getText() {
        return text;
    }

    public String getUserName() {
        return userName;
    }

    public Map<String, String> getLastModifiedMap() {
        return lastModifiedMap;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLastModifiedMap(long lastModifiedMap) {
        timeStamp = lastModifiedMap;
    }
}
