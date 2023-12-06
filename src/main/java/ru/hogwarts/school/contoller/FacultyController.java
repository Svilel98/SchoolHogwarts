package ru.hogwarts.school.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable("id") Integer id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty postFaculty(Faculty faculty) {
        return facultyService.createFaculty(faculty);

    }
    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable("id") Integer id){
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<Faculty> putFaculty(Faculty faculty){
        Faculty editFaculty = facultyService.editFaculty(faculty);
        return ResponseEntity.ok(editFaculty);
    }
    @GetMapping("{color}")
    public ResponseEntity<Collection<Faculty>> getListFacultyByColor(@PathVariable(required = false) String color) {
        if (color != null && !color.isBlank()){
            return ResponseEntity.ok(facultyService.filterColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
