/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.dao;

import com.luanprojetos.musicapostgrerest.connetion.ConexaoBd;
import com.luanprojetos.musicapostgrerest.models.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Luan
 */
public class UsuarioDao extends ConexaoBd {

    public JSONObject getJSONObjectByUsuario(Usuario usu) {
        JSONObject jsonResposta = null;
        try {
            String SQL = "Select * from usuario where login = ? and senha  = ? ";
            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            stmt.setString(1, usu.getLogin());
            stmt.setString(2, usu.getSenha());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuTemp = new Usuario(rs.getInt("codigo"), rs.getString("nome"));
                jsonResposta = new JSONObject(usuTemp.getAsJsonString());
            }
            return jsonResposta;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public JSONObject getUsuarioByCodigo(int codigo) {
        JSONObject jsonResposta = null;
        try {
            String SQL = "Select * from usuario where codigo = ?  ";
            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuTemp = new Usuario(rs.getInt("codigo"), rs.getString("nome"));
                jsonResposta = new JSONObject(usuTemp.getAsJsonString());
            }
            return jsonResposta;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public JSONArray getUsuarioByName(String nome) {
        JSONArray jsonResposta = new JSONArray();
        try {
            String SQL = "Select * from usuario where nome like '%" + nome + "%'";

            /*
            
                select u.* ,'sim' AS segue from usuario u ,post_usuario pu
                where
                u.codigo = pu.codigo_usuario_seguido
                and pu.codigo_usuario = ?
                and u.nome like '%" + nome + "%'"
                union
                select u.*,'nao' AS segue from usuario u
                where
                u.codigo not in (select pu.codigo_usuario_seguido from post_usuario pu where pu.codigo_usuario = ? )
                and  u.codigo <>  ?
                and u.nome like '%" + nome + "%'"
            
            
             */
            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            //stmt.setInt(0, 0);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuTemp = new Usuario(rs.getInt("codigo"), rs.getString("nome"));
                jsonResposta.put(new JSONObject(usuTemp.getAsJsonString()));//.put("segue",rs.getString("segue")));
            }

            return jsonResposta;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
