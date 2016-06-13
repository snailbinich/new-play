package com.izhaoyan.playground.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import org.junit.Test;

/**
 * Created by zhaoyan on 2016/6/13.
 */
public class TestAwsClient {

    private String ak = "AKIAITPT3HHZ4NUI5DBQ";
    private String sk = "pxcHOp5wV7F8ksExJl0ScdJq0HsNDGWIa9zSsrSS";
    private String token = "";
    private String buckeName = "zyks3test";

    @Test
    public void test1(){
        AWSCredentials credentials = null;
        try {
            credentials = new BasicSessionCredentials(ak, sk, token);
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        AmazonS3 s3 = new AmazonS3Client(credentials);
        Region usWest2 = Region.getRegion(Regions.AP_NORTHEAST_1);
        s3.setRegion(usWest2);

//        s3.listObjects();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(buckeName);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setPrefix("dirzhaoyan/");
        s3.listObjects(listObjectsRequest);


    }
}
