package com.ivanxc.netcracker.mailsender.mapper;

import com.ivanxc.netcracker.mailsender.entity.User;
import com.ivanxc.netcracker.mailsender.model.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto map(User from) {
        return new UserDto(
            from.getId(),
            from.getName(),
            from.getSurname(),
            from.getPatronymic(),
            from.getAge(),
            from.getSalary(),
            from.getEmail(),
            from.getJob()
        );
    }
}
