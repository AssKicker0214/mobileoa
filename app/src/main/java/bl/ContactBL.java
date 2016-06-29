package bl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blservice.IContactBL;
import blservice.IIndexService;
import entity.Contact;

/**
 * Created by Ian on 2016/6/6.
 */
public class ContactBL extends UniversalBL implements IContactBL, IIndexService{
    public static int formerCache = 0;
    public static int laterCache = 0;
    public static boolean cacheChanged = false;

    private int pageCount = -1;
    @Override
    public ArrayList<Contact> getContactList(int page) {
        Map<String, String> attri = new HashMap<String, String>();
        attri.put("search", "");
        attri.put("currentpage", ""+page);
        ArrayList<JSONObject> jos = HttpProxy.post(attri, "common_contacts_json");

        ArrayList<Contact> contacts = new ArrayList<>();

        for(JSONObject jo : jos){
            contacts.add(fromJson(jo));
        }

        updatePageCount(attri, "common_contacts_json");
        return contacts;
    }

    public boolean modifyContact(Contact contact){
        JSONObject jo = HttpProxy.getJsonByPost(contact.toMap(true), "contact_modify_json");
        boolean rs = false;
        try {
            int resultCode = jo.getInt("resultcode");
            if(resultCode == 0){
                rs = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("contact", "modify=>"+jo);
        return rs;
    }

    public boolean createContact(Contact contact){
        JSONObject jo = HttpProxy.getJsonByPost(contact.toMap(false), "contact_create_json");
        boolean rs = false;
        try {
            int resultCode = jo.getInt("resultcode");
            if(resultCode == 0){
                rs = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("contact", "create=>"+jo);
        return rs;
    }

    @Override
    public ArrayList<Contact> getContactList(String userID, int page) {
        Map<String, String> attri = new HashMap<String, String>();
        attri.put("staffid", userID);
        attri.put("currentpage", ""+page);
        ArrayList<JSONObject> jos = HttpProxy.post(attri, "common_contacts_json");

        ArrayList<Contact> contacts = new ArrayList<>();

        for(JSONObject jo : jos){
            contacts.add(fromJson(jo));
        }

        updatePageCount(attri, "common_contacts_json");
        return contacts;
    }

    @Override
    public ArrayList<Contact> getContactListByCustomer(String cusID, int page) {

        Map<String, String> attri = new HashMap<String, String>();
        attri.put("customerid", cusID);
        Log.i("post","customerid="+cusID);
        attri.put("search", "");
        attri.put("currentpage", ""+page);
        ArrayList<JSONObject> jos = HttpProxy.post(attri, "common_contacts_json");

        ArrayList<Contact> contacts = new ArrayList<>();

        for(JSONObject jo : jos){
//            Log.i("post", "jo:"+jo.toString());
            contacts.add(fromJson(jo));
        }

        return contacts;
    }

    private Contact fromJson(JSONObject jo){
        Contact contact = null;
        try {
            contact = new Contact(jo.getString("contactsid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contact.name = jo.getString("contactsname");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contact.departmentName = jo.getString("contactsdeptname");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contact.qq = jo.getString("contactsqq");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contact.wechat = jo.getString("contactswechat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contact.wangwang = jo.getString("contactswangwang");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contact.customerName = jo.getString("customername");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contact.customerID = jo.getString("customerid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contact.mobilePhone = jo.getString("contactsmobile");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            contact.email = jo.getString("contactsemail");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return  contact;
    }

    private void updatePageCount(Map<String, String> att, String api){
        pageCount = HttpProxy.getPageCount(att, api);
    }

    public int getPageCount(){
        return pageCount;
    }

    @Override
    public int[] getCount() {
        ArrayList<Contact> customers = getContactList(0);
        int[] rs = {0, 0};
        for(Contact customer : customers){
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
        return "common_contacts_json";
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
