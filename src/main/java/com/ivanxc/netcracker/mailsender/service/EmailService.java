package com.ivanxc.netcracker.mailsender.service;

import com.ivanxc.netcracker.mailsender.service.FileService.UploadResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender emailSender;

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public SendResult sendMessage(String to, String subject, String text) {
        try {
            sendSimpleMessage(to, subject, text);
        } catch (MailException e) {
            return new SendResult(false);
        }
        return new SendResult(true);
    }

    @Getter
    @RequiredArgsConstructor
    public class SendResult {
        private final boolean isSentSuccessfully;
    }
}
