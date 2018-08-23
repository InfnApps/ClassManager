package br.edu.infnet.classmanager.models;

public class User {

    //private int id;
    private String name;
    private String email;
    private String course;
    private static User anonymous;

    public static User getAnonymousUser(){
        anonymous = new User(0, "Anônimo", null, null);
        return anonymous;
    }

    public User(){}

    public User(String email){
        this.name = "";
        this.email = email;
        this.course = "";
    }

    public User(int id, String name, String email, String course) {
        this.name = name;
        this.email = email;
        this.course = course;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
