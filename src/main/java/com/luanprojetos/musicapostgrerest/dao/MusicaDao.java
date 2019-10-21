/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.dao;

import com.luanprojetos.musicapostgrerest.connetion.ConexaoBd;
import com.luanprojetos.musicapostgrerest.models.Musica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Luan
 */
public class MusicaDao extends ConexaoBd {

    public JSONArray getMusicaByName(String nome) {
        JSONArray resposta = new JSONArray();
        try {
            String SQL = "select m.codigo,m.nome,m.data_lancamento,a.nome as artista from Musica m, Artista a "
                    + "where m.id_artista = a.codigo AND"
                    + " m.nome like '%" + nome.toUpperCase() + "%'";
            PreparedStatement stmt = super.getConnetion().prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resposta.put(new JSONObject(new Musica(rs.getInt("codigo"), rs.getString("nome"), rs.getDate("data_lancamento").toString(), rs.getString("artista")).getAsJsonString()));
            }

            return resposta;
        } catch (SQLException e) {
            return null;
        }

    }

}
