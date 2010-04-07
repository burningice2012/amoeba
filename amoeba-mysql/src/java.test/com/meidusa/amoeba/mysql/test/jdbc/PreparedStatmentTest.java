package com.meidusa.amoeba.mysql.test.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class PreparedStatmentTest {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Properties props = new Properties();

        // ����failover ���ϻָ�����
        // props.put("autoReconnect", "false");

        // ������ѯ
        // props.put("roundRobinLoadBalance", "false");

        props.put("user", "root");
        // props.put("password", "....");
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        PreparedStatement statment = null;
        ResultSet result = null;
        
        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8066/test?useUnicode=true&characterEncoding=utf-8&useServerPrepStmts=true", "sdfriend", "sdfriend");
        try {
        	for(int i=1300;i<1400;i++){
            statment = conn.prepareStatement("insert into SD_RELATION.RELATION_ORIGIN(sdid,f_sdid,app_id,reserve1,reserve2,reserve3) values(?,?,24,'','','')");
            statment.setLong(1, 35676);
            statment.setLong(2, 129+i);
           int id = statment.executeUpdate();
           if (statment != null) {
               try {
                   statment.close();
               } catch (Exception e) {
               }
           }
           statment = conn.prepareStatement("select LAST_INSERT_ID() as id");
           result = statment.executeQuery();
           result.next();
           Object lastInsertId = result.getLong("id");
           System.out.println(lastInsertId);
           if (result != null) {
               try {
                   result.close();
               } catch (Exception e) {
               }
           }
           if (statment != null) {
               try {
                   statment.close();
               } catch (Exception e) {
               }
           }
           
           statment = conn.prepareStatement("select * from SD_RELATION.RELATION_ORIGIN where sdid=35676 and f_sdid=129"+i);
           result = statment.executeQuery();
          if( result.next())
           lastInsertId = result.getLong("id");
           if (result != null) {
               try {
                   result.close();
               } catch (Exception e) {
               }
           }
           if (statment != null) {
               try {
                   statment.close();
               } catch (Exception e) {
               }
           }
        }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception e) {
                }
            }

            if (statment != null) {
                try {
                    statment.close();
                } catch (Exception e) {
                }
            }

            if (conn != null) {
                conn.close();
            }
        }
    }
}
