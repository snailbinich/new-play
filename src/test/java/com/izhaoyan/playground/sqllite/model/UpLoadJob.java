package com.izhaoyan.playground.sqllite.model;

/**
 * Created by zhaoyan on 2016/5/30.
 */
public class UpLoadJob {

    Integer id;
    String bucket;
    String objectKey;
    private String localPath;
    private int currentPart = 1;
}
