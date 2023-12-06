package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
@Service
public class FacultyService {
    private HashMap<Integer, Faculty> faculties = new HashMap<>();
    private Integer lastId = 0;

    public Faculty findFaculty(Integer id) {
        return faculties.get(id);
    }

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(lastId++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty editFaculty(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())) {
            return null;
        }
        return faculties.put(faculty.getId(), faculty);
    }

    public Faculty removeFaculty(Integer id) {
        return faculties.remove(id);
    }
    public Collection<Faculty> filterColor(String color) {
        ArrayList<Faculty> listFacultyByColor = new ArrayList<>();
        for (Faculty faculty : listFacultyByColor) {
            if (faculty.getColor() == color) {
                listFacultyByColor.add(faculty);
            }
        }
        return listFacultyByColor;
    }

}
