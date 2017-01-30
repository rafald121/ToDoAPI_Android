//package com.example.android.todoapi_android;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
///**
// * Created by Rafaello on 2017-01-30.
// */
//
//public class ParcelabledTask implements Parcelable{
//
//    String title, details, timeToDo, tag;
//    int id;
//    boolean done;
//    Task task;
//    /* everything below here is for implementing Parcelable */
//
//    // 99.9% of the time you can just ignore this
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    // write your object's data to the passed-in Parcel
//    @Override
//    public void writeToParcel(Parcel out, int flags) {
////        out.writeInt(mData);
//        task = out.writ
////        out.writeString(title);
////        out.writeString(details);
////        out.writeString(timeToDo);
////        out.writeInt(id);
////        out.writeValue((Boolean) done ? 1 : 0);
//
//    }
//
//    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
//    public static final Parcelable.Creator<ParcelabledTask> CREATOR = new Parcelable.Creator<ParcelabledTask>() {
//        public ParcelabledTask createFromParcel(Parcel in) {
//            return new ParcelabledTask(in);
//        }
//
//        public ParcelabledTask[] newArray(int size) {
//            return new ParcelabledTask[size];
//        }
//    };
//
//    // example constructor that takes a Parcel and gives you an object populated with it's values
//    private ParcelabledTask(Parcel in) {
//        task = in.writeValue();
//    }
//}