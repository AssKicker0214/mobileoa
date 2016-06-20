package entity;

/**
 * Created by Ian on 2016/6/9.
 */
public enum SourceType{
    CUSTOMER_KEEPUP("客户跟进"),OPPORTUNITY_KEEPUP("商机跟进"),CONTRACT_KEEPUP("合同跟进");
    private String des = "未知类型跟进";
    private SourceType(String des){
        this.des = des;
    }

    public String toString(){
        return des;
    }

    public static SourceType fromInt(int i){
        if(i == 1){
            return  SourceType.CUSTOMER_KEEPUP;
        }else if(i== 2){
            return SourceType.OPPORTUNITY_KEEPUP;
        }else if(i == 3){
            return SourceType.CONTRACT_KEEPUP;
        }else{
            return null;
        }
    }
}
