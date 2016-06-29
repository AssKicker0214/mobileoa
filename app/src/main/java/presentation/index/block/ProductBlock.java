package presentation.index.block;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

/**
 * Created by Ian on 2016/6/28.
 */
public class ProductBlock extends IndexBlock {
    public ProductBlock(Activity activity) {
        super(activity, "产品");
    }

    @Override
    public void bind() {
        header = (TextView) activity.findViewById(R.id.product_block_header);
        allCount = (TextView) activity.findViewById(R.id.product_detail_all);
        myCount = (TextView) activity.findViewById(R.id.product_detail_my);
        des = (TextView) activity.findViewById(R.id.product_des);
        ripple = (RippleView) activity.findViewById(R.id.productRipple);
        icon = (ImageView) activity.findViewById(R.id.productImage);
    }
}
