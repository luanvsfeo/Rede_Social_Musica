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
}
