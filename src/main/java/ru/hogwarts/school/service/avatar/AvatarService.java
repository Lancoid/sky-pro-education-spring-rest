package ru.hogwarts.school.service.avatar;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar getByStudentId(Long studentId);

    List<Avatar> findAll(int page, int limit);

}
