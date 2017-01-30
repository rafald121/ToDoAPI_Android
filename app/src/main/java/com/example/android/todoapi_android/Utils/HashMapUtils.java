package com.example.android.todoapi_android.Utils;

import com.example.android.todoapi_android.Helpers.ParcelabledTask;

import java.util.HashMap;

/**
 * Created by Rafaello on 2017-01-30.
 */

public class HashMapUtils {

    static HashMap<String,Object> map = new HashMap<>();

    public static HashMap<String, Object> createHashMapFromObject(ParcelabledTask parcelabledTask) {
        map.put("title", parcelabledTask.getTitle());
        map.put("details", parcelabledTask.getDetails());
        map.put("timeToDo", parcelabledTask.getTimeToDo());
        map.put("tag", parcelabledTask.getTag());
        map.put("done", String.valueOf(parcelabledTask.isDone()));
        map.put("id", parcelabledTask.getId());
        return map;
    }
}
