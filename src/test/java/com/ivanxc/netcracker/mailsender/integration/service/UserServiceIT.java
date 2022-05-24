package com.ivanxc.netcracker.mailsender.integration.service;

import com.ivanxc.netcracker.mailsender.model.UserDto;
import com.ivanxc.netcracker.mailsender.service.UserService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceIT {

    private static final UserDto TEST_USER =
        new UserDto(1, "Ivan", "Ivanov", "Ivanovich",
            21, 100000, "a@mail.ru", "NetCracker");

    private static final List<UserDto> usersInDB = List.of(TEST_USER);

    @Autowired
    private UserService userService;

    @Test
    void saveUser() {
    }
}