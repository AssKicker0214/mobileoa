package com.example.ian.mobileoa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import presentation.universal.ListItem;

public class ListItemTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_test);

        LinearLayout root = (LinearLayout) findViewById(R.id.testLayout);
        for(int i=0;i<1;i++)
        root.addView(new ListItem(this));
    }
}
