package com.example.android.todoapi_android;

import java.io.Serializable;

/**
 * Created by Rafaello on 2017-01-29.
 */

public class SerializabledTask implements Serializable {

    String title, details, timeToDo, tag;
    int id;
    boolean done;
    Task task;

    public SerializabledTask(String title, String details, String timeToDo, String tag, int id, boolean done) {
        this.title = title;
        this.details = details;
        this.timeToDo = timeToDo;
        this.tag = tag;
        this.id = id;
        this.done = done;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "SerializabledTask{" +
                "title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", timeToDo='" + timeToDo + '\'' +
                ", tag='" + tag + '\'' +
                ", id=" + id +
                ", done=" + done +
                ", task=" + task +
                '}';
    }
}
