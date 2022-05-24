package com.ivanxc.netcracker.mailsender.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    @Email
    private String to;
    @NotBlank
    private String subject;
    @NotBlank
    private String content;
}
