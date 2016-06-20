package bl;

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
            oppo.name = jo.getString("opportunitytitle");
            oppo.id = jo.getString("opportunityid");
            oppo.cusid = jo.getString("customerid");
            oppo.total = jo.getString("totalamount");

            int stateInt = jo.getInt("opportunitystatus");
            switch (stateInt){
                case 1: oppo.states = OppoState.NEGOTIATION;break;
                case 2: oppo.states = OppoState.REQUIREMENT;break;
                case 3: oppo.states = OppoState.SCHEME;break;
                case 4: oppo.states = OppoState.CONTRACT;break;
                case 5: oppo.states = OppoState.WIN;break;
                case 6: oppo.states = OppoState.LOSE;break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oppo;
    }
}
