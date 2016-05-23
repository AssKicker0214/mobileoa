package entity;

import java.util.ArrayList;

/**
 * Created by Ian on 2016/5/15.
 * ...
 */
public class Customer {
    private String name;
    private String rank;
    private ArrayList<Contact> contacts;
    private String createTime;

    public Customer(String name){
        this.name = name;
        contacts = new ArrayList<>();
    }

    public int addContact(Contact contact){
        contacts.add(contact);
        return contacts.size();
    }

    public int removeContact(Contact contact){
        contacts.remove(contact);
        return contacts.size();
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
