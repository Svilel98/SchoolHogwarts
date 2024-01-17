package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method for id faculty {}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty {}", faculty);
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(Long id) {
        logger.info("Was invoked method for remove faculty {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filterColor(String color) {
        logger.info("Was invoked method for filter faculty by color {}", color);
        ArrayList<Faculty> listFacultyByColor = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            if (Objects.equals(faculty.getColor(), color)) {
                listFacultyByColor.add(faculty);
            }
        }
        return listFacultyByColor;
    }

    public Collection<Faculty> filterNameFaculty(String name) {
        logger.info("Was invoked method for filter faculty by name {}", name);
        ArrayList<Faculty> listFacultyByName = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            if (Objects.equals(faculty.getName(), name)) {
                listFacultyByName.add(faculty);
            }
        }
        return listFacultyByName;
    }
}
