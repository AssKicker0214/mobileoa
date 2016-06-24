package presentation.contract;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ian.mobileoa.R;

import java.util.ArrayList;

import bl.ContractBL;
import bl.CurrentLogin;
import bl.KeepupBL;
import entity.Contract;
import entity.Keepup;
import entity.SourceType;
import presentation.keepup.KeepupCreateActivity;
import presentation.keepup.KeepupListItem;

public class ContractDetailActivity extends AppCompatActivity {

    private TextView title;
    private TextView number;
    private TextView status;

    private Contract contract;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_detail);

        contract = getData();
        bind();
        setFab();
    }

    private void setFab(){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContractDetailActivity.this, KeepupCreateActivity.class);
                Bundle bundle = new Bundle();
                Keepup keepup = new Keepup();
                keepup.creatorID = CurrentLogin.id;
                keepup.sourceType = SourceType.CONTRACT_KEEPUP;
                keepup.sourceID = contract.id;
                bundle.putSerializable("Keepup", keepup);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void bind(){
        title = (TextView) findViewById(R.id.contract_titile_text);
        number = (TextView) findViewById(R.id.contrct_number_text);
        status = (TextView) findViewById(R.id.contract_status_text);

        Button btn = (Button) findViewById(R.id.info_btn);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractDetailActivity.this, ContractCreateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contract", contract);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void fill(){
        title.setText(contract.name);
        number.setText(contract.number);
        status.setText(contract.state.toString());
    }

    public void updateKeepup(){
        AsyncTask<String, Void, ArrayList<Keepup>> task = new AsyncTask<String, Void, ArrayList<Keepup>>() {
            @Override
            protected ArrayList<Keepup> doInBackground(String... params) {
                KeepupBL kbl = new KeepupBL();
                ArrayList<Keepup> keepups = kbl.getKeepupByContractID(params[0]);
                return keepups;
            }

            @Override
            protected void onPostExecute(ArrayList<Keepup> keepups){
                LinearLayout layout = (LinearLayout) findViewById(R.id.contract_keepup_layout);
                if(keepups == null || keepups.size() == 0){
                    layout.removeAllViews();
                    View.inflate(ContractDetailActivity.this, R.layout.no_record_warning, layout);
                }else{
                    for(Keepup keepup : keepups){
                        KeepupListItem item = new KeepupListItem(ContractDetailActivity.this, keepup);
                        layout.addView(item);
                    }
                }
            }
        };
        task.execute(contract.id);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((ViewGroup) findViewById(R.id.contract_keepup_layout)).removeAllViews();
        AsyncTask<Void, Void, Contract> task = new AsyncTask<Void, Void, Contract>() {
            @Override
            protected Contract doInBackground(Void... params) {
                ContractBL contractBL = new ContractBL();
                return contractBL.find(contract.id);
            }

            @Override
            protected void onPostExecute(Contract contract){
                ContractDetailActivity.this.contract = contract;
                fill();
            }
        };
        task.execute();
        updateKeepup();
    }



    private Contract getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return (Contract)bundle.getSerializable("Contract");
    }
}
