package presentation.index.block;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

/**
 * Created by Ian on 2016/6/28.
 */
public class ContractBlock extends IndexBlock{
    public ContractBlock(Activity activity) {
        super(activity, "合同");
    }

    @Override
    public void bind() {
        header = (TextView) activity.findViewById(R.id.contract_header);
        allCount = (TextView) activity.findViewById(R.id.contract_detail_all);
        myCount = (TextView) activity.findViewById(R.id.contract_detail_my);
        des = (TextView) activity.findViewById(R.id.contract_des);
        ripple = (RippleView) activity.findViewById(R.id.contractRipple);
        icon = (ImageView) activity.findViewById(R.id.contractImg);
    }
}
