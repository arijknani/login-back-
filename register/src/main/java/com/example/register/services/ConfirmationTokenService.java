package com.example.register.services;

import com.example.register.models.ConfirmationToken;
import com.example.register.models.User;
import com.example.register.repositories.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {


    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;




    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }



    public  String createAndSaveTokenForUser(User user, String token){

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenRepository.save(confirmationToken);

        return token;
    }
}


