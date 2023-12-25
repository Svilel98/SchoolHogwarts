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
import ru.hogwarts.school.contoller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    public StudentController studentController;
    @Autowired
    public TestRestTemplate restTemplate;
    @Autowired
    public StudentRepository studentRepository;

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
        testPostStudent();
        Student testStudent = studentRepository.findById(1l).get();
        ResponseEntity<Student> deleteStudentResponse = restTemplate.exchange(
                "http://localhost:" + port + "/student/1",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Student.class);
        Assertions.assertThat(studentRepository.findById(1L).isEmpty());
    }

    @Test
    void testPostStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Гоша");
        student.setAge(21);
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }
//    @Test
//    public void getListStudentByAge() throws Exception {
//        ResponseEntity<Student> responseStudentAgeBetween = restTemplate.getForEntity("/student?age=" + 20 + "&minAge=" + 30 + "&maxAge"+ 40
//                ,Student.class);
//    }

}
