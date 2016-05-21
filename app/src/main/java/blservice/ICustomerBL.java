package blservice;

import java.util.ArrayList;

import entity.Customer;

/**
 * Created by Ian on 2016/5/15.
 */
public interface ICustomerBL {
    public ArrayList<Customer> getCustomerList();

    public ArrayList<Customer> getCustomerList(String userID);
}
