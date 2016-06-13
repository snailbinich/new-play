package com.izhaoyan.playground.ks3client;

import com.ksyun.ks3.dto.Bucket;
import com.ksyun.ks3.dto.HeadObjectResult;
import com.ksyun.ks3.dto.Ks3ObjectSummary;
import com.ksyun.ks3.dto.ObjectListing;
import com.ksyun.ks3.http.HttpClientConfig;
import com.ksyun.ks3.service.Ks3;
import com.ksyun.ks3.service.Ks3Client;
import com.ksyun.ks3.service.Ks3ClientConfig;
import com.ksyun.ks3.service.request.ListObjectsRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by zhaoyan on 2016/5/31.
 */
public class ObjectTests {

//    private static String accessKey = "S1guCl0KF/qxO4CElPY/";
//    private static String secretKey = "b7zBDxv9ohTPc0tgc8Hpp89i7I0FDnkyQY4mYY6I";
    private static String accessKey = "S1guCl0KF/o1bcF89q6Z";
    private static String secretKey = "FdY6UAzmQkmaHIpkb3kbsLat0aKsC7MDKcDgiLfc";

    Ks3 client = null;


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
//        config.setEndpoint("ks3-cn-shanghai.ksyun.com");
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
        client = new Ks3Client(accessKey,secretKey, config);
    }

      @Test
    public void testHeadObject(){

        HeadObjectResult cloudFile =  client.headObject("snailbinich123", "13.jpg");
        System.out.println(cloudFile.getObjectMetadata().getLastModified());
        System.out.println(cloudFile);

    }

    @Test
    public void testlistBuckets(){

        for(Bucket bucket : client.listBuckets()){
            System.out.println(bucket.getName());
        }

    }


    @Test
    public void testListObjects(){
        String bucketName = "wz-hz-test";
        ListObjectsRequest request = new ListObjectsRequest(bucketName);
        //设置参数
        request.setMaxKeys(1000);//指定返回条数最大值
//        request.setPrefix(prefix);//返回以指定前缀开头的object
        request.setDelimiter("/");//设置文件分隔符，系统将根据该分隔符组织文件夹结构，默认是"/"
//        request.setMarker(marker);
        //执行操作

        ObjectListing objectListing = client.listObjects(request);
        for(String dir : objectListing.getCommonPrefixes()){
            System.out.println("文件夹:"+dir);
        }

        for(Ks3ObjectSummary summary: objectListing.getObjectSummaries()){
            System.out.println("文件:"+summary.getKey());
        }
    }
}
