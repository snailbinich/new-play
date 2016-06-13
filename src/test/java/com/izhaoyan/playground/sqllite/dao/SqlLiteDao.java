package com.izhaoyan.playground.sqllite.dao;

import com.izhaoyan.playground.sqllite.model.UpLoadJob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zhaoyan on 2016/5/30.
 */
public class SqlLiteDao {

    private String CREATE_SQL = "sqlite> CREATE TABLE UPLOADJOBS(" +
            "   ID INT PRIMARY KEY     NOT NULL,\n" +
            "   BUCKET         VARCHAR(255)    NOT NULL,\n" +
            "   objectKey      VARCHAR(255)     NOT NULL,\n" +
            "   localPath      VARCHAR(255),\n" +
            "   currentPart    INT\n" +
            ");";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:ks3explorer.db");
        return c;
    }

    private void releaseConnection(Connection connection) throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }

    public void createUploadTable() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();


    }




    public int createUploadJob(UpLoadJob upLoadJob){

        return 0;
    }
}
