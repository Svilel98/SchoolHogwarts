package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.contoller.FacultyController;
import ru.hogwarts.school.contoller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestFaculty {
    @LocalServerPort
    private int port;

    @Autowired
    public FacultyController facultyController;
    @Autowired
    public TestRestTemplate restTemplate;
    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    public FacultyRepository facultyRepository;
    @Autowired
    public AvatarRepository avatarRepository;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @AfterEach
    void deleteAll() {
        avatarRepository.deleteAll();
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    private ResponseEntity<Faculty> createFaculty(String name, String color) {
        Faculty request = new Faculty();
        request.setName(name);
        request.setColor(color);
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty", request, Faculty.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        return response;
    }

    @Test
    public void testGetFaculty() throws Exception {
        Faculty testFaculty = new Faculty();
        testFaculty.setId(1L);
        testFaculty.setName("Кот");
        testFaculty.setColor("Зеленый");
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + testFaculty.getId(), String.class))
                .isNotNull();
    }

    @Test
    public void testPutFaculty() throws Exception {
        Faculty testFaculty = new Faculty();
        testFaculty.setId(1L);
        testFaculty.setName("Кот");
        testFaculty.setColor("Зеленый");
        ResponseEntity<Student> updateStudentResponse = restTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(testFaculty),
                Student.class
        );
        Assertions.assertThat(updateStudentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Faculty request = new Faculty();
        request.setId(1L);
        request.setName("Cерафим");
        request.setColor("Зеленый");
        ResponseEntity<Student> deleteStudentResponse = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/1",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Student.class);
        Assertions.assertThat(facultyRepository.findById(1L).isEmpty());
    }

    @Test
    void testPostFaculty() throws Exception {
        Faculty request = new Faculty();
        request.setName("Гоша");
        request.setColor("Зеленый");
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty", request, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(facultyRepository.findById(response.getBody().getId())).isPresent();
    }
    @Test
    void getListFacultyByColor() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setName("griffindor");
        faculty1.setColor("red");
        facultyRepository.save(faculty1);

        Faculty faculty2 = new Faculty();
        faculty2.setName("griffindor2");
        faculty2.setColor("blue");
        facultyRepository.save(faculty2);
        ResponseEntity<Collection> forEntity = restTemplate.getForEntity("/faculty?color=red",
                Collection.class);
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(forEntity.getBody().size()).isEqualTo(1);
    }
    @Test
    void getListFacultyByName() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setName("griffindor");
        faculty1.setColor("red");
        facultyRepository.save(faculty1);

        Faculty faculty2 = new Faculty();
        faculty2.setName("griffindor2");
        faculty2.setColor("blue");
        facultyRepository.save(faculty2);
        ResponseEntity<Collection> forEntity = restTemplate.getForEntity("/faculty?name=griffindor2",
                Collection.class);
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(forEntity.getBody().size()).isEqualTo(1);
    }
}
