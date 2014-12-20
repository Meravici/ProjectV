package com.steps.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ProjectV.R;
import com.steps.Objects.GroupObject;

import java.util.ArrayList;


/**
 * Created by Xato on 12/20/2014.
 */
public class GroupAdapter extends ArrayAdapter<GroupObject> {

    public GroupAdapter(Context context, ArrayList<GroupObject> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        GroupObject group = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group, parent, false);
        }

//        if(group.countPendingTasks() > 0){
//            convertView.setBackgroundColor(parent.getResources().getColor(R.color.groupHasNotifications));
//        }

        // Lookup view for data population
        ImageView tvName = (ImageView) convertView.findViewById(R.id.GroupImageSmall);
        TextView tvHome = (TextView) convertView.findViewById(R.id.GroupNameSmall);

        // Populate the data into the template view using the data object
        tvName.setImageDrawable(parent.getResources().getDrawable(R.drawable.ic_launcher));
        tvHome.setText(group.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}