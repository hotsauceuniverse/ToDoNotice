package com.seyoung.todonotice;

public class ToDoItem {

    int id;
    private String todo;
    private String hour;
    private String min;
    private String place;
    private String memo;
    private String writeDate;

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

    public String getPlace() {
        return place;
    }

    public String getMemo() {
        return memo;
    }

    public int getId() {
        return id;
    }

    public String getWriteDate() {
        return writeDate;
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

    public void setPlace(String place) {
        this.place = place;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}
