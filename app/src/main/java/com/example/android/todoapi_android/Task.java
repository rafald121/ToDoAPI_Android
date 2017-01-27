package com.example.android.todoapi_android;

/**
 * Created by Rafaello on 2017-01-17.
 */

public class Task {

    String title, details, timeToDo, tag;
    int id;
    boolean done;

    public Task(String title, String details, String timeToDo, String tag, int id, boolean done) {
        this.title = title;
        this.details = details;
        this.timeToDo = timeToDo;
        this.tag = tag;
        this.done = done;
        this.id = id;
    }

    public Task(String title, String details) {
        this.title = title;
        this.details = details;
    }

    public Task() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTimeToDo() {
        return timeToDo;
    }

    public void setTimeToDo(String timeToDo) {
        this.timeToDo = timeToDo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", timeToDo='" + timeToDo + '\'' +
                ", tag='" + tag + '\'' +
                ", id=" + id +
                ", done=" + done +
                '}';
    }
}
