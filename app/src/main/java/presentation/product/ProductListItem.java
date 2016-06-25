package presentation.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

import entity.Product;
import presentation.universal.ListItem;

/**
 * Created by Ian on 2016/6/25.
 */
public class ProductListItem extends ListItem {
    private Product product;
    TextView nameText;
    TextView snText;
    TextView classText;
    ImageView photo;

    public ProductListItem(Context context, Product product) {
        super(context, 100);
        this.product = product;

        init();
        bind();
        update();
    }

    private void init(){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = height;
        params.setMargins(0, 4, 0, 4);
        this.setLayoutParams(params);
        inflate(this.context, R.layout.product_item_layout, content.getMainContent());

    }

    private void bind(){
        nameText = (TextView) findViewById(R.id.product_name_text);
        snText = (TextView) findViewById(R.id.product_sn_text);
        classText = (TextView) findViewById(R.id.product_classfication_text);
        photo = (ImageView) findViewById(R.id.product_photo);
    }

    public void update(){
        nameText.setText(product.name);
        snText.setText(product.productsn);
        classText.setText(product.classification);
    }

    @Override
    protected void addButtons(){

    }
    @Override
    public void onComplete(RippleView rippleView) {
        Intent intent = new Intent(this.context, ProductCreateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Product", product);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
