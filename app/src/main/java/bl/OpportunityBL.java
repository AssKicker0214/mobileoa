package bl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.OppoState;
import entity.Opportunity;

/**
 * Created by Ian on 2016/6/11.
 */
public class OpportunityBL {
    public ArrayList<Opportunity> getOppoList(int page){
        Map<String, String> attr = new HashMap<>();
        attr.put("currentpage", page+"");
        return getOppoListByMap(attr);
    }

    public Opportunity find(String id){
        Map<String, String> attr = new HashMap<>();
        attr.put("currentpage", 0+"");
        attr.put("opportunityid", id);
        ArrayList<Opportunity> rs = getOppoListByMap(attr);
        if(rs != null && rs.size() > 0){
            return rs.get(0);
        }else{
            return null;
        }
    }

    public boolean modify(Opportunity oppo){

        JSONObject jo = HttpProxy.getJsonByPost(oppo.toMap(false), "opportunity_modify_json");
        try {
            int resultCode = jo.getInt("resultcode");
            if(resultCode == 0){
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean create(Opportunity oppo){
        JSONObject jo = HttpProxy.getJsonByPost(oppo.toMap(true), "opportunity_create_json");
        try {
            int resultCode = jo.getInt("resultcode");
            if(resultCode == 0){
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Opportunity> getOppoListByCusId(int page, String cusid){
        Map<String, String> attr = new HashMap<>();
        attr.put("currentpage", page+"");
        attr.put("customerid", cusid);
        return getOppoListByMap(attr);
    }

    public ArrayList<Opportunity> getOppoListByUserId(int page, String uid){
        Map<String, String> attr = new HashMap<>();
        attr.put("currentpage", page+"");
        attr.put("customerid", uid);
        return getOppoListByMap(attr);
    }



    private ArrayList<Opportunity> getOppoListByMap(Map<String, String> attr){
        attr.put("search", "");
        ArrayList<JSONObject> jos = HttpProxy.post(attr, "common_opportunity_json");
        ArrayList<Opportunity> oppos = new ArrayList<>();
        for(JSONObject jo : jos){
            oppos.add(fromJson(jo));
        }
        return oppos;
    }

    private Opportunity fromJson(JSONObject jo){
        Opportunity oppo = new Opportunity();
        try {
            oppo.cusName = jo.getString("customername");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            oppo.name = jo.getString("opportunitytitle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            oppo.id = jo.getString("opportunityid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            oppo.cusid = jo.getString("customerid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            oppo.estimateAmount = jo.getDouble("estimatedamount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            oppo.staffID = jo.getString("staffid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            oppo.setExpectedDate(jo.getString("expecteddate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            oppo.setAcquisitionDate(jo.getString("acquisitiondate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            oppo.successRate = jo.getInt("successrate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int stateInt = 0;
        try {
            stateInt = jo.getInt("opportunitystatus");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (stateInt){
                case 1: oppo.states = OppoState.NEGOTIATION;break;
                case 2: oppo.states = OppoState.REQUIREMENT;break;
                case 3: oppo.states = OppoState.SCHEME;break;
                case 4: oppo.states = OppoState.CONTRACT;break;
                case 5: oppo.states = OppoState.WIN;break;
                case 6: oppo.states = OppoState.LOSE;break;
                default: oppo.states = OppoState.NEGOTIATION;
            }
        return oppo;
    }
}
