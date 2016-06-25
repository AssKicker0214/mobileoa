package entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ian on 2016/6/11.
 */
public class Opportunity implements Serializable{
    public String name = "";
    public String cusName = "";
    public String id = "";
    public String cusid = "";
    public OppoState states = null;
    public String staffID = "";
    public String staffName = "";
    public int rank = 0;
    public String remark = "";

    public int successRate = 0;
    public Date expectedDate = null;
    public double estimateAmount = 0.0;

    public Map<String, String> toMap(boolean isCreate){
        Map<String, String> att = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(!isCreate){
            att.put("opportunityid", id);
        }else {
            att.put("acquisitiondate", format.format(new Date(System.currentTimeMillis())));
            att.put("staffid", staffID);
            att.put("customerid", cusid);
        }
//        att.put()
        att.put("opportunitytitle", name);
        att.put("successrate", successRate+"");
        att.put("opportunityremarks", remark);
        if(expectedDate != null)
            att.put("expecteddate", format.format(expectedDate));

        if(states != null)
        att.put("opportunitystatus", states.getFigure()+"");

        return att;
    }

    public void setExpectedDate(String s){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(s);
            expectedDate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
