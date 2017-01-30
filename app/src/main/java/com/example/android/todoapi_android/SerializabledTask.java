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

    public SerializabledTask(Task task) {
        this.task = task;
    }

    public String getTitle() {
        return task.title;
    }

    public String getDetails() {
        return task.details;
    }

    public String getTimeToDo() {
        return task.timeToDo;
    }

    public String getTag() {
        return task.tag;
    }

    public int getId() {
        return task.id;
    }

    public boolean isDone() {
        return task.done;
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
