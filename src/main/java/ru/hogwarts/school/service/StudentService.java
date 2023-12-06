package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;

@Service
public class StudentService {
    private HashMap<Integer, Student> students = new HashMap<>();
    private Integer lastId = 0;
    private List<Student> listStudents;

    public Student findStudent(Integer id) {
        return students.get(id);
    }

    public Student createStudent(Student student) {
        student.setId(lastId++);
        students.put(lastId, student);
        return student;
    }

    public Student editStudent(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    public Student removeStudent(Integer id) {
        return students.remove(id);
    }

    public Collection<Student> filterAge(Integer age) {
        ArrayList<Student> listStudentByAge = new ArrayList<>();
        for (Student student : listStudentByAge) {
            if (student.getAge() > age) {
                listStudents.add(student);
            }
        }
        return listStudentByAge;
    }
}
