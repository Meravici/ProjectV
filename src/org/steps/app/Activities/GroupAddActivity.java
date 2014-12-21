package org.steps.app.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Rati on 21/12/14.
 */
public class GroupAddActivity extends Activity {
    public void submit(int imagenum){
        EditText txt = (EditText) findViewById(R.id.editText);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupadd);

        ImageView img = (ImageView)findViewById(R.id.imageView4);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(0);
            }
        });
        img = (ImageView)findViewById(R.id.imageView5);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(1);
            }
        });
        img = (ImageView)findViewById(R.id.imageView6);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(2);
            }
        });
        img = (ImageView)findViewById(R.id.imageView7);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(3);
            }
        });
        img = (ImageView)findViewById(R.id.imageView8);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(4);
            }
        });

    }
}
