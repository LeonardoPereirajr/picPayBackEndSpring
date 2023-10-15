package com.picpaysimple.picpaysimple.services;

import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.domain.user.UserType;
import com.picpaysimple.picpaysimple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

public void valideTransaction(User sender, BigDecimal amount) throws Exception {
       if(sender.getUserType() == UserType.MERCHANT ) {
            throw new Exception("Common user can't send more than 100");
        }
       if(sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance");
        }
    }

    public User finfUserById (Long id) throws Exception {
        return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public void save(User user) {
        this.repository.save(user);
    }
}
