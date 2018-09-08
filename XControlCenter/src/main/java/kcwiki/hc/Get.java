/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.hc;

import static org.asynchttpclient.Dsl.*;
import org.asynchttpclient.*;
import io.netty.handler.codec.http.HttpHeaders;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;


/**
 *
 * @author iTeam_VEP
 */
public class Get {
    private static List<Future<Integer>> result = new ArrayList<>();
    
    public void requestNetForGetLogin(String vid, String sname) {
        String path = "http://ooi.moe/kcs/sound/kc" + vid +"/141.mp3?version=35";
        
        File f= new File("C:\\Users\\iTeam_VEP\\Desktop\\tset\\voice\\" + sname +".mp3") ;
        if(f.exists()){
            return;
        }
        Charset chrst = Charset.forName("UTF-8");    
        
        AsyncHttpClient asyncHttpClient = asyncHttpClient();
        Future<Integer> whenStatusCode = asyncHttpClient.prepareGet(path)
        .execute(new AsyncHandler<Integer>() {
                private Integer status;
                @Override
                public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                        status = responseStatus.getStatusCode();
//                        System.out.println(status);
                        if(status == 200){
                            return State.CONTINUE;
                        } else {
                            return State.ABORT;
                        }
                }
                @Override
                public State onHeadersReceived(HttpHeaders headers) throws Exception {
                    Long lastmodified = headers.getTimeMillis("Last-Modified");
                    Date date = new Date(lastmodified);
                    int _y = date.getYear();
                    System.out.println("status: " + status + "\t sname: " + sname + "\t lastmodified: " + date.toLocaleString());
                    if(lastmodified < 1514732400000L){
                        return State.ABORT;
                    }
                    return State.CONTINUE;
                }
                @Override
                public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                    ByteBuffer bf = bodyPart.getBodyByteBuffer();
                    try (FileChannel writeChannel = new FileOutputStream(f, true).getChannel()) {
                        writeChannel.write(bf);
                    }
                    return State.CONTINUE;
                }
                @Override
                public Integer onCompleted() throws Exception {
                    return status;
                }
                @Override
                public void onThrowable(Throwable t) {
                    t.printStackTrace();
                    t.toString();
                    System.err.println(vid + "\t 下载出现错误，错误号： " + status);
                }
        });
        result.add(whenStatusCode);

        /*try {
            Integer statusCode = whenStatusCode.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    
//    public static void main(String[] args) {
//        HashMap<Integer, String> id2name = new HashMap<>();
//        
////            new Get().requestNetForGetLogin("bwsoozmjkbde");
//        try {
//            String start2str = FileUtils.readFileToString(new File("C:\\Users\\iTeam_VEP\\Desktop\\Start2_UTF8.json"), "UTF-8");
//            
//            JSONArray start2 = JSON.parseObject(start2str).getJSONArray("api_mst_ship");
//            for(Object obj:start2){
//                JSONObject jobj = (JSONObject) obj;
//                id2name.put(jobj.getInteger("api_id"), jobj.getString("api_name"));
//            }
//            
//            start2 = JSON.parseObject(start2str).getJSONArray("api_mst_shipgraph");
//            int count = 0;
//            for(Object obj:start2){
//                JSONObject jobj = (JSONObject) obj;
//                new Get().requestNetForGetLogin(jobj.getString("api_filename"), id2name.get(jobj.getInteger("api_id")));
//                count++;
//            }
//            
//            for(Future<Integer> cb:result) {
//                cb.get();
//            }
//            System.out.println("Download completed!");
//        } catch (IOException ex) {
//            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ExecutionException ex) {
//            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
