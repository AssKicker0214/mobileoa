package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ian on 2016/6/24.
 */
public class Product implements Serializable{
    public String name = "";
    public String id = "";
    public String productsn = "";
    public double standardprice = 0.0;
    public String saleunit = "";
    public double unitcost = 0.0;
    public String classification = "";
    public String picture = "";
    public String introduction = "";
    public String remark = "";

    public Map<String, String> toMap(boolean isCreate){
        Map<String, String> att = new HashMap<>();
        if(isCreate){

        }else{
            att.put("productid", id);
        }
        att.put("productname", name);
        att.put("productsn", productsn);
        att.put("standardprice", standardprice+"");
        att.put("saleunit", saleunit);
        att.put("unitcost", unitcost+"");
        att.put("classification", classification);
        att.put("picture", picture);
        att.put("introduction", introduction);
        att.put("picture", productsn);
        att.put("productremarks", remark);
        return att;
    }
}
