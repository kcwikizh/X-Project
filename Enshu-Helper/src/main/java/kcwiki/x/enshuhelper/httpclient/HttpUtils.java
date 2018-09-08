
package kcwiki.x.enshuhelper.httpclient;

import com.google.common.io.CharStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kcwiki.x.enshuhelper.exception.BaseException;
import static kcwiki.x.enshuhelper.tools.ConstantValue.FILESEPARATOR;
import kcwiki.x.enshuhelper.exception.types.KcServerStatus;
import kcwiki.x.enshuhelper.types.LogTypes;
import kcwiki.x.enshuhelper.exception.types.ServiceTypes;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.utils.DateUtils;
import org.apache.hc.core5.http.HttpEntity;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
        
public class HttpUtils {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
    static final List<Integer> ErrorCode = new ArrayList<>();

    public static boolean checkModified(String url, String gtmdate, CloseableHttpClient httpclient, RequestConfig config) throws BaseException {

        HttpHead httpHead = DefaultMethod.getDefaultHeadMethod(url, config);
        try{
            httpHead.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");  
//            httpHead.setHeader("If-Modified-Since", "Sun, 19 Nov 2017 14:04:58 GMT");
            httpHead.setHeader("If-Modified-Since", gtmdate);
            try (CloseableHttpResponse response = httpclient.execute(httpHead)) {
                int rspCode = response.getCode();
                checkResponseCode(rspCode, url);
                if(!response.containsHeader("Last-Modified"))
                    throw new BaseException(ServiceTypes.KanColleServer, KcServerStatus.Unknown, String.format("尝试获取%s时发生错误。", url));
                
                Date modified = DateUtils.parseDate(response.getFirstHeader("Last-Modified").getValue()); 
                if (modified.getTime() == 0)
                    throw new BaseException(ServiceTypes.KanColleServer, KcServerStatus.Maintenance, String.format("尝试获取%s时发生错误。", url));
                
                Date lastmodified = DateUtils.parseDate(gtmdate);
                long diffTime = modified.getTime() - lastmodified.getTime();
                return rspCode == 200 && diffTime > 0;
            }
        } catch (UnknownHostException e) {
            LOG.error("HttpUtils_AHC"+"checkModified时端口出错,请检测网络连接。");
        } catch (ConnectException e){
            LOG.error("HttpUtils_AHC"+"checkModified时文件连接超时。");
        } catch (IOException e){
            LOG.error("HttpUtils_AHC"+"checkModified时文件时发生IOException错误。");  
        }
        throw new BaseException(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
    }
    
    public static boolean checkModified(String url, long timestamp) throws BaseException {
        HttpURLConnection conn = null;
        try {
            URL _url = new URL(url);
            conn = (HttpURLConnection)_url.openConnection();
            conn.setIfModifiedSince(timestamp);
            conn.connect();
            int rspCode = conn.getResponseCode();
            checkResponseCode(rspCode, url);
            
            conn.disconnect();
            long lastmodified = 0L;
            if (conn.getHeaderField("Last-Modified") == null || (lastmodified = conn.getLastModified()) == 0)
                throw new BaseException(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
                
            long diffTime = timestamp - lastmodified;
            return rspCode == 200 && diffTime < 0;
        } catch (MalformedURLException ex) {
            LOG.error("HttpUtils_SHC"+"checkModified时端口出错,请检测网络连接。");
        } catch (IOException ex) {
            LOG.error("HttpUtils_SHC"+"checkModified时端口出错,请检测网络连接。");
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        throw new BaseException(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
    }

    public static boolean downloadFile(String url, String filefolder, String filename, RequestConfig config) throws BaseException {
        if(!url.startsWith("http://") || !url.startsWith("https://")){
            url = "http://" + url;
        }
        
        HttpGet httpGet = DefaultMethod.getDefaultGetMethod(url, config);
        try{
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");  
            if(!new File(filefolder).exists())
                new File(filefolder).mkdirs();
            final RandomAccessFile file = new RandomAccessFile(filefolder + FILESEPARATOR + filename, "rw");
            
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                    if (300 >= response.getCode()) {
                        HttpEntity en = response.getEntity();
                        try (InputStream in = en.getContent()) {
                            byte[] buf = new byte[512];
                            int len = -1;

                            while (-1 != (len = in.read(buf))) {
                                file.write(buf, 0, len);
                            }
                            file.close();
                        }
                    }
                }
            }
            
            return true;
        }catch(UnknownHostException e){
            LOG.error("HttpUtils"+"下载端口出错,请检测网络连接。");
            throw new BaseException(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
        }catch(ConnectException e){
            LOG.error("HttpUtils"+"下载文件连接超时。");
        }catch(IOException e){
            ExceptionUtils.getStackTrace(e);
            LOG.error("HttpUtils"+"下载文件时发生IOException错误。");  
        }  
        throw new BaseException(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
    }
    
    public static String getHttpBody (String url, RequestConfig config) throws BaseException {
        HttpGet httpGet = DefaultMethod.getDefaultGetMethod(url, config);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                if (300 >= response.getCode()) {
                    HttpEntity entity = response.getEntity();
                    String text ;
                    try (final Reader reader = new InputStreamReader(entity.getContent())) {
                        text = CharStreams.toString(reader);
                    }
                    return text;
                }
            } catch (IOException ex) {
                LOG.error("HttpUtils访问{}时发生IOException错误。", url);  
            }
        }catch(UnknownHostException e){
            LOG.error("HttpUtils"+"下载端口出错,请检测网络连接。");
            throw new BaseException(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
        }catch(ConnectException e){
            LOG.error("HttpUtils"+"下载文件连接超时。");
        }catch(IOException e){
            LOG.error("HttpUtils"+"下载文件时发生IOException错误。");  
        }  
        throw new BaseException(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
    }
    
    private static void checkResponseCode(int code, String url) throws BaseException {
        switch(code) {
            case 403:
                throw new BaseException(ServiceTypes.KanColleServer, KcServerStatus.Maintenance, String.format("尝试获取%s时发生错误。", url));
        }
        if(ErrorCode.contains(code)){
            throw new BaseException(ServiceTypes.KanColleServer, LogTypes.ERROR, code);
        }
    }

    static{
        ErrorCode.add(403);
        ErrorCode.add(500);
        ErrorCode.add(503);
    }
}
