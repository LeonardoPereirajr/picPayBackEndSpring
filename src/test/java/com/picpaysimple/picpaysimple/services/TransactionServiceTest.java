package com.picpaysimple.picpaysimple.services;

import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.domain.user.UserType;
import com.picpaysimple.picpaysimple.dtos.TransactionDTO;
import com.picpaysimple.picpaysimple.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository repository;

    @Mock
    private AuthorizationService authService;

    @Mock
    private NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create a transaction")
    void createTransactionCase1() throws Exception {
        User sender = new User(1L, "Leonardo", "Pereira","99999999999","leonardo@com.br",
                "1234567890L", new BigDecimal(1000), UserType.CONSUMER);
        User receiver = new User(2L, "Joao", "Pereira","99999998899","joao@com.br",
                "fsdfsd324", new BigDecimal(2000), UserType.CONSUMER);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(100), 1L, 2L);
        transactionService.createTransaction(request);

        verify(repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(900));
        verify(userService, times(1)).saveUser(sender);

        receiver.setBalance(new BigDecimal(2100));
        verify(userService, times(1)).saveUser(receiver);

        verify(notificationService, times(1)).sendNotification(sender, "Transação realizada com sucesso");
        verify(notificationService, times(1)).sendNotification(receiver, "Transação recebida com sucesso");
        assertEquals(new BigDecimal(900), sender.getBalance());
    }

    @Test
    @DisplayName("Should throw an exception when the transaction is not authorized")
    void createTransactionCase2() {
        User sender = new User(1L, "Leonardo", "Pereira","99999999999","leonardo@com.br",
                "1234567890L", new BigDecimal(1000), UserType.CONSUMER);
        User receiver = new User(2L, "Joao", "Pereira","99999998899","joao@com.br",
                "fsdfsd324", new BigDecimal(2000), UserType.CONSUMER);



    }
}
