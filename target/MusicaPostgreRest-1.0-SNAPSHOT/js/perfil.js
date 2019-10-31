/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var url = window.location.href
url = url.substring(0, url.lastIndexOf('/'));
$(function () {
    var codigo = document.cookie;
    codigo = localStorage.getItem('usu')
    if (codigo == null || codigo == 0) {
        window.location.href = `${url}/login.html`;
    } else {
        $.ajax({
            url: `${url}/rest/usuario/${codigo}/posts`,
            type: "GET",
            success: function (data) {

                $("#nome-usuario").text(data.nome);

                for (var k in data.posts) {
                    if (data.posts[k].hasOwnProperty("img")) {
                        $(".container").append(
                        `<div class="card mb-4 shadow-sm" id="${data.posts[k].codigo}" >
                        <div class="card-body">
                        <p class="card-text">${data.posts[k].texto}</p>
                        <img class="img-fluid img-border" src="data:image/png;base64,${data.posts[k].img}">
                        <p class="card-text"><small class="text-muted">Postado em ${data.posts[k].criado_em}</small></p></div>
                        </div>`
                                )
                    } else {//card-img-bottom
                        $(".container").append(
                        `<div class="card mb-4 shadow-sm" id="${data.posts[k].codigo}" >
                        <div class="card-body">
                        <p class="card-text">${data.posts[k].texto}</p>
                        <p class="card-text"><small class="text-muted">Postado em ${data.posts[k].criado_em}</small></p></div>
                        </div>`
                                )
                    }

                }
            }
        });
    }


});