package presentation.keepup;

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

import com.example.ian.mobileoa.R;

import bl.KeepupBL;
import entity.Keepup;

public class KeepupCreateActivity extends AppCompatActivity {
    TextView sourceType;
    TextView sourceID;
    TextView createTime;
    TextView creatorID;
    EditText content;
    EditText remark;
    Button btn;

    private boolean isEditable = false;
    private boolean isCreate = false;

    private Keepup keepup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keepup_create);

        bind();
        keepup = getData();
        if(keepup.keepupID.equals("")){
            isCreate = true;
            isEditable = true;
            btn.setText("保存");
        }

        fill();
        updateEditable();
    }

    private void bind(){
        sourceType = (TextView) findViewById(R.id.source_type_text);
        sourceID = (TextView) findViewById(R.id.source_id_text);
        createTime = (TextView) findViewById(R.id.create_time_text);
        creatorID = (TextView) findViewById(R.id.creator_id_text);

        content = (EditText) findViewById(R.id.content_edit);
        remark = (EditText) findViewById(R.id.followup_remarks_edit);

        btn = (Button) findViewById(R.id.edit_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditable = !isEditable;
                if (!isEditable) {

                    updateModel();
                    AsyncTask<Keepup, Void, Boolean> task = new AsyncTask<Keepup, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Keepup... params) {
                            KeepupBL keepupBL = new KeepupBL();
                            boolean rs = false;
                            if (isCreate) {
                                rs = keepupBL.create(keepup);
                            } else {
                                rs = keepupBL.modify(keepup);
                            }
                            return rs;
                        }

                        protected void onPostExecute(Boolean rs) {
                            updateEditable();
                            btn.setText("编辑");
                        }
                    };

                    task.execute(keepup);
                } else {
                    updateEditable();
                    btn.setText("保存");
                }
            }


        });
    }

    private void fill(){
        if(!isCreate){

        }
        sourceType.setText(keepup.sourceType.toString());
        sourceID.setText(keepup.sourceID);
        createTime.setText(keepup.getCreateTime());
        creatorID.setText(keepup.creatorID);
        content.setText(keepup.content);
        remark.setText(keepup.followupremarks);
    }

    private void updateEditable(){
        content.clearFocus();
        remark.clearFocus();
        content.setFocusableInTouchMode(isEditable);
        remark.setFocusableInTouchMode(isEditable);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void updateModel(){
        keepup.content = content.getText().toString();
        keepup.followupremarks = remark.getText().toString();
    }

    private Keepup getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return (Keepup)bundle.getSerializable("Keepup");    }

}
