package br.edu.infnet.classmanager;

public class String {

    private int id;
    private java.lang.String name;
    private java.lang.String email;
    private java.lang.String course;
    private static String anonymous;

    public static String getAnonymousUser(){
        anonymous = new String(0, "Anônimo", null, null);
        return anonymous;
    }

    public String(int id, java.lang.String name, java.lang.String email, java.lang.String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public java.lang.String getName() {
        return name;
    }

    public java.lang.String getEmail() {
        return email;
    }

    public java.lang.String getCourse() {
        return course;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public void setCourse(java.lang.String course) {
        this.course = course;
    }
}
