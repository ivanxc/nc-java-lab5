package com.ivanxc.netcracker.mailsender.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanxc.netcracker.mailsender.model.UserDto;
import java.io.IOException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FileService {

    private final UserService userService;

    public UploadResult upload(MultipartFile file) {
        try {
            tryUpload(file);
        } catch (IOException exception) {
            return new UploadResult(false);
        }
        return new UploadResult(true);
    }

    private void tryUpload(MultipartFile file) throws IOException {
        if (!file.isEmpty() && "application/json".equals(file.getContentType())) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String content = new String(file.getInputStream().readAllBytes());
            UserDto user = objectMapper.readValue(content, UserDto.class);
            userService.create(user);
        } else {
            throw new FileUploadException("Format is not supported");
        }
    }

    @Getter
    @RequiredArgsConstructor
    public class UploadResult {
        private final boolean isUploadedSuccessfully;
    }
}
