package com.example.ian.mobileoa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import presentation.universal.ListItem;
import presentation.universal.ListPanel;

public class ListItemTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_test);

//        LinearLayout root = new LinearLayout(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        LinearLayout root = (LinearLayout) findViewById(R.id.testLayout);
////        ListPanel listPanel = new ListPanel(this);
//
////        root.addView(listPanel);
//        for(int i=0;i<15;i++)
////        listPanel.addItem(new ListItem(this));
//        root.addView(new ListItem(this));
    }
}
