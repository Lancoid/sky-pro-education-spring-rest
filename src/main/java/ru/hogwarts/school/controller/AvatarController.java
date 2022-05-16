package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.avatar.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{studentId}/avatar-from-file-directory")
    @Operation(summary = "getFromDirectory", description = "Get Avatar from directory")
    public void getFromDir(@PathVariable Long studentId, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.getByStudentId(studentId);
        Path path = Path.of(avatar.getFilePath());

        try (
                InputStream inputStream = Files.newInputStream(path);
                OutputStream outputStream = response.getOutputStream()
        ) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(avatar.getMediaType());
            response.setContentLength(Math.toIntExact(avatar.getFileSize()));
            inputStream.transferTo(outputStream);
        }
    }

    @GetMapping(value = "/{studentId}/avatar-from-database")
    @Operation(summary = "getFromDatabase", description = "Get Avatar from database")
    public ResponseEntity<byte[]> getFromDatabase(@PathVariable Long studentId) {
        Avatar avatar = avatarService.getByStudentId(studentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

}
