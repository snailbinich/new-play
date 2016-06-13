package com.izhaoyan.playground.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zhaoyan on 2016/6/12.
 */
public class BRRead {

    @Test
    public void readFromConsole(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
//            int c = bufferedReader.read();
//            while(c != -1){
//                c = bufferedReader.read();
//                System.out.println((char)c);
//            }
            char c;
            do{
                c = (char) bufferedReader.read();
                System.out.println(c);
//                File
            }while(c != 'q');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
