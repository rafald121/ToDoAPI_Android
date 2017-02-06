package com.example.android.todoapi_android.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.todoapi_android.Activities.EditTaskActivity;
import com.example.android.todoapi_android.Activities.ListOfTasksActivity;
import com.example.android.todoapi_android.Activities.LoginActivity;
import com.example.android.todoapi_android.DTO.Task;
import com.example.android.todoapi_android.Helpers.ParcelabledTask;
import com.example.android.todoapi_android.Interfaces.RecyclerViewClickListener;
import com.example.android.todoapi_android.Interfaces.VolleyCallback;
import com.example.android.todoapi_android.Interfaces.VolleyCallbackDelete;
import com.example.android.todoapi_android.R;
import com.example.android.todoapi_android.Utils.HashMapUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.android.todoapi_android.Activities.ListOfTasksActivity.getTasksListURL;
import static com.example.android.todoapi_android.Helpers.ApplicationController.TAG;

/**
 * Created by Rafaello on 2017-01-27.
 */

public class ListOfTaskAdapter extends RecyclerView.Adapter<ListOfTaskAdapter.MyViewHolder> {


    List <Task> list;
    private Context context;
    private static RecyclerViewClickListener clickListener;
//    private static RecyclerViewItemActions itemListener;
    public ListOfTaskAdapter(List<Task> listOfTask) {
        this.list = listOfTask;
    }

    public ListOfTaskAdapter(Context context, List<Task>
            listOfTask, RecyclerViewClickListener itemListener){

        if(listOfTask==null)
            Log.e(TAG, "ListOfTaskAdapter: PASSED TO ADAPTER LSIT IS NULL");

        this.context = context;
        this.list = listOfTask;
        this.clickListener = itemListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recyclerItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);

