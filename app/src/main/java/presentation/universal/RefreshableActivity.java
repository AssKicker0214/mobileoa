package presentation.universal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import bl.BLProvider;
import presentation.product.ActivityComponent;
import presentation.product.DaggerActivityComponent;

/**
 * Created by Ian on 2016/6/24.
 */
public abstract class RefreshableActivity extends AppCompatActivity {
    protected boolean showRefreshText = false;
    protected abstract void refresh();

    protected ActivityComponent activityComponent;

    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        activityComponent = DaggerActivityComponent.builder().bLProvider(new BLProvider()).build();

    }
    public void onResume(){
        super.onResume();
        refresh();
        if(showRefreshText){
            Toast.makeText(this, "已刷新", Toast.LENGTH_SHORT).show();

        }
    }

    public void onPause(){
        super.onPause();
        showRefreshText = true;
    }
}
