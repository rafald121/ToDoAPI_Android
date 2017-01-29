package com.example.android.todoapi_android;

import android.view.View;

/**
 * Created by Rafaello on 2017-01-29.
 */

public interface RecyclerViewItemActions {
    public void recyclerViewEditTask(View v, int position);
    public void recyclerViewDeleteTask(View v, int position);
    public void recyclerViewUnOrDoneTask(View v, int position);
}
