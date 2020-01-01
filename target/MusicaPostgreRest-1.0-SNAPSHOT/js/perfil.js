/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var url = window.location.href
url = url.substring(0, url.lastIndexOf('/'));
$(document).ready(function () {
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
                console.log(data)
                for (var k in data.posts) {
                    if (data.posts[k].hasOwnProperty("img") && data.posts[k].hasOwnProperty("nome_musica")) {
                        $(".container").append(
                                `<div class="card mb-4 shadow-sm" id="${data.posts[k].codigo}" >
                        <div class="card-body">
                        <p class="card-text">${data.posts[k].texto}</p>
                        <img class="img-fluid img-border" src="data:image/png;base64,${data.posts[k].img}">
                        <p class="card-text"><small class="text-muted">Postado em ${data.posts[k].criado_em}</small></p></div>
                        <div class="card-footer">
                        <p class="card-text">Ouvindo: ${data.posts[k].nome_musica}</p>
                        </div>`
                                )
                    } else if (data.posts[k].hasOwnProperty("img") && !data.posts[k].hasOwnProperty("nome_musica")) {
                        $(".container").append(
                                `<div class="card mb-4 shadow-sm" id="${data.posts[k].codigo}" >
                        <div class="card-body">
                        <p class="card-text">${data.posts[k].texto}</p>
                        <img class="img-fluid img-border" src="data:image/png;base64,${data.posts[k].img}">
                        <p class="card-text"><small class="text-muted">Postado em ${data.posts[k].criado_em}</small></p></div>
                        </div>`)
                    } else if (data.posts[k].hasOwnProperty("nome_musica") && !data.posts[k].hasOwnProperty("img")) {
                        $(".container").append(
                                `<div class="card mb-4 shadow-sm" id="${data.posts[k].codigo}" >
                        <div class="card-body">
                        <p class="card-text">${data.posts[k].texto}</p>
                        <p class="card-text"><small class="text-muted">Postado em ${data.posts[k].criado_em}</small></p></div>
                        <div class="card-footer">
                        <p class="card-text">Ouvindo: ${data.posts[k].nome_musica}</p>
                        </div>`)

                    } else {
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


$("#btn-pesquisar").on("click", function () {
    var nome = $("#example-search-input").val();
    $.ajax({
        url: `${url}/rest/usuario/nome/${nome}`,
        type: "GET",
        success: function (data) {
            $(".container").empty();
            console.log(data)
            for (var k in data.usuarios) {
                $(".container").append(`
                <div id="${data.usuarios[k].codigo}"class="input-group margin-bottom">       
                <div class="form-control">${data.usuarios[k].nome}</div>
                 <div class="input-group-prepend">
                     <button type="button" class="btn btn-info">Seguir</button>
                </div>
                </div>
      `)
            }
        }
    });

});


$("#logout").on("click", function () {
    localStorage.removeItem('usu')
    window.location.href = `${url}/login.html`;

});