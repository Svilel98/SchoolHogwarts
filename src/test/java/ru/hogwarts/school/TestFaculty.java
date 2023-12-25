package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
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
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestFaculty {
    @LocalServerPort
    private int port;

    @Autowired
    public FacultyController facultyController;
    @Autowired
    public TestRestTemplate restTemplate;
    @Autowired
    public FacultyRepository facultyRepository;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
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
        testPostFaculty();
        Faculty testFaculty = facultyRepository.findById(1l).get();
        ResponseEntity<Student> deleteStudentResponse = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/1",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Student.class);
        Assertions.assertThat(facultyRepository.findById(1L).isEmpty());
    }

    @Test
    void testPostFaculty() throws Exception {
        Faculty testFaculty = new Faculty();
        testFaculty.setId(1L);
        testFaculty.setName("Кот");
        testFaculty.setColor("Зеленый");
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", testFaculty, String.class))
                .isNotNull();
    }

//    @Test
//    public void getListStudentByFaculty() {
//        ResponseEntity<Faculty> response = createFaculty("Кот", "Красный");
//        Student student = new Student();
//        Faculty expectedFaculty = response.getBody();
//        student.setFaculty(expectedFaculty);
//        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity("/student/studentByFaculty/1", student, Student.class);
//        long studentId = studentResponseEntity.getBody().getId();
//        response = restTemplate.getForEntity("/faculty/by-student?studentId=" + studentId, Faculty.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(response.getBody()).isNotNull();
//        Assertions.assertThat(response.getBody()).isEqualTo(expectedFaculty);
//    }
}
