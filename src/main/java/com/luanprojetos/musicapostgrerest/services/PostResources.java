/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.services;

import com.google.gson.Gson;
import com.luanprojetos.musicapostgrerest.dao.PostDao;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author medilab
 */
@Path("/post")
public class PostResources {

    @POST
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response newPost(@FormDataParam("file") File inputfile, @FormDataParam("file") FormDataContentDisposition fileMetaData, @FormDataParam("json") String json) throws IOException {//

       

        if (new PostDao().setNewPost(json,inputfile)) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }

    }

}
