package presentation.index;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ian.mobileoa.R;

import bl.UserBL;
import entity.User;

public class RegisterActivity extends AppCompatActivity {
    UserBL userBL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userBL = new UserBL();
        setContentView(R.layout.activity_register);

        final EditText nameText = (EditText)findViewById(R.id.userNameText);
        Button btn = (Button)findViewById(R.id.RegisterBtn);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AsyncTask<String[], Void, Boolean> task = new AsyncTask<String[], Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(String[]... params) {
                        return userBL.register(params[0]);
                    }
                };
                String[] attri = new String[1];
                attri[0] = nameText.getText().toString();
                task.execute(attri);
            }
        });
    }

//    private void set
}
