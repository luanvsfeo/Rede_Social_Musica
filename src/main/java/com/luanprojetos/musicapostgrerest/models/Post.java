/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.models;

/**
 *
 * @author Luan
 */
public class Post {

    int codigo;
    String texto;
    String img;
    String criado_em;
    int criado_por;
    String cod_musica;
    String nome_criador;
    String nome_musica;

    public Post(int codigo, String texto, String img, String criado_em, int criado_por, String nome_musica) {
        //usado para perfil
        this.codigo = codigo;
        this.texto = texto;
        this.img = img;
        this.criado_em = criado_em;
        this.criado_por = criado_por;
        this.nome_musica = nome_musica;
    }

    public Post(int codigo, String texto, String img, String criado_em, int criado_por, String cod_musica, String nome_musica) {
        //usado para perfil
        this.codigo = codigo;
        this.texto = texto;
        this.img = img;
        this.criado_em = criado_em;
        this.criado_por = criado_por;
        this.cod_musica = cod_musica;
        this.nome_musica = nome_musica;
    }

    public Post(int codigo, String texto, String img, String criado_em, int criado_por, String cod_musica, String nome_criador, String nome_musica) {
        //usado no feed
        this.codigo = codigo;
        this.texto = texto;
        this.img = img;
        this.criado_em = criado_em;
        this.criado_por = criado_por;
        this.nome_criador = nome_criador;
        this.nome_musica = nome_musica;
    }

    public Post() {

    }

    public String getCod_musica() {
        return cod_musica;
    }

    public void setCod_musica(String cod_musica) {
        this.cod_musica = cod_musica;
    }

    
    public String getNome_musica() {
        return nome_musica;
    }

    public void setNome_musica(String nome_musica) {
        this.nome_musica = nome_musica;
    }

    public String getNome_criador() {
        return nome_criador;
    }

    public void setNome_criador(String nome_criador) {
        this.nome_criador = nome_criador;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCriado_em() {
        return criado_em;
    }

    public void setCriado_em(String criado_em) {
        this.criado_em = criado_em;
    }

    public int getCriado_por() {
        return criado_por;
    }

    public void setCriado_por(int criado_por) {
        this.criado_por = criado_por;
    }

}
