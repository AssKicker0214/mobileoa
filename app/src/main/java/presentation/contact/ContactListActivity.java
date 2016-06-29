package presentation.contact;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;

import com.example.ian.mobileoa.R;

import java.util.ArrayList;

import bl.ContactBL;
import bl.CurrentLogin;
import entity.Contact;
import presentation.universal.IListAppendable;
import presentation.universal.MoreBtn;

public class ContactListActivity extends AppCompatActivity implements IListAppendable{
    ContactBL contactBL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactBL = new ContactBL();
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

        setTab();
        appendContent(true, 1);
        appendContent(false, 1);
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


        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("全部联系人").setContent(R.id.allContactScroll));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我的联系人").setContent(R.id.myContactScroll));

    }

    public void appendContent(final boolean isAll, final int page){
        AsyncTask<Integer, Void, ArrayList<Contact>> task = new AsyncTask<Integer, Void, ArrayList<Contact>>() {
            int pageCount = 0;
            @Override
            protected ArrayList<Contact> doInBackground(Integer... params) {
                ArrayList<Contact> contacts = null;
                if(isAll){
                    contacts = contactBL.getContactList(params[0]);
                    pageCount = contactBL.getPageCount();
                }else{
                    contacts = contactBL.getContactList(CurrentLogin.id, params[0]);
                    pageCount = contactBL.getPageCount();
                }
                return contacts;
            }

            protected void onPostExecute(ArrayList<Contact> contacts){
                LinearLayout layout = null;
                if(isAll){
                    layout = ((LinearLayout)(findViewById(R.id.allContactContent)));
                }else{
                    layout = ((LinearLayout)(findViewById(R.id.myContactContent)));
                }
                assert layout!=null;
                assert contacts!=null;
                if(layout.getChildCount() > 0) {
                    layout.removeViewAt(layout.getChildCount() - 1);
                }
                for(Contact contact : contacts){
                    ContactListItem item = new ContactListItem(ContactListActivity.this, contact);
                    layout.addView(item);
                }

                MoreBtn moreBtn = null;
                moreBtn = new MoreBtn(layout, ContactListActivity.this);

                moreBtn.show(pageCount, true, page);
            }
        };

        task.execute(page);
    }

    @Override
    public int getPageCount(boolean isAll) {
        return 0;
    }
}
