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
            url: `${url}/rest/usuario/${codigo}/feed`,
            type: "GET",
            success: function (data) {


                for (var k in data.posts) {
                    if (data.posts[k].hasOwnProperty("img")) {
                        $(".container").append(
                                `<div class="card mb-4 shadow-sm" id="${data.posts[k].codigo}" >
                        <div class="card-header">${data.posts[k].nome_criador}</div>
                        <div class="card-body">
                        <p class="card-text">${data.posts[k].texto}</p>
                        <img class="img-fluid img-border" src="data:image/png;base64,${data.posts[k].img}">
                        </div>`
                                )
                    } else {//card-img-bottom
                        $(".container").append(
                                `<div class="card mb-4 shadow-sm" id="${data.posts[k].codigo}" >
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
    nome = nome.toUpperCase();
    $.ajax({
        url: `${url}/rest/musica/${nome}`,
        type: "GET",
        success: function (data) {
            $(".container").empty();

            for (var k in data) {
                $(".container").append(`<div class="card mb-4 shadow-sm">
                <div class="card-header">${data[k].nome}</div>
                <div class="card-body">${data[k].nomeArtista}</div>
                <div class="card-body">${data[k].dataLancamento}</div>
            </div>`)
            }
        }
    });

});


$("#btn-publicar").on("click", function () {
    var texto = $("#texto-post").val();

    var json = {};

    json['texto'] = texto;
    json['criado_por'] = localStorage.getItem('usu');
    json['criado_em'] = Date.now().toString();

    file_data = $("#foto")[0].files[0];



    if (file_data) {
        console.log(file_data)
        var nome = file_data.name



        if (file_data != null && nome.indexOf(".jpg") > 0 || nome.indexOf(".png") > 0) {

            var form = new FormData();
            form.append('file', file_data);
            form.append('json', JSON.stringify(json));

            $.ajax({
                url: `${url}/rest/post/`,
                type: "POST",
                dataType: "json",
                contentType: false,
                processData: false,
                enctype: 'multipart/form-data',
                data: form,
                success: function (data) {//verificar pq nao esta caindo no sucess
                    $("#texto-post").val("");
                    console.log("a")

                }, error: function (request, status, error) {

                    if (request.status == 200) {
                        $("#texto-post").val("");
                        $('.custom-file-label').html("")
                    } else {
                        alert(request.status);
                    }
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
            dataType: "json",
            contentType: false,
            processData: false,
            enctype: 'multipart/form-data',
            data: form,
            success: function (data) {//verificar pq nao esta caindo no sucess
                $("#texto-post").val("");
                console.log("a")

            }, error: function (request, status, error) {

                if (request.status == 200) {
                    $("#texto-post").val("");
                    $('.custom-file-label').html("")
                } else {
                    alert(request.status);
                }
            }
        });
    }






});

$('#foto').on('change', function () {
    //get the file name
    var fileName = $(this).val();
    fileName = fileName.substring(fileName.lastIndexOf('th') + 3);
    //replace the "Choose a file" label
    $(this).next('.custom-file-label').html(fileName);
})


