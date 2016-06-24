package presentation.customer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ian.mobileoa.R;

import java.util.ArrayList;
import java.util.List;

import bl.ContactBL;
import bl.ContractBL;
import bl.CurrentLogin;
import bl.CustomerBL;
import bl.KeepupBL;
import bl.OpportunityBL;
import blservice.IContactBL;
import blservice.IKeepupBLService;
import entity.Contact;
import entity.Contract;
import entity.Customer;
import entity.Keepup;
import entity.Opportunity;
import entity.SourceType;
import presentation.contact.ContactDetailActivity;
import presentation.contact.ContactListItem;
import presentation.contract.ContractCreateActivity;
import presentation.contract.ContractListItem;
import presentation.keepup.KeepupCreateActivity;
import presentation.keepup.KeepupListItem;
import presentation.opportunity.OppoCreateActivity;
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

    private boolean showRefreshText = false;
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
//        fab.setMainImage(R.mipmap.ic_add_circle_black_48dp);
//        fab.setImageTint("#43a047");
        fab.setSatelliteDistance(150);
        fab.setRotation(270);
//        fab.setImgBkgd("#FFFFFF");
        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(4, R.mipmap.refresh, "#54a2a4"));
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
                }else if(id == 1){
                    intent = new Intent(CustomerDetailActivity.this, KeepupCreateActivity.class);
                    Keepup keepup = new Keepup();
                    keepup.sourceType = SourceType.CUSTOMER_KEEPUP;
                    keepup.sourceID = customer.id;
                    keepup.creatorID = CurrentLogin.id;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Keepup", keepup);
                    intent.putExtras(bundle);
                }else if(id == 3){
                    intent = new Intent(CustomerDetailActivity.this, OppoCreateActivity.class);
                    Opportunity opportunity = new Opportunity();
                    opportunity.cusid = customer.id;
                    opportunity.cusName = customer.name;
                    opportunity.staffID = CurrentLogin.id;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Opportunity", opportunity);
                    intent.putExtras(bundle);
                }else if(id == 4){
                    updateData();
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
        bind();

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
        if(rate == 3){
            ratingText.setText("★☆☆");
        }else if(rate == 2){
            ratingText.setText("★★☆");
        }else if(rate == 1){
            ratingText.setText("★★★");
        }
    }

    private void setTab(){
        tabHost = (TabHost) findViewById(R.id.customerDetailTabHost);
        assert tabHost != null;
        tabHost.setup();
        ScrollView keepupScroll = (ScrollView) findViewById(R.id.keepupScroll);

        FrameLayout tabContent = (FrameLayout) keepupScroll.getParent();

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("跟进").setContent(R.id.keepupScroll));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("联系人").setContent(R.id.contactScroll));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("商机").setContent(R.id.oppoScroll));
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("合同").setContent(R.id.contractScroll));
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
                if(contacts == null || contacts.size() == 0){
                    View.inflate(CustomerDetailActivity.this, R.layout.no_record_warning, (ViewGroup)findViewById(R.id.customerContractContent));
                }
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
                if(keepups == null || keepups.size() == 0){
                    View.inflate(CustomerDetailActivity.this, R.layout.no_record_warning, (ViewGroup)findViewById(R.id.customerContractContent));
                }
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
                ArrayList<Opportunity> oppos = oppoBL.getOppoListByCusId(params[0], customer.id);
                return oppos;
            }

            protected void onPostExecute(ArrayList<Opportunity> oppos){
                if(oppos == null || oppos.size() == 0){
                    View.inflate(CustomerDetailActivity.this, R.layout.no_record_warning, (ViewGroup)findViewById(R.id.customerOppoContent));
                }
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
                ArrayList<Contract> contracts = contractBL.getContractListByCustomerID(params[0], customer.id);
//                        new ArrayList<>();
//                contracts.add(new Contract());
//                contracts.add(new Contract());
//                contracts.add(new Contract());
                return contracts;
            }

            protected void onPostExecute(ArrayList<Contract> contracts) {
                if(contracts == null || contracts.size() == 0){
                    View.inflate(CustomerDetailActivity.this, R.layout.no_record_warning, (ViewGroup)findViewById(R.id.customerContractContent));
                }
                for(Contract contract : contracts){
                    ContractListItem item = new ContractListItem(CustomerDetailActivity.this, contract);
                    ((LinearLayout)findViewById(R.id.customerContractContent)).addView(item);
                }
            }
        };
        task.execute(page);

    }

    private void clearContent(){
        ((ViewGroup) findViewById(R.id.customerKeepupContent)).removeAllViews();
        ((ViewGroup) findViewById(R.id.customerContactContent)).removeAllViews();
        ((ViewGroup) findViewById(R.id.customerOppoContent)).removeAllViews();
        ((ViewGroup) findViewById(R.id.customerContractContent)).removeAllViews();
    }


    @Override
    public void onResume(){
        super.onResume();
        updateData();
        update();
    }

    public void updateData(){
        AsyncTask<String, Void, Customer> task = new AsyncTask<String, Void, Customer>() {
            @Override
            protected Customer doInBackground(String... params) {
                CustomerBL cbl = new CustomerBL();
                return cbl.find(params[0]);
            }

            @Override
            protected void onPostExecute(Customer customer){
                CustomerDetailActivity.this.customer = customer;

                clearContent();
                updateContacts(0);
                updateKeepups();
                updateOppo(0);
                updateContract(0);

            }
        };
        task.execute(customer.id);
    }

    private Customer getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return (Customer)bundle.getSerializable("Customer");
    }

}
