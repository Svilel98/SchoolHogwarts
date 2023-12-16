package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student createStudent(Student student) {
       return studentRepository.save(student);
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void removeStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> filterAge(Integer age) {
        ArrayList<Student> listStudentByAge = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getAge() == age) {
                listStudentByAge.add(student);
            }
        }
        return listStudentByAge;
    }
}
