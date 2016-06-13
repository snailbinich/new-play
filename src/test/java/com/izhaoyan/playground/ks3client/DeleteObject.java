package com.izhaoyan.playground.ks3client;

import com.ksyun.ks3.http.HttpClientConfig;
import com.ksyun.ks3.service.Ks3;
import com.ksyun.ks3.service.Ks3Client;
import com.ksyun.ks3.service.Ks3ClientConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyan on 2016/6/8.
 */
public class DeleteObject {

    Ks3 client = null;
    String ak="S1guCl0KF/o1bcF89q6Z";
    String sk="FdY6UAzmQkmaHIpkb3kbsLat0aKsC7MDKcDgiLfc";


    @Before
    public void init()  {
        Ks3ClientConfig config = new Ks3ClientConfig();
        /**
         * 设置服务地址</br>
         * 中国标准:kss.ksyun.com</br>
         * 中国cdn:kssws.ks-cdn.com</br>
         * 美国（圣克拉拉）:ks3-us-west-1.ksyun.com</br>
         */
//        config.setEndpoint("ks3-cn-beijing.ksyun.com");
        config.setEndpoint("kss.ksyun.com");
        config.setProtocol(Ks3ClientConfig.PROTOCOL.http);
        /**
         *true表示以   endpoint/{bucket}/{key}的方式访问</br>
         *false表示以  {bucket}.endpoint/{key}的方式访问
         */
        config.setPathStyleAccess(true);
        HttpClientConfig hconfig = new HttpClientConfig();
        //在HttpClientConfig中可以设置httpclient的相关属性，比如代理，超时，重试等。
        hconfig.setConnectionTimeOut(5000);
        config.setHttpClientConfig(hconfig);
        client = new Ks3Client(ak,sk, config);
    }

    @Test
    public void uploadObject(){

        String bucket="12345";
        String objectKey="20160523/test.txt";
        File file = new File("E:\\test.txt");
        client.putObject(bucket,objectKey,file);
    }


    @Test
    public void deleteObject(){
        uploadObject();
      List<Thread> threadList = new ArrayList<Thread>();
        Runner runner = new Runner();
        for(int i = 0;i<100;i++){
            threadList.add(new Thread(runner));
        }
        for(Thread t : threadList){
            t.start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class Runner implements Runnable{
        @Override
        public void run() {
            String bucket="12345";
            String objectKey="20160523/test.txt";
            try {

                Thread.sleep(100);
                client.deleteObject(bucket, objectKey);
            }catch (Throwable t){
                t.printStackTrace();
            }
        }
    }
}
