package org.steps.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.example.ProjectV.R;
import com.steps.Objects.TaskObject;

import java.util.ArrayList;


/**
 * Created by Xato on 12/20/2014.
 */
public class TaskAdapter extends ArrayAdapter<TaskObject> {

    public TaskAdapter(Context context, ArrayList<TaskObject> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TaskObject task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task, parent, false);
        }

        ImageView status = (ImageView) parent.findViewById(R.id.TaskStatus);
        Drawable statusDrawable = null;
        if(task.getStatus() == TaskObject.STATUS_PENDING){
            statusDrawable = parent.getResources().getDrawable(R.drawable.task_status_pending);
        }else if(task.getStatus() == TaskObject.STATUS_FINISHED) {
            statusDrawable = parent.getResources().getDrawable(R.drawable.task_status_green);
        }else if(task.getStatus() == TaskObject.STATUS_FAILED){
            statusDrawable = parent.getResources().getDrawable(R.drawable.task_status_red);
        }else{
            statusDrawable = parent.getResources().getDrawable(R.drawable.task_status_grey);
        }
        status.setImageDrawable(statusDrawable);


        // Return the completed view to render on screen
        return convertView;
    }
}