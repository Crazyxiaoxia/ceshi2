/*
 * FileName: ToolSql.java
 * 北京神州新桥科技有限公司
 * Copyright 2013-2014 (C) Sino-Bridge Software CO., LTD. All Rights Reserved.
 */
package com.haili.site.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description: 
 * </p>
 *
 * @author admin
 * @version 1.0

 * <p>
 * History: 
 *
 * Date                     Author         Version     Description
 * ---------------------------------------------------------------------------------
 * 2016年2月26日 下午10:01:17          admin        1.0         To create
 * </p>
 *
 * @since 
 * @see     
 */
public class ToolSql {
    public static String sqlIn(String tableName, String colunm, List<String> list) {
        StringBuffer sql = new StringBuffer("select * from " + tableName + " where " + colunm + " in (");
        for (String n : list) {
            sql.append("'" + n + "'" + ",");
        }
        String sqlString = sql.toString().substring(0, sql.toString().lastIndexOf(","));
        StringBuffer buffer = new StringBuffer();
        buffer.append(sqlString);
        buffer.append(")");
        return buffer.toString();
    }

    public static String sqlIn(String tableName, String colunm, String[] list) {
        StringBuilder sql = new StringBuilder("select * from " + tableName + " where " + colunm + " in (");
        for (String n : list) {
            sql.append("'" + n + "'" + ",");
        }
        String sqlString = sql.toString().substring(0, sql.toString().lastIndexOf(","));
        StringBuffer buffer = new StringBuffer();
        buffer.append(sqlString);
        buffer.append(")");
        return buffer.toString();
    }

    public static String sqlIn(String[] list) {
        StringBuilder sql = new StringBuilder(" in (");
        for (String n : list) {
            sql.append("'" + n + "'" + ",");
        }
        String sqlString = sql.toString().substring(0, sql.toString().lastIndexOf(","));
        StringBuffer buffer = new StringBuffer();
        buffer.append(sqlString);
        buffer.append(")");
        return buffer.toString();
    }

    public static String sqlIn(List<String> list) {
        StringBuffer buffer = new StringBuffer();
        StringBuilder sql = new StringBuilder(" in (");
        for (String n : list) {
            sql.append("'" + n + "'" + ",");
        }
        if (list.size() > 0) {
            String sqlString = sql.toString().substring(0, sql.toString().lastIndexOf(","));
            buffer.append(sqlString);
            buffer.append(")");
        }
        return buffer.toString();
    }

    public static String sqlNotIn(String tableName, String colunm, String[] list) {
        StringBuilder sql = new StringBuilder("select * from " + tableName + " where " + colunm + " not in (");
        for (String n : list) {
            sql.append("'" + n + "'" + ",");
        }
        String sqlString = sql.toString().substring(0, sql.toString().lastIndexOf(","));
        StringBuffer buffer = new StringBuffer();
        buffer.append(sqlString);
        buffer.append(")");
        return buffer.toString();
    }

    public static String sqlIn(String tableName, String colunm, String value, String split) {
        StringBuilder sql = new StringBuilder("select * from " + tableName + " where " + colunm + " in (");
        String[] list = value.split(split);
        for (String n : list) {
            sql.append("'" + n + "'" + ",");
        }
        String sqlString = sql.toString().substring(0, sql.toString().lastIndexOf(","));
        StringBuffer buffer = new StringBuffer();
        buffer.append(sqlString);
        buffer.append(")");
        return buffer.toString();
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("q");
        list.add("q");
        list.add("q");
        list.add("q");
        String aString = "1";
        String sqlString = sqlIn("sys_user", "ids", aString.split(","));
        System.err.println(sqlString);

    }
}
