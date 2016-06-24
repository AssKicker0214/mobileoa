package presentation.contract;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

import entity.Contract;
import entity.ContractState;
import presentation.universal.ListItem;

/**
 * Created by Ian on 2016/6/11.
 */
public class ContractListItem extends ListItem {
    private Contract contract;

    private Button stateView;
    private TextView contractTitle;
    private TextView contractContent;
    private TextView contractTotal;
    private TextView contractType;
    public ContractListItem(Context context, Contract contract) {
        super(context, 100);
        this.contract = contract;

        init();;
        update();;
    }

    private void init(){
        LinearLayout layout = (LinearLayout)inflate(this.context, R.layout.contract_item_layout, content.getMainContent());
        contractTitle = (TextView)layout.findViewById(R.id.contract_item_title);
        contractContent = (TextView)layout.findViewById(R.id.contract_item_oppo);
        contractTotal = (TextView)layout.findViewById(R.id.contract_item_total);
        contractType = (TextView)layout.findViewById(R.id.contract_item_type);
        stateView = (Button)layout.findViewById(R.id.contract_item_state);
    }

    private void update(){
        contractTitle.setText(contract.name);
        contractContent.setText(contract.oppoTitle);
        contractTotal.setText(contract.total);
        contractType.setText(contract.type);

        if(contract.state == ContractState.DEAD){
            stateView.setText("意外终止");
            stateView.setBackgroundColor(Color.rgb(192,57,43));
            stateView.setTextColor(Color.WHITE);
        }else if(contract.state == ContractState.SUCCEEFUL){
            stateView.setText("成功结束");
            stateView.setBackgroundColor(Color.rgb(39,174,96));
            stateView.setTextColor(Color.WHITE);
        }else if(contract.state == ContractState.BEFORE){
            stateView.setText("未开始");
            stateView.setBackgroundColor(Color.rgb(127,140,141));
            stateView.setTextColor(Color.WHITE);
        }else if(contract.state == ContractState.PERFORMING){
            stateView.setText("执行中");
            stateView.setBackgroundColor(Color.rgb(41,128,185));
            stateView.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onComplete(RippleView rippleView) {
        Intent intent = new Intent(context, ContractDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Contract", contract);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void addButtons() {
    }
}
