package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.hogwarts.school.contoller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SchoolApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private AvatarService avatarService;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private StudentController studentController;

    @Test
    public void saveStudentTest() throws Exception {
        final String name = "Andrey";
        final Integer age = 24;
        final long id = 1;
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student testStudent = new Student();
        testStudent.setId(id);
        testStudent.setName(name);
        testStudent.setAge(age);
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(testStudent));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(objectMapper.writeValueAsString(testStudent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .content(objectMapper.writeValueAsString(testStudent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/")
                        .queryParam("min", "10")
                        .queryParam("max", "30")
                        .content(objectMapper.writeValueAsString(testStudent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
