package com.example.android.todoapi_android.Utils;

import android.util.Log;

import com.example.android.todoapi_android.DTO.Task;

import java.util.HashMap;

import static com.example.android.todoapi_android.Helpers.ApplicationController.TAG;

/**
 * Created by Rafaello on 2017-01-30.
 */

public class HashMapUtils {

    static HashMap<String,Object> map = new HashMap<>();

    public static HashMap<String, Object> createHashMapFromObject
            (String title, String details, String timeToDo, String tag, boolean done) {
        map.put("title", title);
        map.put("details", details);
        map.put("timeToDo", timeToDo);
        map.put("tag", tag);
        map.put("done", done);

        Log.i("MAPA TERAZ", "createHashMapFromObject: MAPA WARTOSCI: " + map.toString());

        return map;
    }

    public static HashMap<String,Object> createHashMapFromObject(Task task) {

        map.put("title", task.getTitle());
        map.put("details", task.getDetails());
        map.put("timeToDo", task.getTimeToDo());
        map.put("tag", task.getTag());
        if(task.isDone()) {
            Log.i(TAG, "createHashMapFromObject: isDone = true");
            map.put("done", false);
        }
        else{
            Log.i(TAG, "createHashMapFromObject: isDone = false");
            map.put("done", true);
        }




        Log.i(TAG, "createHashMapFromObject: MAPA TERAZ PO DOSTNAIU OBIEKTU TASK: " + map.toString());

        return map;

    }
}
