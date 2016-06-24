package presentation.universal;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Ian on 2016/6/24.
 */
public abstract class RefreshableActivity extends Activity {
    protected boolean showRefreshText = false;
    protected abstract void refresh();

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
