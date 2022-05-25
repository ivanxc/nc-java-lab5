package com.ivanxc.netcracker.mailsender.controller;

import com.ivanxc.netcracker.mailsender.model.EmailDto;
import com.ivanxc.netcracker.mailsender.service.EmailService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Data
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@ModelAttribute("email") @Valid EmailDto emailDto,
        BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model,
        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("emailFields", bindingResult);
            String[] linkPieces = request.getHeader("Referer").split("/");
            return "redirect:/users/" + linkPieces[linkPieces.length - 1];
        }

        boolean isSent = emailService.sendMessage(emailDto.getTo(), emailDto.getSubject(),
            emailDto.getContent()).isSentSuccessfully();
        model.addAttribute("isSent", isSent);

        return "email-result";
    }
}
