package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getAmountOfAllStudents();
    @Query(value = "SELECT AVG(s.age) FROM student s", nativeQuery = true)
    Integer getAverageAgeByStudent();
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5 ", nativeQuery = true)
    List<Student> getLastStudent();
    Collection<Student> findByAgeBetween(int minAge, int maxage);
    Student getById(long id);
}