        return new MyViewHolder(recyclerItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task task = list.get(position);

        holder.title.setText(task.getTitle());
        holder.details.setText(task.getDetails());
        holder.timetodo.setText(task.getTimeToDo());
        Log.i(TAG, "onBindViewHolder: TASK ID DONE OR NOT for:" +task.getTitle() + "  " + task.isDone());
        if(task.isDone())
            holder.done.setImageResource(R.drawable.ic_done_black_24dp);
        else
            holder.done.setImageResource(R.drawable.ic_cancel_black_24dp);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener{

        ImageButton done, edit, delete;
        TextView title,details,timetodo;

        public MyViewHolder(View itemView) {
            super(itemView);

            done = (ImageButton) itemView.findViewById(R.id.imageButtonDone);
            edit = (ImageButton) itemView.findViewById(R.id.imageButtonEdit);
            delete = (ImageButton) itemView.findViewById(R.id.imageButtonDelete);

            title = (TextView) itemView.findViewById(R.id.textViewTitle);
            details = (TextView) itemView.findViewById(R.id.textViewDetails);
            timetodo = (TextView) itemView.findViewById(R.id.textViewTimeToDo);

            itemView.setOnClickListener(this);
            done.setOnClickListener(this);
            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(v.getId() == done.getId()) {
                Log.i(TAG, "onClick: DONE CLICKED");
                Log.i(TAG, "onClick: position: " + this.getLayoutPosition());
                Task task = list.get(getLayoutPosition());
                Log.i(TAG, "onClick: task info: " + task.toString());

                doneUndoneEdit(task, new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        Log.i(TAG, "onSuccess: result is edited properly, result info: " + result
                                .toString());

                        done.setImageResource(R.drawable.ic_done_black_24dp);

                        Intent editIntent = new Intent(context, ListOfTasksActivity
                                .class);
                        editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(editIntent);
                    }

                    @Override
                    public void onFailure(VolleyError error) {

                    }

                });



            }
            else if(v.getId() == edit.getId()) {
                Log.i(TAG, "onClick: EDIT CLICKED");
                Log.i(TAG, "onClick: position: " + getLayoutPosition());
                Task task = list.get(getLayoutPosition());

                Log.i(TAG, "onClick: task info: " + task.toString());
                ParcelabledTask pTask = new ParcelabledTask();
                pTask.setTitle(task.getTitle());
                pTask.setDetails(task.getDetails());
                pTask.setTimeToDo(task.getTimeToDo());
                pTask.setTag(task.getTag());
                pTask.setId(task.getId());
                pTask.setDone(task.isDone());
                Intent editIntent = new Intent(context, EditTaskActivity
                        .class);
                editIntent.putExtra("task", pTask);
                editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(editIntent);

            }
            else if(v.getId() == delete.getId()){

                Log.i(TAG, "onClick: DELETE CLICKED");
                Log.i(TAG, "onClick: position: " + getLayoutPosition());
                Task task = list.get(getLayoutPosition());
                Log.i(TAG, "recyclerViewDeletetTask: delete clicked for " +
                        "title: " +
                        task.getTitle());
                String taskID = String.valueOf(task.getId());

                try {

                    deleteRequest(taskID, new VolleyCallbackDelete() {
                        @Override
                        public void onSuccess(JSONObject result) throws JSONException {
                            Log.i(TAG, "onSuccess: " + result.toString());
//                            list.notifyDataSetChanged();
                            Log.i(TAG, "onSuccess: after listOfTaskAdapter.notifyDataSetChanged()");
                        }

                        @Override
                        public void onFailure(VolleyError error) {
                            Log.i(TAG, "onFailure: " + error.toString());

                        }
                    });
                } catch (AuthFailureError authFailureError) {
                    authFailureError.printStackTrace();
                }

                Intent editIntent = new Intent(context, ListOfTasksActivity
                        .class);
                editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(editIntent);
            }
            else { //CLICKED IN ITEM CONTENT, NOT IN BUTTON
                Task clickedTask = list.get(getLayoutPosition());
                Log.i(TAG, "onClick: clickedTask: " + clickedTask);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }


        private void doneUndoneEdit(Task mtask, final VolleyCallback volleyCallback) {

            Task task = mtask;

            Log.i(TAG, "doneUndoneEdit: GOTTEN TASK: " + task.toString());
            int taskid = task.getId();
            HashMap<String, Object> map = HashMapUtils.createHashMapFromObject(task);

//            if (task.isDone())
//                task.setDone(false);
//            else
//                task.setDone(true);


            Log.i(TAG, "doneUndoneEdit: CONVERTED MAP READY TO SEND PUT REQUEST: " + map.toString
                    ());


            SharedPreferences sharedPreferences = context.getSharedPreferences(LoginActivity.SESSIONINFO,
                    Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString("token", "");
            RequestQueue mRequestQueue = Volley.newRequestQueue(context);

            String url = getTasksListURL + "/" + taskid;


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                    new JSONObject(map),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(TAG, "onResponse: wynik undone done edit: " + response.toString());

                            if (response != null) {
                                try {
                                    volleyCallback.onSuccess(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else
                                Log.e(TAG, "onResponse: response from server while editing " +
                                        "done/undone is null");

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e("Error while edit done/undone task", error.getMessage());
                            volleyCallback.onFailure(error);
                        }
                    }){
                @Override
                public Map<String,String> getHeaders() throws AuthFailureError{
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("token", token);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            mRequestQueue.add(request);

        }



        private void deleteRequest(String id, final VolleyCallbackDelete volleyCallbackDelete) throws AuthFailureError {
            SharedPreferences sharedPreferences = context.getSharedPreferences(LoginActivity
                    .SESSIONINFO,
                    Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString("token", "");

            RequestQueue mRequestQueue = Volley.newRequestQueue(context);
            Log.i(TAG, "getListOfTasks: URL: " + getTasksListURL + "/" + id);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE,
                    getTasksListURL + "/" + id,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                volleyCallbackDelete.onSuccess(response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            volleyCallbackDelete.onFailure(error);
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("token", token);

                    return headers;
                }

            };
//            Log.i(TAG, "getListOfTasks: headers: " + request.getHeaders().toString());
            mRequestQueue.add(request);
        }


    }


}
