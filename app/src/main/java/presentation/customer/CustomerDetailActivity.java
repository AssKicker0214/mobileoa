package presentation.customer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.ian.mobileoa.R;

import java.util.ArrayList;
import java.util.List;

import bl.ContactBL;
import bl.ContractBL;
import bl.CurrentLogin;
import bl.KeepupBL;
import bl.OpportunityBL;
import blservice.IContactBL;
import blservice.IKeepupBLService;
import entity.Contact;
import entity.Contract;
import entity.Customer;
import entity.Keepup;
import entity.Opportunity;
import presentation.contact.ContactDetailActivity;
import presentation.contact.ContactListItem;
import presentation.contract.ContractListItem;
import presentation.keepup.KeepupListItem;
import presentation.opportunity.OppoListItem;
import presentation.universal.satellite.SatelliteMenu;
import presentation.universal.satellite.SatelliteMenuItem;

public class CustomerDetailActivity extends AppCompatActivity {
    private TabHost tabHost;
    private Customer customer;
    private IContactBL contactBL;
    private IKeepupBLService keepupBL;
    private ContractBL contractBL;
    private OpportunityBL oppoBL;

    private TextView cusNameText;
    private TextView cusIDText;
    private TextView creatorNameText;
    private TextView creatorIDText;
    private TextView ratingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = getData();
        contactBL = new ContactBL();
        keepupBL = new KeepupBL();
        contractBL = new ContractBL();
        oppoBL = new OpportunityBL();
        setContentView(R.layout.activity_customer_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SatelliteMenu fab = (SatelliteMenu ) findViewById(R.id.fab);
        fab.setMainImage(R.mipmap.ic_add_circle_black_48dp);
//        fab.setImageTint("#43a047");
        fab.setSatelliteDistance(150);
        fab.setRotation(270);
//        fab.setImgBkgd("#FFFFFF");
        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(4, R.mipmap.contract, "#54a2a4"));
        items.add(new SatelliteMenuItem(3, R.mipmap.oppo, "#54a2a4"));
        items.add(new SatelliteMenuItem(2, R.mipmap.contact, "#54a2a4"));
        items.add(new SatelliteMenuItem(1, R.mipmap.keepup, "#23f2f3"));
        fab.addItems(items);

        fab.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {
            @Override
            public void eventOccured(int id) {

                Intent intent = null;
                if(id == 2){
                    intent = new Intent(CustomerDetailActivity.this, ContactDetailActivity.class);
                    intent.putExtra("Contact", new Contact());
                    intent.putExtra("customerid", customer.id);
                    intent.putExtra("customername", customer.name);
                    intent.putExtra("staffid", CurrentLogin.id);
                }

                if(intent != null)
                startActivity(intent);
            }
        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        setTab();;

        updateContacts(0);
        updateKeepups();
        updateOppo(0);
        updateContract(0);

        bind();
        update();
    }

    private void bind(){
        cusNameText = (TextView)findViewById(R.id.customer_create_cusname);
        cusIDText = (TextView)findViewById(R.id.customer_detail_cusid);;
        creatorNameText = (TextView)findViewById(R.id.customer_detail_staffname);;
        creatorIDText = (TextView)findViewById(R.id.customer_detail_staffid);;
        ratingText = (TextView)findViewById(R.id.customer_detail_rating);;

        assert ((Button)findViewById(R.id.customer_detial_btn)) != null;
        ((Button)findViewById(R.id.customer_detial_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("Customer", customer);
                Intent intent = new Intent(CustomerDetailActivity.this, CustomerCreateActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void update(){
        cusNameText.setText(customer.name);
        cusIDText.setText(customer.id);
        creatorNameText.setText(customer.creatorName);
        creatorIDText.setText(customer.creatorid);
        setRating(customer.rank);
    }

    public void setRating(int rate){
        assert rate<=3&rate>=1;
        if(rate == 1){
            ratingText.setText("★☆☆");
        }else if(rate == 2){
            ratingText.setText("★★☆");
        }else if(rate == 3){
            ratingText.setText("★★★");
        }
    }

    private void setTab(){
        tabHost = (TabHost) findViewById(R.id.customerDetailTabHost);
        assert tabHost != null;
        tabHost.setup();
        ScrollView keepupScroll = (ScrollView) findViewById(R.id.keepupScroll);

        FrameLayout tabContent = (FrameLayout) keepupScroll.getParent();

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("跟进记录").setContent(R.id.keepupScroll));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("联系人").setContent(R.id.contactScroll));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("商机").setContent(R.id.oppoScroll));
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("合同").setContent(R.id.contractScroll));
    }


