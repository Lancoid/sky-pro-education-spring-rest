SELECT student.name, student.age, faculty.name
FROM student
LEFT JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name, student.age
FROM avatar
LEFT JOIN student ON avatar.student_id = student.id;

