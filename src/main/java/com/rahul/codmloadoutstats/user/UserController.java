package com.rahul.codmloadoutstats.user;

import com.rahul.codmloadoutstats.exception.UserAlreadyExistException;
import com.rahul.codmloadoutstats.exception.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        JSONObject jsonObject = new JSONObject();
        return "This is Hello From RAHUL CODM Mobile LoadOut Companion APP";
    }

    @PostMapping(path = "/user/", consumes = {"application/json"})
    public ResponseEntity<UserDAO> addUser(@RequestBody UserDAO user) {

        log.info("User Data in POST Request is : " + user);

        Optional<UserDAO> byId = userService.findById(user.getUserId());
        if (byId.isPresent())
            throw new UserAlreadyExistException("User Already Exist with UID : " + user.getUserId() + "!!");


        UserDAO byUIgn = userService.findByUserIgn(user.getUserIgn());
        if (byUIgn != null) {
            throw new UserAlreadyExistException("User Already Exist with IGN : " + user.getUserIgn());
        }

        user.setCreateDate(new Date());
        userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getUserId())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserDAO>> getAllUsers() {
        List<UserDAO> all = userService.findAll();
        return ResponseEntity.ok(all);
    }

    @DeleteMapping("/user/{uid}")
    public ResponseEntity<UserDAO> removeUser(@PathVariable long uid) {
        log.info("Looking for User with IGN : " + uid);
        Optional<UserDAO> user = userService.findById(uid);

        if (user.isPresent()) {
            userService.delete(user.get());
        } else {
            throw new UserNotFoundException("No User found with UID : " + uid);
        }
        log.info("User Deleted : " + user);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/user/", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<UserDAO> updateUser(@RequestBody UserDAO user) {

        Optional<UserDAO> byId = userService.findById(user.getUserId());

        if (byId.isEmpty())
            throw new UserNotFoundException("No User found with UID : " + user.getUserId());

        userService.save(user);

        return ResponseEntity.ok(user);
    }

}
