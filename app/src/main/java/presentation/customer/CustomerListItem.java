package presentation.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

import entity.Customer;
import presentation.universal.ListItem;
import presentation.universal.ListItemBtnFactory;

/**
 * Created by Ian on 2016/5/25.
 * 条目置顶功能？
 */
public class CustomerListItem extends ListItem {
    private Context context;
    private Customer customer;
    private ImageView stateImg;
//    public CustomerListItem(Context context){
//        super(context, 120);
//        this.context = context;
//
//        init();
//    }

    public CustomerListItem(Context context, Customer customer){
        super(context, 120);
        setCustomer(customer);
        this.context = context;
        init();
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public void init(){
        setState(0);
        String rankDes = "";
        setRankAndName(customer.rankDes, customer.name);
        setCreator();
//        this.addButton(new ListItemBtnFactory(context).getDelete(80, 80));

    }

    private void setState(int state){//0(未定义),1,2,3,4,5
        if(stateImg == null){
            stateImg = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            layoutParams.leftMargin = 10;
            stateImg.setLayoutParams(layoutParams);
            stateImg.setImageResource(R.mipmap.ic_not_interested_black_48dp);
            this.addContent(stateImg);
        }
    }

    private void setRankAndName(String rank, String name){
        LinearLayout rankNameLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.leftMargin = 30;
        rankNameLayout.setOrientation(LinearLayout.VERTICAL);
        rankNameLayout.setLayoutParams(layoutParams);
        this.addContent(rankNameLayout);

        TextView rankText = new TextView(context);
        rankText.setTextSize(15f);
        rankText.setText(rank);
        rankNameLayout.addView(rankText);

        TextView nameText = new TextView(context);
        nameText.setTextSize(25f);
        nameText.setText(name);
        rankNameLayout.addView(nameText);
    }

    private void setCreator(){
        TextView creatorText = new TextView(context);
        creatorText.setText(customer.creatorName);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,2);
        params.gravity = Gravity.CENTER_VERTICAL;
//        params.leftMargin = 50;
        creatorText.setLayoutParams(params);
        this.addContent(creatorText);
    }


    @Override
    public void onComplete(RippleView rippleView) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Customer", customer);
        Intent intent = new Intent(this.context, CustomerDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
