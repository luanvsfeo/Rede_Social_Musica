/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.services;

import com.luanprojetos.musicapostgrerest.dao.MusicaDao;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Luan
 */
@Path("/musica")
public class MusicaResources {

    @GET
    @Path("{nome}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMusicByName(@PathParam("nome") String nome) {

        JSONArray resposta = new MusicaDao().getMusicaByName(nome);

        if (resposta.isNull(0)) {
            return Response.serverError().build();
        } else {
            return Response.ok().entity(resposta.toString()).build();
        }

    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMusicByName() {

        return Response.ok().entity("oi").build();

    }
    
    
   
    

}
