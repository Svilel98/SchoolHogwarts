package ru.hogwarts.school.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") Integer id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student postStudent(Student student) {
        return studentService.createStudent(student);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable("id") Integer id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Student> putStudent(Student student) {
        Student editStudent = studentService.editStudent(student);
        return ResponseEntity.ok(editStudent);
    }

    @GetMapping("{age}")
    public ResponseEntity<Collection<Student>> getListStudentByAge(@PathVariable(required = false) Integer age) {
        if (age > 0){
            return ResponseEntity.ok(studentService.filterAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}

