package bl;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by Ian on 2016/6/4.
 */
public class HttpProxy {
    private static String rootUrl =
            "http://nqiwx.mooctest.net:8090/wexin.php/api/index/staff_query_json?staffid=113";

    public static JSONObject getJson() {
        JSONObject jsonObject = null;


        return jsonObject;

    }

    public static ArrayList<JSONObject> get(Map<String, String> attri, String api){
        ArrayList<JSONObject> jos = new ArrayList<>();
        String urlPath = "http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/" + api + "?";

        String param = packPara(attri);
        try {
            URL url = new URL(urlPath);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpConn.setRequestProperty("Charset", "UTF-8");

            int resultCode = httpConn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                StringBuffer sb = new StringBuffer();
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                JSONObject jsonObject = new JSONObject(sb.toString());
                Log.i("post", sb.toString());

                int recordCount = jsonObject.getInt("recordcount");
                int currentCount = jsonObject.getInt("currentcount");
                int pageSize = jsonObject.getInt("pagesize");
                int currentPage = jsonObject.getInt("currentpage")+1;

                if(currentPage * pageSize >= recordCount){
                    for (int i=0;i<(recordCount - (currentPage-1)*pageSize); i++){
                        jos.add(jsonObject.getJSONObject(""+i));
                        Log.i("post", "subjson:"+jsonObject.getJSONObject(""+i));
                    }
                }else{
                    for(int i=0;i<jsonObject.getInt("pagesize");i++){
                        jos.add(jsonObject.getJSONObject(""+i));
                        Log.i("post", "subjson:"+jsonObject.getJSONObject(""+i));
                    }
                }

            }else{
                Log.i("get", "http failed:"+resultCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  jos;
    }

    public static ArrayList<JSONObject> post(Map<String, String> attri, String api) {
        ArrayList<JSONObject> jos = new ArrayList<>();
//        JSONObject jsonObject = null;
//        String urlPath = "http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/" + api;
//        //String urlPath = new String("http://localhost:8080/Test1/HelloWorld?name=丁丁".getBytes("UTF-8"));
//        //建立连接
//        URL url = null;
//        try {
////            String param = "name=" + URLEncoder.encode("丁丁", "UTF-8");
//            String param = packPara(attri);
//            url = new URL(urlPath);
//            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//            //设置参数
//            httpConn.setDoOutput(true);   //需要输出
//            httpConn.setDoInput(true);   //需要输入
//            httpConn.setUseCaches(false);  //不允许缓存
//            httpConn.setRequestMethod("POST");   //设置POST方式连接
//            //设置请求属性
//            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
//            httpConn.setRequestProperty("Charset", "UTF-8");
//            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
//            httpConn.connect();
//            //建立输入流，向指向的URL传入参数
//            DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
//            dos.writeBytes(param);
//            dos.flush();
//            dos.close();
//            //获得响应状态
//            int resultCode = httpConn.getResponseCode();
//            if (HttpURLConnection.HTTP_OK == resultCode) {
//                StringBuffer sb = new StringBuffer();
//                String readLine = new String();
//                BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
//                while ((readLine = responseReader.readLine()) != null) {
//                    sb.append(readLine).append("\n");
//                }
//                jsonObject = new JSONObject(sb.toString());
//                Log.i("post", sb.toString());
//
//                int recordCount = jsonObject.getInt("recordcount");
//                int currentCount = jsonObject.getInt("currentcount");
//                int pageSize = jsonObject.getInt("pagesize");
//                int currentPage = jsonObject.getInt("currentpage")+1;
//
//                if(currentPage * pageSize >= recordCount){
//                    for (int i=0;i<(recordCount - (currentPage-1)*pageSize); i++){
//                        jos.add(jsonObject.getJSONObject(""+i));
////                        Log.i("post", "subjson:"+jsonObject.getJSONObject(""+i));
//                    }
//                }else{
//                    for(int i=0;i<jsonObject.getInt("pagesize");i++){
//                        jos.add(jsonObject.getJSONObject(""+i));
////                        Log.i("post", "subjson:"+jsonObject.getJSONObject(""+i));
//                    }
//                }
//            }else{
//                Log.i("post", "post failed:"+resultCode);
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ProtocolException e1) {
//            e1.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        JSONObject jsonObject = getJsonByPost(attri, api);
        int recordCount = 0;
        try {
            recordCount = jsonObject.getInt("recordcount");
            int currentCount = jsonObject.getInt("currentcount");
            int pageSize = jsonObject.getInt("pagesize");
            int currentPage = jsonObject.getInt("currentpage")+1;

            Log.i("user", "toServer: currentPage="+attri.get("currentpage"));
            Log.i("user", "api="+api+"=>recordCount"+recordCount+",currentpage:"+currentPage+",pageSize:"+pageSize);
            if(currentPage * pageSize >= recordCount){
//                for (int i=(currentPage-1)*pageSize;i<recordCount; i++){
//                    jos.add(jsonObject.getJSONObject("" + i));
//                }
//                for(int i=0;i<)
            }else{
//                for(int i=(currentPage-1)*pageSize;i<currentPage*pageSize;i++){
//                    jos.add(jsonObject.getJSONObject(""+i));
//                }
            }
//            jsonObject.
            Iterator<String> it = jsonObject.keys();
            while(it.hasNext()){
                String key = it.next();
                try{
                    Integer.parseInt(key);
                    jos.add(jsonObject.getJSONObject(key));
                }catch (Exception e){

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jos;

    }

//    public static JSONObject simplePost(Map<String, String> attri, String api){
//
//    }

    public static int getPageCount(Map<String, String> attri, String api){
        JSONObject jsonObject = getJsonByPost(attri, api);
        int pageCount = 1;
        try {
            pageCount = jsonObject.getInt("pagecount");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pageCount;
    }

    public static JSONObject getJsonByPost(Map<String, String> attri, String api){
        JSONObject jsonObject = null;
        String urlPath = "http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/" + api;
        //String urlPath = new String("http://localhost:8080/Test1/HelloWorld?name=丁丁".getBytes("UTF-8"));
        //建立连接
        URL url = null;
        try {
//            String param = "name=" + URLEncoder.encode("丁丁", "UTF-8");
            String param = packPara(attri);
            url = new URL(urlPath);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            //设置参数
            httpConn.setDoOutput(true);   //需要输出
            httpConn.setDoInput(true);   //需要输入
            httpConn.setUseCaches(false);  //不允许缓存
            httpConn.setRequestMethod("POST");   //设置POST方式连接
            //设置请求属性
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpConn.setRequestProperty("Charset", "UTF-8");
            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
            httpConn.connect();
            //建立输入流，向指向的URL传入参数
            DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();
            //获得响应状态
            int resultCode = httpConn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                StringBuffer sb = new StringBuffer();
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                jsonObject = new JSONObject(sb.toString());
            }else{
                Log.e("http", "http fault: "+resultCode);
            }

            Log.e("http", urlPath);
            Log.e("http", param);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("http", jsonObject.toString());

        return jsonObject;
    }

    public static String packPara(Map<String, String> map){
        String paras = "";
        for(Map.Entry<String, String> entry : map.entrySet()){
            String param = null;
            try {
                param = entry.getKey()+"=" + URLEncoder.encode(entry.getValue(), "UTF8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            paras+=param;
            paras += "&";
        }
//        paras = paras.substring(0, paras.length()-2);
        return paras;
    }
}