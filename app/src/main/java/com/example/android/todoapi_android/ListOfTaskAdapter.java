package com.example.android.todoapi_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import static com.example.android.todoapi_android.ApplicationController.TAG;

/**
 * Created by Rafaello on 2017-01-27.
 */

public class ListOfTaskAdapter extends RecyclerView.Adapter<ListOfTaskAdapter.MyViewHolder> {


    List <Task> list;
    private Context context;
    private static RecyclerViewClickListener clickListener;
    private static RecyclerViewItemActions itemListener;
    public ListOfTaskAdapter(List<Task> listOfTask) {
        this.list = listOfTask;
    }

    public ListOfTaskAdapter(Context context, List<Task>
            listOfTask, RecyclerViewClickListener itemListener, RecyclerViewItemActions
            itemActions){
        this.context = context;
        this.list = listOfTask;
        this.clickListener = itemListener;
        this.itemListener = itemActions;
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
                itemListener.recyclerViewUnOrDoneTask(v, this.getLayoutPosition());
            }
            else if(v.getId() == edit.getId()) {
                Log.i(TAG, "onClick: EDIT CLICKED");
                itemListener.recyclerViewEditTask(v, this.getLayoutPosition());
            }
            else if(v.getId() == delete.getId()){
                Log.i(TAG, "onClick: DELETE CLICKED");
                itemListener.recyclerViewDeleteTask(v, this.getLayoutPosition());
            }
            else
                clickListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }


        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
