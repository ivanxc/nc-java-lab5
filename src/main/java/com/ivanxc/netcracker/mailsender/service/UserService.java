package com.ivanxc.netcracker.mailsender.service;

import static com.ivanxc.netcracker.mailsender.entity.QUser.user;

import com.ivanxc.netcracker.mailsender.dao.UserRepository;
import com.ivanxc.netcracker.mailsender.model.UserFilter;
import com.ivanxc.netcracker.mailsender.entity.User;
import com.ivanxc.netcracker.mailsender.mapper.UserCreateUpdateMapper;
import com.ivanxc.netcracker.mailsender.mapper.UserReadMapper;
import com.ivanxc.netcracker.mailsender.model.UserDto;
import com.ivanxc.netcracker.mailsender.querydsl.QPredicates;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateUpdateMapper userCreateUpdateMapper;

    public List<UserDto> findAll(UserFilter userFilter) {
        Predicate predicate = QPredicates.builder()
            .add(userFilter.getName(), user.name::eq)
            .add(userFilter.getSurname(), user.surname::contains)
            .add(userFilter.getPatronymic(), user.patronymic::contains)
            .add(userFilter.getAge(), user.age::eq)
            .add(userFilter.getSalary(), user.salary::eq)
            .add(userFilter.getEmail(), user.email::eq)
            .add(userFilter.getJob(), user.job::eq)
            .build();

        return ((List<User>)userRepository.findAll(predicate))
            .stream()
            .map(userReadMapper::map)
            .collect(Collectors.toList());
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
            .map(entity -> userReadMapper.map(entity));
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        return Optional.of(userDto)
            .map(userCreateUpdateMapper::map)
            .map(userRepository::save)
            .map(userReadMapper::map)
            .orElseThrow();
    }

    @Transactional
    public Optional<UserDto> update(Long id, UserDto userDto) {
        return userRepository.findById(id)
            .map(entity -> userCreateUpdateMapper.map(userDto, entity))
            .map(userRepository::save) // ДОЛЖНО БЫТЬ SAVE AND FLUSH. Разобраться!
            .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
            .map(entity -> {
                userRepository.delete(entity);
                return true;
            })
            .orElse(false);
    }
}
