package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Source;

/**
 * Created by Ian on 2016/6/8.
 */
public class Keepup {
    public String keepupID = "";
    public SourceType sourceType;
    private Date createTime;
    public String content = "";
    public String sourceID = "";  //可能是客户、商机、合同的id，根据sourceType来定
    public String keeperName = "";
    public String sourceName = "";

    public void setCreateTime(String str){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            createTime = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getCreateTime(){
        return createTime.toString();
    }

    public String getMonthDate(){
        String rs = createTime.getMonth()+"月"+createTime.getDate()+"日";

        return rs;
    }

    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(createTime);
//        return createTime.
    }

}
