package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filterColor(String color) {
        ArrayList<Faculty> listFacultyByColor = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            if (Objects.equals(faculty.getColor(), color)) {
                listFacultyByColor.add(faculty);
            }
        }
        return listFacultyByColor;
    }

    public Collection<Faculty> filterNameFaculty(String name) {
        ArrayList<Faculty> listFacultyByName = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            if (Objects.equals(faculty.getName(), name)) {
                listFacultyByName.add(faculty);
            }
        }
        return listFacultyByName;
    }
}
