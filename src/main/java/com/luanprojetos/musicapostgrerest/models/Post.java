/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.models;

/**
 *
 * @author medilab
 */
public class Post {
    
    
    int codigo;
    String texto;
    String img;
    String criado_em; 
    int criado_por ;

    public Post(int codigo, String texto, String img, String criado_em, int criado_por) {
        this.codigo = codigo;
        this.texto = texto;
        this.img = img;
        this.criado_em = criado_em;
        this.criado_por = criado_por;
    }

    public Post(){
        
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
