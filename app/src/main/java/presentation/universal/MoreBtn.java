package presentation.universal;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ian.mobileoa.R;

import org.w3c.dom.Text;

/**
 * Created by Ian on 2016/6/12.
 */
public class MoreBtn extends LinearLayout {
    private LinearLayout layout;
    private IListAppendable appendable;

    public MoreBtn(ViewGroup parent, IListAppendable appendable){
        super(parent.getContext());
        this.appendable = appendable;
        layout = (LinearLayout) inflate(parent.getContext(), R.layout.show_more_btn, parent);
    }

    public void show(int pageCount, final boolean isAll, final int currentPage){
        TextView title = (TextView)layout.findViewById(R.id.show_more_btn_title);
        TextView subtitle = (TextView)layout.findViewById(R.id.show_more_btn_subtitle);

        boolean isMore = (pageCount > currentPage);
        if(isMore){
            title.setText("点击加载更多");
            subtitle.setText("还有"+(pageCount-currentPage)+"页");
            layout.setClickable(true);
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    appendable.appendContent(isAll, currentPage+1);
                    Log.e("show more", "click");
                }
            });
        }else{
            title.setText("没有更多内容");
            subtitle.setText("共"+pageCount+"页");
            layout.setClickable(false);

        }
    }
}
