package org.steps.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.steps.app.Activities.R;
import org.steps.app.objects.Group;

import java.util.ArrayList;


/**
 * Created by Xato on 12/20/2014.
 */
public class GroupAdapter extends ArrayAdapter<Group> {

    public GroupAdapter(Context context, ArrayList<Group> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Group group = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group, parent, false);
        }

        if(group.getTasksPending() > 0){
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.groupHasNotifications));
        }

        // Lookup view for data population
        ImageView image = (ImageView) convertView.findViewById(R.id.GroupImageSmall);
        TextView name = (TextView) convertView.findViewById(R.id.GroupNameSmall);

        Drawable draw = null;
        switch(group.getImageID()){
            case 1:
                draw = parent.getResources().getDrawable(R.drawable.sh1);
                break;
            case 2:
                draw = parent.getResources().getDrawable(R.drawable.sh2);
                break;
            default:
                draw = parent.getResources().getDrawable(R.drawable.sh3);
                break;
        }

        // Populate the data into the template view using the data object
        image.setImageDrawable(draw);
        name.setText(group.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}