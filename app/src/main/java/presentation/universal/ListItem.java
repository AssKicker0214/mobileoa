package presentation.universal;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
//import android.support.annotation.DimenRes;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.util.ArrayList;

/**
 * Created by Ian on 2016/5/17.
 * 动态测量调整布局似乎是很难办的。原因是添加子组件总是在动态测量之前，可行的办法是1.利用事件触发（没有实验过）、
 * 或者2.在第一次动态测量之后完成全部内容的加载（但之后就不能再添加组件了）3.或者直接获取屏幕宽高进行调整【采用】
 *
 * 待完成列表：
 *  1. 点击主体，按钮自动隐藏
 *  2. evalation设置
 *
 */
public abstract class ListItem extends HorizontalScrollView implements ViewTreeObserver.OnScrollChangedListener, RippleView.OnRippleCompleteListener {
    private static int index = 0;
//    public static int measuredWidth = 0;

//    private int width;
    protected int height = 80;
//    private boolean inited = false;

    protected ListItemContent content;
    protected Context context;
    public ListItem(Context context) {
        super(context);
        this.context = context;
        init();
    }

//    public boolean isInited(){
//        return inited;
//    }

    public ListItem(Context context, int height) {
        super(context);
        this.context = context;
        this.height = height;
        init();
    }

    private void init(){
        if((index++)%2 == 0){
            this.setBackgroundColor(Color.RED);
        }else{
            this.setBackgroundColor(Color.GRAY);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = height;
        params.setMargins(0, 1, 0, 1);

        this.setHorizontalScrollBarEnabled(false);
        this.setLayoutParams(params);
        this.setShadow();
        //添加滚动监听
        this.getViewTreeObserver().addOnScrollChangedListener(this);


        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        setContent(context, width, height);

        //添加点击监听
        content.setJumpListener(this);
//        content.addBtn(new ListItemBtnFactory(context).getDelete(80, 80));

    }

    public void setShadow(){

    }

    protected void setContent(Context context, int width, int height){
        content = new ListItemContent(context, width, height);
        addButtons();
        if(this.getChildCount() != 0){
            this.removeAllViews();
        }
        this.addView(content);
    }

    protected ArrayList<View> getButtons(){
        ArrayList<View> btns = new ArrayList<>();
//        btns.add(new ListItemBtnFactory(this.context).getDelete(80, 80));
        return btns;
    }

    //默认加入删除按钮,重写方法来获得其他效果
    protected void addButtons(){
        content.addBtn(new ListItemBtnFactory(context).getDelete(80, 80));
        content.addBtn(new ListItemBtnFactory(context).getDelete(80, 80));
    }

    public void addContent(View view){
        if(content != null){
            content.addContent(view);
        }else{
            Log.i("MD", "error, content=null");
        }
    }



    @Override
    public void onScrollChanged() {
        int x = this.getScrollX();
//        Log.i("MD", "scroll-x="+x);

    }
}
