package blservice;

import java.lang.reflect.Array;
import java.util.ArrayList;

import entity.Keepup;

/**
 * Created by Ian on 2016/6/8.
 */
public interface IKeepupBLService {
    public void getKeepup(String userid, int page);

    public ArrayList<Keepup> getKeepup(int page);

    public ArrayList<Keepup> getKeepupByCusID(String cusid);
}
