package bl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.Department;

/**
 * Created by Ian on 2016/6/26.
 */
public class DepartmentBL {

    public ArrayList<Department> getList(){
        ArrayList<Department> dpts = new ArrayList<>();
        ArrayList<JSONObject> jos = new ArrayList<>();
        Map<String, String> att = new HashMap<>();
        att.put("currentpage", 0+"");
        jos = HttpProxy.post(att, "common_department_json");
        for(JSONObject jo : jos){
            dpts.add(fromJson(jo));
        }
        return dpts;
    }

    public Department fromJson(JSONObject jo){
        Department department = new Department();
        try {
            department.id = jo.getString("departmentid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            department.name = jo.getString("departmentname");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return department;
    }
}
