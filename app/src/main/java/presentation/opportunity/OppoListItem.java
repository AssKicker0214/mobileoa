package presentation.opportunity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

import entity.Opportunity;
import presentation.universal.ListItem;

/**
 * Created by Ian on 2016/6/11.
 */
public class OppoListItem extends ListItem {
    private Opportunity oppo;
    private Button stateView;
    private TextView oppoTitle;
    private TextView cusName;
    private TextView oppoTotal;
    private TextView oppoType;

    public OppoListItem(Context context, Opportunity oppo) {
        super(context, 100);
        this.oppo = oppo;

        init();;
        update();;
    }

    private void init(){
        LinearLayout layout = (LinearLayout)inflate(this.context, R.layout.oppo_item_layout, content.getMainContent());
        oppoTitle = (TextView)layout.findViewById(R.id.oppo_item_title);
        cusName = (TextView)layout.findViewById(R.id.oppo_item_customer);
        oppoTotal = (TextView)layout.findViewById(R.id.oppo_item_total);
        oppoType = (TextView)layout.findViewById(R.id.oppo_item_type);
        stateView = (Button)layout.findViewById(R.id.oppo_item_state);
    }

    private void update(){
        oppoTitle.setText(oppo.name);
        cusName.setText(oppo.cusName);
//        oppoType.setText(oppo.ty);
//        oppoTitle.setText(oppo.name);
        oppoTotal.setText(oppo.estimateAmount+"");
    }

    @Override
    public void onComplete(RippleView rippleView) {
        Intent intent = new Intent(context, OppoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Opportunity", oppo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void addButtons() {
    }
}
