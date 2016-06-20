package presentation.contact;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ian.mobileoa.R;

import bl.ContactBL;
import entity.Contact;

public class ContactDetailActivity extends AppCompatActivity {
    private EditText contactNameText;
    private EditText contactCusText;
    private EditText contactPhoneText;
    private EditText contactEmailText;
    private EditText contactQQText;
    private EditText contactWechatText;
    private EditText contactWangwangText;

    private boolean isEditable = false;
    private Contact contact;
    private ContactBL contactBL;

    private boolean isCreate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        contact = getData();
        contactBL = new ContactBL();

        setToolBar();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        bind();
        updateEditable();
        //创建模式
        if(contact.getID().equals("")){
            isEditable = true;
            updateEditable();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            isCreate = true;
            contact = fillCreateAttri(contact);
            Log.i("contact", "创建模式"+contact.getID());
        }else{
            Log.i("contact", "编辑模式"+contact.getID());
        }

        fill();
    }

    private void setToolBar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle("联系人");
        setSupportActionBar(toolbar);

        //设置在setSupportActionBar之后才有效果
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                isEditable = !isEditable;
//                Log.i("contact", "editable="+isEditable);
                updateEditable();
                if (isEditable) {
                    item.setTitle("保存");
                } else {
                    updateModel();
                    AsyncTask<Contact, Void, Boolean> task = new AsyncTask<Contact, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Contact... params) {
                            if(isCreate){
                                return contactBL.createContact(contact);
                            }else{
                                return contactBL.modifyContact(params[0]);
                            }

                        }

                        protected void onPostExecute(Boolean succeeded){
                            String warning = "";
                            if(succeeded){
                                warning = "已保存";
                            }else{
                                warning = "数据上传失败";
                            }
                            Toast.makeText(ContactDetailActivity.this, warning, Toast.LENGTH_SHORT).show();

                            item.setTitle("编辑");
                        }
                    };
                    task.execute(contact);
                }
                return false;
            }
        });
    }

    private void updateModel(){
        contact.name = contactNameText.getText().toString();
        contact.mobilePhone = contactPhoneText.getText().toString();
        contact.wechat = contactWechatText.getText().toString();
        contact.qq = contactQQText.getText().toString();
        contact.email = contactEmailText.getText().toString();
        contact.wangwang = contactWangwangText.getText().toString();
    }

    private void bind(){
        contactNameText = (EditText)findViewById(R.id.contactNameText);
        contactCusText = (EditText)findViewById(R.id.contactCustomerText);
        contactPhoneText = (EditText)findViewById(R.id.contactPhoneText);
        contactEmailText = (EditText)findViewById(R.id.contactEmailText);
        contactQQText = (EditText)findViewById(R.id.contactQQText);
        contactWechatText = (EditText)findViewById(R.id.contactWechatText);
        contactWangwangText = (EditText)findViewById(R.id.contactWangwangText);
    }

    private void fill(){
        contactNameText.setText(contact.name);
        contactCusText.setText(contact.customerName);
        contactPhoneText.setText(contact.getMobilePhone());
        contactEmailText.setText(contact.email);
        contactQQText.setText(contact.qq);
        contactWechatText.setText(contact.wechat);
        contactWangwangText.setText(contact.wangwang);
    }

    public void updateEditable(){
//        contactNameText.setEnabled(editable);
//        Log.i("contact", "isEditable="+isEditable);
        if(!isEditable){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        contactCusText.setEnabled(false);

        contactNameText.setFocusableInTouchMode(isEditable);
        contactNameText.clearFocus();

        contactPhoneText.setFocusableInTouchMode(isEditable);
        contactPhoneText.clearFocus();

        contactEmailText.setFocusableInTouchMode(isEditable);
        contactEmailText.clearFocus();

        contactQQText.setFocusableInTouchMode(isEditable);
        contactQQText.clearFocus();

        contactWechatText.setFocusableInTouchMode(isEditable);
        contactWechatText.clearFocus();

        contactWangwangText.setFocusableInTouchMode(isEditable);
        contactWangwangText.clearFocus();

    }


    private Contact getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return (Contact)bundle.getSerializable("Contact");
    }

    private Contact fillCreateAttri(Contact c){

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        c.customerName = (String)bundle.getSerializable("customername");
        c.customerID = (String)bundle.getSerializable("customerid");
        c.staffID = (String)bundle.getSerializable("staffid");

        return c;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(isCreate){
            getMenuInflater().inflate(R.menu.save, menu);
        }else{

            getMenuInflater().inflate(R.menu.edit, menu);
        }
        return true;
    }

}
