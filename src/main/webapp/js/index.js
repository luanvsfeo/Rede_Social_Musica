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
            url: `${url}/rest/usuario/${codigo}/feed`,
            type: "GET",
            success: function (data) {
                console.log(data)

                for (var k in data.posts) {
                    if (data.posts[k].hasOwnProperty("img") && data.posts[k].hasOwnProperty("nome_musica")) {
                        $(".container").append(
                                `<div class="card mb-4 " id="${data.posts[k].codigo}" >
                        <div class="card-header">${data.posts[k].nome_criador}</div>
                        <div class="card-body">
                        <p class="card-text">${data.posts[k].texto}</p>
                        <img class="img-fluid img-border" src="data:image/png;base64,${data.posts[k].img}">
                        </div> <div class="card-footer">
                        <p class="card-text">Ouvindo: ${data.posts[k].nome_musica}</p>
                        </div>`
                                )
                    } else if (data.posts[k].hasOwnProperty("nome_musica")) {//card-img-bottom
                        $(".container").append(
                                `<div class="card mb-4 " id="${data.posts[k].codigo}" >
                        <div class="card-header">${data.posts[k].nome_criador}</div>
                        <div class="card-body">
                        <p class="card-text">${data.posts[k].texto}</p>
                        </div>
                        <div class="card-footer">
                        <p class="card-text">Ouvindo: ${data.posts[k].nome_musica}</p>
                        </div>`
                                )
                    } else if (data.posts[k].hasOwnProperty("img")) {
                        $(".container").append(
                                `<div class="card mb-4 " id="${data.posts[k].codigo}" >
                        <div class="card-header">${data.posts[k].nome_criador}</div>
                        <div class="card-body">
                        <p class="card-text">${data.posts[k].texto}</p>
                        <img class="img-fluid img-border" src="data:image/png;base64,${data.posts[k].img}">
                        </div>`
                                )
                    } else {
                        $(".container").append(
                                `<div class="card mb-4 " id="${data.posts[k].codigo}" >
                        <div class="card-header">${data.posts[k].nome_criador}</div>
                        <div class="card-body">
                        <p class="card-text">${data.posts[k].texto}</p>
                        </div>`
                                )
                    }


                }



            }
        });
    }


});


$("#logout").on("click", function () {
    localStorage.removeItem('usu')
    window.location.href = `${url}/login.html`;

});

$("#btn-pesquisar").on("click", function () {
    var nome = $("#example-search-input").val();
     var codigo = document.cookie;
    codigo = localStorage.getItem('usu')

    $.ajax({
        url: `${url}/rest/usuario/nome/${codigo}/${nome}`,
        type: "GET",
        success: function (data) {
            $(".container").empty();

            for (var k in data.usuarios) {
                $(".container").append(`
                <div id="${data.usuarios[k].codigo}"class="input-group margin-bottom">       
                <div class="form-control">${data.usuarios[k].nome}</div>
                 <div class="input-group-prepend">
                     <button type="button" class="btn btn-info btn-seguir">Seguir</button>
                </div>
                </div>
      `)
            }
        }
    });

});



$("#btn-pesquisar-musica").on("click", function () {

    var nome = $("#example-search-input-12").val();

    nome = nome.toUpperCase();

    $.ajax({
        url: `${url}/rest/musica/${nome}`,
        type: "GET",
        success: function (data) {
            $(".modal-body").empty();

            for (var k in data) {
                $(".modal-body").append(`<div  id="${data[k].codigo}"  class="alert alert-dark">
                <div ><span class="nome-musica">${data[k].nome}</span>
                <small class="artista-musica">${data[k].nomeArtista}</small></div>
                <div>
                <button type="button" class="btn btn-light "> 
                <span class="oi oi-play-circle"  aria-hidden="true"></span>
                </button>       
                
                <button type="button" class="btn btn-light seleciona">selecionar</button>
                </div>
            </div>`)
            }
        }
    });



});

$(document).on('click', '#btn-publicar', function () {
    var texto = $("#texto-post").val();

    var json = {};

    json['texto'] = texto;
    json['criado_por'] = localStorage.getItem('usu');
    json['criado_em'] = Date.now().toString();
    json['cod_musica'] = $('.musica').attr('id');

    file_data = $("#foto")[0].files[0];



    if (file_data) {
        console.log(file_data)
        var nome = file_data.name



        if (file_data != null && nome.indexOf(".jpg") > 0 || nome.indexOf(".png") > 0 || nome.indexOf(".jpeg") > 0) {

            var form = "";
            form = new FormData();

            form.append('file', file_data);
            form.append('json', JSON.stringify(json));
            console.log(JSON.stringify(json))
            $.ajax({
                url: `${url}/rest/post/`,
                type: "POST",
                enctype: 'multipart/form-data',
                data: form,
                processData: false,
                contentType: false,
                success: function (data) {//verificar pq nao esta caindo no sucess
                    $("#texto-post").val("");
                    $(".musica").attr("placeholder","");
                    $('input').val("");
                    $('label.custom-file-label').text("");
                }, error: function (data) {

                   console.log("erro")
                }
            });
        } else {
            alert("tipo invalido, apenas .jpg ou .png")
        }
    } else {
        var form = new FormData();

        form.append('json', JSON.stringify(json));

        $.ajax({
            url: `${url}/rest/post/`,
            type: "POST",
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            data: form,
            success: function (data) {//verificar pq nao esta caindo no sucess
                $("#texto-post").val("");
                $(".musica").attr("placeholder","");
                $('input').val("");
                $('label.custom-file-label').text("");
                console.log("a")

            }, error: function (data) {

               console.log("erro")
            }
        });
    }


});

$('#foto').on('change', function () {
    var fileName = $(this).val();
    fileName = fileName.substring(fileName.lastIndexOf('th') + 3);
    $(this).next('.custom-file-label').html(fileName);
})


$('#exampleModalLong').on('hidden.bs.modal', function () {
    $(".modal-body").empty();
    $("#example-search-input-12").val("");
})


$(document).on('click', '.btn-seguir', function () {

    var id = $(this).parent().parent().attr('id')



    var json = {};
    json['id_usu'] = localStorage.getItem('usu');
    json['id_usu_seguir'] = id;

    $.ajax({
        url: `${url}/rest/usuario/seguir/`,
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
            $("#texto-post").val("");
            console.log("a")

        }
    });




})


$(document).on('click', '.seleciona', function () {

    var clicado = $(this).parents()[1].id;
    var clicadoNome = $(this).parents()[1];
    var nome = $(clicadoNome).find('.nome-musica').text();
    var artista = $(clicadoNome).find('.artista-musica').text();

    $('.musica').attr('id', clicado)
    $('.fade').trigger('click');

    $('.musica').attr('placeholder', nome + ' ' + artista);

})
