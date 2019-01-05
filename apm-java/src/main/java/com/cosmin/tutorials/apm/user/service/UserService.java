package com.cosmin.tutorials.apm.user.service;

import com.cosmin.tutorials.apm.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private Map<Integer, User> users;

    public UserService() {
        users = new ConcurrentHashMap<>();
    }

    @PostConstruct
    private void init() {
        users.put(1, new User(1, "User 1"));
        users.put(2, new User(1, "User 2"));
        users.put(3, new User(1, "User 3"));
        users.put(4, new User(1, "User 4"));
    }

    public void save(User user) {
        sleep();
        users.put(user.getId(), user);
    }

    public Optional<User> get(Integer id) {
        sleep();
        return Optional.ofNullable(users.get(id));
    }

    public void delete(Integer id) {
        sleep();
        users.remove(id);
    }

    private void sleep() {
        try {
            Random random = new Random();
            int milis = random.nextInt(1000 - 100 + 1) + 100;
            logger.info(String.format("Sleep ---> %s ms", milis));
            Thread.sleep(milis);
        } catch (Exception e) {
           logger.error(e.getMessage(), e);
        }
    }
}
