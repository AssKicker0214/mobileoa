package presentation.index;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.andexert.library.RippleView;

/**
 * Created by Ian on 2016/5/10.
 */
public class JumpListener implements View.OnClickListener, RippleView.OnRippleCompleteListener {
    private Activity currentAct;
    private Class nextClass;
    public JumpListener(Activity current, Class next){
        currentAct = current;
        nextClass = next;
    }
    @Override
    public void onClick(View v) {
//        Toast.makeText(currentAct, "jump click", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete(RippleView rippleView) {
        currentAct.startActivity(new Intent(currentAct, nextClass));
    }
}
