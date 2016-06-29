package presentation.index.block;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import bl.ContactBL;
import bl.ContractBL;
import bl.CurrentLogin;
import bl.CustomerBL;
import bl.OpportunityBL;
import bl.ProductBL;
import blservice.IIndexService;

/**
 * Created by Ian on 2016/6/24.
 */
public class IndexBlockController implements View.OnLongClickListener, View.OnClickListener {
    private TextView t;
    private IndexBlock block;
    private IndexBlockModel model;
    private RippleView.OnRippleCompleteListener jumpListener;
    private IIndexService service;

    private Timer timer;
    private String name;

    private Date lastRefresh = CurrentLogin.loginTime;

    public IndexBlockController(String name, Activity activity) {
        this.name = name;
        if (name.equals("客户")) {
            block = new CustomerBlock(activity);
            model = new IndexBlockModel(block);
            jumpListener = block.getJumpListener();
            service = new CustomerBL();
        }else if(name.equals("产品")){

            block = new ProductBlock(activity);
            model = new IndexBlockModel(block);
            jumpListener = block.getJumpListener();
            service = new ProductBL();
        }else if(name.equals("商机")){

            block = new OppoBlock(activity);
            model = new IndexBlockModel(block);
            jumpListener = block.getJumpListener();
            service = new OpportunityBL();
        }else if(name.equals("合同")){

            block = new ContractBlock(activity);
            model = new IndexBlockModel(block);
            jumpListener = block.getJumpListener();
            service = new ContractBL();
        }else if(name.equals("联系人")){

            block = new ContactBlock(activity);
            model = new IndexBlockModel(block);
            jumpListener = block.getJumpListener();
            service = new ContactBL();
        }


        block.setModel(model);
        block.setOnLongClickListener(this);
        block.setOnClickListener(this);
    }


    public void setUpTimer(int period) {
        stopTimer();
        timer = new Timer();
        timer.schedule(new ModelUpdateTask(), 0, period);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = null;
    }

    public void setUpUITick(int period) {
        model.setTick(period);
    }

    public void stopUITick() {
        model.stopTick();
    }

    @Override
    public boolean onLongClick(View v) {

        block.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                model.changeStatus();
            }
        });
        return false;
    }

    @Override
    public void onClick(View v) {
        block.setOnRippleCompleteListener(jumpListener);
    }




    class ModelUpdateTask extends TimerTask {

        @Override
        public void run() {
            int newCount = service.getNew();
            Log.e("message", newCount+"");

            if (newCount > 0) {
                model.addMsg(new Message("+" + newCount + " 新" + name));
                lastRefresh = new Date(System.currentTimeMillis());
            }
            int[] rate = service.getCount();
            model.setRate(rate[0], rate[1]);
        }
    }
}