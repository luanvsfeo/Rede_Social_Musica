/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = window.location.href
url = url.substring(0, url.lastIndexOf('/'));
$(function () {
    var codigo = document.cookie;
    codigo = codigo.substring(codigo.indexOf("=") + 1, codigo.indexOf("_"))
    $.ajax({
        url: `${url}/rest/usuario/${codigo}/feed`,
        type: "GET",
        success: function (data) {
            console.log(data)
        }
    });
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
