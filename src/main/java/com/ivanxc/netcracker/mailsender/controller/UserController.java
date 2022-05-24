package com.ivanxc.netcracker.mailsender.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanxc.netcracker.mailsender.model.UserFilter;
import com.ivanxc.netcracker.mailsender.model.EmailDto;
import com.ivanxc.netcracker.mailsender.model.UserDto;
import com.ivanxc.netcracker.mailsender.service.UserService;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String findAll(Model model, @ModelAttribute("userFilter") UserFilter userFilter,
        HttpServletRequest httpServletRequest) {
        List<UserDto> users = userService.findAll(userFilter);

        if (users.isEmpty()) {
            return "redirect:/users/not-found";
        }

        model.addAttribute("userFilter", userFilter);
        model.addAttribute("users", users);

        if (userFilter.hasParams()) {
            model.addAttribute("userAgent", httpServletRequest.getHeader("user-agent"));
            model.addAttribute("time", LocalTime.now());
        }

        return "users";
    }

    @GetMapping("/new")
    public String newUser(Model model, @ModelAttribute("newUser") UserDto newUser) {
        model.addAttribute("newUser", newUser);
        return "new-user";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        Optional<UserDto> user = userService.findById(id);
        if (user.isEmpty()) {
            return "redirect:/users/not-found";
        }

        EmailDto emailDto = new EmailDto();
        emailDto.setTo(user.get().getEmail());

        model.addAttribute("user", user.get());
        model.addAttribute("email", emailDto);
        return "user";
    }

    @GetMapping("not-found")
    public String notFound() {
        return "not-found";
    }

    @PostMapping
    public String create(@ModelAttribute("newUser") @Valid UserDto newUser,
        BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newUser", newUser);
            redirectAttributes.addFlashAttribute("userFields", bindingResult);
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/users/new";
        }

        userService.create(newUser);
        return "redirect:/users";
    }

    @PostMapping("/json")
    public String create(@RequestParam("file") MultipartFile file, Model model) {
        if (!file.isEmpty() && "application/json".equals(file.getContentType())) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                String content = new String(file.getInputStream().readAllBytes());
                UserDto user = objectMapper.readValue(content, UserDto.class);
                userService.create(user);
            } catch (IOException exception) {
                model.addAttribute("fileError", true);
                model.addAttribute("newUser", new UserDto());
                return "new-user";
            }
            return "redirect:/users";
        } else {
            model.addAttribute("fileError", true);
            model.addAttribute("newUser", new UserDto());
            return "new-user";
        }
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
        @Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("userFields", bindingResult);
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/users/{id}";
        }

        return userService.update(id, user)
            .map(it -> "redirect:/users/{id}")
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        System.out.println("delete");
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}
