package com.example.android.todoapi_android.Utils;

import android.util.Log;

import com.example.android.todoapi_android.DTO.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.android.todoapi_android.Helpers.ApplicationController.TAG;

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

    public static List<Task> getListOfTask(JSONArray response) throws JSONException{
        List<Task> list = new ArrayList<>();
        Task task = null;

        int idToTask;
        boolean doneToTask = false;

        for (int i = 0 ; i < response.length() ; i ++ ){
            JSONObject jsonTask = response.getJSONObject(i);

            String title = jsonTask.getString("title");
            String details = jsonTask.getString("details");
            String timeToDo = jsonTask.getString("timeToDo");
            String tag = jsonTask.getString("tag");
            String done = jsonTask.getString("done");
            String id = jsonTask.getString("id");

            idToTask = Integer.parseInt(id);

            if (done.equals("1"))
                doneToTask = true;
            else if (done.equals("0"))
                doneToTask = false;
            else
                Log.e(TAG, "getListOfTask: doneToTask must have 0 or 1 value");

            task = new Task(title, details, timeToDo, tag, idToTask, doneToTask);

            Log.i(TAG, "getListOfTask: sparsowany task przed dodaniem do listy:  " + task.toString());

            list.add(task);
        }

        return list;

    }

}
