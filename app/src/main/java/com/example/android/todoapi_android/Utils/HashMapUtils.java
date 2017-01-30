package com.example.android.todoapi_android.Utils;

import android.util.Log;

import java.util.HashMap;

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
}
