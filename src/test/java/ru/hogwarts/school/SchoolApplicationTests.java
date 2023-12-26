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
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.contoller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class SchoolApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    public StudentController studentController;
    @Autowired
    public TestRestTemplate restTemplate;
    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    public FacultyRepository facultyRepository;
    @Autowired
    public AvatarRepository avatarRepository;

    @AfterEach
    void cleanUp() {
        avatarRepository.deleteAll();
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudent() throws Exception {
        Student testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setName("Гоша");
        testStudent.setAge(21);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student" + testStudent.getId(), String.class))
                .isNotNull();
    }

    @Test
    public void testPutStudent() throws Exception {
        Student testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setName("Cерафим");
        testStudent.setAge(21);
        ResponseEntity<Student> updateStudentResponse = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(testStudent),
                Student.class
        );
        Assertions.assertThat(updateStudentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Student testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setName("Cерафим");
        testStudent.setAge(21);
        ResponseEntity<Student> deleteStudentResponse = restTemplate.exchange(
                "http://localhost:" + port + "/student/1",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Student.class);
        Assertions.assertThat(studentRepository.findById(1L).isEmpty());
    }

    @Test
    void testPostStudent() throws Exception {
        Student request = new Student();
        request.setName("Гоша");
        request.setAge(21);
        ResponseEntity<Student> response = restTemplate.postForEntity("/student", request, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(studentRepository.findById(response.getBody().getId())).isPresent();
    }

    @Test
    void getListStudentByAge() throws Exception {
        Student student1 = new Student();
        student1.setName("griffindor");
        student1.setAge(22);
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setName("griffindor2");
        student2.setAge(10);
        studentRepository.save(student2);
        ResponseEntity<Collection> forEntity = restTemplate.getForEntity("/student?minAge=20&maxAge=30",
                Collection.class);
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(forEntity.getBody().size()).isEqualTo(1);

    }
}

