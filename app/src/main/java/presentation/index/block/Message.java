package presentation.index.block;

import java.util.Date;

/**
 * Created by Ian on 2016/6/27.
 */
public class Message {
    public String msg = "";
    public Date createTime;
    public Message(String msg){
        this.msg = msg;
        createTime = new Date(System.currentTimeMillis());
    }
}
