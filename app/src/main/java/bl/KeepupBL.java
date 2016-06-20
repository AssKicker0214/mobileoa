package bl;

import android.support.annotation.Keep;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blservice.IKeepupBLService;
import entity.Customer;
import entity.Keepup;
import entity.SourceType;

/**
 * Created by Ian on 2016/6/8.
 *
 */
public class KeepupBL implements IKeepupBLService {
    @Override
    public void getKeepup(String userid, int page) {

    }

    public ArrayList<Keepup> getKeepupByCusID(String cusid){
        ArrayList<Keepup> keepups = new ArrayList<>();

        Map<String, String> attri = new HashMap<String, String>();
        attri.put("currentpage", "0");
        attri.put("sourcetype", "1");
        attri.put("sourceid", cusid);
        ArrayList<JSONObject> jos = HttpProxy.post(attri, "common_followup_json");
        for(JSONObject jo : jos){
//            Log.i("keepup", jo.toString());
            keepups.add(fromJson(jo));
        }

        return keepups;
    }

    @Override
    public ArrayList<Keepup> getKeepup(int page) {

        ArrayList<Keepup> keepups = new ArrayList<>();

        Map<String, String> attri = new HashMap<String, String>();
        attri.put("currentpage", "0");
        attri.put("search", "");
        ArrayList<JSONObject> jos = HttpProxy.post(attri, "common_followup_json");
        for(JSONObject jo : jos){
//            Log.i("keepup", jo.toString());
            keepups.add(fromJson(jo));
        }

        return keepups;
    }

    public static ArrayList<Keepup> filterBySourceType(ArrayList<Keepup> src, SourceType type){
        ArrayList<Keepup> filtered = new ArrayList<>();
        for(int i=0;i<src.size();i++){
            if(src.get(i).sourceType.equals(type)){
                filtered.add(src.get(i));
            }
        }
        return filtered;
    }

    private Keepup fromJson(JSONObject jo){
        Keepup keepup = new Keepup();
        try {
            keepup.content = jo.getString("content");
            keepup.setCreateTime(jo.getString("createtime"));
            keepup.sourceType = SourceType.fromInt(jo.getInt("sourcetype"));
            keepup.keeperName = jo.getString("name");
            keepup.sourceID = jo.getString("sourceid");
            keepup.keepupID = jo.getString("followupid");

            if(keepup.sourceType.equals("1")){
                CustomerBL cbl = new CustomerBL();
                Customer customer = cbl.find(keepup.sourceID);
                keepup.sourceName = customer.name;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return keepup;
    }
}
