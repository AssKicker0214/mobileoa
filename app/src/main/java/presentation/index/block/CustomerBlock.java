package presentation.index.block;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

/**
 * Created by Ian on 2016/6/27.
 */
public class CustomerBlock extends IndexBlock {
    public CustomerBlock(Activity activity) {
        super(activity, "客户");
    }

    @Override
    public void bind() {
        header = (TextView) activity.findViewById(R.id.cus_header);
        allCount = (TextView) activity.findViewById(R.id.cus_detail_all);
        myCount = (TextView) activity.findViewById(R.id.cus_detail_my);
        des = (TextView) activity.findViewById(R.id.cus_des);
        ripple = (RippleView) activity.findViewById(R.id.customerRipple);
        icon = (ImageView) activity.findViewById(R.id.customerImg);
    }

}
