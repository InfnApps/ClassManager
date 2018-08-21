package br.edu.infnet.classmanager;

public class Answer {

    public String text;
    public String userName;
    public String timeStamp;

    public Answer(String text, String userName, String timeStamp) {
        this.text = text;
        this.userName = userName;
        this.timeStamp = timeStamp;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
