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
        return null;
    }

    @Override
    public ArrayList<Customer> getCustomerList(String userID) {
        return null;
    }
}
