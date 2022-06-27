package ru.hogwarts.school.service.student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.exception.ValidatorException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->create");

        if (studentRepository.existsByNameEqualsIgnoreCaseAndAgeEquals(student.getName(), student.getAge())) {
            throw new ValidatorException("Студент уже добавлен");
        }

        return studentRepository.save(student);
    }

    @Override
    public Student getById(Long id) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getById");

        if (!studentRepository.existsById(id)) {
            throw new NotFoundException("Студент с таким id не найден");
        }

        return studentRepository.getById(id);
    }

    @Override
    public List<Student> getAll() {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getAll");

        List<Student> result = studentRepository.findAll();

        if (result.size() == 0) {
            throw new NotFoundException("Студенты не найдены");
        }

        return result;
    }

    @Override
    public List<Student> getAllByTwoParalleledStreams() {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getAllByTwoParalleledStreams");

        List<Student> result = studentRepository.findAll();

        if (result.size() < 6) {
            throw new NotFoundException("Студенты не найдены");
        }

        Runnable taskFirst = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " : " + result.get(0).getName());
            System.out.println(threadName + " : " + result.get(1).getName());
        };

        Runnable taskSecond = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " : " + result.get(1).getName());
            System.out.println(threadName + " : " + result.get(2).getName());
        };

        Runnable taskThird = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " : " + result.get(4).getName());
            System.out.println(threadName + " : " + result.get(5).getName());
        };

        (new Thread(taskFirst)).start();
        (new Thread(taskSecond)).start();
        (new Thread(taskThird)).start();

        return result;
    }

    @Override
    public List<Student> getAllByTwoSynchronizedStreams() {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getAllByTwoSynchronizedStreams");

        List<Student> result = studentRepository.findAll();

        if (result.size() < 6) {
            throw new NotFoundException("Студенты не найдены");
        }

        Object lock = new Object();

        Runnable taskFirst = () -> {
            synchronized (lock) {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " : " + result.get(0).getName());
                System.out.println(threadName + " : " + result.get(1).getName());
            }
        };

        Runnable taskSecond = () -> {
            synchronized (lock) {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " : " + result.get(1).getName());
                System.out.println(threadName + " : " + result.get(2).getName());
            }
        };

        Runnable taskThird = () -> {
            synchronized (lock) {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " : " + result.get(4).getName());
                System.out.println(threadName + " : " + result.get(5).getName());
            }
        };

        (new Thread(taskFirst)).start();
        (new Thread(taskSecond)).start();
        (new Thread(taskThird)).start();

        return result;
    }


    @Override
    public List<Student> getByAge(int age) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getByAge");

        List<Student> result = studentRepository.findByAgeEquals(age);

        if (result.size() == 0) {
            throw new NotFoundException("Студенты с таким age не найдены");
        }

        return result;
    }

    @Override
    public List<Student> getByAgeBetween(int minAge, int maxAge) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getByAgeBetween");

        if (minAge > maxAge) {
            throw new ValidatorException("Минимальный порог возраста должен быть меньше максимального");
        }

        List<Student> result = studentRepository.findByAgeBetween(minAge, maxAge);

        if (result.size() == 0) {
            throw new NotFoundException("Студенты с таким диапазоном age не найдены");
        }

        return result;
    }

    @Override
    public List<Student> getLastStudents(int count) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getLastStudents");

        if (count < 1) {
            throw new ValidatorException("Количество должно быть больше 1");
        }

        List<Student> result = studentRepository.getLastStudents(count);

        if (result.size() == 0) {
            throw new NotFoundException("Студенты не найдены");
        }

        return result;
    }

    @Override
    public int getStudentsCount() {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getStudentsCount");

        return studentRepository.getStudentsCount();
    }

    @Override
    public int getStudentsAverageAge() {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getStudentsAverageAge");

        return studentRepository.getStudentsAverageAge();
    }

    @Override
    public Student update(Student student) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->update");

        if (!studentRepository.existsById(student.getId())) {
            throw new NotFoundException("Студент с таким id не найден");
        }

        if (studentRepository.existsByNameEqualsIgnoreCaseAndAgeEqualsAndIdNot(student.getName(), student.getAge(), student.getId())) {
            throw new ValidatorException("Студент уже добавлен c другим id");
        }

        return studentRepository.save(student);
    }

    @Override
    public Student delete(Long id) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->delete");

        Student student = getById(id);

        studentRepository.deleteById(id);

        return student;
    }

    @Override
    public Faculty getFacultyById(Long id) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getFacultyById");

        Student student = getById(id);

        return student.getFaculty();
    }

}
