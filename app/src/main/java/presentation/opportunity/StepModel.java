package presentation.opportunity;

import android.widget.TextView;

import entity.OppoState;

/**
 * Created by Ian on 2016/6/20.
 */
public class StepModel {
    private OppoState step;
    private TextView text;

    public StepModel(TextView text){
        this.text = text;
    }

    public void update(int i){
        step = OppoState.convert(i);
        text.setText(step.toString());
    }
}
