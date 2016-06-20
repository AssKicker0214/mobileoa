package bl;

import entity.User;

/**
 * Created by Ian on 2016/6/11.
 */
public class CurrentLogin {
    public static boolean logged = false;
    public static String id = "102";
    public static String name = "";

    public static void setUser(User user){
        id = user.userid;
        name = user.name;
        logged = true;
    }

    public static void logout(){
        id = "";
        name = "";
        logged = false;
    }
}
