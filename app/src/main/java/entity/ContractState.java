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

    public int toFigure(){
        int f = 0;
        if(des.equals("未开始")){
            f=1;
        }else if(des.equals("执行中")){
            f=2;
        }else if(des.equals("意外终止")){
            f=4;
        }else if(des.equals("成功结束")){
            f = 3;
        }
        return f;
    }
}
