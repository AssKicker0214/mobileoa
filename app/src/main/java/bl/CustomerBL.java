package bl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import blservice.ICustomerBL;
import blservice.IIndexService;
import entity.Customer;

/**
 * Created by Ian on 2016/5/23.
 */
public class CustomerBL extends UniversalBL implements ICustomerBL, IIndexService{
    public static int formerCache = 0;
    public static int laterCache = 0;
    public static boolean cacheChanged = false;

    @Override
    public ArrayList<Customer> getCustomerList(int page) {
        Map<String, String> attri = new HashMap<String, String>();
        attri.put("currentpage", page+"");
        attri.put("search", "");
        ArrayList<JSONObject> jos = HttpProxy.post(attri, "common_customer_json");

        ArrayList<Customer> customers = new ArrayList<>();
        for(JSONObject jo : jos){
            customers.add(fromJson(jo));
        }

        return customers;
    }

    public Customer find(String id){
        Map<String, String> attri = new HashMap<String, String>();
        attri.put("currentpage", "0");
        attri.put("customerid", id);
        ArrayList<JSONObject> jos = HttpProxy.post(attri, "common_customer_json");
        if (jos.size() == 0){
            return null;
        }

        Customer customer = null;
        for(JSONObject jo : jos){
            customer = fromJson(jo);
        }

        return customer;
    }

    public int getPageCount(){

        Map<String, String> attri = new HashMap<String, String>();
        attri.put("currentpage", "1");
        int pageCount = HttpProxy.getPageCount(attri, "common_customer_json");

        return pageCount;
    }

    @Override
    public boolean create(Customer customer) {
        Map<String, String> att = customer.toMap(false);
        att.put("staffid", CurrentLogin.id);
//        att.put("currentpage", "0");
        JSONObject jo = HttpProxy.getJsonByPost(att, "customer_create_json");
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

    @Override
    public boolean modify(Customer customer) {
        Map<String, String> att = customer.toMap(true);
//        att.put("currentpage", "0");
        JSONObject jo = HttpProxy.getJsonByPost(att, "customer_modify_json");
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

    public int getPageCount(String userid){

        Map<String, String> attri = new HashMap<String, String>();
        attri.put("currentpage", "1");
        attri.put("staffid", userid);
        attri.put("search", "");
        int pageCount = HttpProxy.getPageCount(attri, "common_customer_json");

        return pageCount;
    }

    @Override
    public ArrayList<Customer> getCustomerList(String userID, int page) {
        Map<String, String> attri = new HashMap<String, String>();
        attri.put("currentpage", page+"");
        attri.put("search", "");
        attri.put("staffid", userID);
        ArrayList<JSONObject> jos = HttpProxy.post(attri, "common_customer_json");

        ArrayList<Customer> customers = new ArrayList<>();
        for(JSONObject jo : jos){
            customers.add(fromJson(jo));
        }

        return customers;
    }

    private Customer fromJson(JSONObject jo){
        Customer customer = null;
        try {
            customer = new Customer(jo.getString("customername"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            customer.id = jo.getString("customerid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            customer.creatorid = jo.getString("staffid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            customer.creatorName = jo.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            customer.address = jo.getString("address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            customer.email = jo.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            customer.website = jo.getString("website");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            customer.createDate = jo.getString("createdate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            customer.profile = jo.getString("profile");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            switch (customer.rank = jo.getInt("customertype")){
                case 1 : customer.rankDes = "重要客户"; break;
                case 2 : customer.rankDes = "一般客户"; break;
                case 3 : customer.rankDes = "低价值客户"; break;
                default: customer.rankDes = "未知";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public int[] getCount() {
        ArrayList<Customer> customers = getCustomerList(0);
        int[] rs = {0, 0};
        for(Customer customer : customers){
            if(customer.creatorid.equals(CurrentLogin.id)){
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
        return "common_customer_json";
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
