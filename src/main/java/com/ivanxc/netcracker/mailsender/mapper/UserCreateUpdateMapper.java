package com.ivanxc.netcracker.mailsender.mapper;

import com.ivanxc.netcracker.mailsender.entity.User;
import com.ivanxc.netcracker.mailsender.model.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserCreateUpdateMapper implements Mapper<UserDto, User> {

    @Override
    public User map(UserDto from) {
        User user = new User();
        copy(from, user);

        return user;
    }

    @Override
    public User map(UserDto fromUserDto, User toUser) {
        copy(fromUserDto, toUser);

        return toUser;
    }

    private void copy(UserDto from, User user) {
        user.setId(from.getId());
        user.setName(from.getName());
        user.setSurname(from.getSurname());
        user.setPatronymic(from.getPatronymic());
        user.setAge(from.getAge());
        user.setSalary(from.getSalary());
        user.setEmail(from.getEmail());
        user.setJob(from.getJob());
    }
}
