package com.picpaysimple.picpaysimple.repositories;

import com.picpaysimple.picpaysimple.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByDocument(String document);
    Optional<User> findById(Long id);
}
