package org.steps.app.Activities;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.ProjectV.R;
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
        ImageView image = (ImageView) findViewById(R.id.GroupImageLarge);
//        image.setImageDrawable();
        //Name
        TextView name = (TextView) findViewById(R.id.GroupNameLarge);
        name.setText(group.getName());
        //tasks
        ListView listView = (ListView) findViewById(R.id.GroupTasks);
        System.out.println("bla: " + group.getTasks());
        TaskAdapter adapter = new TaskAdapter(this, group.getTasks());

        listView.setAdapter(adapter);
    }
}