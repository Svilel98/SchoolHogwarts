package ru.hogwarts.school.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public ResponseEntity<Faculty> postFaculty(@RequestBody Faculty faculty) {
        Faculty facultyNew = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(facultyNew);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Faculty> putFaculty(@RequestBody Faculty faculty) {
        Faculty editFaculty = facultyService.editFaculty(faculty);
        if (editFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editFaculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getListFacultyByColor(@RequestParam(required = false) String color,
                                                                     @RequestParam(required = false) String name) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.filterColor(color));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.filterNameFaculty(name));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("idFaculty")
    public ResponseEntity<Collection<Student>> getFacultyStudent(@RequestParam(required = false) Long idFaculty) {
        if (idFaculty != null) {
            return ResponseEntity.ok(facultyService.findFaculty(idFaculty).getStudents());
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
    @GetMapping("/findAverageAgeByStudent")
    public String findAverageAgeByStudent(){
        return facultyService.findFacultyWithTheLongestName();
    }
    @GetMapping("/getLongNumber")
    public int getLongNumber(){
        return facultyService.getLongNumber();
    }
}
