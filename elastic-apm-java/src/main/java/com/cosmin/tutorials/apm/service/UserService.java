package com.cosmin.tutorials.apm.service;

import co.elastic.apm.api.CaptureSpan;
import com.cosmin.tutorials.apm.database.User;
import com.cosmin.tutorials.apm.database.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User save(User user) {
        sleep();
        return userRepository.save(user);
    }

    public Optional<User> get(Integer id) {
        sleep();
        return userRepository.findById(id);
    }

    public void delete(Integer id) {
        sleep();
        userRepository.deleteById(id);
    }

    @CaptureSpan("otherOperations")
    private void sleep() {
        try {
            Random random = new Random();
            int milis = random.nextInt(100 - 20 + 1) + 20;
            logger.info(String.format("Sleep ---> %s ms", milis));
            Thread.sleep(milis);
        } catch (Exception e) {
           logger.error(e.getMessage(), e);
        }
    }
}
