package com.cosmin.tutorials.apm.service;

import co.elastic.apm.api.CaptureSpan;
import com.cosmin.tutorials.apm.database.User;
import com.cosmin.tutorials.apm.database.UserRepository;
import java.util.Optional;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
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

    @SuppressWarnings("checkstyle:MagicNumber")
    @CaptureSpan("otherOperations")
    private void sleep() {
        try {
            Random random = new Random();
            int milis = random.nextInt(100 - 20 + 1) + 20;
            log.info(String.format("Sleep ---> %s ms", milis));
            Thread.sleep(milis);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
