package bl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blservice.IIndexService;
import entity.Contract;
import entity.ContractState;

/**
 * Created by Ian on 2016/6/11.
 */
public class ContractBL extends UniversalBL implements IIndexService{
    public static int formerCache = 0;
    public static int laterCache = 0;
    public static boolean cacheChanged = false;

    private int pageCount;
    public ArrayList<Contract> getContractList(int page){
        ArrayList<Contract> contracts = new ArrayList<>();
        Map<String, String> attr = new HashMap<>();
        attr.put("currentpage", page + "");
        ArrayList<JSONObject> jos = HttpProxy.post(attr, "common_contract_json");
        for(JSONObject jo : jos){
            contracts.add(fromJson(jo));
        }
        pageCount = HttpProxy.getPageCount(attr, "common_contract_json");

        return contracts;
    }

    public Contract find(String id){
        ArrayList<Contract> contracts = new ArrayList<>();
        Map<String, String> attr = new HashMap<>();
        attr.put("currentpage", 0+"");
        attr.put("contractid", id);
        ArrayList<JSONObject> jos = HttpProxy.post(attr, "common_contract_json");
        if(jos != null && jos.size()>0){
            return fromJson(jos.get(0));
        }else{
            return null;
        }
    }

    public ArrayList<Contract> getContractListByOppoID(String id, int page){
        ArrayList<Contract> contracts = new ArrayList<>();
        Map<String, String> attr = new HashMap<>();
        attr.put("currentpage", page + "");
        attr.put("opportunityid", id);
        ArrayList<JSONObject> jos = HttpProxy.post(attr, "common_contract_json");
        for(JSONObject jo : jos){
            contracts.add(fromJson(jo));
        }
        pageCount = HttpProxy.getPageCount(attr, "common_contract_json");

        return contracts;
    }

    public ArrayList<Contract> getContractListByStaffID(String id, int page){
        ArrayList<Contract> contracts = new ArrayList<>();
        Map<String, String> attr = new HashMap<>();
        attr.put("currentpage", page + "");
        attr.put("staffid", id);
        ArrayList<JSONObject> jos = HttpProxy.post(attr, "common_contract_json");
        for(JSONObject jo : jos){
            contracts.add(fromJson(jo));
        }
        pageCount = HttpProxy.getPageCount(attr, "common_contract_json");

        return contracts;
    }

    public boolean modify(Contract contract){
        JSONObject jo = HttpProxy.getJsonByPost(contract.toMap(false), "contract_modify_json");
        int rscode = -1;
        try {
            rscode = jo.getInt("resultcode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(rscode == 0){
            return true;
        }else {
            return false;
        }

    }

    public boolean create(Contract contract){
        JSONObject jo = HttpProxy.getJsonByPost(contract.toMap(true), "contract_create_json");
        int rscode = -1;
        try {
            rscode = jo.getInt("resultcode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(rscode == 0){
            return true;
        }else {
            return false;
        }

    }

    public ArrayList<Contract> getContractListByCustomerID(int page, String cusid){
        ArrayList<Contract> contracts = new ArrayList<>();
        Map<String, String> attr = new HashMap<>();
        attr.put("currentpage", page+"");
        attr.put("customerid", cusid);
        ArrayList<JSONObject> jos = HttpProxy.post(attr, "common_contract_json");
        for(JSONObject jo : jos){
            contracts.add(fromJson(jo));
        }

        return contracts;
    }

    private Contract fromJson(JSONObject jo){
        Contract contract = new Contract();
        try {
            contract.name = jo.getString("contracttitle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.oppoTitle = jo.getString("opportunitytitle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.total = jo.getString("totalamount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.customerID = jo.getString("customerid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int states = 0;
        try {
            states = jo.getInt("contractstatus");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        try {
            contract.id = jo.getString("contractid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.type = jo.getString("contracttype");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.setStartDate(jo.getString("startdate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.setEndDate(jo.getString("enddate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.setSigningDate(jo.getString("signingdate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.oppoID = jo.getString("opportunityid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.contractRemarks = jo.getString("contractremarks");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.clientContractor = jo.getString("clientcontractor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.ourContractor = jo.getString("ourcontractor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.payMethod = jo.getString("paymethod");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.number = jo.getString("contractnumber");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contract.staffID = jo.getString("staffid");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return contract;
    }

    public int getPageCount(){
        return pageCount;
    }

    @Override
    public int[] getCount() {
        ArrayList<Contract> customers = getContractList(0);
        int[] rs = {0, 0};
        for(Contract customer : customers){
            if(customer.staffID.equals(CurrentLogin.id)){
                rs[0] ++;
            }
            rs[1] ++;
        }
        return rs;
    }

    @Override
    public int getNew() {
//        ArrayList<Customer> customers = getCustomerList(0);
//        int newCount = 0;
//        for(Customer customer : customers){
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            try {
//                Date createDate = format.parse(customer.createDate);
//                Log.e("date", "last="+format.format(date1)+",create="+format.format(createDate)+",next="+format.format(date2));
//                if(createDate.after(date2) && createDate.before(date1)){
//                    newCount ++;
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        return newCount;
        updateRecordCount();
//        Log.e("customer", "former="+formerCache+", later="+laterCache);
        if(cacheChanged){
            return laterCache-formerCache;
        }
        return 0;
    }

    @Override
    protected String getAPI() {
        return "common_contract_json";
    }

    protected void updateCache(int count){
        if(count > laterCache){
            formerCache = laterCache;
            laterCache = count;
            if(formerCache == 0){
                //第一次不算
                cacheChanged = false;
            }else{

                cacheChanged = true;
            }
        }else{
            cacheChanged = false;
        }
    }
}
