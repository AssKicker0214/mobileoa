package presentation.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;

import com.example.ian.mobileoa.R;

import java.util.ArrayList;

import bl.CurrentLogin;
import bl.CustomerBL;
//import bl_stub.ICustomerBL_Stub;
import blservice.ICustomerBL;
import entity.Customer;
import presentation.universal.IListAppendable;
import presentation.universal.ListItemBtnFactory;
import presentation.universal.MoreBtn;

public class CustomerListActivity extends Activity implements IListAppendable{
    private LinearLayout allCusLayout;
    private LinearLayout myCusLayout;
    private ICustomerBL cusBL;

    private RelativeLayout showMoreBtn;

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        cusBL = new ICustomerBL_Stub();
        cusBL = new CustomerBL();
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerListActivity.this, CustomerCreateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Customer", new Customer());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setTab();
//        appendContent(true, 1);
//        appendContent(false, 1);

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

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("全部客户").setContent(R.id.allCusScroll));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我的客户").setContent(R.id.myCusScroll));


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
                    customers = cusBL.getCustomerList(0);
                }else{
                    customers = cusBL.getCustomerList(0);
                }
                for(Customer customer : customers){
                    publishProgress(customer);
                }

                return customers;
            }

            @Override
            protected void onProgressUpdate(Customer... values) {
                CustomerListItem item = new CustomerListItem(CustomerListActivity.this, values[0]);
//                item.addButton(new ListItemBtnFactory(CustomerListActivity.this).getDelete(80, 80));
//                item.addButton(new ListItemBtnFactory(CustomerListActivity.this).getDelete(80, 80));
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

    @Override
    public void appendContent(final boolean isAll, final int page) {
        AsyncTask<Integer, Customer, ArrayList<Customer>> task = new AsyncTask<Integer, Customer, ArrayList<Customer>>() {

            private int pageCount;
            @Override
            protected ArrayList<Customer> doInBackground(Integer... params) {
                ArrayList<Customer> customers = null;
                int page = params[0];
                if(isAll){
                    customers = cusBL.getCustomerList(page);
                    pageCount = cusBL.getPageCount();
                }else{
                    customers = cusBL.getCustomerList(CurrentLogin.id, page);
                    pageCount = cusBL.getPageCount(CurrentLogin.id);

                }



                return customers;
            }

            @Override
            protected void onProgressUpdate(Customer... values) {

            }

            @Override
            protected void onPostExecute(ArrayList<Customer> customers) {
//              done

                LinearLayout layout = null;
                if(isAll){
                    layout = allCusLayout;
                }else{
                    layout = myCusLayout;
                }
                if(layout.getChildCount() > 0) {
                    layout.removeViewAt(layout.getChildCount() - 1);
                }
                for(Customer customer : customers){
                    CustomerListItem item = new CustomerListItem(CustomerListActivity.this, customer);
                    layout.addView(item);
                }
                MoreBtn moreBtn = null;
                moreBtn = new MoreBtn(layout, CustomerListActivity.this);

                moreBtn.show(pageCount, isAll, page);

            }
        };

        task.execute(page);
    }

    @Override
    public int getPageCount(boolean isAll) {
        return 0;
    }

    public void clearContent(){
        ((ViewGroup) findViewById(R.id.allCusContent)).removeAllViews();
        ((ViewGroup) findViewById(R.id.myCusContent)).removeAllViews();
    }

    @Override
    public void onPostResume(){
        super.onPostResume();
        clearContent();
        appendContent(true, 1);
        appendContent(false, 1);
    }
}
