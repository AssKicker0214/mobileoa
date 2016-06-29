package presentation.index.block;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.andexert.library.RippleView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ian on 2016/6/27.
 */
public class IndexBlockModel{
    IUpdateReceiver block;

    private BlockStatus status = BlockStatus.ICON;
    private int myCount;
    private int allCount;
    private ArrayList<Message> msgs;
    private int volume = 3;
    private int currentDisplay = 0;

    private RippleView.OnRippleCompleteListener jumpListener;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if(what == 0){
                block.setHeader();
                Log.e("message","update ui");
            }else if(what == 1){
                changeStatus();
            }
            block.setRate(myCount+"", "/"+allCount);
        }
    };

    private Timer timer;

    public IndexBlockModel(IUpdateReceiver block){
        this.block = block;
        msgs = new ArrayList<>();

//        setTick(5000);
    }

    public BlockStatus getStatus(){
        return status;
    }

    public void setVolume(int v){
        this.volume = v;
    }

    public void changeStatus(){
        if(status == BlockStatus.ICON){
            status = BlockStatus.DETAIL;
        }else{
            status = BlockStatus.ICON;
        }
        block.updateStatus(status);
    }

    public void setRate(int my, int all){
        myCount = my;
        allCount = all;
//        block.setRate(my + "", "/" + all);
    }

    public int[] getRate(){
        int[] rs = new int[2];
        rs[0] = myCount;
        rs[1] = allCount;
        return rs;
    }

    public String getMsg(){

        Log.e("message", "getMsg: size="+msgs.size()+", currentDisplay="+currentDisplay);
        if(msgs.size()>currentDisplay){
            java.util.Date current = new java.util.Date(System.currentTimeMillis());
            Message msg = msgs.get(currentDisplay);
            int diff = (int)(current.getTime() - msg.createTime.getTime())/1000;
            String unit = "秒";
            if(diff > (3600*24-1)){
                unit = "天";
                diff = diff/(3600*24);
            }else if(diff>3599){
                unit = "小时";
                diff = diff/3600;
            }else if(diff > 59){
                unit = "分钟";
                diff = diff/60;
            }
            return msg.msg+","+diff+unit+"前";
        }else{
            return "";
        }
    }

    public void addMsg(Message msg){
        if(msgs.size() == volume){
            msgs.remove(0);
        }
        msgs.add(msg);
    }

    //asyncTask 不同的实例共用一个子线程，子线程不停另一个实例就没办法利用
    public void setTick(int period){
//        AsyncTask<Integer, String, Void> task = new AsyncTask<Integer, String, Void>() {
//            @Override
//            protected Void doInBackground(Integer... params) {
//
//                while(true){
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if(msgs.size()>0){
//                            String display = msgs.get(currentDisplay).msg;
//                            currentDisplay++;
//                            if(currentDisplay == volume){
//                                currentDisplay = 0;
//                            }
//                        publishProgress(display);
//                    }
//                }
//            }
//
//            @Override
//            protected void onProgressUpdate(String... values) {
//                super.onProgressUpdate(values);
//                block.setHeader(values[0]);
////                Log.e("index", "setHeader="+values[0]);
//            }
//        };
//
//        task.execute(5000);
        stopTick();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(msgs.size()>0){
                    currentDisplay++;
                    if(currentDisplay == volume || currentDisplay >= msgs.size()){
                        currentDisplay = 0;
                    }
                    handler.sendEmptyMessage(0);
                }

                if(Math.random() > 0.7){
                    handler.sendEmptyMessage(1);
                }

            }
        }, 0, period);
    }

    public void stopTick(){
        if(timer != null){
            timer.cancel();
            timer.purge();
        }

        timer = null;
    }


}
