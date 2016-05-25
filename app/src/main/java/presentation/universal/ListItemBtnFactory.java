package presentation.universal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ian.mobileoa.R;

import entity.Contact;

/**
 * Created by Ian on 2016/5/24.
 */
public class ListItemBtnFactory {
    Context context;
    public ListItemBtnFactory(Context context){
        this.context = context;
    }
    private ImageView makeBtn(int width, int height){
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = width;
        params.height = height;
        imageView.setLayoutParams(params);
        return  imageView;
    }

    public ImageView getDelete(int width ,int height){
        ImageView btn = makeBtn(width, height);
        btn.setImageResource(R.mipmap.delete54);
        btn.setBackgroundColor(Color.RED);
        return btn;
    }
}
