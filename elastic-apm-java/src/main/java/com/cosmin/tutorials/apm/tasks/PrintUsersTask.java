package com.cosmin.tutorials.apm.tasks;

import co.elastic.apm.api.CaptureSpan;
import co.elastic.apm.api.CaptureTransaction;
import com.cosmin.tutorials.apm.database.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PrintUsersTask {

    private final static Logger logger = LoggerFactory.getLogger(PrintUsersTask.class);
    private UserRepository userRepository;

    @Autowired
    public PrintUsersTask(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(fixedDelayString = "5000")
    public void execute() {
        logger.info("run scheduled test");
        doExecute();
    }

    @CaptureTransaction(type = "Task", value = "PrintUsers")
    private void doExecute() {
        userRepository.findAll().forEach(user-> logger.debug(user.getEmail()));
        sleep();
    }

    @CaptureSpan("someCustomOperation")
    private void sleep() {
        try {
            Random random = new Random();
            int milis = random.nextInt(120 - 20 + 1) + 20;
            Thread.sleep(milis);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
