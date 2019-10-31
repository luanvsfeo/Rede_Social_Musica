/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.dao;

import com.luanprojetos.musicapostgrerest.connetion.ConexaoBd;
import com.luanprojetos.musicapostgrerest.models.Musica;
import com.luanprojetos.musicapostgrerest.models.Post;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Luan
 */
public class PostDao extends ConexaoBd {

    public JSONArray getPostByUser(int codigo) {
        JSONArray resposta = new JSONArray();
        try {
            String SQL = "select * from post where criado_por = ?  order by criado_em desc ";
//codigo para postgres: select codigo,texto,criado_em,criado_por,encode(img, 'escape') img from post where criado_por = ?  order by criado_em desc 
            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            stmt.setString(1, Integer.toString(codigo));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resposta.put(new JSONObject(new Post(rs.getInt("codigo"), rs.getString("texto"), rs.getString("img"), rs.getTimestamp("criado_em").toString(), rs.getInt("criado_por"))));
            }

            return resposta;
        } catch (SQLException e) {
            return resposta;
        }
    }

    public boolean setNewPostWithImage(String json, File inputfile) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(inputfile);
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        JSONObject jsonObj = new JSONObject(json);
        String date = jsonObj.getString("criado_em");

        Date currentDate = new Date(Long.parseLong(date));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateString = format.format(currentDate);

        try {
            String SQL = "insert into post(codigo,texto,criado_em,criado_por,img)\n"
                    + "values((SELECT GEN_ID( id_post, 1 ) FROM RDB$DATABASE),?,?,?,?)";
            //postgres: insert into post(texto,criado_em,criado_por,img)values(?,?,?,?)
            
            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            stmt.setString(1, jsonObj.getString("texto"));
            stmt.setString(2, dateString);
            stmt.setString(3, jsonObj.getString("criado_por"));
            stmt.setString(4, encodedString);

            stmt.execute();

            System.out.println("foi");

            return true;
        } catch (SQLException e) {
            return false;
        }

    }
    
    public boolean setNewPostWithoutImage(String json) throws IOException {

        JSONObject jsonObj = new JSONObject(json);
        String date = jsonObj.getString("criado_em");

        Date currentDate = new Date(Long.parseLong(date));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateString = format.format(currentDate);

        try {
            String SQL = "insert into post(codigo,texto,criado_em,criado_por)\n"
                    + "values((SELECT GEN_ID( id_post, 1 ) FROM RDB$DATABASE),?,?,?)";
            //postgres: insert into post(texto,criado_em,criado_por)values(?,?,?)
            
            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            stmt.setString(1, jsonObj.getString("texto"));
            stmt.setString(2, dateString);
            stmt.setString(3, jsonObj.getString("criado_por"));

            stmt.execute();

            System.out.println("foi");

            return true;
        } catch (SQLException e) {
            return false;
        }

    }
}
