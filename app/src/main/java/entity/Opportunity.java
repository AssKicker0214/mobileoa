package entity;

import java.util.Date;

/**
 * Created by Ian on 2016/6/11.
 */
public class Opportunity {
    public String name;
    public String cusName;
    public String total;
    public String id;
    public String cusid;
    public OppoState states;
    public String staffID;
    public String staffName;
    public int rank;

    public int successRate = 0;
    public Date expectedDate = null;
}
