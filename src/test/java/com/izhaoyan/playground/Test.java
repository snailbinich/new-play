package com.izhaoyan.playground;

import com.ksyun.ks3.utils.DateUtils;
import com.ksyun.ks3.utils.HttpUtils;
import com.ksyun.ks3.utils.RequestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by zhaoyan on 2016/5/20.
 */
public class Test {
    public static void main(String[] args) {
        try {
            put();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void put() throws Throwable{
        String endpoint="http://kss.ksyun.com:80";
        String bucket="12345";
        String objectKey="20160526/test.txt";
        String filePath="e:/test.txt";
        String ak="S1guCl0KF/o1bcF89q6Z";
        String sk="FdY6UAzmQkmaHIpkb3kbsLat0aKsC7MDKcDgiLfc";
        String url=endpoint+"/"+bucket+"/"+objectKey;

        System.out.println(url);

        StringBuffer sbf=new StringBuffer();
        sbf.append("PUT /12345/20160526/test.txt HTTP/1.1").append("\r\n");
        sbf.append("HOST: kss.ksyun.com").append("\r\n");
        sbf.append("Content-Type: text/plain").append("\r\n");

        File file=new File(filePath);
        System.out.println("file length:"+file.length());
        HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type","text/plain");
        connection.setRequestProperty("Content-Length",(file.length())+"");

        sbf.append("Content-Length: ").append(file.length()+"").append("\r\n");

        String resource = CanonicalizedKSSResource(bucket,objectKey,new HashMap<String, String>());
        String requestMethod = connection.getRequestMethod();
        String contentMd5 = "";
        String contentType = connection.getRequestProperty("Content-Type");

        String signDate=DateUtils.convertDate2Str(new Date(), DateUtils.DATETIME_PROTOCOL.RFC1123);
        connection.setRequestProperty(HttpHeaders.DATE,signDate);

        List<String> signList = new ArrayList<String>();
        signList.addAll(Arrays.asList(new String[] {
                requestMethod, contentMd5, contentType, signDate
        }));
        signList.add(resource);

        String signStr = StringUtils.join(signList.toArray(), "\n");
        String signature = Signature.calculateRFC2104HMAC(signStr, sk);

        String value = "KSS"+" "+ak+":"+signature;
        connection.setRequestProperty(HttpHeaders.AUTHORIZATION.toString(),value);

        sbf.append(HttpHeaders.DATE.toString()).append(": ").append(signDate).append("\r\n");
        sbf.append(HttpHeaders.AUTHORIZATION.toString()).append(": ").append(value).append("\r\n");
        sbf.append("\r\n\r\n");
        sbf.append("\r\n\r\n");
        System.out.println(sbf);

        connection.setUseCaches(false);
        System.out.println(connection.getRequestProperties());
        FileInputStream fis=new FileInputStream(file);

        connection.connect();

        OutputStream os = connection.getOutputStream();
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(sbf.toString());
        clip.setContents(tText, null);

        os.write(sbf.toString().getBytes("utf-8"));
        os.flush();

                for (int i=0;i<120;i++){

            Thread.sleep(1000);
            System.out.println("i = "+i);
        }

        copy(fis, os,4096);

        getInputStream(connection);

        os.close();
        fis.close();

        System.out.println("success");
    }

    public static void getInputStream(final HttpURLConnection  connection){
        try {
            InputStream is=connection.getInputStream();
            System.out.println("-----------------------");
            IOUtils.copy(is, System.out);
            System.out.flush();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Thread getInputStreamThread(final HttpURLConnection  connection){
        Thread t=new Thread(){
            public void run() {
                getInputStream(connection);
            };
        };
        return t;
    }

    public static long copy(final InputStream input, final OutputStream output, int buffersize) throws IOException {
        final byte[] buffer = new byte[buffersize];
        int n = 0;
        long count=0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            output.flush();
            count += n;
//            System.out.println("count:"+count);
        }
        return count;
    }

    public static long doCopy(final InputStream input, final OutputStream output,int buffersize,int total) throws IOException {
        final byte[] buffer = new byte[buffersize];
        int n = 0;
        long count=0;
        output.write("\r\n\r\n\r\n\r\n".getBytes());
        output.flush();
        for (int i = 0; i < total; i++) {
            if (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
                output.flush();
                count += n;
                System.out.println("count:"+count);
            }else{
                System.out.println("break count:"+count);
                return count;
            }
        }

        try {
            System.out.println("sleep");
            Thread.sleep(120*1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("count:"+count);
        return count;
    }

    public static String CanonicalizedKSSResource(String bucketName,String objectKey,Map<String,String> queryParam) {
        boolean escapeDoubleSlash = true;

        StringBuffer buffer = new StringBuffer();
        buffer.append("/");
        if (!StringUtils.isBlank(bucketName)) {
            buffer.append(bucketName).append("/");
        }

        if (!StringUtils.isBlank(objectKey)) {
            String encodedPath = HttpUtils.urlEncode(objectKey, true);
            buffer.append(encodedPath);
        }

        String resource = buffer.toString();
        if (escapeDoubleSlash) {
            resource = resource.replace("//", "/%2F");
        }

        String queryParams = encodeParams(queryParam);
        if (queryParams != null && !queryParams.equals(""))
            resource = resource + "?" + queryParams;
        return resource;
    }

    public static String encodeParams(Map<String,String> params) {
        List<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>(
                params.entrySet());
        Collections.sort(arrayList,
                new Comparator<Map.Entry<String, String>>() {
                    public int compare(Map.Entry<String, String> o1,
                                       Map.Entry<String, String> o2) {
                        return o1.getKey().compareTo(o2.getKey());
                    }
                });
        List<String> kvList = new ArrayList<String>();
        for (Map.Entry<String, String> entry : arrayList) {
            String value = null;
            //8203,直接从浏览器粘下来的字符串中可能含有这个非法字符
            String key = entry.getKey().replace(String.valueOf((char)8203),"");
            if (!StringUtils.isBlank(entry.getValue()))
                value = entry.getValue();
            if (RequestUtils.subResource.contains(entry.getKey())||RequestUtils.QueryParam.contains(entry.getKey())) {
                if (value != null && !value.equals(""))
                    kvList.add(key + "=" + value);
                else{
                    if (RequestUtils.subResource.contains(key))
                        kvList.add(key);
                }
            }
        }

        return StringUtils.join(kvList.toArray(), "&");
    }

}
