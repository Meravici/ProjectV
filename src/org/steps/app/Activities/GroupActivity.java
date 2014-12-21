package org.steps.app.Activities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.steps.adapters.TaskAdapter;
import org.steps.app.objects.Group;
import org.steps.utils.Globals;


/**
 * Created by Xato on 12/20/2014.
 */
public class GroupActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_view);
        Group group = Globals.GROUP;
        //Image
        Drawable draw = null;
        switch(group.getImageID()){
            case 1:
                draw = getResources().getDrawable(R.drawable.h1);
                break;
            case 2:
                draw = getResources().getDrawable(R.drawable.h2);
                break;
            default:
                draw = getResources().getDrawable(R.drawable.h3);
                break;
        }
        ImageView image = (ImageView) findViewById(R.id.imageView2);
        image.setImageDrawable(draw);
        //Name
        TextView name = (TextView) findViewById(R.id.textView);
        name.setText(group.getName());
        //tasks
        ListView listView = (ListView) findViewById(R.id.GroupListView);
//        System.out.println("bla: " + group.getTasks());
        TaskAdapter adapter = new TaskAdapter(this, group.getTasks());
//
        listView.setAdapter(adapter);
    }
}