package bl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.Contract;
import entity.ContractState;

/**
 * Created by Ian on 2016/6/11.
 */
public class ContractBL {
    public ArrayList<Contract> getContractList(int page){
        ArrayList<Contract> contracts = new ArrayList<>();
        Map<String, String> attr = new HashMap<>();
//        attr.put("contractid", "");
        attr.put("search", "");
        attr.put("currentpage", page+"");
        Log.i("post", "contract");
//        ArrayList<JSONObject> jos = HttpProxy.post(attr, "contract_query_json");
        ArrayList<JSONObject> jos = HttpProxy.post(attr, "common_contract_json");
        for(JSONObject jo : jos){
//            Log.i("contract", jo.toString());
            contracts.add(fromJson(jo));
        }

        return contracts;
    }

    private Contract fromJson(JSONObject jo){
        Contract contract = new Contract();
        try {
            contract.name = jo.getString("contracttitle");
            contract.content = jo.getString("opportunitytitle");
            contract.total = jo.getString("totalamount");
            contract.customerID = jo.getString("customerid");
            int states = jo.getInt("contracttype");
            assert states<=4;
            if(states == 1){
                contract.state = ContractState.BEFORE;
            }else if(states == 2){
                contract.state = ContractState.PERFORMING;
            }else if(states == 3){
                contract.state = ContractState.SUCCEEFUL;
            }else if(states == 4){
                contract.state = ContractState.DEAD;
            }
            contract.type = jo.getString("contracttype");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contract;
    }
}
