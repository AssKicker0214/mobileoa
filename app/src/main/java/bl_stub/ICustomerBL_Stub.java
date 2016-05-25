package bl_stub;

import java.util.ArrayList;

import blservice.ICustomerBL;
import entity.Customer;

/**
 * Created by Ian on 2016/5/17.
 */
public class ICustomerBL_Stub implements ICustomerBL{
    public ICustomerBL_Stub(){

    }

    @Override
    public ArrayList<Customer> getCustomerList() {
        ArrayList<Customer> customers = new ArrayList<>();
        for(int i=0;i<1;i++){
            Customer cus = new Customer("客户"+i);
            customers.add(cus);
        }
        return customers;
    }

    @Override
    public ArrayList<Customer> getCustomerList(String userID) {
        return getCustomerList();
    }
}
