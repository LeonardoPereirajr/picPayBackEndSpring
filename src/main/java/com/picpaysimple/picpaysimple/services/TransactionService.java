package com.picpaysimple.picpaysimple.services;

import com.picpaysimple.picpaysimple.domain.transaction.Transaction;
import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.dtos.TransactionDTO;
import com.picpaysimple.picpaysimple.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public void createTransaction(TransactionDTO transaction) throws Exception {
      User sender = this.userService.finfUserById(transaction.senderId());
      User receiver = this.userService.finfUserById(transaction.receiverId());

      userService.valideTransaction(sender, transaction.value());

      boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if (!this.authorizeTransaction(sender, transaction.value())) {
             throw new Exception("Não foi possível autorizar a transação");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setAmount(transaction.value());
        newTransaction.setTimestamp(String.valueOf(LocalDateTime.now()));

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.userService.save(sender);
        this.userService.save(receiver);

    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {

        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationResponse.getBody().get("message");
            return message.equals("Autorizado");
        } else return false;
    }
}
