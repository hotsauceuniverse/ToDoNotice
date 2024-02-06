package com.example.todonotice;

public class ToDoItem {
    private String todo;
    private String hour;
    private String min;
    private String am;
    private String pm;

    public ToDoItem(String todo, String hour, String min, String am, String pm) {
        this.todo = todo;
        this.hour = hour;
        this.min = min;
        this.am = am;
        this.pm = pm;
    }

    public String getTodo() {
        return todo;
    }

    public String getHour() {
        return hour;
    }

    public String getMin() {
        return min;
    }

    public String getAm() {
        return am;
    }

    public String getPm() {
        return pm;
    }
}
