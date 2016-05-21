package presentation.contract;

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

public class ContractListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setTab();
    }

    private void setTab() {
        TabHost tabHost = (TabHost) findViewById(R.id.contractListTabHost);
        // 如果没有继承TabActivity时，通过该种方法加载启动tabHost
        tabHost.setup();
        ScrollView allContractScroll = (ScrollView) findViewById(R.id.allContractScroll);
        ScrollView myContractScroll = (ScrollView) findViewById(R.id.myContractScroll);

        FrameLayout tabContent = (FrameLayout) allContractScroll.getParent();
        int tabContentHeight = tabContent.getMeasuredHeight();

        LinearLayout allContractLayout = (LinearLayout) findViewById(R.id.allContractContent);
        LinearLayout myContractLayout = (LinearLayout) findViewById(R.id.myContractContent);

        allContractLayout.setMinimumHeight(tabContentHeight);
        myContractLayout.setMinimumHeight(tabContentHeight);


        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("全部合同").setContent(R.id.allContractScroll));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我的我的合同").setContent(R.id.myContractScroll));

    }

}
