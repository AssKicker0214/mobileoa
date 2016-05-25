package presentation.universal;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Ian on 2016/5/24.
 */
public class ListPanel extends ScrollView {
    private LinearLayout itemContainer;
    public ListPanel(Context context){
        super(context);
        itemContainer = new LinearLayout(context);
        init();
    }

    private void init(){
        itemContainer.setBackgroundColor(Color.BLACK);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);

        itemContainer.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        itemContainer.setLayoutParams(layoutParams);
        this.addView(itemContainer);
    }

    public void addItem(View view){
        itemContainer.addView(view);
    }

}
