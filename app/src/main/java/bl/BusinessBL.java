package bl;

import android.util.Log;

import java.util.ArrayList;

import entity.OppoState;
import entity.Opportunity;

/**
 * Created by Ian on 2016/6/26.
 */
public class BusinessBL {
    public double getRateOfOppo(OppoState state){
        OpportunityBL obl = new OpportunityBL();
        ArrayList<Opportunity> oppos = obl.getOppoList(0);
        double target = 0.0;
        for(Opportunity oppo : oppos){
            if(oppo.states.equals(state)){
                target++;
            }
        }
        int total = oppos.size();
//        Log.e("business", "targetCount="+target+" total="+total);
        double rate = Math.round((1000*target/(total+0.0)))/10;
        return rate;
    }

    public int getCountOfOppo(OppoState state){
        OpportunityBL obl = new OpportunityBL();
        ArrayList<Opportunity> oppos = obl.getOppoList(0);
        int target = 0;
        for(Opportunity oppo : oppos){
            if(oppo.states.equals(state)){
                target++;
            }
        }
        return target;
    }

    public double getTotalOfOppo(OppoState state){
        OpportunityBL obl = new OpportunityBL();
        ArrayList<Opportunity> oppos = obl.getOppoList(0);
        double total = 0.0;
        for(Opportunity oppo : oppos){
            if(oppo.states.equals(state)){
                total += oppo.estimateAmount;
            }
        }
        return total;
    }

    public Double[] getRateCountTotal(OppoState state){
        OpportunityBL obl = new OpportunityBL();
        ArrayList<Opportunity> oppos = obl.getOppoList(0);
        double target = 0.0;
        double total = 0.0;
        for(Opportunity oppo : oppos){
            if(oppo.states.equals(state)){
                target++;
                total += oppo.estimateAmount;
            }
        }
        int size = oppos.size();
        double rate = Math.round((1000*target/(size+0.0)))/10;
        Double[] rs = new Double[3];
        rs[0] = rate;
        rs[1] = target;
        rs[2] = total;
        return rs;
    }
}
