package com.izhaoyan.playground;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**
 * Created by zhaoyan on 2016/6/8.
 */
public class SocketTest {

    public static void main(String[] args){

        String host = "127.0.0.1";
        int port = 8080;
        try {
            Socket client = new Socket(host, port);
            Writer writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(genGetString());
            writer.flush();
            writer.close();
//            OutputStream ps = client.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String genGetString(){
        StringBuffer sb = new StringBuffer();
        sb.append("PUT /12345/20160526/test.pdf HTTP/1.1\n" +
                "HOST: kss.ksyun.com\n" +
                "Content-Type: text/plain\n" +
                "Content-Length: 14914198\n" +
                "Date: Wed, 08 Jun 2016 03:27:59 GMT\n" +
                "Authorization: Authorization S1guCl0KF/o1bcF89q6Z:atUMtE8ZvAeevTFtuzbUDS+O5yY=\n" +
                "\n");
        return sb.toString();

    }
}
