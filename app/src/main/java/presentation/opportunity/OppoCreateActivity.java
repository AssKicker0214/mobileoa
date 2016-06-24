package presentation.opportunity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ian.mobileoa.R;

import java.text.SimpleDateFormat;

import bl.OpportunityBL;
import entity.OppoState;
import entity.Opportunity;

public class OppoCreateActivity extends AppCompatActivity {
    private Opportunity oppo;
    EditText title;
    EditText expectedDate;
    EditText estimatedAmount;
    EditText successRate;
    EditText remark;
    TextView ranking;
    SeekBar bar;
    TextView step;
    Button btn;

    TextView cusnameText;
    TextView cusidText;
    TextView staffnameText;
    TextView staffidText;

    private boolean isEditable = false;
    private boolean isCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oppo_create);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        oppo = getData();
        if(oppo.id.equals("")){
            isEditable = true;
            isCreate = true;
        }
        bind();
        fill();
        updateEditable();
    }

    private void bind(){
        staffidText = (TextView) findViewById(R.id.oppo_create_staffid);
        staffnameText = (TextView) findViewById(R.id.oppo_create_staffname);
        cusidText = (TextView) findViewById(R.id.oppo_create_cusid);
        cusnameText = (TextView) findViewById(R.id.oppo_create_cusname);
        title = (EditText) findViewById(R.id.oppo_create_title);
        expectedDate = (EditText) findViewById(R.id.expected_date);
        successRate = (EditText) findViewById(R.id.success_rate);
        estimatedAmount = (EditText) findViewById(R.id.estimated_amount);
        remark = (EditText) findViewById(R.id.oppo_remarks);
        ranking = (TextView) findViewById(R.id.ranking);
        ranking.setClickable(true);
        ranking.setOnClickListener(new View.OnClickListener() {
            int rate = 0;

            @Override
            public void onClick(View v) {
                rate++;
                if (rate == 4) {
                    rate = 1;
                }

                if (rate == 1) {
                    ranking.setText("★☆☆");
                    oppo.rank = 1;
                } else if (rate == 2) {
                    ranking.setText("★★☆");
                    oppo.rank = 2;

                } else if (rate == 3) {
                    ranking.setText("★★★");
                    oppo.rank = 3;
                }
            }
        });
        bar = (SeekBar) findViewById(R.id.seekBar);
        step = (TextView) findViewById(R.id.oppo_step_text);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                oppo.states = OppoState.convert(progress + 1);
                step.setText(oppo.states.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn = (Button) findViewById(R.id.edit_btn);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditable = !isEditable;

                if (!isEditable) {
                    updateModel();
                    //bl
                    AsyncTask<Opportunity, Void, Boolean> task = new AsyncTask<Opportunity, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Opportunity... params) {
                            Opportunity oppo = params[0];
                            OpportunityBL obl = new OpportunityBL();
                            if(isCreate){
                                return obl.create(oppo);
                            }else{
                                return obl.modify(oppo);
                            }
                        }

                        protected void onPostExecute(Boolean success) {
                            String mode = "";
                            if (isCreate) {
                                mode = "创建";
                            } else {
                                mode = "更新";
                            }
                            String rs = "";
                            if (success) {
                                rs = "成功";
                            } else {
                                rs = "失败";
                            }

                            updateEditable();
                            Toast.makeText(OppoCreateActivity.this, mode + rs, Toast.LENGTH_SHORT).show();

                        }
                    };
                    task.execute(oppo);
                } else {
                    updateEditable();
                }
                fill();
            }
        });
    }

    public void setRaking(int rate){
        assert rate<=3&rate>=1;
        if(rate == 1){
            ranking.setText("★☆☆");
        }else if(rate == 2){
            ranking.setText("★★☆");
        }else if(rate == 3){
            ranking.setText("★★★");
        }
    }

    private void fill(){
        staffidText.setText(oppo.staffID);
        staffnameText.setText(oppo.staffName);
        cusidText.setText(oppo.cusid);
        cusnameText.setText(oppo.cusName);
        title.setText(oppo.name);
        remark.setText(oppo.remark);
        successRate.setText(oppo.successRate+"");
        estimatedAmount.setText(oppo.estimateAmount+"");
        if(oppo.expectedDate != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            expectedDate.setText(format.format(oppo.expectedDate));
        }
        setRaking(oppo.rank);
        bar.setProgress(oppo.states.getFigure()-1);
    }

    private void updateEditable(){
        if(isEditable){
            btn.setText("保存");
        }else{
            btn.setText("编辑");
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        title.setFocusableInTouchMode(isEditable);
        expectedDate.setFocusableInTouchMode(isEditable);
        estimatedAmount.setFocusableInTouchMode(isEditable);

        ranking.setClickable(isEditable);
        bar.setEnabled(isEditable);
        remark.setFocusableInTouchMode(isEditable);
        successRate.setFocusableInTouchMode(isEditable);

        if(getCurrentFocus() != null)
            getCurrentFocus().clearFocus();
    }

    public void updateModel(){
        oppo.name = title.getText().toString();
        oppo.estimateAmount = Double.parseDouble(estimatedAmount.getText().toString());
        oppo.successRate = Integer.parseInt(successRate.getText().toString());
        oppo.setExpectedDate(expectedDate.getText().toString());
        oppo.remark = remark.getText().toString();
    }

    private Opportunity getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return (Opportunity)bundle.getSerializable("Opportunity");
    }

}
