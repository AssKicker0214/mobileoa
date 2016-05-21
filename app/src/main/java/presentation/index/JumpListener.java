package presentation.index;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Ian on 2016/5/10.
 */
public class JumpListener implements View.OnClickListener {
    private Activity currentAct;
    private Class nextClass;
    public JumpListener(Activity current, Class next){
        currentAct = current;
        nextClass = next;
    }
    @Override
    public void onClick(View v) {
        currentAct.startActivity(new Intent(currentAct, nextClass));
        Toast.makeText(currentAct, "jump click", Toast.LENGTH_LONG).show();
    }
}
