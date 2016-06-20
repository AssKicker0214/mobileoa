package presentation.universal;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

import org.w3c.dom.Attr;

import java.util.ArrayList;

/**
 * Created by Ian on 2016/5/21.
 * 包括列表项的内容，以及隐藏的工具按钮
 * **********
 * layoutParams似乎要在添加子元素之前完成添加，设置好之后再添加子元素会引发错误
 */
public class ListItemContent extends LinearLayout {
    private int width;
    private int height;
    private ArrayList<View> btns = new ArrayList<>();
    private LinearLayout mainContent;
    private LinearLayout.LayoutParams params;
    private RippleView rippleView;
    public ListItemContent(Context context, int width, int height){
        super(context);
        this.width = width;
        this.height = height;
        this.btns = btns;
        init();
//        Log.d("MD", "width=" + width);
//        for(View btn : btns){
//            this.addView(btn);
//        }
    }

    private void init(){
        params = new LinearLayout.LayoutParams(width+btns.size()*height, LinearLayout.LayoutParams.MATCH_PARENT);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.width = width;
        this.setMinimumWidth(width);
        this.setLayoutParams(params);
        this.setOrientation(HORIZONTAL);
        setRipple();
        setMainContent();

    }

    public LinearLayout getMainContent(){
        return mainContent;
    }

    public void setRipple(){
        rippleView = (RippleView) inflate(this.getContext(), R.layout.rippled_layout, null);
        rippleView.setRippleDuration(100);
        this.addView(rippleView);
    }


    public void addContent(View view){
        mainContent.addView(view);
    }

    private void setMainContent(){
        mainContent = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT);

        mainContent.setMinimumWidth(width);
        mainContent.setBackgroundColor(Color.WHITE);
        mainContent.setLayoutParams(params);
        rippleView.addView(mainContent);
    }

    public void updateWidth(int btnCounts){
        params = new LinearLayout.LayoutParams(width+80*btnCounts, LinearLayout.LayoutParams.MATCH_PARENT);

        this.setLayoutParams(params);
    }


    public void addBtn(View btn){
        btns.add(btn);
        updateWidth(btns.size());
        this.addView(btn);

    }

    public void setJumpListener(RippleView.OnRippleCompleteListener l){
        rippleView.setOnRippleCompleteListener(l);
    }
}
