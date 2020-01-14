/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.dao;

import com.luanprojetos.musicapostgrerest.connetion.ConexaoBd;
import com.luanprojetos.musicapostgrerest.models.Musica;
import com.luanprojetos.musicapostgrerest.models.Post;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import javax.imageio.ImageIO;
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
            String SQL = "select p.codigo,p.texto,p.criado_em,p.criado_por,img_base_64 as img, m.nome as nome_musica\n"
                    + "from post p left join musica m on m.codigo = p.cod_musica\n"
                    + "where \n"
                    + "criado_por = ?\n"
                    + "order by criado_em desc ";

            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resposta.put(new JSONObject(new Post(rs.getInt("codigo"), rs.getString("texto"), rs.getString("img"), rs.getTimestamp("criado_em").toString(), rs.getInt("criado_por"), rs.getString("nome_musica"))));
            }

            return resposta;
        } catch (SQLException e) {
            return resposta;
        }
    }

    public JSONArray getPostsToFeed(int codigo) {
        JSONArray resposta = new JSONArray();
        try {
            String SQL = "select p.codigo,p.texto,p.criado_em,p.criado_por,img_base_64 as img, m.nome as nome_musica, u.nome as nome_criador, m.codigo as cod_musica\n"
                    + "from usuario u,post p left join musica m on m.codigo = p.cod_musica\n"
                    + "where \n"
                    + "u.codigo = p.criado_por and \n"
                    + "criado_por in ( select codigo_usuario_seguido from post_usuario as pu where pu.codigo_usuario = ?)\n"
                    + "order by criado_em desc ";

            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resposta.put(new JSONObject(new Post(rs.getInt("codigo"), rs.getString("texto"), rs.getString("img"), rs.getTimestamp("criado_em").toString(), rs.getInt("criado_por"), Integer.toString(rs.getInt("cod_musica")), rs.getString("nome_criador"), rs.getString("nome_musica"))));
            }

            return resposta;
        } catch (SQLException e) {
            return resposta;
        }
    }

    public boolean setNewPostWithImage(String json, InputStream inputfile) throws IOException, ParseException {
        BufferedImage img = ImageIO.read(inputfile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", baos);
        baos.flush();
        //byte[] fileContent = FileUtils.readFileToByteArray();
        String encodedString = Base64.getEncoder().encodeToString(baos.toByteArray());
        baos.close();
        JSONObject jsonObj = new JSONObject(json);
        String date = jsonObj.getString("criado_em");

        Date currentDate = new Date(Long.parseLong(date));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(currentDate);

        java.util.Date parsedDate = format.parse(dateString);
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        try {
            PreparedStatement stmt = null;
            String SQL;
            if (jsonObj.has("cod_musica")) {
                SQL = " insert into post(texto,criado_em,criado_por,img_base_64,cod_musica)values(?,?,?,?,?)";

                stmt = super.getConnetion().prepareStatement(SQL);
                stmt.setString(1, jsonObj.getString("texto"));
                stmt.setTimestamp(2, timestamp);
                stmt.setInt(3, Integer.parseInt(jsonObj.getString("criado_por")));
                stmt.setString(4, encodedString);
                stmt.setInt(5, jsonObj.getInt("cod_musica"));

            } else {
                SQL = " insert into post(texto,criado_em,criado_por,img_base_64)values(?,?,?,?)";

                stmt = super.getConnetion().prepareStatement(SQL);
                stmt.setString(1, jsonObj.getString("texto"));
                stmt.setTimestamp(2, timestamp);
                stmt.setInt(3, Integer.parseInt(jsonObj.getString("criado_por")));
                stmt.setString(4, encodedString);

            }

            stmt.execute();

            //System.out.println("foi");
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public boolean setNewPostWithoutImage(String json) throws IOException, ParseException {

        JSONObject jsonObj = new JSONObject(json);
        String date = jsonObj.getString("criado_em");

        Date currentDate = new Date(Long.parseLong(date));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(currentDate);

        java.util.Date parsedDate = format.parse(dateString);
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        try {
            PreparedStatement stmt;
            String SQL;
            if (jsonObj.has("cod_musica")) {
                SQL = " insert into post(texto,criado_em,criado_por,cod_musica)values(?,?,?,?)";

                stmt = super.getConnetion().prepareStatement(SQL);
                stmt.setString(1, jsonObj.getString("texto"));
                stmt.setTimestamp(2, timestamp);
                stmt.setInt(3, Integer.parseInt(jsonObj.getString("criado_por")));
                stmt.setInt(4, Integer.parseInt(jsonObj.getString("cod_musica")));
            } else {
                SQL = " insert into post(texto,criado_em,criado_por)values(?,?,?)";

                stmt = super.getConnetion().prepareStatement(SQL);
                stmt.setString(1, jsonObj.getString("texto"));
                stmt.setTimestamp(2, timestamp);
                stmt.setInt(3, Integer.parseInt(jsonObj.getString("criado_por")));

            }

            stmt.execute();

            //System.out.println("foi");
            return true;
        } catch (SQLException e) {
            return false;
        }

    }
}
