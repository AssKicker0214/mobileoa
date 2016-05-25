package presentation.customer;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ian.mobileoa.R;

import presentation.universal.ListItem;

/**
 * Created by Ian on 2016/5/25.
 * 条目置顶功能？
 */
public class CustomerListItem extends ListItem {
    private Context context;

    private ImageView stateImg;
    public CustomerListItem(Context context){
        super(context, 120);
        this.context = context;

        init();
    }

    public void init(){
//        Log.i("item","init");
        setState(0);
    }

    private void setState(int state){//0(未定义),1,2,3,4,5
        if(stateImg == null){
            stateImg = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            stateImg.setLayoutParams(layoutParams);
            stateImg.setImageResource(R.mipmap.ic_not_interested_black_48dp);
            this.addContent(stateImg);
        }
    }


}
