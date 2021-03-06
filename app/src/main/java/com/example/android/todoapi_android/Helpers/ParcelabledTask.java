package com.example.android.todoapi_android.Helpers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rafaello on 2017-01-30.
 */

public class ParcelabledTask implements Parcelable{

    String title, details, timeToDo, tag;
    int id;
    boolean done;

    public ParcelabledTask(){}

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


    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(details);
        dest.writeString(timeToDo);
        dest.writeString(tag);
        dest.writeInt(id);
        dest.writeInt(done? 1:0);

    }
    public ParcelabledTask(Parcel pc){
        title = pc.readString();
        details = pc.readString();
        timeToDo = pc.readString();
        tag = pc.readString();
        id = pc.readInt();
        done = (pc.readInt() == 1);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ParcelabledTask createFromParcel(Parcel in) {
            return new ParcelabledTask(in);
        }

        public ParcelabledTask[] newArray(int size) {
            return new ParcelabledTask[size];
        }
    };

    @Override
    public String toString() {
        return "ParcelabledTask{" +
                "title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", timeToDo='" + timeToDo + '\'' +
                ", tag='" + tag + '\'' +
                ", id=" + id +
                ", done=" + done +
                '}';
    }
}