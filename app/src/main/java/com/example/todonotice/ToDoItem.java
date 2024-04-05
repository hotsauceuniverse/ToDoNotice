package com.example.todonotice;

public class ToDoItem {

    int id;
    private String todo;
    private String hour;
    private String min;
    private String am;
    private String pm;

    public ToDoItem() {
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

    public int getId() {
        return id;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public void setId(int id) {
        this.id = id;
    }
}
