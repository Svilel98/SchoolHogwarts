package ru.hogwarts.school.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;


@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student postStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> putStudent(@RequestBody Student student) {
        Student editStudent = studentService.editStudent(student);
        if (editStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Integer id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getListStudentByAge(@RequestParam(required = false) Integer age,
                                                                   @RequestParam(required = false) Integer minAge,
                                                                   @RequestParam(required = false) Integer maxAge) {
        if (age != null) {
            return ResponseEntity.ok(studentService.filterAge(age));
        }
        if (minAge > 0 && maxAge > minAge) {
            return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/studentByFaculty/{idStudent}")
    public ResponseEntity<Faculty> getListStudentByFaculty(@PathVariable Integer idStudent) {
        Student student = studentService.findStudent(idStudent);
        Faculty faculty = student.getFaculty();
        ResponseEntity<Faculty> ok = ResponseEntity.ok(faculty);
        return ok;
    }
}

