/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luanprojetos.musicapostgrerest.models;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Luan
 */
public class JavaEmail {

    private Session session;
    private String user, password;

    public JavaEmail() {

        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Gmail
         */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        user = "feoluan@gmail.com";
        password = "14010200";
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.password", password);

        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        session.setDebug(true);
    }

    public boolean SendEmail(String destinatario, String codVerificador) {//String Destinatario

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(destinatario);//

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Codigo de Verificação");//Assunto
            message.setText(
                    "Esse é o seu codigo de verificação : " + codVerificador);

            /*FUTURO USO DE HTML NO TEXTO
              message.setText(htmlPedido, "utf-8", "html");
              message.setContent(htmlPedido, "text/html; charset=utf-8"); 
             */
            /**
             * Método para enviar a mensagem criada
             */
            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
