package presentation.customer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;

import bl.CustomerBL;
import entity.Customer;

public class CustomerCreateActivity extends AppCompatActivity {
    private boolean isCreate = false;
    private boolean isEditable = false;

    private Customer customer;

    private EditText nameText;
    private TextView timeText;
    private TextView ratingText;
    private int rank;
    private Button btn;
    private EditText emailText;
    private EditText addressText;
    private EditText webText;
    private EditText profileText;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bind();

        customer = getData();
        if(customer.id == null || customer.id.equals("")){
            isCreate = true;
            isEditable = true;
            updateEditable();
        }else{
            updateEditable();
        }

        fill();
    }

    private void updateModel(){
        customer.name = nameText.getText().toString();
        customer.createDate = timeText.getText().toString();
        if(rank>0 && rank<4){
            customer.rank = rank;
        }
        customer.email = emailText.getText().toString();
        customer.address = addressText.getText().toString();
        customer.website = webText.getText().toString();
        customer.profile = profileText.getText().toString();

    }

    private void updateEditable() {
        nameText.setFocusableInTouchMode(isEditable);
        emailText.setFocusableInTouchMode(isEditable);
        addressText.setFocusableInTouchMode(isEditable);
        webText.setFocusableInTouchMode(isEditable);
        profileText.setFocusableInTouchMode(isEditable);
        ratingText.setClickable(isEditable);
        if(isEditable){
            btn.setText("保存");
        }else{
            btn.setText("编辑");

            profileText.clearFocus();
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        View f = getCurrentFocus();
        if(f != null){
            f.clearFocus();
        }

    }

    private void fill(){
        nameText.setText(customer.name);
        if(isCreate){

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            timeText.setText(formatter.format(curDate));
        }else{
            timeText.setText(customer.createDate);

        }

        String rate = "";
        if(customer.rank == 3){
            rate = "★☆☆";
        }else if(customer.rank == 2){
            rate = "★★☆";
        }else if(customer.rank == 1){
            rate = "★★★";
        }else{
            rate = "☆☆☆";
        }
        ratingText.setText(rate);
        emailText.setText(customer.email);
        addressText.setText(customer.address);
        webText.setText(customer.website);
        profileText.setText(customer.profile);
    }

    private void bind() {
        nameText = (EditText) findViewById(R.id.customer_create_cusname);
        timeText = (TextView) findViewById(R.id.customer_create_time);
        ratingText = (TextView) findViewById(R.id.customer_create_rating);
        emailText = (EditText) findViewById(R.id.customer_create_email);
        addressText = (EditText) findViewById(R.id.customer_create_address);
        webText = (EditText) findViewById(R.id.customer_create_web);
        profileText = (EditText) findViewById(R.id.customer_create_profile);
        btn = (Button) findViewById(R.id.customer_create_btn);



        ratingText.setOnClickListener(new View.OnClickListener() {
            int rate = 0;

            @Override
            public void onClick(View v) {
                rate++;
                if (rate == 4) {
                    rate = 1;
                }

                if (rate == 1) {
                    ratingText.setText("★☆☆");
                    rank = 3;
                } else if (rate == 2) {
                    ratingText.setText("★★☆");
                    rank = 2;

                } else if (rate == 3) {
                    ratingText.setText("★★★");
                    rank = 1;
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditable) {
                    //网络
                    updateModel();
                    AsyncTask<Customer, Void, Boolean> task = new AsyncTask<Customer, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Customer... params) {
                            CustomerBL cbl = new CustomerBL();
                            if(isCreate){
                                return cbl.create(customer);
                            }else{
                                return cbl.modify(customer);
                            }
                        }

                        protected void onPostExecute(Boolean rs){
                            String toast = "";
                            if(rs){
                                toast = "更新成功";
                            }else{
                                toast = "更新失败";
                            }
                            Toast.makeText(CustomerCreateActivity.this,toast,Toast.LENGTH_SHORT).show();

                        }
                    };
                    task.execute(customer);

                }else{

                }

                isEditable = !isEditable;
                updateEditable();
            }
        });
    }

    public void updateStatus() {
        if (isEditable) {
            btn.setText("保存");

        } else {
            btn.setText("编辑");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private Customer getData(){

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return  (Customer)bundle.getSerializable("Customer");
    }
}
