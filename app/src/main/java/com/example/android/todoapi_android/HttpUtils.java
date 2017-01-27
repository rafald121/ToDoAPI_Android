package com.example.android.todoapi_android;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Rafaello on 2017-01-24.
 */

public class HttpUtils {


    public static HashMap<String, String> parseJSONLogin(JSONObject response) throws JSONException {
        HashMap<String, String> map = new HashMap<>();

        String info = response.getString("info");
        String token = response.getString("token");
        String userID = response.getString("userID");

        map.put("info", info);
        map.put("token", token);
        map.put("userID", userID);

        return map;
    }

    public static HashMap<String, String> parseJSONNewTask(JSONObject response) throws JSONException{
        HashMap<String, String> map = new HashMap<>();

        String title = response.getString("title");
        String details = response.getString("details");
        String timeToDo = response.getString("timeToDo");
        String tag = response.getString("tag");
        String done = response.getString("done");

        map.put("title", title);
        map.put("details", details);
        map.put("timeToDo", timeToDo);
        map.put("tag", tag);
        map.put("done", done);

        return map;
    }

}
