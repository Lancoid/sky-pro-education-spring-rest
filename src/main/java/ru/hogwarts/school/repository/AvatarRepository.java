package ru.hogwarts.school.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import ru.hogwarts.school.model.Avatar;

public interface AvatarRepository extends PagingAndSortingRepository<Avatar, Long> {

    boolean existsByStudentId(@NonNull Long studentId);

    Avatar findByStudentId(@NonNull Long studentId);

}
