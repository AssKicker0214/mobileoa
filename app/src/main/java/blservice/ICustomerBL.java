package blservice;

import java.util.ArrayList;

import entity.Customer;

/**
 * Created by Ian on 2016/5/15.
 */
public interface ICustomerBL {
    public ArrayList<Customer> getCustomerList(int page);

    public ArrayList<Customer> getCustomerList(String userID, int page);

    public int getPageCount(String userID);

    public int getPageCount();

    public boolean create(Customer customer);

    public boolean modify(Customer customer);
}
