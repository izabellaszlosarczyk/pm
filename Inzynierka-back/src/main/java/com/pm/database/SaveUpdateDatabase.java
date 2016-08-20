package com.pm.database;

import com.pm.model.User;
import com.pm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by izabella on 15.08.16.
 */
@Service
public class SaveUpdateDatabase {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    // user
}
