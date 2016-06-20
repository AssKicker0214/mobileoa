package presentation.keepup;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

import entity.Keepup;
import entity.SourceType;
import presentation.universal.ListItem;

/**
 * Created by Ian on 2016/6/9.
 */
public class KeepupListItem extends ListItem {
    private Keepup keepup;
    private TextView dateText;
    private TextView keeperText;
    private TextView keepupIDText;
    private TextView keepupContent;
    private ImageView keepupType;

    public KeepupListItem(Context context, Keepup keepup) {
        super(context, 150);
        this.keepup = keepup;
        init();
        bind();
        update();
    }

    private void init(){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = height;
        params.setMargins(0, 4, 0, 4);
        this.setLayoutParams(params);
        inflate(this.context, R.layout.keepup_item_layout, content.getMainContent());

    }

    private void bind(){
        keepupIDText = (TextView) findViewById(R.id.keepup_targt);
        dateText = (TextView)findViewById(R.id.keepupMonthDateText);
        keeperText = (TextView)findViewById(R.id.keepup_keeper);
        keepupIDText = (TextView)findViewById(R.id.keepup_targt);
        keepupContent = (TextView)findViewById(R.id.keepup_content);
        keepupType = (ImageView)findViewById(R.id.keepup_type);
    }

    public void update(){
        keepupIDText.setText("跟进编号："+keepup.keepupID);
        dateText.setText(keepup.getMonthDate());
        keeperText.setText(keepup.keeperName);
        keepupContent.setText(keepup.content);
//        keepupIDText
        if(keepup.sourceType.equals(SourceType.CUSTOMER_KEEPUP)){
            keepupType.setImageResource(R.mipmap.keepup_customer_x54);
        }else if(keepup.sourceType.equals(SourceType.OPPORTUNITY_KEEPUP)){
            keepupType.setImageResource(R.mipmap.keepup_oppo_x54);
        }else if(keepup.sourceType.equals(SourceType.CONTRACT_KEEPUP)){
            keepupType.setImageResource(R.mipmap.keepup_contract_x54);
        }else{
            keepupType.setBackgroundColor(Color.DKGRAY);
        }
    }

    @Override
    public void onComplete(RippleView rippleView) {

    }
}
