package com.cosmin.tutorials.apm.user.controller;

import com.cosmin.tutorials.apm.user.exception.UserNotFoundException;
import com.cosmin.tutorials.apm.user.model.User;
import com.cosmin.tutorials.apm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") Integer id) {
        return userService.get(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody User user) {
        userService.save(user);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        User user = userService.get(id).orElseThrow(UserNotFoundException::new);
        userService.delete(user.getId());

        return new ResponseEntity(HttpStatus.OK);
    }
}
