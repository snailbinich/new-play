package com.izhaoyan.playground.hbase;

import com.izhaoyan.playground.Application;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by zhaoyan on 2016/5/5.
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定
public class HBaseDaoTest {
    public static HConnection connection = null;

    public static Configuration configuration = null;

    static {
        Configuration conf = new Configuration();

        conf.addResource("hbase-site-bj.xml");

        configuration = HBaseConfiguration.create(conf);

        try {
            connection = HConnectionManager.createConnection(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testScan(){

    }



}
