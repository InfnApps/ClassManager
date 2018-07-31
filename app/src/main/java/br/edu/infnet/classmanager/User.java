package br.edu.infnet.classmanager;

public class User {

    private int id;
    private String name;
    private String email;
    private String course;
    private static User anonymous;

    public static User getAnonymousUser(){
        anonymous = new User(0, "Anônimo", null, null);
        return anonymous;
    }

    public User(int id, String name, String email, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    public int getId() {
        return id;
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
