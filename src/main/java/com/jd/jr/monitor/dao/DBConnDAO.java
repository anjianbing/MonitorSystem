package com.jd.jr.monitor.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lingchungui on 14-9-15.
 */
public class DBConnDAO {

    private Connection conn = null ;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public DBConnDAO(Connection connection) {
        this.conn = connection;
    }


/*
*查询 返回结果: Object对象数组
*
*/

    public List<Object[]> queryArray(String sql, Object... params) {
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] data;
        try {
            pst = conn.prepareStatement(sql); // 得到对象PreparedStatement
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]); // 为预编译sql设置参数
                }
            }
            rs = pst.executeQuery(); // 执行SQL语句
            //获取元数据
            ResultSetMetaData rsd = rs.getMetaData();
            while (rs.next()) {
                //每一行都需一个数组
                data = new Object[rsd.getColumnCount()];
                for (int i = 0; i < rsd.getColumnCount(); i++) {
                    data[i] = rs.getObject(i + 1);
                }
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 处理ClassNotFoundException异常
        } finally {
            closeALL(rs, pst, conn);
        }
        return list;
    }

/*
*查询 返回结果: 字符串数组
*
*/


    public List<String[]> queryStringArray(String sql, Object... params){
        List<String[]> list = new ArrayList<String[]>();
        String[] data;
        try {
            pst = conn.prepareStatement(sql); // 得到对象PreparedStatement
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]); // 为预编译sql设置参数
                }
            }
            rs = pst.executeQuery(); // 执行SQL语句
            //获取元数据
            ResultSetMetaData rsd = rs.getMetaData();
            while (rs.next()) {
                //每一行都需一个数组
                data = new String[rsd.getColumnCount()];
                for (int i = 0; i < rsd.getColumnCount(); i++) {
                    data[i] = rs.getString(i + 1);
                }
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 处理ClassNotFoundException异常
        } finally {
            closeALL(rs, pst, conn);
        }
        return list;
    }

/*
*查询 返回结果: map list集合
*
*/

    public List<Map<String, Object>> queryMap(String sql, Object... params) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> data;
        ResultSetMetaData rsMetaData;
        try {
            pst = conn.prepareStatement(sql); // 得到对象PreparedStatement
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]); // 为预编译sql设置参数
                }
            }
            rs = pst.executeQuery(); // 执行SQL语句
            rsMetaData = rs.getMetaData();
            while (rs.next()) {
                //每一行都需一个map
                data = new HashMap<String, Object>();
                for(int i=1; i<=rsMetaData.getColumnCount(); i++){
                    data.put(rsMetaData.getColumnName(i), rs.getObject(i));
                }
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 处理ClassNotFoundException异常
        } finally {
            closeALL(rs, pst, conn);
        }
        return list;
    }

/*
*执行 增、删、改
*
*/


    public int execute(String sql, Object... params) {
        int row = 0;
        try {
            pst = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }
            row = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeALL(null, pst, conn);
        }
        return row;
    }



    public int executeBatch(String sql, List<Object[]> params) {
        return this.executeBatch(sql, params, true);
    }


/*
*批量 执行 增、删、改
*
*/

    public int executeBatch(String sql, List<Object[]> params, boolean transaction) {
        int[] rows = new int[0];
        try {
            if(transaction){
                //启动事务
                conn.setAutoCommit(false);
            }
            pst = conn.prepareStatement(sql);
            for (Object[] ps : params) {
                for (int i = 0; i < ps.length; i++) {
                    pst.setObject(i + 1, ps[i]);
                }
                pst.addBatch();
            }
            rows = pst.executeBatch();
            if(transaction){
                //提交事务
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeALL(null, pst, conn);
        }
        return rows.length;
    }

    /**
     * 批量执行多条sql语句,使用事务机制
     *
     * @param sql
     * @return
     */

    public int executeBatch(String... sql) {
        int[] rows = new int[0];
        try {
            //启动事务
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            for (String s : sql) {
                stmt.addBatch(s);
            }
            rows = stmt.executeBatch();
            //提交事务
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeALL(null, pst, conn);
        }
        return rows.length;
    }


    public void closeALL(ResultSet rs, Statement pst, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null && (!conn.isClosed())) {
                conn.close();
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

}
