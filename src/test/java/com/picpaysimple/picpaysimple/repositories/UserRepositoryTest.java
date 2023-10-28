package com.picpaysimple.picpaysimple.repositories;

import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.domain.user.UserType;
import com.picpaysimple.picpaysimple.dtos.UserDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return true when document exists")
    void findUserByDocumentSucess() {
        String document = "12345678900";
        UserDTO data = new UserDTO("Leonardo", "Pereira", document, new BigDecimal("1000.00"), "leonardo@teste.com.br", "123456", UserType.CONSUMER);
        this.createUser(data);
        Optional<User> result = this.userRepository.findUserByDocument(document);
            assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not return true when document exists")
    void findUserByDocumentCaseNotTrue() {
        String document = "12345678900";
        Optional<User> result = this.userRepository.findUserByDocument(document);
        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserDTO data){
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}