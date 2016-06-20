package bl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blservice.ICustomerBL;
import entity.Customer;

/**
 * Created by Ian on 2016/5/23.
 */
public class CustomerBL implements ICustomerBL{


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
        attri.put("search", "");
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
            customer.id = jo.getString("customerid");
            customer.creatorid = jo.getString("staffid");
            customer.creatorName = jo.getString("name");
            customer.address = jo.getString("address");
            customer.email = jo.getString("email");
            customer.website = jo.getString("website");
            customer.createDate = jo.getString("createdate");
            customer.profile = jo.getString("profile");
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
}
