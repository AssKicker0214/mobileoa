package presentation.index.block;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import presentation.index.JumpListener;

/**
 * Created by Ian on 2016/6/27.
 *
 */
public abstract class IndexBlock implements IUpdateReceiver{
    protected String name;

    protected Activity activity;
    protected ImageView icon;
    protected TextView myCount;
    protected TextView allCount;
    protected TextView header;
    protected TextView des;
    protected RippleView ripple;

    protected AlphaAnimation fadeAnimation;
    protected AlphaAnimation showAnimation;

    protected IndexBlockModel model;

//    protected GestureDetector gd;

    public IndexBlock(final Activity activity, String name){
        this.name = name;
        this.activity = activity;
        bind();
        setUpAnimation();
        updateStatus(BlockStatus.ICON);
    }

    public void setModel(IndexBlockModel model){
        this.model = model;
    }

    public void setOnRippleCompleteListener(RippleView.OnRippleCompleteListener l){
        ripple.setOnRippleCompleteListener(l);
    }

    public void setOnLongClickListener(View.OnLongClickListener l){
        ripple.setOnLongClickListener(l);
    }

    public void setOnClickListener(View.OnClickListener l){
        ripple.setOnClickListener(l);
    }

    public RippleView.OnRippleCompleteListener getJumpListener(){
        return ripple.getOnRippleCompleteListener();
    }

    protected void setUpAnimation(){
        fadeAnimation = new AlphaAnimation(1.0f, 0.0f);
        fadeAnimation.setFillAfter(true);
        fadeAnimation.setDuration(250);

        showAnimation = new AlphaAnimation(0.0f, 1.0f);
        showAnimation.setFillAfter(true);
        showAnimation.setDuration(250);
    }

    public void updateStatus(BlockStatus status){
        if(status == BlockStatus.ICON){
            if(model != null){
                header.setText(model.getMsg());
            }
            des.startAnimation(showAnimation);
            icon.startAnimation(showAnimation);
            allCount.startAnimation(fadeAnimation);
            myCount.startAnimation(fadeAnimation);
        }else{
            des.startAnimation(fadeAnimation);
            icon.startAnimation(fadeAnimation);
            allCount.startAnimation(showAnimation);
            myCount.startAnimation(showAnimation);
            header.setText(name);
            allCount.setText("/"+model.getRate()[1]);
            myCount.setText(model.getRate()[0]+"");
        }
    }


    public abstract void bind();


    @Override
    public void setHeader() {
        if (model.getStatus() == BlockStatus.DETAIL){
            //header不变化
        }else{
//            header.startAnimation(fadeAnimation);
            header.setText(model.getMsg());
//            header.startAnimation(showAnimation);
        }
    }

    @Override
    public void setRate(String my, String all) {
        myCount.setText(my);
        allCount.setText(all);
    }

}
