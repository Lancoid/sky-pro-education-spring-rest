package ru.hogwarts.school.service.avatar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->uploadAvatar");

        Student student = studentRepository.getById(studentId);
        String extension = getExtensions(Objects.requireNonNull(avatarFile.getOriginalFilename()));
        Path filePath = Path.of(avatarsDir, student + "." + extension);

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                BufferedInputStream bis = new BufferedInputStream(avatarFile.getInputStream(), 1024);
                BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(filePath, CREATE_NEW), 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId);

        if (avatar == null) {
            avatar = new Avatar();
        }

        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepository.save(avatar);
    }

    @Override
    public Avatar getByStudentId(Long studentId) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getByStudentId");

        if (!avatarRepository.existsByStudentId(studentId)) {
            throw new NotFoundException("Аватар с таким studentId не найден");
        }

        return avatarRepository.findByStudentId(studentId);
    }

    @Override
    public List<Avatar> findAll(int page, int limit) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->findAll");

        return avatarRepository.findAll(PageRequest.of(page, limit)).getContent();
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
