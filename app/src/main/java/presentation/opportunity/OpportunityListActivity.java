package presentation.opportunity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;

import com.example.ian.mobileoa.R;

import java.util.ArrayList;

import bl.OpportunityBL;
import entity.Opportunity;

public class OpportunityListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        setTab();

        appendContent(true, 0);
    }

    private void setTab() {
        TabHost tabHost = (TabHost) findViewById(R.id.opportunityListTabHost);
        // 如果没有继承TabActivity时，通过该种方法加载启动tabHost
        tabHost.setup();
        ScrollView allOppScroll = (ScrollView) findViewById(R.id.allOppScroll);
        ScrollView myOppScroll = (ScrollView) findViewById(R.id.myOppScroll);

        FrameLayout tabContent = (FrameLayout) allOppScroll.getParent();
        int tabContentHeight = tabContent.getMeasuredHeight();

        LinearLayout allOppLayout = (LinearLayout) findViewById(R.id.allOppContent);
        LinearLayout myOppLayout = (LinearLayout) findViewById(R.id.myOppContent);

        allOppLayout.setMinimumHeight(tabContentHeight);
        myOppLayout.setMinimumHeight(tabContentHeight);


        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("全部商机").setContent(R.id.allOppScroll));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我的商机").setContent(R.id.myOppScroll));

    }

    public void appendContent(final boolean isAll, final int page){
        AsyncTask<Integer, Void, ArrayList<Opportunity>> task = new AsyncTask<Integer, Void, ArrayList<Opportunity>>() {
            @Override
            protected ArrayList<Opportunity> doInBackground(Integer... params) {
                OpportunityBL oppoBL = new OpportunityBL();
                return oppoBL.getOppoList(page);
            }

            protected void onPostExecute(ArrayList<Opportunity> oppos) {
                for(Opportunity oppo : oppos){
                    OppoListItem item = new OppoListItem(OpportunityListActivity.this, oppo);
                    if(isAll){
                        assert ((LinearLayout)findViewById(R.id.allOppContent)) != null;
                        ((LinearLayout)findViewById(R.id.allOppContent)).addView(item);
                    }else{
                        ((LinearLayout)findViewById(R.id.myOppContent)).addView(item);
                    }
                }

            }
        };

        task.execute(page);
    }
}
