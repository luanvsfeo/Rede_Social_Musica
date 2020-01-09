/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.dao;

import com.luanprojetos.musicapostgrerest.connetion.ConexaoBd;
import com.luanprojetos.musicapostgrerest.models.Usuario;
import com.luanprojetos.musicapostgrerest.models.JavaEmail;
import static com.luanprojetos.musicapostgrerest.models.StaticFunctions.getRandomNumberAsVerificationCod;
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
            String SQL = "Select * from usuario where login = ? and senha  = ? and cod_verificacao is null";
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

    public boolean followUsuario(String json) {
        JSONObject jsonObj = new JSONObject(json);
        try {

            String SQL = "insert into post_usuario(codigo_usuario,codigo_usuario_seguido) values(?,?)";
            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            stmt.setInt(1, Integer.parseInt(jsonObj.getString("id_usu")));
            stmt.setInt(2, Integer.parseInt(jsonObj.getString("id_usu_seguir")));
            stmt.execute();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean newUsuario(String json) {
        JSONObject jsonObj = new JSONObject(json);
        String codVerificador = Integer.toString(getRandomNumberAsVerificationCod());
        try {
            String SQL = "insert into Usuario (nome,login,senha,email,cod_verificacao)\n"
                    + "            select distinct ? as nome,? as login, ? as senha, ? as email , ? as cod_verificacao from usuario\n"
                    + "            where \n"
                    + "            not exists (select email from usuario where email = ? )"
                    + "and not exists (select login from usuario where login = ? ) ";


            /*
            insert into Usuario (nome,login,senha,email,cod_verificacao)
            select distinct ? as nome,? as login, ? as senha, ? as email , ? as cod_verificacao from usuario
            where 
            not exists (select email from usuario where email = ?)

            
            
             */
            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            stmt.setString(1, jsonObj.getString("nome"));
            stmt.setString(2, jsonObj.getString("login"));
            stmt.setString(3, jsonObj.getString("senha"));
            stmt.setString(4, jsonObj.getString("email"));
            stmt.setString(5, codVerificador);
            stmt.setString(6, jsonObj.getString("email"));
            stmt.setString(7, jsonObj.getString("login"));

            stmt.execute();

            if (new JavaEmail().SendEmail(jsonObj.getString("email"), codVerificador)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }
    }

    public boolean validateUsuario(String json) {
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(json); // email e cod_verificacao
            String SQL = "update usuario set cod_verificacao = null where email = ? and cod_verificacao = ?";
            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            stmt.setString(1, jsonObj.getString("email"));
            stmt.setString(2, jsonObj.getString("cod_verificacao"));
            stmt.execute();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
