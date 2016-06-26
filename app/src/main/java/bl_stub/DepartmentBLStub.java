package bl_stub;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bl.HttpProxy;
import entity.Department;

/**
 * Created by Ian on 2016/6/26.
 */
public class DepartmentBLStub {
    public ArrayList<Department> getList(){
        ArrayList<Department> dpts = new ArrayList<>();
        for(int i=0;i<4;i++){
            dpts.add(new Department((i+"2"), "department "+(i+2)));
        }
        return dpts;
    }
}
