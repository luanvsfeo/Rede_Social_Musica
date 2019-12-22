/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.connetion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.glassfish.jersey.*;

/**
 *
 * @author Luan
 */
public class ConexaoBd {

    /* ver uma maneira de deixa isso editaverl: driver,url,user e pass*/
    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://localhost:5432/postgres";// editar aqui  
    private String user = "postgres"; // editar aqui 
    private String pass = "admin69";// editar aqui 
    private Connection conn = null;

    public Connection getConnetion() {

        Connection con;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            return conn;
        } catch (ClassNotFoundException ex) {
            System.out.print(ex.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public void closeConnetion() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JSONArray ResultSetAsJSONArray(ResultSet rs) throws SQLException, JSONException {
        JSONArray jArray = new JSONArray();
        JSONObject jsonObject = null;
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        int index = 1;
        while (rs.next() && index < columnCount) {
            jsonObject = new JSONObject();

            String column = rsmd.getColumnName(index);
            Object value = rs.getObject(column);
            if (value == null) {
                jsonObject.put(column, "");
            } else if (value instanceof Integer) {
                jsonObject.put(column, (Integer) value);
            } else if (value instanceof String) {
                jsonObject.put(column, (String) value);
            } else if (value instanceof Boolean) {
                jsonObject.put(column, (Boolean) value);
            } else if (value instanceof Date) {
                jsonObject.put(column, ((Date) value).getTime());
            } else if (value instanceof Long) {
                jsonObject.put(column, (Long) value);
            } else if (value instanceof Double) {
                jsonObject.put(column, (Double) value);
            } else if (value instanceof Float) {
                jsonObject.put(column, (Float) value);
            } else if (value instanceof BigDecimal) {
                jsonObject.put(column, (BigDecimal) value);
            } else if (value instanceof Byte) {
                jsonObject.put(column, (Byte) value);
            } else if (value instanceof byte[]) {
                jsonObject.put(column, (byte[]) value);
            } else if (value instanceof Timestamp) {
                jsonObject.put(column, (Timestamp) value);
            } else {
                throw new IllegalArgumentException("Unmappable object type: " + value.getClass());
            }
            jArray.put(jsonObject);
            index++;
        }

        return jArray;
    }
}
