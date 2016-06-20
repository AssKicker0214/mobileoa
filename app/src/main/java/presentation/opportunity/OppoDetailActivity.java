package presentation.opportunity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.ian.mobileoa.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import entity.Keepup;
import entity.Opportunity;

public class OppoDetailActivity extends AppCompatActivity {
    private TabHost tabHost;

    private TextView titleText;
    private TextView oppoIDText;
    private TextView customerNameText;
    private TextView customerIDText;
    private TextView staffNameText;
    private TextView staffIDText;
    private TextView rankingText;
    private SeekBar stageBar;
    private TextView stageText;

    private Opportunity oppo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oppo_detail);
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
        oppo = getData();
        bind();
        fill();
    }

    private void bind(){

        titleText = (TextView) findViewById(R.id.oppo_detail_title);
        oppoIDText = (TextView) findViewById(R.id.oppo_detail_id);
        customerNameText = (TextView) findViewById(R.id.oppo_detail_cusname);
        customerIDText = (TextView) findViewById(R.id.oppo_detail_cusid);
        staffNameText = (TextView) findViewById(R.id.oppo_detail_staffname);
        staffIDText = (TextView) findViewById(R.id.oppo_detail_staffid);
        rankingText = (TextView) findViewById(R.id.ranking);
        stageBar = (SeekBar) findViewById(R.id.seekBar);
        stageText = (TextView) findViewById(R.id.oppo_step_text);
        stageBar.setEnabled(false);
    }

    private void fill(){
        titleText.setText(oppo.name);
        oppoIDText.setText(oppo.id);
        customerNameText.setText(oppo.cusName);
        customerIDText.setText(oppo.cusid);
        staffNameText.setText(oppo.staffName);
        staffIDText.setText(oppo.staffID);
            int rate = oppo.rank;
            if(rate == 1){
                rankingText.setText("★☆☆");
            }else if(rate == 2){
                rankingText.setText("★★☆");
            }else if(rate == 3){
                rankingText.setText("★★★");
            }
//        setStage(oppo.states.getFigure());

        stageText.setText(oppo.states.getFigure());
        stageBar.setProgress(oppo.states.getFigure());
    }

    public void setStage(int stage){
//        String text = "";
//        if(stage == 1){
//            text = "初步洽谈";
//        }else if(stage == 2){
//            text = "需求确定";
//        }else if(stage == 3){
//            text = "方案报价";
//        }else if(stage == 4){
//            text = "谈判合同";
//        }else if(stage == 5){
//            text = "赢单";
//        }else if(stage == 6){
//            text = "输单";
//        }

    }

    private void setTab(){
        tabHost = (TabHost) findViewById(R.id.oppo_detail_host);
        assert tabHost != null;
        tabHost.setup();
//        ScrollView keepupScroll = (ScrollView) findViewById(R.id.keepupScroll);
//
//        FrameLayout tabContent = (FrameLayout) keepupScroll.getParent();

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("跟进").setContent(R.id.keepupScroll));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("产品").setContent(R.id.contactScroll));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("合同").setContent(R.id.contractScroll));
    }

    public void updateKeepup(){
        AsyncTask<Void, Void, ArrayList<Keepup>> task = new AsyncTask<Void, Void, ArrayList<Keepup>>() {
            @Override
            protected ArrayList<Keepup> doInBackground(Void... params) {
                return null;
            }
        };
        task.execute();
    }

    private Opportunity getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return (Opportunity)bundle.getSerializable("Opportunity");
    }
}
