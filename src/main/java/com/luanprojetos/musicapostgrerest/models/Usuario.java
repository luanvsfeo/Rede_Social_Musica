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
public class Usuario {

    private int codigo;
    private String nome;
    private String login;
    private String senha;
    private String email;

    public Usuario(int codigo, String nome, String login, String senha, String email) {
        this.codigo = codigo;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Usuario() {

    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
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

    public Usuario fromStringToUsuario(String json) {
        return new Gson().fromJson(json, Usuario.class);
    }
}
