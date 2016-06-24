package entity;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Source;

import bl.CurrentLogin;

/**
 * Created by Ian on 2016/6/8.
 */
public class Keepup implements Serializable{
    public String keepupID = "";
    public SourceType sourceType;
    private Date createTime;
    public String content = "";
    public String followupremarks = "";
    public String sourceID = "";  //可能是客户、商机、合同的id，根据sourceType来定
    public String keeperName = "";
    public String sourceName = "";
    public String creatorID = "";

    public Map<String, String> toMap(boolean isCreate){
        Map<String, String> att = new HashMap<>();
        if(isCreate){
            createTime = new Date(System.currentTimeMillis());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            att.put("createtime", format.format(createTime));
            att.put("sourcetype", sourceType.getFigure()+"");
            att.put("creatorid", CurrentLogin.id);
            att.put("sourceid", sourceID);
        }else{
            att.put("followupid", keepupID);
        }

        att.put("content", content);
        att.put("followupremarks", followupremarks);
        return att;

    }

    public void setCreateTime(String str){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            createTime = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getCreateTime(){
        if(createTime == null){
            return "-";
        }else{

        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(createTime);
    }

    public String getMonthDate(){
        String rs = createTime.getMonth()+"月"+createTime.getDate()+"日";

        return rs;
    }

    public String getTime(){
        if(createTime == null){
            return "-";
        }
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(createTime);
//        return createTime.
    }

}
