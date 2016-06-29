package bl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blservice.IIndexService;
import entity.Product;

/**
 * Created by Ian on 2016/6/24.
 */
public class ProductBL extends UniversalBL implements IIndexService {
    public static int formerCache = 0;
    public static int laterCache = 0;
    public static boolean cacheChanged = false;

    public boolean create(Product product){
        return getResult(product.toMap(true), "product_create_json");
    }

    public boolean modify(Product product){
        return getResult(product.toMap(false), "product_modify_json");
    }


    public ArrayList<Product> getListByOppoID(String oppoID){
        Map<String, String> att = new HashMap<>();
        att.put("currentpage", "0");
        att.put("opportunityid", oppoID);
        ArrayList<JSONObject> jos = HttpProxy.post(att, "common_product_json");
        ArrayList<Product> products = new ArrayList<>();

        for(JSONObject jo : jos){
            products.add(fromJson(jo));
        }

        return products;
    }

    public ArrayList<Product> getList(int page){
        Map<String, String> att = new HashMap<>();
        att.put("currentpage", ""+page);
        ArrayList<JSONObject> jos = HttpProxy.post(att, "common_product_json");
        ArrayList<Product> products = new ArrayList<>();

        for(JSONObject jo : jos){
            products.add(fromJson(jo));
        }

        return products;
    }

    public int getPageCount(){
        Map<String, String> att = new HashMap<>();
        att.put("currentpage", "1");
        return HttpProxy.getPageCount(att, "common_product_json");

    }


    public Product fromJson(JSONObject jo){
        Product product = new Product();
        try {
            product.id = jo.getString("productid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            product.name = jo.getString("productname");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            product.classification = jo.getString("classification");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            product.remark = jo.getString("productremarks");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            product.saleunit = jo.getString("saleunit");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            product.productsn = jo.getString("productsn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            product.standardprice = jo.getDouble("standardprice");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            product.unitcost = jo.getDouble("unitcost");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            product.picture = jo.getString("picture");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            product.introduction = jo.getString("introduction");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    protected String getAPI() {
        return "common_product_json";
    }

    @Override
    protected void updateCache(int count) {
        if(count > laterCache){

            formerCache = laterCache;
            laterCache = count;
            if(formerCache == 0){
                //第一次不算
                cacheChanged = false;
            }else{

                cacheChanged = true;
            }
        }else{
            cacheChanged = false;
        }
    }

    @Override
    public int[] getCount() {
        ArrayList<Product> customers = getList(0);
        int[] rs = {0, 0};
        for(Product customer : customers){
            rs[1] ++;
            rs[0] ++;
        }
        return rs;
    }

    @Override
    public int getNew() {
        updateRecordCount();
        Log.e("message", "product: former=" + formerCache + ", later=" + laterCache);
        if(cacheChanged){
            return laterCache-formerCache;
        }
        return 0;
    }
}
