/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.services;

import com.luanprojetos.musicapostgrerest.connetion.ConexaoBd;
import com.luanprojetos.musicapostgrerest.dao.UsuarioDao;
import com.luanprojetos.musicapostgrerest.models.Usuario;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 *
 * @author Luan
 */
@Path("/usuario")
public class UsuarioResources {

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String json) {
        Usuario usu = new Usuario().fromStringToUsuario(json);
        JSONObject resposta = new UsuarioDao().getJSONObjectByUsuario(usu);
        if (resposta != null) {
            return Response.ok().entity(resposta.toString()).build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("{codigo}/feed")
    public Response getFeed(@PathParam("codigo") int codigo) {
        
        return Response.ok().build();
    }
    
    
   

}
