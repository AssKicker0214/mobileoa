package blservice;

import java.util.ArrayList;

import entity.Contact;

/**
 * Created by Ian on 2016/6/6.
 */
public interface IContactBL {

    public ArrayList<Contact> getContactList(int page);

    public ArrayList<Contact> getContactList(String userID, int page);

    public ArrayList<Contact> getContactListByCustomer(String cusID, int page);
}
