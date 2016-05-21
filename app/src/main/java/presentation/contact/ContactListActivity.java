package presentation.contact;

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

public class ContactListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
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
    }

    private void setTab() {
        TabHost tabHost = (TabHost) findViewById(R.id.contactListTabHost);
        // 如果没有继承TabActivity时，通过该种方法加载启动tabHost
        tabHost.setup();
        ScrollView allContactScroll = (ScrollView) findViewById(R.id.allContactScroll);
        ScrollView myContactScroll = (ScrollView) findViewById(R.id.myContactScroll);

        FrameLayout tabContent = (FrameLayout) allContactScroll.getParent();
        int tabContentHeight = tabContent.getMeasuredHeight();

        LinearLayout allContactLayout = (LinearLayout) findViewById(R.id.allContactContent);
        LinearLayout myContactLayout = (LinearLayout) findViewById(R.id.myContactContent);

        allContactLayout.setMinimumHeight(tabContentHeight);
        myContactLayout.setMinimumHeight(tabContentHeight);


        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("全部合同").setContent(R.id.allContactScroll));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我的合同").setContent(R.id.myContactScroll));

    }
}
