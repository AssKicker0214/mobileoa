package bl;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ian on 2016/6/24.
 */
public abstract class UniversalBL {

    public JSONObject getJsonByPost(Map<String, String> att, String api){
        JSONObject jo = HttpProxy.getJsonByPost(att, api);
        return jo;
    }

    public boolean getResult(Map<String, String> att, String api){
        boolean rs = false;

        try {
            int resultCode = getJsonByPost(att, api).getInt("resultcode");
            if(resultCode == 0){
                rs = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public int updateRecordCount(){
        Map<String, String> att = new HashMap<>();
        att.put("currentpage", "1");
        JSONObject jo = HttpProxy.getJsonByPost(att, getAPI());
        int recordCount = 0;

        try {
            recordCount = jo.getInt("recordcount");
            updateCache(recordCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("universal",recordCount+"");
        return recordCount;
    }

    protected abstract String getAPI();

    protected abstract void updateCache(int recordCount);

}
