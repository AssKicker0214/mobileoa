package presentation.contract;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;

import com.example.ian.mobileoa.R;

import java.io.IOException;
import java.util.ArrayList;

import bl.ContractBL;
import bl.CurrentLogin;
import entity.Contract;
import presentation.universal.IListAppendable;
import presentation.universal.MoreBtn;

public class ContractListActivity extends AppCompatActivity implements IListAppendable {

    private int page = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我的合同").setContent(R.id.myContractScroll));

    }

    @Override
    public void appendContent(final boolean isAll, final int page) {
        AsyncTask<Integer, Void, ArrayList<Contract>> task = new AsyncTask<Integer, Void, ArrayList<Contract>>() {

            private int pageCount = 0;
            @Override
            protected ArrayList<Contract> doInBackground(Integer... params) {
                ContractBL cbl = new ContractBL();
                ArrayList<Contract> contracts = new ArrayList<>();
                if(isAll){
                    contracts = cbl.getContractList(params[0]);
                    pageCount = cbl.getPageCount();
                }else{
                    contracts = cbl.getContractListByStaffID(CurrentLogin.id, params[0]);
                    pageCount = cbl.getPageCount();
                }
                return contracts;
            }

            @Override
            protected void onPostExecute(ArrayList<Contract> contracts){
                LinearLayout layout = null;
                if(isAll){
                    layout = (LinearLayout) findViewById(R.id.allContractContent);
                }else{
                    layout = (LinearLayout) findViewById(R.id.myContractContent);
                }
                if(layout.getChildCount() > 0) {
                    layout.removeViewAt(layout.getChildCount() - 1);
                }
                if(contracts == null || contracts.size() == 0){
                    View.inflate(ContractListActivity.this, R.layout.no_record_warning, layout);
                }else{

                    for(Contract contract : contracts){
                        ContractListItem item = new ContractListItem(ContractListActivity.this, contract);
                        layout.addView(item);
                    }
                    MoreBtn moreBtn = new MoreBtn(layout, ContractListActivity.this);

                    moreBtn.show(pageCount, isAll, page);
                }
            }
        };

        task.execute(page);
    }

    @Override
    public int getPageCount(boolean isAll) {
        return page;
    }

    public void clearContent(){
        ((ViewGroup) findViewById(R.id.allContractContent)).removeAllViews();
        ((ViewGroup) findViewById(R.id.myContractContent)).removeAllViews();
    }

    @Override
    public void onPostResume(){
        super.onPostResume();
        clearContent();
        appendContent(true, 1);
        appendContent(false, 1);
    }
}
