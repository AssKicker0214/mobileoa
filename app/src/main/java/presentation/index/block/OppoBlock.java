package presentation.index.block;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

/**
 * Created by Ian on 2016/6/28.
 */
public class OppoBlock extends IndexBlock{
    public OppoBlock(Activity activity) {
        super(activity, "商机");
    }

    @Override
    public void bind() {
        header = (TextView) activity.findViewById(R.id.oppo_header);
        allCount = (TextView) activity.findViewById(R.id.oppo_detail_all);
        myCount = (TextView) activity.findViewById(R.id.oppo_detail_my);
        des = (TextView) activity.findViewById(R.id.oppo_des);
        ripple = (RippleView) activity.findViewById(R.id.oppoRipple);
        icon = (ImageView) activity.findViewById(R.id.oppoImg);
    }
}
