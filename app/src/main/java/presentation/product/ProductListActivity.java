package presentation.product;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ian.mobileoa.R;

import java.util.ArrayList;


import javax.inject.Inject;

import bl.BLProvider;
import bl.ProductBL;
import entity.Product;
import presentation.universal.IListAppendable;
import presentation.universal.MoreBtn;
import presentation.universal.MyApplication;
import presentation.universal.RefreshableActivity;

public class ProductListActivity extends RefreshableActivity implements IListAppendable{

    private LinearLayout listLayout;
    @Inject
    ProductBL pbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ObjectGraph og = ObjectGraph.create(new BLProvider());
//        ((MyApplication)getApplication()).inject(this);
        activityComponent.inject(this);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product();
                Intent intent = new Intent(ProductListActivity.this, ProductCreateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Product", product);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        listLayout = (LinearLayout) findViewById(R.id.product_list_content);
    }


    @Override
    protected void refresh() {
        listLayout.removeAllViews();
        appendContent(true, 1);
    }

    @Override
    public void appendContent(final boolean isAll, final int page) {
        AsyncTask<Integer, Product, Integer> task = new AsyncTask<Integer, Product, Integer>() {
            LinearLayout layout = (LinearLayout) findViewById(R.id.product_list_content);
            @Override
            protected Integer doInBackground(Integer... params) {
                int pageCount = pbl.getPageCount();
                ArrayList<Product> products = pbl.getList(params[0]);
                for(Product product : products){
                    publishProgress(product);

                }

                return pageCount;
            }

            @Override
            protected void onPreExecute(){
                if(layout.getChildCount() > 0){
                    layout.removeViewAt(layout.getChildCount() - 1);
                }
            }

            @Override
            protected void onProgressUpdate(Product... product) {


                ProductListItem item = new ProductListItem(ProductListActivity.this, product[0]);
                layout.addView(item);

            }

            @Override
            protected void onPostExecute(Integer pageCount){
                MoreBtn moreBtn = null;
                moreBtn = new MoreBtn(layout, ProductListActivity.this);

                moreBtn.show(pageCount, isAll, page);
            }

        };
        task.execute(page);
    }

    @Override
    public int getPageCount(boolean isAll) {
        return 0;
    }
}
