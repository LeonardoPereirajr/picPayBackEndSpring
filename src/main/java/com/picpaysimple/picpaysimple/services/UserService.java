package com.picpaysimple.picpaysimple.services;

import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.domain.user.UserType;
import com.picpaysimple.picpaysimple.dtos.UserDTO;
import com.picpaysimple.picpaysimple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

public void validateTransaction(User sender, BigDecimal amount) throws Exception {
       if(sender.getUserType() == UserType.MERCHANT ) {
            throw new Exception("Common user can't send more than 100");
        }
       if(sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }
    public void saveUser(User user) {
        this.repository.save(user);
    }


    public List<User> getAllUsers() {
        return this.repository.findAll();
    }
}
