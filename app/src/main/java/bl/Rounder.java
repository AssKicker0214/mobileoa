package bl;

/**
 * Created by Ian on 2016/6/27.
 */
public class Rounder {
    public static double round(double d, int remain){
        double rs = 0.0;
        double tmp = Math.pow(10, remain);
        rs = Math.round(d * tmp) / tmp;
        return rs;
    }
}
