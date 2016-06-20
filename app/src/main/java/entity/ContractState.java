package entity;

/**
 * Created by Ian on 2016/6/11.
 */
public enum ContractState {
    BEFORE("未开始"),PERFORMING("执行中"),DEAD("意外终止"),SUCCEEFUL("成功结束");

    private  String des = "";
    private ContractState(String des){
        this.des = des;
    }

    public String toString(){
        return des;
    }
}
