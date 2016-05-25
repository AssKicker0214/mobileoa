package presentation.customer;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;

import com.example.ian.mobileoa.R;

import java.util.ArrayList;

import bl_stub.ICustomerBL_Stub;
import blservice.ICustomerBL;
import entity.Customer;

public class CustomerListActivity extends Activity{
    private LinearLayout allCusLayout;
    private LinearLayout myCusLayout;
    private ICustomerBL cusBL;

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        cusBL = new ICustomerBL_Stub();
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        setTab();
        refreshContent(0);
        refreshContent(1);
    }

    private void setTab() {
        tabHost = (TabHost) findViewById(R.id.customerListTabHost);
        // 如果没有继承TabActivity时，通过该种方法加载启动tabHost
        tabHost.setup();
        ScrollView allCusScroll = (ScrollView) findViewById(R.id.allCusScroll);
        ScrollView myCusScroll = (ScrollView) findViewById(R.id.myCusScroll);

        FrameLayout tabContent = (FrameLayout) allCusScroll.getParent();
        int tabContentHeight = tabContent.getMeasuredHeight();

        allCusLayout = (LinearLayout) findViewById(R.id.allCusContent);
        myCusLayout = (LinearLayout) findViewById(R.id.myCusContent);

//        allCusLayout.setMinimumHeight(tabContentHeight);
//        myCusLayout.setMinimumHeight(tabContentHeight);

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("全部客户").setContent(R.id.allCusScroll));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我的客户").setContent(R.id.myCusScroll));

//        tabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tabHost.setCurrentTab(0);
////                refreshContent(0);
//                ArrayList<CustomerListItem> items = addItemMock(allCusLayout);
////                updateItemMock(items);
//            }
//        });
//        tabHost.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tabHost.setCurrentTab(1);
////                refreshContent(1);
//                ArrayList<CustomerListItem> items = addItemMock(myCusLayout);
////                updateItemMock(items);
//            }
//        });

    }

//    public ArrayList<CustomerListItem> addItemMock(LinearLayout layout){
//        layout.removeAllViews();
//        ArrayList<CustomerListItem> items = new ArrayList<>();
//        for(int i=0;i<1;i++) {
//            CustomerListItem item = new CustomerListItem(CustomerListActivity.this);
//            item.init();
//            layout.addView(item);
//            items.add(item);
//        }
//        return items;
//    }
//
//    public void updateItemMock(ArrayList<CustomerListItem> items){
//        for(CustomerListItem item : items){
//            item.init();
//        }
//    }

    public void refreshContent(final int allmy){
        //异步加载
        AsyncTask<Integer, Customer, ArrayList<Customer>> task = new AsyncTask<Integer, Customer, ArrayList<Customer>>() {

            @Override
            protected ArrayList<Customer> doInBackground(Integer... params) {
                ArrayList<Customer> customers = null;
                int flag = params[0];
                if(flag == 0){
                    customers = cusBL.getCustomerList();
                }else{
                    customers = cusBL.getCustomerList();
                }
                for(Customer customer : customers){
                    publishProgress(customer);
                }

                return customers;
            }

            @Override
            protected void onProgressUpdate(Customer... values) {
                CustomerListItem item = new CustomerListItem(CustomerListActivity.this);

                if(allmy == 0){
                    allCusLayout.addView(item);
                }else if(allmy == 1){
                    myCusLayout.addView(item);
                }
            }

            @Override
            protected void onPostExecute(ArrayList<Customer> customers) {
//              done

            }
        };

        task.execute(allmy);

    }

}
