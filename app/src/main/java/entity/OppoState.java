package entity;

/**
 * Created by Ian on 2016/6/11.
 */
public enum OppoState {
    NEGOTIATION("初步洽谈", 1),REQUIREMENT("需求确定", 2),SCHEME("方案报价", 3),
    CONTRACT("谈判合同", 4),WIN("赢单", 5),LOSE("输单", 6);

    private String des = "";
    private int figure = 0;
    private OppoState(String des, int figure){
        this.des = des;
        this.figure = figure;
    }

    public String toString(){
        return des;
    }

    public int getFigure(){
        return figure;
    }

    public static OppoState convert(int i){
        OppoState rs = null;
        if(i == 1){
            rs = NEGOTIATION;
        }else if(i == 2){
            rs = REQUIREMENT;
        }else if(i == 3){
            rs = SCHEME;
        }else if(i == 4){
            rs = CONTRACT;
        }else if(i == 5){
            rs = WIN;
        }else if(i == 6){
            rs = LOSE;
        }

        return rs;
    }
}
