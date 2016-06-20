package presentation.opportunity;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Ian on 2016/6/20.
 */
public class StepController {
//    TextView text;
    SeekBar bar;
    StepModel model;
    public StepController(TextView text, SeekBar bar){
//        this.text = text;
        this.bar = bar;
        this.model = new StepModel(text);
    }

    public void setModel(int i){
        model.update(i);
    }
}
