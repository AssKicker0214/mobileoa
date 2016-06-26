package com.example.ian.user;

import bl.UserBL;
import presentation.index.RegisterActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.ian.mobileoa.R;
import com.robotium.solo.Solo;
import com.robotium.*;
import com.robotium.solo.*;

/**
 * Created by Ian on 2016/6/25.
 */
public class UserTest extends ActivityInstrumentationTestCase2<RegisterActivity>{
    Solo solo;
    UserBL ubl;
    public UserTest() {
        super(RegisterActivity.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        ubl = new UserBL();
    }

    //测试是否注册成功

    public void testRegister(){
        int before = ubl.getRecordCount();
        EditText name = (EditText) solo.getView(R.id.user_name_edit);
        solo.clearEditText(name);
        String nameText = "robotium1";
        solo.enterText(name, nameText);
        solo.clickOnButton("注册");

        String id = ubl.matchUser(nameText);
        assert id!=null;
        int after = ubl.getRecordCount();
        assertEquals(before+1, after);
    }

}
