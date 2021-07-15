package br.com.cwi.apus.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SenderMailService {

    @Autowired
    private JavaMailSender mailSender;

    public  void enviar() {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("joao@teste.com.br");
        email.setSubject("Pedido 555555");
        email.setText("Seu pedido 555555 foi Criado com sucesso.");
        mailSender.send(email);
    }
}
