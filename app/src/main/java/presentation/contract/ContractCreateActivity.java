package presentation.contract;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ian.mobileoa.R;

import java.util.Date;

import bl.ContractBL;
import entity.Contract;
import entity.ContractState;

public class ContractCreateActivity extends AppCompatActivity {

    EditText title;
    EditText number;
    EditText totalAmount;
    EditText payMethod;
    EditText startDate;
    EditText endDate;
    EditText clientContractor;
    EditText ourContractor;
    EditText signingDate;
    EditText remark;

    Button btn;

    private TextView status;

    private boolean isEditable = false;
    private boolean isCreate = false;
    private Contract contract;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_create);
        contract = getData();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        bind();
        if(contract.id == null || contract.id.equals("")){
            isEditable = true;
            isCreate = true;
        }
        updateEditable();
        fill();
    }

    private void bind(){
        title = (EditText) findViewById(R.id.contract_titile_text);
        number = (EditText) findViewById(R.id.contrct_number_text);
        totalAmount = (EditText) findViewById(R.id.total_amount_edit);
        payMethod = (EditText) findViewById(R.id.pay_method_edit);
        startDate = (EditText) findViewById(R.id.start_date_edit);
        endDate = (EditText) findViewById(R.id.end_date_edit);
        clientContractor = (EditText) findViewById(R.id.client_contract_edit);
        ourContractor = (EditText) findViewById(R.id.our_contractor_edit);
        signingDate = (EditText) findViewById(R.id.signing_date_edit);
        remark = (EditText) findViewById(R.id.contract_remarks_edit);

        status = (TextView) findViewById(R.id.contract_status_text);
        status.setOnClickListener(new View.OnClickListener() {
            ContractState status = contract.state;

            @Override
            public void onClick(View v) {
                if (status.equals(ContractState.BEFORE)) {
                    status = ContractState.PERFORMING;
                } else if (status.equals(ContractState.PERFORMING)) {
                    status = ContractState.SUCCEEFUL;
                } else if (status.equals(ContractState.SUCCEEFUL)) {
                    status = ContractState.DEAD;
                } else {
                    status = ContractState.BEFORE;
                }
                ContractCreateActivity.this.status.setText(status.toString());
                contract.state = status;
            }
        });

        btn = (Button) findViewById(R.id.info_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditable = !isEditable;
                if(!isEditable){
                    updateModel();
                    AsyncTask<Contract, Void, Boolean> task = new AsyncTask<Contract, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Contract... params) {
                            ContractBL cbl = new ContractBL();
                            boolean rs = false;
                            if(isCreate){
                                rs = cbl.create(params[0]);
                            }else{
                                rs = cbl.modify(params[0]);
                            }
                            return rs;
                        }

                        @Override
                        protected void onPostExecute(Boolean success){
                            String mode = "";
                            if(isCreate){
                                mode = "创建";
                            }else{
                                mode = "更新";
                            }

                            String rs = "";
                            if(success){
                                rs = "成功";
                            }else{
                                rs = "失败";
                            }
                            updateEditable();

                            Toast.makeText(ContractCreateActivity.this, mode+rs, Toast.LENGTH_SHORT).show();;
                        }
                    };
                    task.execute(contract);
                }else{
                    updateEditable();
                }
            }
        });
    }

    private void fill(){
        title.setText(contract.name);
        number.setText(contract.number);
        totalAmount.setText(contract.total);
        payMethod.setText(contract.payMethod);
        startDate.setText(contract.getStartDate());
        endDate.setText(contract.getEndDate());
        signingDate.setText(contract.getSigningDate());
        clientContractor.setText(contract.clientContractor);
        ourContractor.setText(contract.ourContractor);
        remark.setText(contract.contractRemarks);
        status.setText(contract.state.toString());
//        number.setText(contract.);
    }

    private void updateEditable(){
        if(isEditable){
            btn.setText("保存");
        }else{
            btn.setText("编辑");
        }
        status.setClickable(isEditable);
        title.setFocusableInTouchMode(isEditable);
        number.setFocusableInTouchMode(isEditable);
        totalAmount.setFocusableInTouchMode(isEditable);
        payMethod.setFocusableInTouchMode(isEditable);
        startDate.setFocusableInTouchMode(isEditable);
        endDate.setFocusableInTouchMode(isEditable);
        signingDate.setFocusableInTouchMode(isEditable);
        clientContractor.setFocusableInTouchMode(isEditable);
        ourContractor.setFocusableInTouchMode(isEditable);
        remark.setFocusableInTouchMode(isEditable);


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        View view = getCurrentFocus();
        if(view != null){
            view.clearFocus();
        }
    }

    private void updateModel(){
        contract.name = title.getText().toString();
        contract.number = number.getText().toString();
        contract.total = totalAmount.getText().toString();
        contract.setStartDate(startDate.getText().toString());
        contract.setEndDate(endDate.getText().toString());
        contract.setSigningDate(signingDate.getText().toString());
        contract.clientContractor = clientContractor.getText().toString();
        contract.ourContractor = ourContractor.getText().toString();
        contract.payMethod = payMethod.getText().toString();
        contract.contractRemarks = remark.getText().toString();
    }

    private Contract getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return (Contract)bundle.getSerializable("Contract");
    }

}
