package presentation.index;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.ian.mobileoa.R;

import java.util.ArrayList;

import bl.CurrentLogin;
import bl.DepartmentBL;
import bl.UserBL;
import bl_stub.DepartmentBLStub;
import entity.Department;
import entity.User;

public class RegisterActivity extends AppCompatActivity {
    UserBL userBL;
    private User staff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userBL = new UserBL();
        setContentView(R.layout.activity_register);

        staff = new User();
        bind();

    }

    private void bind(){
        final EditText nameText = (EditText)findViewById(R.id.user_name_edit);
        Button btn = (Button)findViewById(R.id.register_btn);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staff.name = nameText.getText().toString();
                AsyncTask<User, Void, String> task = new AsyncTask<User, Void, String>() {
                    @Override
                    protected String doInBackground(User... params) {
                        boolean registered = false;
                        if(params[0].name.equals("") || params[0].dptid.equals("")){
                            Log.e("staff", "name/dptid empty");
                            return "";
                        }
                        registered = userBL.register(params[0]);
                        if (!registered) {
                            return "";
                        }
                        //注册成功

                        String id = userBL.matchUser(params[0].name);
                        return id;

                    }

                    @Override
                    protected void onPostExecute(String id) {
                        String prompt = "创建失败";
                        if (!id.equals("")) {
                            prompt = "创建成功,id:"+id;
                        }
                        Toast.makeText(RegisterActivity.this, prompt, Toast.LENGTH_SHORT).show();


                    }
                };
                task.execute(staff);
            }
        });

        setUpSpinner();

    }

    private void setUpSpinner(){

        final Spinner spinner = (Spinner) findViewById(R.id.dpt_spinner);
        AsyncTask<Void, Void, ArrayList<Department>> task = new AsyncTask<Void, Void, ArrayList<Department>>() {
            @Override
            protected ArrayList<Department> doInBackground(Void... params) {
                ArrayList<Department> dpts = new DepartmentBL().getList();
                return dpts;
            }

            @Override
            protected void onPostExecute(ArrayList<Department> dpts){
                final ArrayList<CharSequence> names = new ArrayList<>();
                final ArrayList<CharSequence> ids = new ArrayList<>();
                for(Department dpt : dpts){
                    names.add(dpt.name);
                    ids.add(dpt.id);
                }
                ArrayAdapter adapter = new ArrayAdapter<CharSequence>(RegisterActivity.this, android.R.layout.simple_spinner_item, names);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                assert spinner != null;
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String dptid = ids.get(position).toString();
                        staff.dptid = dptid;
                        staff.dptname = names.get(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        };
        task.execute();
    }

//    private void set
}
