package com.izhaoyan.playground.rest;

import com.izhaoyan.playground.Application;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by zhaoyan on 2016/5/19.
 */

public class RestDemoTest {

    static  HttpClient client = null;
    private String accessKey = "S1guCl0KF/qxO4CElPY/";
    private String secretKey = "b7zBDxv9ohTPc0tgc8Hpp89i7I0FDnkyQY4mYY6I";


    @Before
    public void initClient(){
        client = new HttpClient();
    }


    @Test
    public void testGet() throws IOException {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("http://10.4.2.38:19092/");
        int result = client.executeMethod(method);
        System.out.println("result is "+result);

        System.out.println("print headers .....");
        for(Header header : method.getResponseHeaders()){
            System.out.println(header.getName()+":"+header.getValue());
        }

        System.out.println("print body");

        System.out.println(method.getResponseBodyAsString());
    }

    @Test
    public void testPost() throws IOException {

        PostMethod postMethod = new PostMethod("http://10.4.2.38:19092/");
        int statusCode = client.executeMethod(postMethod);
        for(Header header : postMethod.getResponseHeaders()){
            System.out.println(header.getName()+":"+header.getValue());
        }

        System.out.println("print body");

        System.out.println(postMethod.getResponseBodyAsString());
    }

    @Test
    public void testGetKs3Object() throws IOException {
        String url = "http://ks3-cn-beijing.ksyun.com/zz-test-bj/Desertcover.jpg";
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);


        String currentDate = getGMTTime();
        String signString = calcAuthorization("GET", "", "", currentDate, "", "/zz-test-bj/Desertcover.jpg");
        System.out.println(signString);
//        Authorization = “KSS YourAccessKey:Signature”
        method.setRequestHeader(new Header("Authorization", "KSS "+accessKey+":"+signString));
        method.setRequestHeader(new Header("Date", currentDate.toString()));
        method.setRequestHeader(new Header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36"));
        int result = client.executeMethod(method);
        System.out.println("result is "+result);

        System.out.println("print headers .....");
        for(Header header : method.getResponseHeaders()){
            System.out.println(header.getName()+":"+header.getValue());
        }


        System.out.println("print body");

        System.out.println(method.getResponseBodyAsString());
    }

    private String calcAuthorization(String httpVerb, String contentMd5, String contentType, String date, String kssHeaders,
                                     String resource){

        StringBuffer stringToSign = new StringBuffer();
        stringToSign.append(httpVerb+"\n");
        stringToSign.append(contentMd5+"\n");
        stringToSign.append(contentType+"\n");
        stringToSign.append(date+"\n");
        stringToSign.append(kssHeaders);
        stringToSign.append(resource);
        System.out.println(stringToSign);

        try {
            String strToSign = new String(stringToSign.toString().getBytes("UTF-8"));
//            String signStr = "\n\n\nFri, 08: /?uploads";;
            String HMAC_SHA1 = "HmacSHA1";
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA1);
            Mac mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(strToSign.getBytes());
            String result = new BASE64Encoder().encode(rawHmac);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getGMTTime(){
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        String str = sdf.format(cd.getTime());
        return str;
    }


    @Test
    public void testGetServerIp() throws UnknownHostException {
        InetAddress address = getServerIp("ks3-cn-beijing.ksyun.com");
//        InetAddress address = getServerIp("www.baidu.com");
        System.out.println(address.getHostAddress().toString());
    }

    public static InetAddress getServerIp(String host) throws UnknownHostException {
        return InetAddress.getByName(host);
    }
}
