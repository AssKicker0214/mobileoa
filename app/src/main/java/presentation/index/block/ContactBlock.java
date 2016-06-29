package presentation.index.block;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

/**
 * Created by Ian on 2016/6/28.
 */
public class ContactBlock extends IndexBlock{
    public ContactBlock(Activity activity) {
        super(activity, "联系人");
    }

    @Override
    public void bind() {
        header = (TextView) activity.findViewById(R.id.contact_header);
        allCount = (TextView) activity.findViewById(R.id.contact_detail_all);
        myCount = (TextView) activity.findViewById(R.id.contact_detail_my);
        des = (TextView) activity.findViewById(R.id.contact_des);
        ripple = (RippleView) activity.findViewById(R.id.contactRipple);
        icon = (ImageView) activity.findViewById(R.id.contactImg);
    }
}
