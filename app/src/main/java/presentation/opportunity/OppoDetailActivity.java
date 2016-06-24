package presentation.opportunity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ian.mobileoa.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import bl.ContractBL;
import bl.CurrentLogin;
import bl.KeepupBL;
import bl.OpportunityBL;
import entity.Contact;
import entity.Contract;
import entity.Keepup;
import entity.Opportunity;
import entity.SourceType;
import presentation.contact.ContactDetailActivity;
import presentation.contract.ContractCreateActivity;
import presentation.contract.ContractListItem;
import presentation.customer.CustomerDetailActivity;
import presentation.keepup.KeepupCreateActivity;
import presentation.keepup.KeepupListItem;
import presentation.universal.RefreshableActivity;
import presentation.universal.satellite.SatelliteMenu;
import presentation.universal.satellite.SatelliteMenuItem;

import static android.view.View.inflate;

public class OppoDetailActivity extends RefreshableActivity {
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

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        oppo = getData();
        setFab();
        bind();

        setTab();
//        updateKeepup();
    }

    private void setFab(){
        SatelliteMenu fab = (SatelliteMenu ) findViewById(R.id.fab);
        fab.setSatelliteDistance(150);
        fab.setRotation(270);
//        fab.setImgBkgd("#FFFFFF");
        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(4, R.mipmap.refresh, "#54a2a4"));
        items.add(new SatelliteMenuItem(3, R.mipmap.contract, "#54a2a4"));
        items.add(new SatelliteMenuItem(2, R.mipmap.product, "#54a2a4"));
        items.add(new SatelliteMenuItem(1, R.mipmap.keepup, "#23f2f3"));
        fab.addItems(items);

        fab.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {
            @Override
            public void eventOccured(int id) {

                Intent intent = null;
                if (id == 1) {
                    intent = new Intent(OppoDetailActivity.this, KeepupCreateActivity.class);
                    Bundle bundle = new Bundle();
                    Keepup keepup = new Keepup();
                    keepup.sourceType = SourceType.OPPORTUNITY_KEEPUP;
                    keepup.sourceID = oppo.id;
                    keepup.creatorID = CurrentLogin.id;
                    bundle.putSerializable("Keepup", keepup);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (id == 3) {
                    intent = new Intent(OppoDetailActivity.this, ContractCreateActivity.class);
                    Contract contract = new Contract();
                    contract.customerID = oppo.cusid;
                    contract.oppoID = oppo.id;
                    contract.staffID = CurrentLogin.id;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Contract", contract);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (id == 4) {
                    refresh();
                }


            }
        });
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

        assert ((Button)findViewById(R.id.infoBtn)) != null;
        ((Button)findViewById(R.id.infoBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Opportunity", oppo);
                Intent intent = new Intent(OppoDetailActivity.this, OppoCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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

        stageText.setText(oppo.states.toString());
        stageBar.setProgress(oppo.states.getFigure() - 1);
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
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("产品").setContent(R.id.productScroll));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("合同").setContent(R.id.contractScroll));
    }

    public void updateKeepup(){
        AsyncTask<Void, Void, ArrayList<Keepup>> task = new AsyncTask<Void, Void, ArrayList<Keepup>>() {
            @Override
            protected ArrayList<Keepup> doInBackground(Void... params) {
                KeepupBL keepupBL = new KeepupBL();
                ArrayList<Keepup> rs = new ArrayList<>();
                rs = keepupBL.getKeepupByOppoID(oppo.id);
                return rs;
            }

            @Override
            protected void onPostExecute(ArrayList<Keepup> keepups){
                LinearLayout layout = (LinearLayout) findViewById(R.id.oppoKeepupContent);
                layout.removeAllViews();
                for(Keepup keepup : keepups){
                    KeepupListItem item = new KeepupListItem(OppoDetailActivity.this, keepup);
                    layout.addView(item);
                }

                if(keepups.size() == 0){
                    View w = inflate(OppoDetailActivity.this, R.layout.no_record_warning, layout);

                }
            }
        };
        task.execute();
    }

    public void updateContract(){
        AsyncTask<Void, Void, ArrayList<Contract>> task = new AsyncTask<Void, Void, ArrayList<Contract>>() {
            @Override
            protected ArrayList<Contract> doInBackground(Void... params) {
                ContractBL contractBL = new ContractBL();
                ArrayList<Contract> rs = new ArrayList<>();
                rs = contractBL.getContractListByOppoID(oppo.id, 0);
                return rs;
            }

            @Override
            protected void onPostExecute(ArrayList<Contract> contracts){
                LinearLayout layout = (LinearLayout) findViewById(R.id.oppoContractContent);
                layout.removeAllViews();
                for(Contract contract : contracts){
                    ContractListItem item = new ContractListItem(OppoDetailActivity.this, contract);
                    layout.addView(item);
                }

                if(contracts.size() == 0){
                    View w = inflate(OppoDetailActivity.this, R.layout.no_record_warning, layout);

                }
            }
        };
        task.execute();
    }

    private Opportunity getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return (Opportunity)bundle.getSerializable("Opportunity");
    }

    private void clearContent(){
        ((ViewGroup) findViewById(R.id.oppoKeepupContent)).removeAllViews();
        ((ViewGroup) findViewById(R.id.oppoContractContent)).removeAllViews();
        ((ViewGroup) findViewById(R.id.oppoProductContent)).removeAllViews();
    }
//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        refresh();
//    }

    public void refresh(){
        AsyncTask<String, Void, Opportunity> task = new AsyncTask<String, Void, Opportunity>() {
            @Override
            protected Opportunity doInBackground(String... params) {
                OpportunityBL obl = new OpportunityBL();
                Opportunity newOppo = obl.find(oppo.id);
                return newOppo;
            }

            protected void onPostExecute(Opportunity newOppo){
                if(newOppo != null){
                    oppo = newOppo;
                    fill();
                    clearContent();
                    updateKeepup();
                    updateContract();
                }
            }
        };
        task.execute(oppo.id);
    }
}
