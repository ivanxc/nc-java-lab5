package com.ivanxc.netcracker.mailsender.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;

    @Size(min = 2, message = "Имя должно содержать 2 и более символа")
    @NotBlank
    private String name;

    @Size(min = 2, message = "Фамилия должна содержать 2 и более символа")
    @NotBlank
    private String surname;

    @Size(min = 2, message = "Отчество должно содержать 2 и более символа")
    @NotBlank
    private String patronymic;

    @Min(value = 0, message = "Возраст должен быть больше 0")
    @Max(value = 200, message = "Возраст должен быть меньше 200")
    @NotNull
    private Integer age;

    @Min(value = 1, message = "Зарплата должна быть больше 0")
    @NotNull
    private Integer salary;

    @Email(message = "email должен быть в виде example@mail.com")
    @NotBlank
    private String email;

    @NotBlank
    private String job;
}
