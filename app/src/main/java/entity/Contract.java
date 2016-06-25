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
public class Contract implements Serializable{
    public String name;
    public String id = "";
    public String number = "";
    public String oppoTitle = "";
    public String total;
    public ContractState state = ContractState.BEFORE;
    public String type;
    public String customerID = "";
    public String customerName = "";
    public String oppoID = "";
    private Date startDate;
    private Date endDate;
    private Date signingDate;
    public String clientContractor = "";
    public String ourContractor = "";
    public String payMethod = "";
    public String contractRemarks = "";
    public String staffID = "";

    public Map<String, String> toMap(boolean isCreate){
        Map<String, String> att = new HashMap<>();
        if(isCreate){
            att.put("customerid", customerID);
            att.put("oppotunityid", oppoID);
            att.put("staffid", staffID);
        }else{
            att.put("contractid", id);
        }
        att.put("contracttitle", name);
        att.put("contractnumber", number);
        att.put("totalamount", total);

        att.put("contractstatus", state.toFigure()+"");
        if(getStartDate()!=null){
            att.put("startdate", getStartDate());
        }

        if(getEndDate()!=null){
            att.put("enddate", getEndDate());
        }

        if(getSigningDate()!=null){
            att.put("signingdate", getSigningDate());
        }
        att.put("clientcontractor", clientContractor);
        att.put("ourcontractor", ourContractor);
        att.put("paymethod", payMethod);
        return att;
    }

    public void setStartDate(String str){
        startDate = format(str);
    }

    public String getEndDate(){
        return getDate(endDate);
    }

    public void setEndDate(String str){
        endDate = format(str);
    }

    public String getStartDate(){
        return getDate(startDate);
    }

    public void setSigningDate(String str){
        signingDate = format(str);
    }

    public String getSigningDate(){
        return getDate(signingDate);
    }

    private Date format(String str){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private String getDate(Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String rs = "";
        rs = format.format(date);
        return rs;
    }

}


