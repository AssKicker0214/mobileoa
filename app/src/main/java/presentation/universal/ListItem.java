package presentation.universal;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
//import android.support.annotation.DimenRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ian on 2016/5/17.
 */
public class ListItem extends HorizontalScrollView {
    //第一次动态测量的值
    private int width;
    private int height;
    private boolean inited = false;

    private LinearLayout content;
    private Context context;
    public ListItem(Context context) {
        super(context);
        this.context = context;

        init();
    }

    private void init(){
        this.setBackgroundColor(Color.BLUE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 1, 0, 1);

//        this.setMinimumHeight(80);
        this.setHorizontalScrollBarEnabled(false);
        this.setLayoutParams(params);
//        setSurface();
//        setContent();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
        if(!inited){
            inited = true;
            setContent();
        }else{
            //update
        }
        Log.d("MD","on measure("+getMeasuredWidth()+","+getMeasuredHeight()+")");
//        Toast.makeText(context,"on measure("+getMeasuredWidth() + ","+getMeasuredHeight()+")", Toast.LENGTH_LONG).show();
    }

    public Point measure(){
//        Toast.makeText(this.getContext(),dimension.x+","+dimension.y,Toast.LENGTH_LONG).show();

        Log.d("MD","(widht+height)("+width+","+height+")");
        return null;
    }

    public void setContent(){
        measure();
        ListItemContent content = new ListItemContent(context, width);
        this.addView(content);
//        Toast.makeText(this.getContext(),"width:"+measure().x, Toast.LENGTH_LONG).show();
    }


}

class TestView extends TextView{

    public TestView(Context context) {
        super(context);
        this.setText("abcdefg,hijklmn,opq,rst,uvw,xyz!abcdefg,hijklmn,opq,rst,uvw,xyz!");
    }


}

class FocusListener implements View.OnFocusChangeListener{

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
}