    public void updateData(int tab, int page){

    }

    public void updateContacts(int page){
        AsyncTask<Integer, Contact, ArrayList<Contact>> task = new AsyncTask<Integer, Contact, ArrayList<Contact>>() {
            @Override
            protected ArrayList<Contact> doInBackground(Integer... params) {
                ArrayList<Contact> contacts = contactBL.getContactListByCustomer(customer.id, params[0]);

                return contacts;
            }

            @Override
            protected void onPostExecute(ArrayList<Contact> contacts) {
                for(Contact contact : contacts){
                    ContactListItem item = new ContactListItem(CustomerDetailActivity.this, contact);
                    ((LinearLayout)findViewById(R.id.customerContactContent)).addView(item);
                }
            }
        };

        task.execute(page);

    }

    public void updateKeepups(){
        AsyncTask<String, Keepup, ArrayList<Keepup>> task = new AsyncTask<String, Keepup, ArrayList<Keepup>>() {
            @Override
            protected ArrayList<Keepup> doInBackground(String... params) {
                ArrayList<Keepup> keepups = keepupBL.getKeepupByCusID(params[0]);
//                keepups = KeepupBL.filterBySourceType(keepups, SourceType.CUSTOMER_KEEPUP);
                return keepups;
            }

            @Override
            protected void onPostExecute(ArrayList<Keepup> keepups) {
                for(Keepup keepup : keepups){
                    KeepupListItem item = new KeepupListItem(CustomerDetailActivity.this, keepup);
                    ((LinearLayout)findViewById(R.id.customerKeepupContent)).addView(item);
                }
            }
        };

        task.execute(customer.id);
    }

    public void updateOppo(int page){
        AsyncTask<Integer, Void, ArrayList<Opportunity>> task = new AsyncTask<Integer, Void, ArrayList<Opportunity>>() {
            @Override
            protected ArrayList<Opportunity> doInBackground(Integer... params) {
                ArrayList<Opportunity> oppos = oppoBL.getOppoList(params[0]);
                return oppos;
            }

            protected void onPostExecute(ArrayList<Opportunity> oppos){
                for(Opportunity oppo : oppos){
                    OppoListItem item = new OppoListItem(CustomerDetailActivity.this, oppo);
                    ((LinearLayout)findViewById(R.id.customerOppoContent)).addView(item);

                }
            }
        };

        task.execute(page);
    }

    public void updateContract(int page){
        AsyncTask<Integer, Void, ArrayList<Contract>> task = new AsyncTask<Integer, Void, ArrayList<Contract>>() {
            @Override
            protected ArrayList<Contract> doInBackground(Integer... params) {
                ArrayList<Contract> contracts = contractBL.getContractList(params[0]);
//                        new ArrayList<>();
//                contracts.add(new Contract());
//                contracts.add(new Contract());
//                contracts.add(new Contract());
                return contracts;
            }

            protected void onPostExecute(ArrayList<Contract> contracts) {
                for(Contract contract : contracts){
                    ContractListItem item = new ContractListItem(CustomerDetailActivity.this, contract);
                    ((LinearLayout)findViewById(R.id.customerContractContent)).addView(item);
                }
            }
        };
        task.execute(page);

    }




    private Customer getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return (Customer)bundle.getSerializable("Customer");
    }

}
