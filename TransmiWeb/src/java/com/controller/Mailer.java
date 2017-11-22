/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.entity.Correo;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Miguel
 */
public class Mailer {
    public boolean enviarCorreo(Correo correo){
        try {
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", correo.getUsuario());
            p.setProperty("mail.smtp.auth", "true");
            
            Session s = Session.getDefaultInstance(p,null);
            BodyPart texto = new MimeBodyPart();
            texto.setText(correo.getMensaje());
            BodyPart adjunto = new MimeBodyPart();
            if (!correo.getRutaArchivo().equals("")) {
                adjunto.setDataHandler(new DataHandler(new FileDataSource(correo.getRutaArchivo())));
                adjunto.setFileName(correo.getArchivo());
            }
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(texto);
            if (!correo.getRutaArchivo().equals("")) {
                m.addBodyPart(adjunto);
            }
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(correo.getUsuario()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correo.getDestino()));
            mensaje.setSubject(correo.getAsunto());
            mensaje.setContent(m);
            
            Transport t = s.getTransport("smtp");
            t.connect(correo.getUsuario(),correo.getContrasena());
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
