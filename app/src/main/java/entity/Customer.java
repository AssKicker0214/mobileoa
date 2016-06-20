package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ian on 2016/5/15.
 * ...
 */
public class Customer implements Serializable{
    public String name = "default name";
    public int rank;
    public String rankDes;
    public ArrayList<Contact> contacts;
    public String createDate = "";
    public String id;
    public String creatorid = "";
    public String creatorName = "";
    public String email = "";
    public String website = "";
    public String address = "";
    public String staffid = "";
    public String profile = "没有描述";

    public Customer(String name){
        this.name = name;
        contacts = new ArrayList<>();
    }

    public Customer(){

    }

    public Map<String, String> toMap(boolean forEdit){
        Map<String, String> att = new HashMap<>();
        if(forEdit){
            att.put("customerid", id);
        }else{
        }

        att.put("customername", name);
        att.put("customertype", rank+"");
        att.put("website", website);
        att.put("email", email);
        att.put("website", website);
        att.put("address", address);
        att.put("profile", profile);

        return att;
    }

    public int addContact(Contact contact){
        contacts.add(contact);
        return contacts.size();
    }

    public int removeContact(Contact contact){
        contacts.remove(contact);
        return contacts.size();
    }


}
