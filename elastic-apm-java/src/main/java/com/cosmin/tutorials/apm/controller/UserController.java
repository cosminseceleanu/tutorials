package com.cosmin.tutorials.apm.controller;

import com.cosmin.tutorials.apm.exception.UserNotFoundException;
import com.cosmin.tutorials.apm.database.User;
import com.cosmin.tutorials.apm.service.UserService;
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
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        User user = userService.get(id).orElseThrow(UserNotFoundException::new);
        userService.delete(user.getId());

        return new ResponseEntity(HttpStatus.OK);
    }
}
