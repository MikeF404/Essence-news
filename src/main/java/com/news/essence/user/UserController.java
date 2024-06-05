package com.news.essence.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save-user-id")
    public String saveUserId(@RequestBody UserDto userDto) {
        User user = userRepository.findByUserId(userDto.getUserId());
        if (user == null) {
            user = new User();
            user.setUserId(userDto.getUserId());
            userRepository.save(user);
        }
        return "User ID saved: " + userDto.getUserId();
    }
}
