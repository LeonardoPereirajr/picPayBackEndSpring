package com.picpaysimple.picpaysimple.services;

import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        ResponseEntity<String> notificationResponse = restTemplate.getForEntity("http://o4d9z.mocklab.io/notify", String.class);

        if (!(notificationResponse.getStatusCode() == HttpStatus.OK)){
           System.out.println("Não foi possível enviar a notificação");
           throw new Exception("Não foi possível enviar a notificação");
        }
    }

}
