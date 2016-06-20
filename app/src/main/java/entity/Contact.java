package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ian on 2016/5/15.
 *
 */
public class Contact implements Serializable {
    private String id = "";
    public String name = "";
    public String fixedlinePhone = "";
    public String mobilePhone = "";
    public String qq = "";
    public String wechat = "";
    public String email = "";
    public String wangwang = "";
    public String departmentName = "";
    public String customerName="";
    public String customerID = "";
    public String staffID = "";
    private Customer releventCus;

    public Contact(String id){
        this.id = id;
    }

    public Contact(){

    }

    public Map<String, String> toMap(boolean containsID){
        Map<String, String> att = new HashMap<>();
        if(containsID){     //修改用
            att.put("contactsid", id);
        }else{              //创建用
            att.put("customername", customerName);
            att.put("customerid", customerID);
        }
        att.put("contactsdeptname", departmentName);
        att.put("contactsname", name);
        att.put("contactswangwang", wangwang);
        att.put("contactsemail", email);
        att.put("contactsmobile", mobilePhone);
        att.put("contactstelephone", fixedlinePhone);
        att.put("contactsqq", qq);
        att.put("contactswechat", wechat);
        return att;
    }

    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setFixedlinePhone(String numbers){
        this.fixedlinePhone = numbers;
    }

    public String getFixedlinePhone(){
        return fixedlinePhone;
    }

    public void setMobilePhone(String numbers){
        this.mobilePhone = numbers;
    }

    public String getMobilePhone(){
        return mobilePhone;
    }

    public void setReleventCus(Customer cus){
        this.releventCus = cus;
    }

    public Customer getReleventCus(){
        return releventCus;
    }
}
