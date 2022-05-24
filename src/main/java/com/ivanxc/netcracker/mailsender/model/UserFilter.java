package com.ivanxc.netcracker.mailsender.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserFilter {
    private final String name;
    private final String surname;
    private final String patronymic;
    private final Integer age;
    private final Integer salary;
    private final String email;
    private final String job;

    public boolean hasParams() {
        return name != null || surname != null || patronymic != null ||
            age != null || salary != null || email != null || job != null;
    }

    @Override
    public String toString() {
        return "UserFilter{" +
            "name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", patronymic='" + patronymic + '\'' +
            ", age=" + age +
            ", salary=" + salary +
            ", email='" + email + '\'' +
            ", job='" + job + '\'' +
            '}';
    }
}
