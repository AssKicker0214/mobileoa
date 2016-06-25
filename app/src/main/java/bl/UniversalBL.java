package bl;

import org.json.JSONException;
import org.json.JSONObject;

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

}
