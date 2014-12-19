package com.steps.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.steps.Objects.GroupObject;

/**
 * Created by Xato on 12/20/2014.
 */
public class GroupAdapter extends ArrayAdapter<GroupObject> {

    public GroupAdapter(Context context, int resource) {
        super(context, resource);
    }
}
