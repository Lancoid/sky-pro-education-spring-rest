package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.hogwarts.school.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    boolean existsByStudentId(@NonNull Long studentId);

    Avatar findByStudentId(@NonNull Long studentId);

}
