package presentation.universal;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Ian on 2016/5/21.
 * 包括列表项的内容，以及隐藏的工具按钮
 */
public class ListItemContent extends LinearLayout {
    private int mainWidth;
    private ArrayList<View> btns = new ArrayList<>();
    private LinearLayout mainContent;
    public ListItemContent(Context context, int width){
        super(context);
        init(width);
        updateMainWidth(width);
    }

    private void init(int width){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
        this.setBackgroundColor(Color.RED);
        setMainContent();

        this.addContent(new TestView(this.getContext()));
    }

    public void updateMainWidth(int width){
        this.mainWidth = mainWidth;
    }

    public void addContent(View view){
        mainContent.addView(view);
    }

    private void setMainContent(){
        mainContent = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mainWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        mainContent.setLayoutParams(params);
        mainContent.setBackgroundColor(Color.LTGRAY);
        this.addView(mainContent);
    }

//

    public void addBtn(View btn){
        btns.add(btn);
        this.addView(btn);
    }
}
