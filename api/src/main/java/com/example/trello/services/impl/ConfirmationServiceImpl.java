package com.example.trello.services.impl;

import com.example.trello.exception.NotFoundException;
import com.example.trello.models.ConfirmationEntity;
import com.example.trello.models.UserEntity;
import com.example.trello.repos.ConfirmationRepo;
import com.example.trello.services.ConfirmationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
@Slf4j
public class ConfirmationServiceImpl implements ConfirmationService {
    private ConfirmationRepo confirmationRepo;

    private JavaMailSender javaMailSender;
    public static int noOfQuickServiceThreads = 20;
    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);

    @Async
    public void sendConfirmationEmail(String link, String emailAddress) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailAddress);
        email.setSubject("Complete Registration!");
        email.setFrom("trello-clone-api@gmail.com");
        email.setText("To confirm your account, please click here : "
                +"http://localhost:3000/api/confirmation?link="+link);

        // run in extra thread
        quickService.submit(() -> {
            try{
                javaMailSender.send(email);
            }catch(Exception e){
                log.error("Exception occur while send a mail : ",e);
            }
        });
    }

    @Autowired
    public ConfirmationServiceImpl(ConfirmationRepo confirmationRepo, JavaMailSender javaMailSender) {
        this.confirmationRepo = confirmationRepo;
        this.javaMailSender = javaMailSender;
    }

    public void create(UserEntity user) {
        String link = UUID.randomUUID().toString();

        confirmationRepo.save(
                ConfirmationEntity.builder()
                        .user(user)
                        .link(link)
                        .build()
        );

        this.sendConfirmationEmail(link, user.getEmail());
    }

    public void confirm(String link) {
        ConfirmationEntity confirmationEntity = confirmationRepo.findByLink(link).orElseThrow(() -> new NotFoundException("Link not found"));
        confirmationRepo.delete(confirmationEntity);
    }
    public boolean confirmed(UserEntity user) {
        Optional<ConfirmationEntity> confirmationEntity = confirmationRepo.findByUser(user);
        return !confirmationEntity.isPresent();
    }
}
