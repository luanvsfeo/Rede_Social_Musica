/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = window.location.href
url = url.substring(0, url.lastIndexOf('/'));

$("#btn-enviar").on("click", function () {
    console.log(url)
    var login = $("#inputLogin").val();
    var senha = $("#inputSenha").val();
    var json = {};

    json['login'] = login;
    json['senha'] = senha;

    console.log(json)
    $.ajax({
        url: `${url}/rest/usuario/login`,
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
            document.cookie = "usu=" + data['codigo'] + "_" + data['nome'] + "";
            localStorage.setItem('usu', data['codigo'].toString())
            window.location.href = 'index.html';
        }
    });

});



$("#btn-cadastrar").on("click",function (){
    
   var login = $("#inputLogin").val();
   var nome = $("#inputNome").val();
   var senha = $("#inputSenha").val();
   var email = $("#inputEmail").val();
   
   var json  = {};
   
   json['login'] = login;
   json['nome'] = nome;
   json['senha'] = senha;
   json['email'] = email;
   
    $.ajax({
        url: `${url}/rest/usuario/`,
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(json),
        success: function (data) {
            window.location.href = 'login.html';
        }
    });

   
});



$(function () {
 
    var codigo =  localStorage.getItem('usu')
    console.log(codigo)
    console.log(`${url}/index.html`)
    


});