package bl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.User;

/**
 * Created by Ian on 2016/6/9.
 */
public class UserBL {
    public boolean login(String id, String password){
        boolean rs = false;

        Map<String, String> attr = new HashMap<>();
        attr.put("staffid", id);
        attr.put("currentpage", "0");

        ArrayList<JSONObject> jos = HttpProxy.post(attr, "common_staff_json");
        if(jos.size() == 1){
            rs = true;
            CurrentLogin.setUser(fromJson(jos.get(0)));
        }else if(jos.size()>1){
            Log.e("user", "多个staff对象");
        }else{
            Log.e("user", "没查到staff");
        }

        return rs;
    }

    public boolean register(User staff){
        boolean rs = false;
        Map<String, String> attri = new HashMap<>();
        attri.put("name", staff.name);
        attri.put("departmentid", staff.dptid);
        if(HttpProxy.post(attri, "staff_create_json") != null){
            rs = true;
        };
//        matchUser("");
        return rs;
    }

    //暂时不可用，currentpage设置0则返回所有信息
    public String matchUser(String staffName){
        Map<String, String> attr = new HashMap<>();
        attr.put("search","");
//        attr.put("staffid", "101");
        attr.put("name", staffName);
        attr.put("currentpage", "0");
//        int pageCount = HttpProxy.getPageCount(attr, "common_staff_json");
        ArrayList<JSONObject> jos = HttpProxy.post(attr, "common_staff_json");
        for(JSONObject jo : jos){
            User tmp = fromJson(jo);
            if(tmp.name.equals(staffName)){
                return tmp.userid;
            }
        }

        return null;
//        Log.e("user", "pageCount:" + pageCount);

//        for(int i=0;i<pageCount;i++){
//            Log.e("user", "page:"+(i+1));
//            attr.remove("currentpage");
//            attr.put("currentpage", ""+ (i+1));
//            ArrayList<JSONObject> jos = HttpProxy.post(attr, "common_staff_json");
//            for(JSONObject jo : jos){
//                try {
//                    Log.e("user", "staff_name:"+jo.getString("name")+"\t\tstaff_id"+jo.getString("staffid"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    public int getRecordCount(){
        Map<String, String> att = new HashMap<>();
        att.put("currentpage", "1");
        JSONObject jo = HttpProxy.getJsonByPost(att, "common_staff_json");
        int recordCount = 0;
        try {
            recordCount = jo.getInt("recordcount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  recordCount;
    }

    public User fromJson(JSONObject jo){
        User user = new User();
        try {
            user.name = jo.getString("name");
            user.userid = jo.getString("staffid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
