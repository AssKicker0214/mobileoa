package presentation.business;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ian.mobileoa.R;

import javax.inject.Inject;

import bl.BusinessBL;
import bl.Rounder;
import entity.OppoState;
import presentation.universal.RefreshableActivity;

public class BussinessIndexActivity extends RefreshableActivity {
    @Inject BusinessBL bbl;

    TextView nText;
    TextView rText;
    TextView sText;
    TextView cText;
    TextView wText;
    TextView lText;

    ProgressBar nBar;
    ProgressBar rBar;
    ProgressBar sBar;
    ProgressBar cBar;
    ProgressBar wBar;
    ProgressBar lBar;

    @Override
    protected void refresh() {
        update(OppoState.NEGOTIATION);
        update(OppoState.REQUIREMENT);
        update(OppoState.SCHEME);
        update(OppoState.CONTRACT);
        update(OppoState.WIN);
        update(OppoState.LOSE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness_index);

        activityComponent.inject(this);
        bind();
        update(OppoState.NEGOTIATION);
        update(OppoState.REQUIREMENT);
        update(OppoState.SCHEME);
        update(OppoState.CONTRACT);
        update(OppoState.WIN);
        update(OppoState.LOSE);
    }

    private void bind(){

        nText = (TextView) findViewById(R.id.negotiation_des_text);
        rText = (TextView) findViewById(R.id.requirement_des_text);
        sText = (TextView) findViewById(R.id.schema_des_text);
        cText = (TextView) findViewById(R.id.contract_des_text);
        wText = (TextView) findViewById(R.id.win_des_text);
        lText = (TextView) findViewById(R.id.lose_des_text);

        nBar = (ProgressBar) findViewById(R.id.negotiation_bar);
        rBar = (ProgressBar) findViewById(R.id.requirement_bar);
        sBar = (ProgressBar) findViewById(R.id.schema_bar);
        cBar = (ProgressBar) findViewById(R.id.contract_bar);
        wBar = (ProgressBar) findViewById(R.id.win_bar);
        lBar = (ProgressBar) findViewById(R.id.lose_bar);
    }

    public void update(final OppoState state){
        AsyncTask<OppoState, Void, Double[]> task = new AsyncTask<OppoState, Void, Double[]>() {
            @Override
            protected Double[] doInBackground(OppoState... params) {
//                Double[] rateAndCountAndTotal = new Double[3];
//                rateAndCountAndTotal[0] = bbl.getRateOfOppo(params[0]);
//                rateAndCountAndTotal[1] = 0.0+bbl.getCountOfOppo(params[0]);
//                rateAndCountAndTotal[2] = bbl.getTotalOfOppo(params[0]);
//                return rateAndCountAndTotal;
                return bbl.getRateCountTotal(params[0]);
            }

            @Override
            protected void onPostExecute(Double[] rateAndCountAndTotal){
                TextView text = null;
                ProgressBar bar = null;
                switch (state){
                    case NEGOTIATION:
                        text = nText;
                        bar = nBar;
                        break;
                    case REQUIREMENT:
                        text = rText;
                        bar = rBar;
                        break;
                    case SCHEME:
                        text = sText;
                        bar = sBar;
                        break;
                    case CONTRACT:
                        text = cText;
                        bar = cBar;
                        break;
                    case WIN:
                        text = wText;
                        bar = wBar;
                        break;
                    case LOSE:
                        text = lText;
                        bar = lBar;
                        break;
                }
                String unit = "元";
                double total = rateAndCountAndTotal[2];
                if(total >= 10000.0){
                    unit = "万元";
                    total = Rounder.round(total/10000, 2);
                }

                text.setText("跟单数 " + (int)(rateAndCountAndTotal[1]+0.0) + "，金额 " + total+unit);
//                Log.e("business", state.toString()+"跟单数 " + rateAndCountAndTotal[1] + "，金额 ??" + rateAndCountAndTotal[2]);
                bar.setProgress((int) (rateAndCountAndTotal[0]+0.0));
            }
        };
        task.execute(state);
    }
}
