package bl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blservice.IContactBL;
import entity.Contact;

/**
 * Created by Ian on 2016/6/6.
 */
public class ContactBL implements IContactBL {
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

}
