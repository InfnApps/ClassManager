package br.edu.infnet.classmanager.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.Date;
import java.util.Map;

public class Answer {

    private String text;
    private String userName;
    private long timeStamp;
    private Map<String, String> lastModifiedMap;

    public Answer(String text, String userName) {
        this.text = text;
        this.userName = userName;
        lastModifiedMap = ServerValue.TIMESTAMP;
    }

    public Map<String, String> getLastModifiedMap() {
        return lastModifiedMap;
    }

    public void setLastModifiedMap(long lastModifiedMap) {
        timeStamp = lastModifiedMap;
    }

    @Exclude
    public Date getLastModified(){
        return new Date(timeStamp);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //public String getTimeStamp() {
//        return timeStamp;
//    }
//
//    public void setTimeStamp(String timeStamp) {
//        this.timeStamp = timeStamp;
//    }
}
