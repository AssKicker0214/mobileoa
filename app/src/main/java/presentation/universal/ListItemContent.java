package presentation.universal;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Ian on 2016/5/21.
 * 包括列表项的内容，以及隐藏的工具按钮
 * **********
 */
public class ListItemContent extends LinearLayout {
    private int width;
    private int height;
    private ArrayList<View> btns = new ArrayList<>();
    private LinearLayout mainContent;
    private LinearLayout.LayoutParams params;
    public ListItemContent(Context context, int width, int height){
        super(context);
        this.width = width;
        this.height = height;
        init();
        Log.d("MD", "width=" + width);
    }

    private void init(){
        params = new LinearLayout.LayoutParams(width+80, ViewGroup.LayoutParams.MATCH_PARENT);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.width = width;
        this.setMinimumWidth(width+80);
        this.setLayoutParams(params);
        this.setOrientation(HORIZONTAL);
        setMainContent();

//        this.addContent(new TestView(this.getContext()));
    }


    public void addContent(View view){
        mainContent.addView(view);
    }

    private void setMainContent(){
        mainContent = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.width = width;
//        params.height = height;
        mainContent.setMinimumWidth(width);
        Log.d("MD", "main content width="+width);
        mainContent.setBackgroundColor(Color.GREEN);
        mainContent.setLayoutParams(params);
        this.addView(mainContent);
    }

    public void updateWidth(int width){
        this.params.width = width;
        this.setLayoutParams(params);
    }


    public void addBtn(View btn){
        btns.add(btn);
        updateWidth(width + height * btns.size());
        Log.d("MD", "content width:"+width+"+" + height * btns.size());
        this.addView(btn);

    }
}
