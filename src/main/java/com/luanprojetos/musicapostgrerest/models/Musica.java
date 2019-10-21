/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.models;

import com.google.gson.Gson;
import org.json.JSONObject;

/**
 *
 * @author Luan
 */
public class Musica {

    private int codigo;
    private String nome;
    private String dataLancamento;
    private String idArtista;
    private String nomeArtista;

    public Musica(int codigo, String nome, String dataLancamento) {
        this.codigo = codigo;
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.idArtista = idArtista;
    }

    public Musica(int codigo, String nome, String dataLancamento, String nomeArtista) {
        this.codigo = codigo;
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.nomeArtista = nomeArtista;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public String getIdArtista() {
        return idArtista;
    }

    public JSONObject getAsJSONObject() {
        return new JSONObject(this);
    }

    public String getAsJsonString() {
        return new Gson().toJson(this);
    }

    public JSONObject fromStringToJSONObject(String json) {
        return new JSONObject(new Gson().toJson(json));
    }

    public Musica fromStringToUsuario(String json) {
        return new Gson().fromJson(json, Musica.class);
    }
}
